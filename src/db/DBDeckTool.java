package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.cardModels.Card;
import models.cardModels.Format;
import models.deckModels.Deck;
import util.MTGHelper;

/**
 * A tool that handles simple deck based operations for the application. This includes the saving, modification,
 * lookup, and deletion of existing decks within the DB
 * @author Mitchell
 *
 */
public class DBDeckTool extends DBTool {

  private final static String DECK_CREATE_STRING = "CREATE TABLE IF NOT EXISTS DECKS_TABLE (CREATING_USER varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                       + "DECK_NAME varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                       + "DECK_FORMAT varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                       + "DECK_DESCRIPTION varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                       + "DECK_ARCHETYPE varchar (" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                       + "PRIMARY KEY (CREATING_USER, DECK_NAME));";
  
  private final static String DECK_JUNC_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS DECKS_JUNC_TABLE (CREATING_USER varchar(" + DB_CHAR_COLUMN_LIMIT + "), "
                                                                                                  + "DECK_NAME varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                                  + "CARD_ID varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                                  + "CARD_QUANTITY integer,"
                                                                                                  + "FOREIGN KEY(CREATING_USER) REFERENCES DECKS_TABLE(CREATING_USER) ON DELETE CASCADE,"
                                                                                                  + "FOREIGN KEY(DECK_NAME) REFERENCES DECKS_TABLE(DECK_NAME) ON DELETE CASCADE,"
                                                                                                  + "FOREIGN KEY(CARD_ID) REFERENCES CARD_TABLE(CARD_ID),"
                                                                                                  + "PRIMARY KEY(CREATING_USER, DECK_NAME, CARD_ID));";
  
  private final static String SELECT_ALL_FROM_DECKS = "SELECT * FROM DECKS_TABLE;";
  private final static String INSERT_INTO_DECK_TABLE = "INSERT INTO DECKS_TABLE (CREATING_USER, DECK_NAME, DECK_FORMAT, DECK_DESCRIPTION, DECK_ARCHETYPE) VALUES (?,?,?,?,?)";
  private final static String INSERT_INTO_JUNC_TABLE = "INSERT INTO DECKS_JUNC_TABLE (CREATING_USER, DECK_NAME, CARD_ID, CARD_QUANTITY) VALUES (?,?,?,?)";
  private final static String DELETE_FROM_DECKS_TABLE = "DELETE FROM DECKS_TABLE WHERE CREATING_USER = ? AND DECK_NAME = ?;";
  private final static String DELETE_ALL_DECKS = "DELETE FROM DECKS_TABLE";
      
  // Default Constructor For The Object
  protected DBDeckTool(DBPersistanceController parentController) {
    super(parentController);
  }

  // Adds The Deck Into The Database When Called
  public void addDeckToDB(Deck incomingDeck) {
    try (PreparedStatement st = parentController.getStatement(INSERT_INTO_DECK_TABLE);) {
      st.setString(1, incomingDeck.getCreatingUser());
      st.setString(2, incomingDeck.getDeckName());
      st.setString(3, incomingDeck.getDeckFormat().name());
      st.setString(4, incomingDeck.getDeckDescription());
      st.setString(5, incomingDeck.getDeckArchetype());
      st.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  // Gathers All Cards For The Supplied Deck
  private void gatherAllCardsForDeck(Deck incomingDeck) {
    for (Card c : incomingDeck.getCardsWithinDeck().keySet()) {
      Integer quanityValue = incomingDeck.getCardsWithinDeck().get(c);
      try (PreparedStatement st2 = parentController.getStatement(INSERT_INTO_JUNC_TABLE);) {
        st2.setString(1, incomingDeck.getCreatingUser());
        st2.setString(2, incomingDeck.getDeckName());
        st2.setString(3, MTGHelper.generateCardKey(c));
        st2.setInt(quanityValue, quanityValue.intValue());
        st2.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


  // Method For Collecting A Deck From The Database
  public Deck getDeckFromDB (ResultSet rs) throws SQLException {
    Deck returnVal = new Deck();
    returnVal.setCreatingUser(rs.getString("CREATING_USER"));
    returnVal.setDeckName(rs.getString("DECK_NAME"));
    returnVal.setDeckDescription(rs.getString("DECK_DESCRIPTION"));
    returnVal.setDeckFormat(Format.valueOf(rs.getString("DECK_FORMAT")));
    returnVal.setDeckArchetype(rs.getString("DECK_ARCHETYPE"));
    return returnVal;
  }
  
  // Returns All Decks Present Within The DB
  public List<Deck> getAllDecksFromDB () {
    List<Deck> returnVal = new ArrayList<Deck>();
    try (PreparedStatement st = parentController.getStatement(SELECT_ALL_FROM_DECKS);) {
      ResultSet rs = st.executeQuery(); 
      while (rs.next()) {
        Deck newDeck = getDeckFromDB(rs);
        returnVal.add(newDeck);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnVal;
  }
  
  // Deletes A Given Deck From The DB
  public void removeDeckFromDB(Deck incomingDeck) {
    try (PreparedStatement st = parentController.getStatement(DELETE_FROM_DECKS_TABLE);) {
      st.setString(1, incomingDeck.getCreatingUser());
      st.setString(2, incomingDeck.getDeckName());
      st.execute();
    } catch (SQLException e) {}
  }

  // Deletes All Decks From The DB
  public void deleteAllDecksFromDB () {
    try (PreparedStatement st = parentController.getStatement(DELETE_ALL_DECKS);) {
      st.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  @Override
  public List<String> getDBCreationStrings() {
    List<String> returnVal = new ArrayList<>();
    returnVal.add(DECK_CREATE_STRING);
    returnVal.add(DECK_JUNC_TABLE_CREATE);
    return returnVal;
  }

}
