package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.JSONCard;
import util.MTGHelper;

public class DBCardTool extends DBTool {

  private final String CREATE_CARD_TABLE = "CREATE TABLE IF NOT EXISTS CARD_TABLE (CARD_ID varchar(" + DB_CHAR_COLUMN_LIMIT + ") PRIMARY KEY," 
                                                                               + "CARD_NAME varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_TEXT varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_TYPE varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_SUPERTYPES varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_SUBTYPES varchar(" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                               + "CARD_POWER INTEGER,"
                                                                               + "CARD_TOUGHNESS INTEGER,"
                                                                               + "CARD_LOYALITY INTEGER)";

  private final String INSERT_CARD_TABLE = "INSERT INTO CARD_TABLE (CARD_ID, CARD_NAME, CARD_TEXT, CARD_TYPE, CARD_SUPERTYPES, CARD_SUBTYPES, CARD_POWER, CARD_TOUGHNESS, CARD_LOYALITY) VALUES (?,?,?,?,?,?,?,?,?);";;
  
  public DBCardTool(DBPersistanceController controller) {
    super(controller);
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
      e.printStackTrace();
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
