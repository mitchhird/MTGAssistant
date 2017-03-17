package networking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import models.cardModels.Format;
import models.deckModels.Deck;
import util.Constants;

/**
 * Small Thread Who's Sole Purpose Will Be Retrieving Updates On A Periodic Basis
 * @author Mitchell
 */
public class ClientConnectionStatusThread extends Thread {
  private boolean statusThreadRunning;
  private final ClientConnection connection;
  private final Map<Format, Long> formatLastModifiedMap;
  private final Map<Format, List<Deck>> decksForFormats;
  
  // Constructor For The Status Updating Thread
  public ClientConnectionStatusThread(ClientConnection clientConnection) {
    statusThreadRunning = true;
    connection = clientConnection;
    formatLastModifiedMap = new HashMap<Format, Long>();
    decksForFormats = new ConcurrentHashMap<Format, List<Deck>>();
    for (Format f: Format.values()) {
      formatLastModifiedMap.put(f, (long) -1);
    }
    
    setName("Client Status Updater Thread");
  }
  
  @Override
  public void run() {
    while (statusThreadRunning && connection.isConnectedToServer()) {
      try {
        Thread.sleep(Constants.STATUS_UPDATE_INTERVAL);
      } catch (InterruptedException e) {
      }
      
      // Now Get All The Last Modified Stamps And Call The Fetch Methods Accordingly
      for (Format f: formatLastModifiedMap.keySet()) {
        long serverLastMod = connection.getServerLastModified(f);
        long ourLastMod = formatLastModifiedMap.get(f);
        if (ourLastMod != serverLastMod) {
          formatLastModifiedMap.put(f, serverLastMod);
          List<Deck> decksForFormat = connection.getServerDecksForFormat(f);
          decksForFormats.put(f, decksForFormat);
        }
      }
    }
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
