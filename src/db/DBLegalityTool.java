package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.MTGHelper;
import util.JSONConvertTools.JSONCard;
import util.JSONConvertTools.JSONLegality;

public class DBLegalityTool extends DBTool {

  private static String LEGAL_TABLE_CREATE = "CREATE TABLE IF NOT EXISTS LEGAL_TABLE (CARD_ID varchar (" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                                   + "FORMAT_LEGAL varchar (" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                   + "LEGAL_STATUS varchar (" + DB_CHAR_COLUMN_LIMIT + "),"
                                                                                   + "FOREIGN KEY(CARD_ID) REFERENCES CARD_TABLE(CARD_ID)," 
                                                                                   + "PRIMARY KEY(CARD_ID, FORMAT_LEGAL));";

  private static String LEGAL_TABLE_INSERT = "INSERT INTO LEGAL_TABLE (CARD_ID, FORMAT_LEGAL, LEGAL_STATUS) VALUES (?,?,?)";

  // Constructor For The Legality Tool
  protected DBLegalityTool(DBPersistanceController parentController) {
    super(parentController);
  }

  // Inserts A Card Into The Table When Called
  public void insertIntoLegalTable(JSONCard incomingCard) {
    for (JSONLegality legal : incomingCard.getLegalities()) {
      try (PreparedStatement st = parentController.getStatement(LEGAL_TABLE_INSERT);) {
        st.setString(1, MTGHelper.generateCardKey(incomingCard));
        st.setString(2, legal.getFormat().toUpperCase());
        st.setString(3, legal.getLegality().toUpperCase());
        st.execute();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<String> getDBCreationStrings() {
    List<String> returnVal = new ArrayList<String>();
    returnVal.add(LEGAL_TABLE_CREATE);
    return returnVal;
  }

}
