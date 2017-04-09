package unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import db.DBPersistanceController;
import models.cardModels.Card;
import models.cardModels.CardRarity;
import models.cardModels.Format;
import models.deckModels.Deck;
import models.validatorModels.DeckValidator;
import models.validatorModels.ValidatorFactory;
import util.BaseTest;
import util.ModelHelper;

/**
 * Unit tests responsible for testing deck functionality
 * 
 * @author Mitchell
 */
public class DeckTests extends BaseTest {

  private final String testDeckName = "testDeck";
  private final String testDeckDescription = "testDescription";
  private final String testCreatingUser = "testUser";
  private final Format testFormat = Format.COMMANDER;

  private Deck classUnderTest;
  private DBPersistanceController persistController;
  
  @Before
  public void setup() {
    classUnderTest = new Deck();
    classUnderTest.setCreatingUser(testCreatingUser);
    classUnderTest.setDeckDescription(testDeckDescription);
    classUnderTest.setDeckName(testDeckName);
    classUnderTest.setDeckFormat(testFormat);
    persistController = DBPersistanceController.getInstance();
  }

  @Test
  public void testBasicGettersAndSetters() {
    assertEquals(testCreatingUser, classUnderTest.getCreatingUser());
    assertEquals(testDeckDescription, classUnderTest.getDeckDescription());
    assertEquals(testDeckName, classUnderTest.getDeckName());
    assertEquals(testFormat, classUnderTest.getDeckFormat());
    assertEquals(0, classUnderTest.getCardsWithinDeck().size());
  }

  @Test
  public void testSingletonValidator() {
    addCardToDeck("Island", CardRarity.BASIC_LAND, 100);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(persistController, classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertTrue(validationErrors.isEmpty());
  }

  @Test
  public void testSingletonValidatorInvalidQuant() {
    addCardToDeck("Island", CardRarity.BASIC_LAND, 98);
    addCardToDeck("Force of Will", CardRarity.RARE, 2);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(persistController, classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(1, validationErrors.size());
  }

  @Test
  public void testSingletonValidatorInvalidTotal() {
    addCardToDeck("Island", CardRarity.BASIC_LAND, 100);
    addCardToDeck("Force of Will", CardRarity.RARE, 1);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(persistController, classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(1, validationErrors.size());
  }

  @Test
  public void testValidatorsInvalidDueToBan() {
    Format[] testFormats = { Format.COMMANDER, Format.VINTAGE, Format.MODERN };
    for (Format f : testFormats) {
      classUnderTest.getCardsWithinDeck().clear();
      classUnderTest.setDeckFormat(f);
      testBanListValidation(getBanListForFormat(f));
    }
  }

  @Test
  public void testValidatorInvalidDueToRestricted() {
    classUnderTest.setDeckFormat(Format.VINTAGE);
    for (String s : VINTAGE_RESTRICTED_LIST) {
      addCardToDeck(s, CardRarity.SPECIAL, 2);
    }
    addCardToDeck("Island", CardRarity.BASIC_LAND, 60 - VINTAGE_RESTRICTED_LIST.length);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(persistController, classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(VINTAGE_RESTRICTED_LIST.length, validationErrors.size());
  }

  @Test
  public void testSerializationAndDeserialization() throws Exception {
    addCardToDeck("Mountain", CardRarity.BASIC_LAND, 20);
    addCardToDeck("Island", CardRarity.BASIC_LAND, 20);
    addCardToDeck("Forest", CardRarity.BASIC_LAND, 20);

    String deckJSON = ModelHelper.toJSONFromModel(classUnderTest);
    Deck reconversionDeck = ModelHelper.toModelFromJSON(deckJSON, classUnderTest.getClass());
    assertEquals(classUnderTest.getCreatingUser(), reconversionDeck.getCreatingUser());
    assertEquals(classUnderTest.getDeckArchetype(), reconversionDeck.getDeckArchetype());
    assertEquals(classUnderTest.getDeckDescription(), reconversionDeck.getDeckDescription());
    assertEquals(classUnderTest.getDeckFormat(), reconversionDeck.getDeckFormat());
    assertEquals(classUnderTest.getDeckName(), reconversionDeck.getDeckName());
  }

  // Simple test method for adding cards to deck
  private void addCardToDeck(String cardName, CardRarity rarity, int quantity) {
    Card cardToAdd = new Card(cardName);
    cardToAdd.setCardRarity(rarity);
    cardToAdd.setText("test");
    classUnderTest.addCardToDeck(cardToAdd, quantity);
  }

  private void testBanListValidation(String[] banList) {
    for (String s : banList) {
      addCardToDeck(s, CardRarity.SPECIAL, 1);
    }
    addCardToDeck("Island", CardRarity.BASIC_LAND, 100 - banList.length);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(persistController, classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(banList.length, validationErrors.size());
  }

  @After
  public void teardown() {

  }
}
