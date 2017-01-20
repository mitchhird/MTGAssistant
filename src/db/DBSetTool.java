package db;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.cardModels.CardRarity;
import util.MTGHelper;
import util.JSONConvertTools.JSONCard;
import util.JSONConvertTools.JSONSet;

/**
 * Database tool that is responsible for managing the sets that we may wish to access
 * @author Mitchell
 */
public class DBSetTool extends DBTool {
  private static String CREATE_SET_TABLE = "CREATE TABLE IF NOT EXISTS SET_TABLE (CODE varchar(4) PRIMARY KEY," 
                                                                               + "NAME varchar(" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                               + "GATHERER_CODE varchar(4)," 
                                                                               + "BORDER varchar(" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                               + "RELEASE_DATE varchar(" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                               + "MAGIC_CARDS_INFO_CODE varchar(" + DB_CHAR_COLUMN_LIMIT + "));";
  
  private static String CREATE_SET_JUNCTION_TABLE = "CREATE TABLE IF NOT EXISTS SET_JUNC_TABLE (CODE varchar(4)," 
                                                                                             + "CARD_ID varchar(" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                                             + "ARTIST varchar(" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                                             + "FLAVOUR_TEXT varchar(" + DB_CHAR_COLUMN_LIMIT + ")," 
                                                                                             + "RARITY varchar(15));";

  private static String INSERT_SET_INTO_TABLE = "INSERT INTO SET_TABLE (CODE, NAME, GATHERER_CODE, BORDER, RELEASE_DATE, MAGIC_CARDS_INFO_CODE) VALUES (?,?,?,?,?,?)";
  private static String INSERT_SET_INTO_JUNCTION_TABLE = "INSERT INTO SET_JUNC_TABLE (CODE, CARD_ID, ARTIST, FLAVOUR_TEXT, RARITY) VALUES (?,?,?,?,?)";

  // Default constructor for the tool
  public DBSetTool(DBPersistanceController controller) {
    super(controller);
  }

  /**
   * Adds A JSON SET To The DB. Called When Converting DB Into SQL Format
   * 
   * @param incomingSet
   */
  public void addJSONSetToDB(JSONSet incomingSet) {
    System.out.println("Adding in JSON Set: " + incomingSet);
    try (PreparedStatement p = parentController.getStatement(INSERT_SET_INTO_TABLE);) {
      p.setString(1, incomingSet.getCode());
      p.setString(2, incomingSet.getName());
      p.setString(3, incomingSet.getGathererCode());
      p.setString(4, incomingSet.getBorder());
      p.setString(5, incomingSet.getReleaseDate());
      p.setString(6, incomingSet.getMagicCardsInfoCode());
      p.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    // Load In All Of The Necessary Cards
    for (JSONCard c : incomingSet.getCards()) {
      parentController.addCardToDB(c);
      try (PreparedStatement p = parentController.getStatement(INSERT_SET_INTO_JUNCTION_TABLE);) {
        p.setString(1, incomingSet.getCode());
        p.setString(2, MTGHelper.generateCardKey(c));
        p.setString(3, c.getArtist());
        p.setString(4, c.getFlavor());

        String jsonRarity = c.getRarity().toUpperCase();
        CardRarity cardRarity = (jsonRarity.equals("BASIC LAND")) ? CardRarity.COMMON : CardRarity.valueOf(jsonRarity);
        p.setString(5, cardRarity.name());
        p.execute();
        System.out.println("    --> Added Card: " + c);
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @Override
  public List<String> getDBCreationStrings() {
    List<String> returnVal = new ArrayList<String>();
    returnVal.add(CREATE_SET_TABLE);
    returnVal.add(CREATE_SET_JUNCTION_TABLE);
    return returnVal;
  }
}
