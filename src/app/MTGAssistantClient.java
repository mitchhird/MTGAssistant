package app;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import models.cardModels.Format;
import models.deckModels.Deck;
import networking.ClientConnection;
import ui.clientui.MainApplicationFrame;
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
  
  // Default Constructor For The Main Application
  public MTGAssistantClient () {
    scheduledExecutor = new ScheduledThreadPoolExecutor(5);
    dbController = DBPersistanceController.getInstance();
    applicationUI = new MainApplicationFrame(this);
  }
  
  // Returns All Of The Decks For A Given Format
  public List<Deck> getDecksByFormat (Format formatToGather) {
    return dbController.getDecksByFormatNoContent(formatToGather);
  }
  
  // Connects The Client Application To The Server
  public void connectToServer (String ipAddress, int port) throws IOException {
    clientConnection = new ClientConnection(ipAddress, port);
  }
  
  // Adds A New Execution Thread To Our Scheduler
  public void addNewExcutingThread (Runnable methodToRun, long delay, TimeUnit delayUnit ) {
    scheduledExecutor.schedule(methodToRun, delay, delayUnit);
  }
  
  // Returns Whether Or Not The Application Is Currently Connected To The Server
  public boolean isConnectedToServer () {
    return (clientConnection != null && clientConnection.isConnectedToServer());
  }
  
  // Main Method For This Class
	public static void main (String[] args) {
	  MTGAssistantClient newClient = new MTGAssistantClient();
	  newClient.applicationUI.setVisible(true);
	}
}
