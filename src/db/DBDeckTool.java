package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.cardModels.Card;
import models.cardModels.Format;
import models.deckModels.Deck;
import models.deckModels.DeckCardDataObject;

/**
 * A tool that handles simple deck based operations for the application. This includes the saving, modification, lookup,
 * and deletion of existing decks within the DB
 * 
 * @author Mitchell
 * 
 */
public class DBDeckTool extends DBTool {

  private final static String DECK_CREATE_STRING = "CREATE TABLE IF NOT EXISTS DECKS_TABLE (CREATING_USER varchar(" + DB_CHAR_COLUMN_LIMIT + ")," +
                                                                                           "DECK_NAME varchar(" + DB_CHAR_COLUMN_LIMIT + ")," + 
                                                                                           "DECK_FORMAT varchar(" + DB_CHAR_COLUMN_LIMIT + ")," + 
                                                                                           "DECK_DESCRIPTION varchar(" + DB_CHAR_COLUMN_LIMIT + ")," +
                                                                                           "DECK_ARCHETYPE varchar (" + DB_CHAR_COLUMN_LIMIT + ")," + 
                                                                                           "PRIMARY KEY (CREATING_USER, DECK_NAME));";

  private final static String DECK_JUNC_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS DECKS_JUNC_TABLE (CREATING_USER varchar(" + DB_CHAR_COLUMN_LIMIT + "), " +
                                                                                                    "DECK_NAME varchar(" + DB_CHAR_COLUMN_LIMIT + ")," + 
                                                                                                    "CARD_ID varchar(" + DB_CHAR_COLUMN_LIMIT + ")," +
                                                                                                    "CARD_QUANTITY integer," +
                                                                                                    "FOREIGN KEY(CREATING_USER) REFERENCES DECKS_TABLE(CREATING_USER) ON DELETE CASCADE," +
                                                                                                    "FOREIGN KEY(DECK_NAME) REFERENCES DECKS_TABLE(DECK_NAME) ON DELETE CASCADE," +
                                                                                                    "FOREIGN KEY(CARD_ID) REFERENCES CARD_TABLE(CARD_ID)," +
                                                                                                    "PRIMARY KEY(CREATING_USER, DECK_NAME, CARD_ID));";

  private final static String SELECT_ALL_FROM_DECKS = "SELECT * FROM DECKS_TABLE;";
  private final static String SELECT_ALL_FROM_DECKS_BY_FORMAT = "SELECT * FROM DECKS_TABLE WHERE DECK_FORMAT = ?;";
  private final static String SELECT_ALL_FROM_DECKS_INDIVIDUAL = "SELECT * FROM DECKS_TABLE WHERE DECK_NAME = ? AND CREATING_USER = ?;";
  private final static String SELECT_FROM_DECKS_JUNC_INDIVIDUAL = "SELECT * FROM DECKS_JUNC_TABLE NATURAL JOIN CARD_TABLE NATURAL JOIN SET_JUNC_TABLE WHERE DECK_NAME = ? AND CREATING_USER = ?;";
  private final static String INSERT_INTO_DECK_TABLE = "INSERT INTO DECKS_TABLE (CREATING_USER, DECK_NAME, DECK_FORMAT, DECK_DESCRIPTION, DECK_ARCHETYPE) VALUES (?,?,?,?,?)";
  private final static String INSERT_INTO_JUNC_TABLE = "INSERT INTO DECKS_JUNC_TABLE (CREATING_USER, DECK_NAME, CARD_ID, CARD_QUANTITY) VALUES (?,?,?,?)";
  
  private final static String DELETE_ALL_DECKS = "DELETE FROM DECKS_TABLE";
  private final static String DELETE_ALL_DECKS_JUNC = "DELETE FROM DECKS_JUNC_TABLE";
  private final static String DELETE_FROM_DECKS_TABLE = "DELETE FROM DECKS_TABLE WHERE CREATING_USER = ? AND DECK_NAME = ?;";
  private final static String DELETE_FROM_DECKS_JUNC_TABLE = "DELETE FROM DECKS_JUNC_TABLE WHERE CREATING_USER = ? AND DECK_NAME = ?;";

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
    addDeckCardsToDB(incomingDeck);
  }

