package networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * This class will be directly responsible for intrepreting any communications
 * that come from client connections. When started this thread runs indefinetely,
 * accepting any incoming requests. Once a request comes in, it is first parsed
 * and once successfully parsed, the corresponding method is called.
 * 
 * @author Mitchell
 */
public class ServerCommandHandlerThread extends Thread {

  protected Socket creatingSock;
  protected InputStream inStream;
  protected OutputStream outStream;

  // Constructor For The Command Handler Thread
  public ServerCommandHandlerThread(Socket incomingSocket, String ip) {
    try {
      creatingSock = incomingSocket;
      inStream = incomingSocket.getInputStream();
      outStream = incomingSocket.getOutputStream();
    } catch (IOException e) {
      e.printStackTrace();
    }
    setName("Command Handler (" + ip + ") Thread");
  }
  
  @Override
  public void run() {
    while (true) {
      try {
        inStream.read();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
