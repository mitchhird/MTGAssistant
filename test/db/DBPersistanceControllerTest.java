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
    String testUser = "testUser";
    String testDescription = "testDescription";
    String testDeckName = "testDeckName";
    Format testFormat = Format.MODERN;
    
    Deck testDeck = new Deck(testUser, testDescription, testDeckName, testFormat);
    classUnderTest.addDeckToDB(testDeck);
    
    // Retrieve The Data Back From The DB And Verify That It Is What We Expect It To Be
    List<Deck> decksInDB = classUnderTest.getAllDecksInDB();
    Deck sqlDeck = decksInDB.get(0);

    assertEquals (1, decksInDB.size());
    assertEquals (testDeck.getCreatingUser(), sqlDeck.getCreatingUser());
    assertEquals (testDeck.getDeckDescription(), sqlDeck.getDeckDescription());
    assertEquals (testDeck.getDeckFormat(), sqlDeck.getDeckFormat());
    assertEquals (testDeck.getDeckName(), sqlDeck.getDeckName());
  }
  
  @After
  public void teardown() {
    classUnderTest.clearDatabase();
  }
}
