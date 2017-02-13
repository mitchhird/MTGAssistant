package db;

import java.sql.ResultSet;
import java.util.List;

import models.cardModels.Card;
import models.cardModels.CardRarity;

/**
 * Base class for all database tools. The class is responsible for holding all common and abstract methods
 * @author Mitchell
 */
public abstract class DBTool {
  public static int DB_CHAR_COLUMN_LIMIT = 255;

  protected final DBPersistanceController parentController;

  // Returns A Single Card From A Given Result Set
  protected Card getPartialCardFromRS(ResultSet incomingResult) {
    Card returnVal = new Card();
    try {
      returnVal.setName(incomingResult.getString("CARD_NAME"));
      returnVal.setText(incomingResult.getString("CARD_TEXT"));
      returnVal.setType(incomingResult.getString("CARD_TYPE"));
      returnVal.setFlavor(incomingResult.getString("FLAVOUR_TEXT"));
      returnVal.setArtist(incomingResult.getString("ARTIST"));
      returnVal.setCardRarity(CardRarity.valueOf(incomingResult.getString("RARITY")));
      returnVal.setManaCost(incomingResult.getString("CARD_MANA_COST"));
      returnVal.setMultiverseID(incomingResult.getInt("MULTIVERSE_ID"));
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return returnVal;
  }

  // Default Constructor For The Base Class
  protected DBTool (DBPersistanceController parentController) {
    this.parentController = parentController;
  }
  
  public abstract List<String> getDBCreationStrings();
}
