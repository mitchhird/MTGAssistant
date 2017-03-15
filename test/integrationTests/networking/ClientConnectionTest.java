package integrationTests.networking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import models.cardModels.Format;
import models.deckModels.Deck;
import networking.ClientConnection;

import org.junit.Before;
import org.junit.Test;

import util.BaseTest;
import util.Constants;
import util.ModelHelper;

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
    assertTrue (classUnderTest.getServerLastModified(Format.STANDARD) > 0);
  }

  @Test
  public void testAddDeckToServer() {
    for (Format testFormat: Format.values()) {  
      long currentServerLastModTime = classUnderTest.getServerLastModified(testFormat);
      assertTrue(currentServerLastModTime > 0);

      Deck testDeck = createTestDeck(1000, testFormat);
      ModelHelper.toJSONFromModel(testDeck);
      assertTrue(classUnderTest.addDeckToServer(testDeck));

      long newServerLastModTime = classUnderTest.getServerLastModified(testFormat);
      assertFalse(newServerLastModTime == currentServerLastModTime);
      assertNotEquals(newServerLastModTime, currentServerLastModTime);
    }
  }
}
