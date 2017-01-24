package models;

import static org.junit.Assert.assertEquals;
import models.cardModels.Format;
import models.deckModels.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests responsible for testing deck functionality
 * @author Mitchell
 */
public class DeckTests {
  
  private final String testDeckName = "testDeck";
  private final String testDeckDescription = "testDescription";
  private final String testCreatingUser = "testUser";
  private final Format testFormat = Format.COMMANDER;
  
  private Deck classUnderTest;
  
  @Before
  public void setup() {
    classUnderTest = new Deck();
    classUnderTest.setCreatingUser(testCreatingUser);
    classUnderTest.setDeckDescription(testDeckDescription);
    classUnderTest.setDeckName(testDeckName);
    classUnderTest.setDeckFormat(testFormat);
  }
  
  @Test
  public void basicDeckAdditionToDB () {
    assertEquals(testCreatingUser, classUnderTest.getCreatingUser());
    assertEquals(testDeckDescription, classUnderTest.getDeckDescription());
    assertEquals(testDeckName, classUnderTest.getDeckName());
    assertEquals(testFormat, classUnderTest.getDeckFormat());
    assertEquals(0, classUnderTest.getCardsWithinDeck().size());
  }
  
  @After
  public void teardown() {
    
  }
}
