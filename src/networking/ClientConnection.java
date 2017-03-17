package networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import models.cardModels.Format;
import models.deckModels.Deck;
import util.Constants;
import util.ModelHelper;

/**
 * Main class that is directly responsible for allow the client to make commands to the server
 * 
 * @author Mitchell
 */
public class ClientConnection extends Observable {
  private Socket connectionSocket;
  private BufferedWriter writer;
  private BufferedReader reader;
  
  public ClientConnection(String ipAddress, int port) throws IOException {
    initConnection(new Socket(ipAddress, port));
  }

  public ClientConnection(Socket connectionSocket) throws IOException {
    initConnection(connectionSocket);
  }

  // Initializes The Object With The Necessary Information
  private void initConnection(Socket connectionSocket) throws IOException {
    this.connectionSocket = connectionSocket;
    InputStream inputStream = connectionSocket.getInputStream();
    OutputStream outputStream = connectionSocket.getOutputStream();
    
    reader = new BufferedReader(new InputStreamReader(inputStream));
    writer = new BufferedWriter(new OutputStreamWriter(outputStream));
    
    setChanged();
    notifyObservers();
  }

  // Asks The Server For What It's Current Time Stamp Is
  public long getServerLastModified(Format deckFormat) {
    String serverReply = quote(NetworkingCommands.LMOD, deckFormat.name());
    return (serverReply != null && !serverReply.isEmpty()) ? Long.parseLong(serverReply) : -1;
  }
  
  // Deletes A Deck On The Server. Returns True If Success
  public boolean deleteDeckFromServer (Deck incomingDeck) {
    String serverReply = quote(NetworkingCommands.DDELETE, incomingDeck.getCreatingUser(), incomingDeck.getDeckName());
    return serverReply.equals(Constants.SERVER_GOOD_REPLY);
  }

  // Adds A Deck To The Server. First Sends A Message, Waits For A Reply And Then Sends The Deck Object
  public boolean addDeckToServer (Deck incomingDeck) {
    String serverReply = quote(NetworkingCommands.DPOST, ModelHelper.toJSONFromModel(incomingDeck));
    return serverReply.equals(Constants.SERVER_GOOD_REPLY);
  }
  
  // Gets All Of The Decks In A Format From The Server When Called
  public List<Deck> getServerDecksForFormat (Format formatToFetch) {
    List<Deck> returnVal = new ArrayList<Deck>();
    String serverReply = quote(NetworkingCommands.DGET, formatToFetch.name());
    int quantityOfDecks = Integer.parseInt(serverReply);
    
    // We Have The Quantity So Get Ready To Return It
    for (int i = 0; i < quantityOfDecks; i++) {
      try {
        String serializedDeck = reader.readLine();
        Deck actualDeckObject = ModelHelper.toModelFromJSON(serializedDeck, Deck.class);
        actualDeckObject.setFromServer(true);
        returnVal.add(actualDeckObject);
      } catch (Exception e) {
      }
    }
    
    return returnVal;
  }
  
  // Sends A Command Over To The Server And Waits Until A Response Is Returned
  protected String quote(NetworkingCommands command, String... args) {
    String baseCommand = command.name();
    for (String s : args) {
      baseCommand += Constants.DELIMITER + s;
    }
    return sendAndRecieveCommand(baseCommand);
  }

  // Sends A Command To The Server
  private String sendAndRecieveCommand(String baseCommand) {
    if (isConnectedToServer()) {
      try {
        writer.write(baseCommand + "\n");
        writer.flush();
        return reader.readLine();
      } catch (IOException e) {
        close();
      }
    }
    return "";
  }
  
  // Returns Whether Or Not The Connection Is Actually Connected
  public boolean isConnectedToServer() {
    return connectionSocket != null && connectionSocket.isConnected();
  }

  // Closes The Connection That Is Currently Established
  public void close() {
    try {
      writer.close();
      reader.close();
      connectionSocket.close();
      
      setChanged();
      notifyObservers();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