  // Gathers All Cards For The Supplied Deck
  private void addDeckCardsToDB(Deck incomingDeck) {
    for (String c : incomingDeck.getCardsWithinDeck().keySet()) {
      DeckCardDataObject deckCard = incomingDeck.getCardsWithinDeck().get(c);
      try (PreparedStatement st2 = parentController.getStatement(INSERT_INTO_JUNC_TABLE);) {
        st2.setString(1, incomingDeck.getCreatingUser());
        st2.setString(2, incomingDeck.getDeckName());
        st2.setString(3, c);
        st2.setInt(4, deckCard.getQuantityOfCard());
        st2.execute();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  // Method For Collecting A Deck From The Database. Returns Decks With Their Content
  private Deck getDeckFromDBWithContent(ResultSet rs) throws SQLException {
    Deck returnVal = getDeckWithoutContent(rs);
    getDeckContentsFromDB(returnVal);
    return returnVal;
  }
  
  // Method For Collecting Just Deck Details From The DB
  private Deck getDeckFromDBWithOutContent(ResultSet rs) throws SQLException {
    Deck returnVal = getDeckWithoutContent(rs);
    return returnVal;
  }

  // Small Method That Collects Data For A Given Deck
  private Deck getDeckWithoutContent(ResultSet rs) throws SQLException {
    Deck returnVal = new Deck();
    returnVal.setCreatingUser(rs.getString("CREATING_USER"));
    returnVal.setDeckName(rs.getString("DECK_NAME"));
    returnVal.setDeckDescription(rs.getString("DECK_DESCRIPTION"));
    returnVal.setDeckFormat(Format.valueOf(rs.getString("DECK_FORMAT")));
    returnVal.setDeckArchetype(rs.getString("DECK_ARCHETYPE"));
    return returnVal;
  }
  
  // Collects The Deck's Contents From The DB When Called
  public void getDeckContentsFromDB (Deck incomingDeck) {
    try (PreparedStatement st = parentController.getStatement(SELECT_FROM_DECKS_JUNC_INDIVIDUAL);) {
      st.setString(1, incomingDeck.getDeckName());
      st.setString(2, incomingDeck.getCreatingUser());
      ResultSet rs = st.executeQuery();
      
      while (rs.next()) {
        Card nextCard = getCardFromResultSet(rs);
        incomingDeck.addCardToDeck(nextCard, rs.getInt("CARD_QUANTITY"));
      }
    } catch (SQLException e) {
    }
  }

  // Returns All Decks Present Within The DB
  public List<Deck> getAllDecksFromDB() {
    List<Deck> returnVal = new ArrayList<Deck>();
    try (PreparedStatement st = parentController.getStatement(SELECT_ALL_FROM_DECKS);) {
      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        Deck newDeck = getDeckFromDBWithContent(rs);
        returnVal.add(newDeck);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnVal;
  }

  // Returns All Decks Present Within The DB
  public List<Deck> getDecksByFormatNoContent(Format formatToSearch) {
    List<Deck> returnVal = new ArrayList<Deck>();
    try (PreparedStatement st = parentController.getStatement(SELECT_ALL_FROM_DECKS_BY_FORMAT);) {
      st.setString(1, formatToSearch.name());
      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        Deck newDeck = getDeckFromDBWithOutContent(rs);
        returnVal.add(newDeck);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnVal;
  }

  // Returns All Decks Present Within The DB
  public Deck getIndividualDeck(String creatingUser, String deckName) {
    try (PreparedStatement st = parentController.getStatement(SELECT_ALL_FROM_DECKS_INDIVIDUAL);) {
      st.setString(1, deckName);
      st.setString(2, creatingUser);
      ResultSet rs = st.executeQuery();
      rs.next();
      return getDeckFromDBWithContent(rs);
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  // Deletes A Given Deck From The DB
  public void deleteDeckFromDB(Deck incomingDeck) {
    deleteDeckFromDB(incomingDeck.getCreatingUser(), incomingDeck.getDeckName());
  }

  public void deleteDeckFromDB(String creatingUser, String deckName) {
    deleteFromTable(DELETE_FROM_DECKS_TABLE, creatingUser, deckName);
    deleteFromTable(DELETE_FROM_DECKS_JUNC_TABLE, creatingUser, deckName);
  }
  
  private void deleteFromTable(String command, String creatingUser, String deckName) {
    try (PreparedStatement st = parentController.getStatement(command);) {
      st.setString(1, creatingUser);
      st.setString(2, deckName);
      st.execute();
    } catch (SQLException e) {}
  }

  // Deletes All Decks From The DB
  public void deleteAllDecksFromDB() {
    try (PreparedStatement st = parentController.getStatement(DELETE_ALL_DECKS);) {
      st.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    
    try (PreparedStatement st = parentController.getStatement(DELETE_ALL_DECKS_JUNC);) {
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
