package integrationTests.networking;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import models.cardModels.Card;
import models.cardModels.Format;
import models.deckModels.Deck;
import networking.ClientConnection;
import util.BaseTest;
import util.Constants;

/**
 * Test Case That Is Directly Responsible For Connecting The Client And Running Standard Testing Operations
 * 
 * @author Mitchell
 */
public class ClientConnectionTest extends BaseTest {
  protected String serverIP;
  protected int serverPort;
  protected Random testRandom;
  protected ClientConnection classUnderTest;

  @Before
  public void setup() throws IOException {
    serverIP = "127.0.0.1";
    serverPort = Constants.SERVER_PORT_MIN;
    testRandom = new Random();
    classUnderTest = new ClientConnection(serverIP, serverPort);
    assertTrue(classUnderTest.isConnectedToServer());
    System.out.println();
  }

  @Test
  public void testGetServerLastModified() {
    for (Format f: Format.values()) {
      System.out.println("Testing Fetch Of Last Mod Time From Server (" + f +")");
      assertTrue (classUnderTest.getServerLastModified(f) > 0);
    }
  }

  @Test
  public void testAddDeckToServer() {
    for (Format testFormat : Format.values()) {
      System.out.println("Testing Addition Of Deck From Server (" +testFormat +")");
      Deck testDeck = createTestDeck(testRandom.nextInt(1000), testFormat);
      addAndTestDeckToServer(testDeck, testFormat);
      classUnderTest.deleteDeckFromServer(testDeck);
      System.out.println("Finished Testing Addition Of Deck From Server (" +testFormat +")");
    }
  }
  
  @Test
  public void testAddIllegalDeckToServer() {
    for (Format testFormat : Format.values()) {
      System.out.println("Testing Add Of Illegal Deck From Server (" +testFormat +")");
      
      long currentTimeStamp = classUnderTest.getServerLastModified(testFormat);
      
      // Create An Illegal Deck By Adding Shahrazad Into The Deck... Card Is Banned In All Formats
      Deck illegalDeck = createTestDeck(testRandom.nextInt(1000), testFormat);
      illegalDeck.addCardToDeck(new Card("Shahrazad"));
      
      // Upload The Deck And Verify That Didn't Register In The Server Or The Server DB
      boolean success = classUnderTest.addDeckToServer(illegalDeck);
      assertFalse (success);
      assertEquals(currentTimeStamp, classUnderTest.getServerLastModified(testFormat));
      
      System.out.println("Finishing Add Of Illegal Deck Test From Server (" +testFormat +")");
    }
  }
  
  @Test
  public void testRemoveDeckToServer() {
    for (Format testFormat : Format.values()) {
      System.out.println("Testing Removal Of Deck From Server (" +testFormat +")");
      Deck testDeck = createTestDeck(testRandom.nextInt(1000), testFormat);
      addAndTestDeckToServer(testDeck, testFormat);
      
      long currentServerLastModTime = classUnderTest.getServerLastModified(testFormat);
      classUnderTest.deleteDeckFromServer(testDeck);
      long newServerLastModTime = classUnderTest.getServerLastModified(testFormat);
      assertNotEquals(newServerLastModTime, currentServerLastModTime);
      System.out.println("Finished Removal Of Deck From Server (" +testFormat +")");
    }
  }
  
  
  @Test
  public void testGetDecksFromServer() {
    for (Format testFormat : Format.values()) {
      System.out.println("Testing Fetching Of Deck From Server (" +testFormat +")");
      
      // Get The Current Decks For The Format
      long currentServerLastModTime = classUnderTest.getServerLastModified(testFormat);
      List<Deck> currentDecksForFormat = classUnderTest.getServerDecksForFormat(testFormat);
      
      // Verify That The Last Mod Stamps Didn't Change Since We Are Only Reading Data
      long newServerLastModTime = classUnderTest.getServerLastModified(testFormat);
      assertEquals(newServerLastModTime, currentServerLastModTime);
      
      // Add A Deck Into The System
      Deck testDeck = createTestDeck(testRandom.nextInt(testRandom.nextInt(10000)), testFormat);
      addAndTestDeckToServer(testDeck, testFormat);
      
      // Refetch The Listing And Makes Sure That Sizes Differ
      List<Deck> decksAfterAddition = classUnderTest.getServerDecksForFormat(testFormat);
      assertEquals(currentDecksForFormat.size() + 1, decksAfterAddition.size());
      assertNotEquals(currentDecksForFormat, decksAfterAddition);
      
      classUnderTest.deleteDeckFromServer(testDeck);
      System.out.println("Finished Fetching Of Decks From Server (" +testFormat +")");
    }
  }

  public void addAndTestDeckToServer(Deck testDeck, Format testFormat) {
    long currentServerLastModTime = classUnderTest.getServerLastModified(testFormat);
    assertTrue(currentServerLastModTime > 0);

    assertTrue(classUnderTest.addDeckToServer(testDeck));

    long newServerLastModTime = classUnderTest.getServerLastModified(testFormat);
    assertNotEquals(newServerLastModTime, currentServerLastModTime);
  }
}
