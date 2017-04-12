package db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import models.cardModels.Card;
import models.cardModels.CardRarity;

/**
 * Base class for all database tools. The class is responsible for holding all common and abstract methods
 * @author Mitchell
 */
public abstract class DBTool {
  public static int DB_CHAR_COLUMN_LIMIT = 255;

  protected final DBPersistanceController parentController;

  // Returns A Card From A Result Set. Need The Combined Tables Of SET_JUNC
  protected Card getCardFromResultSet(ResultSet incomingResult) {
    Card returnVal = new Card();
    try {
      returnVal.setName(incomingResult.getString("CARD_NAME"));
      returnVal.setText(incomingResult.getString("CARD_TEXT"));
      returnVal.setType(incomingResult.getString("CARD_TYPE"));
      returnVal.setTypes(getListFromDBEntry(incomingResult.getString("CARD_SUPERTYPES")));
      returnVal.setSubtypes(getListFromDBEntry(incomingResult.getString("CARD_SUBTYPES")));
      returnVal.setCardCMC(incomingResult.getInt("CARD_CMC"));
      returnVal.setColors(getListFromDBEntry(incomingResult.getString("CARD_COLORS")));
      returnVal.setFlavor(incomingResult.getString("FLAVOUR_TEXT"));
      returnVal.setArtist(incomingResult.getString("ARTIST"));
      returnVal.setCardRarity(CardRarity.valueOf(incomingResult.getString("RARITY")));
      returnVal.setMultiverseID(incomingResult.getInt("MULTIVERSE_ID"));
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return returnVal;
  }

  // Returns A List From The Database Entry That Is Common Separated
  protected List<String> getListFromDBEntry (String dbEntry) {
    List<String> returnVal = new ArrayList<String>();
    StringTokenizer st = new StringTokenizer(dbEntry, ",");
    while (st.hasMoreTokens()) {
      String nextToken = st.nextToken();
      returnVal.add(nextToken);
    }
    return returnVal;
  }
  
  // Default Constructor For The Base Class
  protected DBTool (DBPersistanceController parentController) {
    this.parentController = parentController;
  }
  
  public abstract List<String> getDBCreationStrings();
}
