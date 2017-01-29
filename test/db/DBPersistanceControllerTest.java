package db;

import static org.junit.Assert.assertEquals;

import java.util.List;

import models.cardModels.Format;
import models.deckModels.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Tests For The DBPersistanceController Object
public class DBPersistanceControllerTest {
  
  private DBPersistanceController classUnderTest;
  
  @Before
  public void setup() {
    classUnderTest = DBPersistanceController.getInstance();
  }
  
  @Test
  public void basicDeckAdditionToDB () {
    int numOfDeckToTest = 20;
    for (int i = 0; i < numOfDeckToTest; i++) {
      Deck testDeck = new Deck();
      testDeck.setCreatingUser("testUser" + i);
      testDeck.setDeckDescription("testDescription" + i);
      testDeck.setDeckName("testDeckName" + i);
      
      Format[] availableFormats = Format.values();
      testDeck.setDeckFormat(availableFormats[i % availableFormats.length]);
      testDeck.setDeckArchetype("testArt" + i);

      classUnderTest.addDeckToDB(testDeck);

      // Retrieve The Data Back From The DB And Verify That It Is What We Expect It To Be
      List<Deck> decksInDB = classUnderTest.getAllDecksInDB();
      Deck sqlDeck = decksInDB.get(decksInDB.size() - 1);

      assertEquals(i + 1, decksInDB.size());
      assertEquals(testDeck.getCreatingUser(), sqlDeck.getCreatingUser());
      assertEquals(testDeck.getDeckDescription(), sqlDeck.getDeckDescription());
      assertEquals(testDeck.getDeckFormat(), sqlDeck.getDeckFormat());
      assertEquals(testDeck.getDeckName(), sqlDeck.getDeckName());
      assertEquals(testDeck.getDeckDescription(), sqlDeck.getDeckDescription());
    }
  }
  
  @After
  public void teardown() {
    classUnderTest.clearDatabase();
  }
}
