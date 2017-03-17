package app;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import models.cardModels.Format;
import models.deckModels.Deck;
import networking.ClientConnection;
import networking.ClientConnectionStatusThread;
import ui.clientui.MainApplicationFrame;
import util.Constants;
import db.DBPersistanceController;

/**
 * Main class for client side application of this project.
 * Spaans the UI elements when evoked. 
 * @author Mitchell
 */
public class MTGAssistantClient {

  private final DBPersistanceController dbController;
  private final MainApplicationFrame applicationUI;
  private final ScheduledThreadPoolExecutor scheduledExecutor;
  private ClientConnection clientConnection;
  private ClientConnectionStatusThread statusThread;
  
  // Default Constructor For The Main Application
  public MTGAssistantClient () {
    scheduledExecutor = new ScheduledThreadPoolExecutor(5);
    dbController = DBPersistanceController.getInstance(Constants.CLIENT_DB);
    applicationUI = new MainApplicationFrame(this);
  }
  
  // Returns All Of The Decks For A Given Format
  public List<Deck> getDecksByFormat (Format formatToGather) {
    List<Deck> localDecks = dbController.getDecksByFormatNoContent(formatToGather);
    if (statusThread != null) {
      localDecks.addAll(statusThread.getDecksForFormat(formatToGather));
    }
    return localDecks;
  }
  
  // Adds The Deck The Local DB, If Connected To Server Also Sends It To The Server
  public void addDeckToServer(Deck incomingDeck) {
    // If The Deck Is Local Then Perform Local Operations
    if (!incomingDeck.isFromServer()) {
      if (dbController.isDeckInDB(incomingDeck)) {
        dbController.deleteDeckFromDB(incomingDeck);
        dbController.addDeckToDB(incomingDeck);
      }
      else {
        dbController.addDeckToDB(incomingDeck);
      }
    }
    
    // If We Have A Connection, Then We Also Tell The Server To Do The Same
    if (isValidServerConnection()) {
      clientConnection.addDeckToServer(incomingDeck);
    }
  }
  
  // Deletes The Deck From The DB When Called
  public void deleteDeckFromSystem (Deck incomingDeck) {
    if (!incomingDeck.isFromServer()) {
      dbController.deleteDeckFromDB(incomingDeck);
    }
    
    if (isValidServerConnection()) {
      clientConnection.deleteDeckFromServer(incomingDeck);
    }
  }

  private boolean isValidServerConnection() {
    return clientConnection != null && clientConnection.isConnectedToServer();
  }
  
  // Returns All Of The Decks For A Given Format
  public void populateDeckContents (Deck deckToPopulate) {
    if (deckToPopulate.getCardsWithinDeck().isEmpty()) {
      dbController.populateDeckContents(deckToPopulate);
    }
  }
  
  // Connects The Client Application To The Server
  public ClientConnection connectToServer (String ipAddress, int port) throws IOException {
    clientConnection = new ClientConnection(ipAddress, port);
    statusThread = new ClientConnectionStatusThread(clientConnection);
    statusThread.start();
    return clientConnection;
  }
  
  // Disconnects From The Server When Called
  public void disconnectFromServer () {
    clientConnection.close();
    statusThread.close();
    clientConnection = null;
    statusThread = null;
  }
  
  // Adds A New Execution Thread To Our Scheduler
  public void addNewExcutingThread (Runnable methodToRun, long delay, TimeUnit delayUnit ) {
    scheduledExecutor.schedule(methodToRun, delay, delayUnit);
  }
  
  // Returns Whether Or Not The Application Is Currently Connected To The Server
  public boolean isConnectedToServer () {
    return isValidServerConnection();
  }
  
  /**
   * @return the dbController
   */
  public DBPersistanceController getDbController() {
    return dbController;
  }

  // Main Method For This Class
	public static void main (String[] args) {
	  MTGAssistantClient newClient = new MTGAssistantClient();
	  newClient.applicationUI.setVisible(true);
	}
}
