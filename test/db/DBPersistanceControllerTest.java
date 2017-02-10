package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import models.cardModels.Card;
import models.cardModels.Format;
import models.deckModels.Deck;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.TestUtil;

// Tests For The DBPersistanceController Object
public class DBPersistanceControllerTest {

  private DBPersistanceController classUnderTest;

  @Before
  public void setup() {
    classUnderTest = DBPersistanceController.getInstance();
  }

  @Test
  public void basicDeckAdditionToDB() {
    int numOfDeckToTest = 20;
    List<Deck> decksToTest = createTestDecks(numOfDeckToTest);

    for (Deck d : decksToTest) {
      // Retrieve The Data Back From The DB And Verify That It Is What We Expect It To Be
      Deck sqlDeck = classUnderTest.getIndividualDeck(d.getCreatingUser(), d.getDeckName());
      assertEquals(d.getCreatingUser(), sqlDeck.getCreatingUser());
      assertEquals(d.getDeckDescription(), sqlDeck.getDeckDescription());
      assertEquals(d.getDeckFormat(), sqlDeck.getDeckFormat());
      assertEquals(d.getDeckName(), sqlDeck.getDeckName());
      assertEquals(d.getDeckDescription(), sqlDeck.getDeckDescription());
    }
  }
  
  @Test
  public void getDeckByFormat() {
    int numOfDeckToTest = 20;
    createTestDecks(numOfDeckToTest);  
    for (Format f: Format.values()) {
      testFormatFetch(f);
    }
  }

  private void testFormatFetch(Format testFormat) {
    for (Deck d: classUnderTest.getDecksByFormat(testFormat)) {
      assertEquals(testFormat, d.getDeckFormat());
    }
  }


  @Test
  public void testVintageBannedList() {
    testBanList(TestUtil.VINTAGE_BAN_LIST, Format.VINTAGE);
  }

  @Test
  public void testVintageRestrictedList() {
    for (String s : TestUtil.VINTAGE_RESTRICTED_LIST) {
      Card testCard = new Card(s);
      boolean isCardBanned = classUnderTest.isCardRestrictedInFormat(testCard, Format.VINTAGE);
      assertTrue("DB returned invalid result for legality check on " + s, isCardBanned);
    }
  }

  @Test
  public void testModernBanList() {
    testBanList(TestUtil.MODERN_BAN_LIST, Format.MODERN);
  }

  @Test
  public void testCommanderBanList() {
    testBanList(TestUtil.COMMANDER_BAN_LIST, Format.COMMANDER);
  }

  private void testBanList(String[] bannedCards, Format testFormat) {
    for (String s : bannedCards) {
      Card testCard = new Card(s);
      boolean isCardBanned = classUnderTest.isCardBannedInFormat(testCard, testFormat);
      assertTrue("DB returned invalid result for legality check on " + s, isCardBanned);
    }
  }
  

  private List<Deck> createTestDecks(int numOfDeckToTest) {
    List<Deck> decksToTest = new ArrayList<Deck>();
    for (int i = 0; i < numOfDeckToTest; i++) {
      Deck testDeck = new Deck();
      testDeck.setCreatingUser("testUser" + i);
      testDeck.setDeckDescription("testDescription" + i);
      testDeck.setDeckName("testDeckName" + i);

      Format[] availableFormats = Format.values();
      testDeck.setDeckFormat(availableFormats[i % availableFormats.length]);
      testDeck.setDeckArchetype("testArt" + i);
      
      classUnderTest.addDeckToDB(testDeck);
    }
    return decksToTest;
  }

  @After
  public void teardown() {
    classUnderTest.clearDatabase();
  }
}
