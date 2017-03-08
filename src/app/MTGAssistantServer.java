package app;

import models.deckModels.Deck;
import ui.serverui.MainServerUIFrame;
import db.DBPersistanceController;

/**
 * Class that contains the main method for the server application
 * 
 * @author Mitchell
 */
public class MTGAssistantServer {
  private long lastModifiedStamp;
  private final DBPersistanceController dbController;
  private static MTGAssistantServer instance;
  
  // Default Constructor For The Server Class
  protected MTGAssistantServer (){
    resetLastModified();
    dbController = DBPersistanceController.getInstance();
  }
  
  // Get The Last Modified Stamp
  public long getLastModifiedStamp () {
    return lastModifiedStamp;
  }
  
  // Delete The Deck From The DB
  public void deleteDeckFromDB (String creatingUser, String deckName) {
    dbController.deleteDeckFromDB(creatingUser, deckName);
    resetLastModified();
  }
  
  // Add The Deck To The Server DB
  public void addDeckToDB (Deck deckToAdd) {
    dbController.addDeckToDB(deckToAdd);
    resetLastModified();
  }

  // Reset Last Modified Counter When Called
  private void resetLastModified() {
    lastModifiedStamp = System.currentTimeMillis();
  }
  
  public static MTGAssistantServer getInstance() {
    if (instance == null) {
      instance = new MTGAssistantServer();
    }
    return instance;
  }
  
  public static void main(String[] args) {
    MainServerUIFrame frame = new MainServerUIFrame();
    frame.setVisible(true);
  }
}
