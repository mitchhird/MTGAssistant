package unitTests;

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

import util.BaseTest;
import db.DBPersistanceController;

// Tests For The DBPersistanceController Object
public class DBPersistanceControllerTest extends BaseTest {

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
      System.out.println("  ---> Attempting Verification Of " + d.getDeckName());
      // Retrieve The Data Back From The DB And Verify That It Is What We Expect It To Be
      Deck sqlDeck = classUnderTest.getIndividualDeck(d.getCreatingUser(), d.getDeckName());
      assertEquals(d.getCreatingUser(), sqlDeck.getCreatingUser());
      assertEquals(d.getDeckDescription(), sqlDeck.getDeckDescription());
      assertEquals(d.getDeckFormat(), sqlDeck.getDeckFormat());
      assertEquals(d.getDeckName(), sqlDeck.getDeckName());
      assertEquals(d.getDeckDescription(), sqlDeck.getDeckDescription());
      
      for (String s: testCardSet) {
        Card testCard = new Card(s);
        Integer originalContent = d.getCardsWithinDeck().get(testCard);
        Integer sqlContent = sqlDeck.getCardsWithinDeck().get(testCard);
        assertEquals(amountOfEachCard, sqlContent.intValue());
        assertEquals(originalContent, sqlContent);
      }
      System.out.println("  ---> Finished Verification Of " + d.getDeckName());
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
    System.out.println("  ---> Testing Format Only Fetch Tests");
    for (Deck d: classUnderTest.getDecksByFormatNoContent(testFormat)) {
      assertEquals(testFormat, d.getDeckFormat());
    }
    System.out.println("  ---> Finished Format Only Fetch Tests");
  }


  @Test
  public void testVintageBannedList() {
    testBanList(VINTAGE_BAN_LIST, Format.VINTAGE);
  }

  @Test
  public void testVintageRestrictedList() {
    System.out.println("Testing Vintage Restricted List");
    for (String s : VINTAGE_RESTRICTED_LIST) {
      Card testCard = new Card(s);
      boolean isCardBanned = classUnderTest.isCardRestrictedInFormat(testCard, Format.VINTAGE);
      assertTrue("DB returned invalid result for legality check on " + s, isCardBanned);
      System.out.println("  ---> Verified that " + testCard.getName() + " is restricted in vintage");
    }
  }

  @Test
  public void testModernBanList() {
    testBanList(MODERN_BAN_LIST, Format.MODERN);
  }

  @Test
  public void testCommanderBanList() {
    testBanList(COMMANDER_BAN_LIST, Format.COMMANDER);
  }

  private void testBanList(String[] bannedCards, Format testFormat) {
    System.out.println("Testing Ban List For " + testFormat);
    for (String s : bannedCards) {
      Card testCard = new Card(s);
      boolean isCardBanned = classUnderTest.isCardBannedInFormat(testCard, testFormat);
      assertTrue("DB returned invalid result for legality check on " + s, isCardBanned);
      System.out.println("  --> Verified That " + testCard.getName() + " is banned");
    }
    System.out.println("Finished Banlist Test");
  }
  

  private List<Deck> createTestDecks(int numOfDeckToTest) {
    List<Deck> decksToTest = new ArrayList<Deck>();
    System.out.println("Adding " + numOfDeckToTest + " decks to the current system");
    for (int i = 0; i < numOfDeckToTest; i++) {
      Deck testDeck = createTestDeck(i);
      classUnderTest.addDeckToDB(testDeck);
      decksToTest.add(testDeck);
    }
    return decksToTest;
  }

  @After
  public void teardown() {
    //classUnderTest.clearDatabase();
  }
}
