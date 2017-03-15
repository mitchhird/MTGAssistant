package app;

import java.util.HashMap;
import java.util.Map;

import models.cardModels.Format;
import models.deckModels.Deck;
import ui.serverui.MainServerUIFrame;
import util.Constants;
import db.DBPersistanceController;

/**
 * Class that contains the main method for the server application
 * 
 * @author Mitchell
 */
public class MTGAssistantServer {
  private final Map<Format, Long> formatLastModStamps;
  private final DBPersistanceController dbController;
  private static MTGAssistantServer instance;
  
  // Default Constructor For The Server Class
  protected MTGAssistantServer (){
    dbController = DBPersistanceController.getInstance(Constants.SERVER_DB);
    formatLastModStamps = new HashMap<Format, Long>();
    for (Format f: Format.values()) {
      formatLastModStamps.put(f, System.currentTimeMillis());
    }
  }
  
  // Get The Last Modified Stamp
  public long getLastModifiedStampForFormat (Format formatToCheck) {
    return formatLastModStamps.get(formatToCheck);
  }
  
  // Delete The Deck From The DB
  public void deleteDeckFromDB (String creatingUser, String deckName) {
    Deck deckToDelete = dbController.getIndividualDeck(creatingUser, deckName);
    dbController.deleteDeckFromDB(deckToDelete);
    resetLastModified(deckToDelete.getDeckFormat());
  }
  
  // Add The Deck To The Server DB
  public void addDeckToDB (Deck deckToAdd) {
    dbController.addDeckToDB(deckToAdd);
    resetLastModified(deckToAdd.getDeckFormat());
  }
  
  protected void resetLastModified (Format formatChanged) {
    formatLastModStamps.put(formatChanged, System.currentTimeMillis());
  }

  /**
   * @return the dbController
   */
  public DBPersistanceController getDbController() {
    return dbController;
  }

  public static MTGAssistantServer getInstance() {
    if (instance == null) {
      instance = new MTGAssistantServer();
    }
    return instance;
  }
  
  public static void main(String[] args) {
    MTGAssistantServer server = MTGAssistantServer.getInstance();
    MainServerUIFrame frame = new MainServerUIFrame(server);
    frame.setVisible(true);
  }
}
