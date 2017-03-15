package integrationTests.networking;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import models.cardModels.Format;
import models.deckModels.Deck;
import networking.ClientConnection;

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
    assertTrue (classUnderTest.getServerLastModified(Format.STANDARD) > 0);
  }

  @Test
  public void testAddDeckToServer() {
    assertTrue (classUnderTest.getServerLastModified(Format.STANDARD) > 0);
    Deck testDeck = createTestDeck(1);
    classUnderTest.addDeckToServer(testDeck);
  }
  
  @Test
  public void testGetDecksFromServer () {
    assertTrue (classUnderTest.getServerLastModified(Format.STANDARD) > 0);
    List<Deck> testFetch = classUnderTest.getServerDecksForFormat(Format.STANDARD);
  }

}
