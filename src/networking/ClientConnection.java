package networking;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import models.deckModels.Deck;
import util.Constants;

/**
 * Main class that is directly responsible for allow the client to make commands to the server
 * 
 * @author Mitchell
 */
public class ClientConnection {
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
  }

  // Asks The Server For What It's Current Time Stamp Is
  public long getServerLastModified() {
    String serverReply = quote(NetworkingCommands.LMOD);
    return Long.parseLong(serverReply);
  }
  
  // Deletes A Deck On The Server. Returns True If Success
  public boolean deleteDeckFromServer (Deck incomingDeck) {
    String serverReply = quote(NetworkingCommands.DDELETE, incomingDeck.getCreatingUser(), incomingDeck.getDeckName());
    return serverReply.equals(Constants.SERVER_GOOD_REPLY);
  }

  // Adds A Deck To The Server. First Sends A Message, Waits For A Reply And Then Sends The Deck Object
  public boolean addDeckToServer (Deck incomingDeck) {
    String serverReply = quote(NetworkingCommands.DPOST);
    if (serverReply.equals(Constants.SERVER_GOOD_REPLY)) {
      return false;
    } else {
      return false;
    }
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
        e.printStackTrace();
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
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
