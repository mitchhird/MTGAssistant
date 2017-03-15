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
import java.util.Arrays;
import java.util.List;

import models.cardModels.Format;
import models.deckModels.Deck;
import util.Constants;
import util.ModelHelper;
import app.MTGAssistantServer;

/**
 * This class will be directly responsible for intrepreting any communications that come from client connections. When
 * started this thread runs indefinetely, accepting any incoming requests. Once a request comes in, it is first parsed
 * and once successfully parsed, the corresponding method is called.
 * 
 * @author Mitchell
 */
public class ServerCommandHandlerThread extends Thread {

  protected String ipAddress;
  protected Socket creatingSock;
  protected BufferedReader reader;
  protected BufferedWriter writer;
  protected MTGAssistantServer server;

  // Constructor For The Command Handler Thread
  public ServerCommandHandlerThread(Socket incomingSocket) {
    try {
      creatingSock = incomingSocket;
      ipAddress = incomingSocket.getInetAddress().getHostAddress();
      server = MTGAssistantServer.getInstance();

      OutputStream outputStream = incomingSocket.getOutputStream();
      InputStream inputStream = incomingSocket.getInputStream();

      writer = new BufferedWriter(new OutputStreamWriter(outputStream));
      reader = new BufferedReader(new InputStreamReader(inputStream));
      System.out.println("Client Connected From: " + incomingSocket.getInetAddress().getHostAddress());

    } catch (IOException e) {
      e.printStackTrace();
    }
    setName("Command Handler (" + ipAddress + ") Thread");
  }

  // Closes The Connection. Called When The Connection Is Terminated
  protected void close() {
    try {
      if (reader != null) {
        reader.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    try {
      if (reader != null) {
        reader.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Runs A Method For The Corresponding Command
  protected void runMethodForCommand(NetworkingCommands command, List<String> commands) {
    try {
      switch (command) {
        case LMOD:
          handleLMODCommand(commands);
          break;
        case DDELETE:
          handleDDELETECommand(commands);
          break;
        case DPOST:
          handleDPOSTCommand(commands);
          break;
        case DGET:
          handleDGETCommand(commands);
          break;
        default:
          break;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  // Handles The DGET Command By Quoting The Decks To Be Sent, And Then Sends Them
  protected void handleDGETCommand(List<String> commands) throws IOException {
    if (!commands.isEmpty()) {
      Format formatToCollect = Format.valueOf(commands.get(0));
      List<Deck> decksToRecieve = server.getDbController().getDecksByFormatNoContent(formatToCollect);
      sendResponseToClient("" + decksToRecieve.size());
      
      // Client Has Been Quoted About The Decks That They Have Access To, So Ship Them Over
      for (Deck d: decksToRecieve) {
        server.getDbController().populateDeckContents(d);
        String dataToSend = ModelHelper.toJSONFromModel(d);
        sendResponseToClient(dataToSend);
      }
    }
  }

  // Handles The LMOD Command By Sending The Last Timestamp To The Request
  protected void handleLMODCommand(List<String> commands) throws IOException {
    if (!commands.isEmpty()) {
      Format formatToCheck = Format.valueOf(commands.get(0));
      sendResponseToClient("" + server.getLastModifiedStampForFormat(formatToCheck));
    } else {
      sendResponseToClient(Constants.SERVER_BAD_REPLY);
    }
  }

  // Handles The DDelete Command By Deleting The Deck
  protected void handleDDELETECommand(List<String> commands) throws IOException {
    String creatingUser = commands.get(0);
    String deckName = commands.get(1);
    server.deleteDeckFromDB(creatingUser, deckName);
    sendResponseToClient(Constants.SERVER_GOOD_REPLY);
  }

  // Handles The DPOST Command By Adding The Deck That The Client Has Supplied
  protected void handleDPOSTCommand(List<String> commands) throws Exception {
    for (String serializedDeck : commands) {
      Deck actualDeckObject = ModelHelper.toModelFromJSON(serializedDeck, Deck.class);
      if (server.getDbController().isDeckInDB(actualDeckObject)) {        
        server.deleteDeckFromDB(actualDeckObject);
        server.addDeckToDB(actualDeckObject);
      } else {
        server.addDeckToDB(actualDeckObject);
      }
    }
    sendResponseToClient(Constants.SERVER_GOOD_REPLY);
  }

  protected void sendResponseToClient(String command) throws IOException {
    writer.write(command + "\n");
    writer.flush();
  }

  @Override
  public void run() {
    while (true) {
      try {
        String nextCommand = reader.readLine();
        String[] parsedCommand = nextCommand.split(Constants.DELIMITER);
        if (NetworkingCommands.valueOf(parsedCommand[0]) != null) {
          NetworkingCommands command = NetworkingCommands.valueOf(parsedCommand[0]);
          List<String> commandArgs = (parsedCommand.length > 1) ? Arrays.asList(parsedCommand).subList(1, parsedCommand.length) : new ArrayList<String>();
          runMethodForCommand(command, commandArgs);
        }
      } catch (Exception e) {
        this.close();
        break;
      }
    }
  }
}
