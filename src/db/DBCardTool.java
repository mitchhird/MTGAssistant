package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import models.cardModels.Card;
import util.MTGHelper;
import util.JSONConvertTools.JSONCard;

/**
 * A simple tool that is responsible for managing database operations that relate to individual cards themselves
 * @author Mitchell
 */
public class DBCardTool extends DBTool {

  // Table Creation String
  private final String CREATE_CARD_TABLE = "CREATE TABLE IF NOT EXISTS CARD_TABLE (CARD_ID varchar(" + DB_CHAR_COLUMN_LIMIT + ") PRIMARY KEY," 
                                                                               + "CARD_NAME varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_TEXT varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_TYPE varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_SUPERTYPES varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_SUBTYPES varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_POWER INTEGER,"
                                                                               + "CARD_TOUGHNESS INTEGER,"
                                                                               + "CARD_LOYALITY INTEGER)";

  // Insertion String
  private final String INSERT_CARD_TABLE = "INSERT INTO CARD_TABLE (CARD_ID, CARD_NAME, CARD_TEXT, CARD_TYPE, CARD_SUPERTYPES, CARD_SUBTYPES, CARD_POWER, CARD_TOUGHNESS, CARD_LOYALITY) VALUES (?,?,?,?,?,?,?,?,?);";;
  private final String SELECT_CARD_TABLE_BASE = "SELECT * FROM CARD_TABLE NATURAL JOIN SET_JUNC_TABLE";
  
  
  public DBCardTool(DBPersistanceController controller) {
    super(controller);
  }
  
  // Returns 
  public Set<Card> gatherFilteredCards(Set<DBCardSearchDataObject> searchParameters) {
    String fullStatement = genFilteredSearchRequest(searchParameters);
    Set<Card> returnVal = gatherCardsFromQuery(fullStatement);
    return returnVal;
  }

  private Set<Card> gatherCardsFromQuery(String query) {
    Set<Card> returnVal = new HashSet<>();
    try (PreparedStatement st = parentController.getStatement(query);) {
      ResultSet rs = st.executeQuery();
      while (rs.next()) {
        Card nextCard = getCardFromResultSet(rs);
        returnVal.add(nextCard);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnVal;
  }

  // Returns A Single Card From A Given Result Set
  private Card getCardFromResultSet(ResultSet incomingResult) {
    Card returnVal = new Card();
    try {
      returnVal.setName(incomingResult.getString("CARD_NAME"));
      returnVal.setText(incomingResult.getString("CARD_TEXT"));
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return returnVal;
  }
  
  private String genFilteredSearchRequest(Set<DBCardSearchDataObject> searchParameters) {
    StringBuilder baseStatement = new StringBuilder(SELECT_CARD_TABLE_BASE);
    for (DBCardSearchDataObject dataObj: searchParameters) {
      baseStatement.append(" WHERE " + dataObj.getDBColumnKey() + " LIKE '%" + dataObj.getDBSearchValue() + "%'");
    }
    return baseStatement.toString();
  }
  
  
  /**
   * Adds A JSON Card To The Database. Only Called When We Need To Refresh The DB
   * @param card
   */
  public void addJSONCard (JSONCard card) {
    try (PreparedStatement st = parentController.getStatement(INSERT_CARD_TABLE);) {
      st.setString(1, MTGHelper.generateCardKey(card));
      st.setString(2, card.getName());
      st.setString(3, card.getType());
      st.setString(4, card.getType());
      st.setString(5, card.getType());
      st.setInt(6, createSafeNumberFromString(card.getPower()));
      st.setInt(7, createSafeNumberFromString(card.getToughness()));
      st.setInt(8, createSafeNumberFromString(card.getPower()));
      st.execute();
    } catch (SQLException e) {
      // Probably Hit A Card That Shares The Same Name, Not Important, So Just Continue Onward
    }
  }
  
  private int createSafeNumberFromString (String num) {
    try {
      return Integer.parseInt(num);
    } catch (Exception e) {
      return -1;
    }
  }

  @Override
  public List<String> getDBCreationStrings() {
    List<String> creationStrings = new ArrayList<String> ();
    creationStrings.add(CREATE_CARD_TABLE);
    return creationStrings;
  }
}
