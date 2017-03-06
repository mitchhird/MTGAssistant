package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Main class that is directly responsible for allow the client to make commands to the server
 * @author Mitchell
 */
public class ClientConnection {
  private Socket connectionSocket;
  private InputStream outgoingStream;
  private OutputStream incomingStream;

  public ClientConnection(String ipAddress, int port) throws IOException {
    initConnection(new Socket(ipAddress, port));
  }
  
  public ClientConnection(Socket connectionSocket) throws IOException {
    initConnection(connectionSocket);
  }

  // Initializes The Object With The Necessary Information
  private void initConnection(Socket connectionSocket) throws IOException {
    this.connectionSocket = connectionSocket;
    this.incomingStream = connectionSocket.getOutputStream();
    this.outgoingStream = connectionSocket.getInputStream();
  }
  
  // Returns Whether Or Not The Connection Is Actually Connected
  public boolean isConnectedToServer () {
    return connectionSocket != null && connectionSocket.isConnected();
  }
  
  // Closes The Connection That Is Currently Established
  public void close() {
    try {
      outgoingStream.close();
      incomingStream.close();
      connectionSocket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
