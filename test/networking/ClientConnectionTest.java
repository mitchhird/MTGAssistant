package networking;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import util.BaseTest;
import util.Constants;

/**
 * Test Case That Is Directly Responsible For Connecting The Client And Running Standard Testing Operations
 * @author Mitchell
 */
public class ClientConnectionTest extends BaseTest {
  protected String serverIP;
  protected int serverPort;
  protected ClientConnection classUnderTest;

  @Before
  public void setup() throws IOException {
    serverIP = "127.0.0.1";
    serverPort = Constants.SERVER_PORT_MIN;
    classUnderTest = new ClientConnection(serverIP, serverPort);
    assertTrue(classUnderTest.isConnectedToServer());
  }

  @Test
  public void testGetServerLastModified() {
    assertTrue (classUnderTest.getServerLastModified() > 0);
  }
  

}
