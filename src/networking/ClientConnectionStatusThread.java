package networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import models.cardModels.Format;
import models.deckModels.Deck;
import util.Constants;
import app.MTGAssistantClient;

/**
 * Small Thread Who's Sole Purpose Will Be Retrieving Updates On A Periodic Basis
 * @author Mitchell
 */
public class ClientConnectionStatusThread extends Thread {
  private boolean statusThreadRunning;
  private String currentNetworkStatus;
  private final ClientConnection connection;
  private final Map<Format, Long> formatLastModifiedMap;
  private final Map<Format, List<Deck>> decksForFormats;

  private final MTGAssistantClient client;
  
  // Constructor For The Status Updating Thread
  public ClientConnectionStatusThread(MTGAssistantClient client, ClientConnection clientConnection) {
    this.client = client;
    statusThreadRunning = true;
    connection = clientConnection;
    formatLastModifiedMap = new HashMap<Format, Long>();
    decksForFormats = new ConcurrentHashMap<Format, List<Deck>>();
    for (Format f: Format.values()) {
      formatLastModifiedMap.put(f, (long) -1);
    }
    
    setName("Client Status Updater Thread");
    setStatusMessage("Idle");
  }
  
  @Override
  public void run() {
    try {
      while (statusThreadRunning && connection.isConnectedToServer()) {
        try {
          Thread.sleep(Constants.STATUS_UPDATE_INTERVAL);
        } catch (InterruptedException e) {
        }
        
        // Now Get All The Last Modified Stamps And Call The Fetch Methods Accordingly
        for (Format f: formatLastModifiedMap.keySet()) {
          setStatusMessage("Polling Server");
          long serverLastMod = connection.getServerLastModified(f);
          long ourLastMod = formatLastModifiedMap.get(f);
          if (ourLastMod != serverLastMod) {
            setStatusMessage("Fetching " + f + " decks");
            formatLastModifiedMap.put(f, serverLastMod);
            List<Deck> decksForFormat = connection.getServerDecksForFormat(f);
            decksForFormats.put(f, decksForFormat);
            setStatusMessage("Finished Download");
          }
          
        }
        setStatusMessage("Idle");
      }
    } catch (Exception e) {
      client.disconnectFromServer();
    }
    setStatusMessage("");
  }
  
  // Update Status Message
  private void setStatusMessage (String text) {
    currentNetworkStatus = text;
    client.updateNetworkStatus(currentNetworkStatus);
  }
  
  // Returns The Decks For The Given Formats
  public List<Deck> getDecksForFormat(Format formatToFetch) {
    List<Deck> decksInFormat = decksForFormats.get(formatToFetch);
    if (decksInFormat != null) {
      return decksInFormat;
    }
    return new ArrayList<Deck>();
  }

  public void close() {
    this.statusThreadRunning = false;
  }
}
