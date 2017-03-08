package networking;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main Thread That Is Responsible For Accepting New Connections
 * 
 * @author Mitchell
 */
public class ServerMainThread extends Thread{
  private final int serverPort;
  
  public ServerMainThread(int serverPort) {
    this.serverPort = serverPort;
  }
  
  
  @Override
  // Main Method That Will Start Up The Server And Handle Everything
  public void run () {
    System.out.println("Starting Server On Port " + serverPort);
    try (ServerSocket serverSocket = new ServerSocket(serverPort);) {
      setName("Server Acceptor Thread " + InetAddress.getLocalHost().getHostAddress());
      
      // Run Indefinitely Waiting For Incoming Connection
      while (true) {
        Socket newSocket = serverSocket.accept();
        ServerCommandHandlerThread newHandleThread = new ServerCommandHandlerThread(newSocket);
        newHandleThread.start();
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
