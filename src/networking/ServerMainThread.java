package networking;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Thread That Is Responsible For Accepting New Connections
 * 
 * @author Mitchell
 */
public class ServerMainThread extends Thread{
  private final int serverPort;
  private final List<ServerCommandHandlerThread> activeHandlers;
  
  public ServerMainThread(int serverPort) {
    this.serverPort = serverPort;
    activeHandlers = new ArrayList<>();
  }
 
  // Removes An Active Handler From The List. Syncronized So Multiple Threads Don't Access The List
  public synchronized void removeActiveHandler (ServerCommandHandlerThread threadToRemove) {
    this.activeHandlers.remove(threadToRemove);
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
        ServerCommandHandlerThread newHandleThread = new ServerCommandHandlerThread(this, newSocket);
        activeHandlers.add(newHandleThread);
        newHandleThread.start();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
