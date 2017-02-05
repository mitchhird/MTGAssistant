package models;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import models.cardModels.Card;
import models.cardModels.CardRarity;
import models.cardModels.Format;
import models.deckModels.Deck;
import models.validatorModels.DeckValidator;
import models.validatorModels.ValidatorFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import util.TestUtil;

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
  public void testBasicGettersAndSetters () {
    assertEquals(testCreatingUser, classUnderTest.getCreatingUser());
    assertEquals(testDeckDescription, classUnderTest.getDeckDescription());
    assertEquals(testDeckName, classUnderTest.getDeckName());
    assertEquals(testFormat, classUnderTest.getDeckFormat());
    assertEquals(0, classUnderTest.getCardsWithinDeck().size());
  }
  
  @Test
  public void testSingletonValidator () {
    addCardToDeck("Island", CardRarity.BASIC_LAND, 100);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertTrue(validationErrors.isEmpty());
  }
  
  @Test
  public void testSingletonValidatorInvalidQuant() {
    addCardToDeck("Island", CardRarity.BASIC_LAND, 98);
    addCardToDeck("Force of Will", CardRarity.RARE, 2);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(1, validationErrors.size());
  }
  
  @Test
  public void testSingletonValidatorInvalidTotal() {
    addCardToDeck("Island", CardRarity.BASIC_LAND, 100);
    addCardToDeck("Force of Will", CardRarity.RARE, 1);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(1, validationErrors.size());
  }
  
  @Test
  public void testValidatorsInvalidDueToBan() {
    Format[] testFormats = {Format.COMMANDER, Format.VINTAGE, Format.MODERN};
    for (Format f: testFormats) {
      classUnderTest.getCardsWithinDeck().clear();
      classUnderTest.setDeckFormat(f);
      testBanListValidation(TestUtil.getBanListForFormat(f));
    }
  }
  
  @Test
  public void testValidatorInvalidDueToRestricted() {
    classUnderTest.setDeckFormat(Format.VINTAGE);
    for (String s: TestUtil.VINTAGE_RESTRICTED_LIST) {
      addCardToDeck(s, CardRarity.SPECIAL, 2);
    }
    addCardToDeck("Island", CardRarity.BASIC_LAND, 60 - TestUtil.VINTAGE_RESTRICTED_LIST.length);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(TestUtil.VINTAGE_RESTRICTED_LIST.length, validationErrors.size());
  }

  
  // Simple test method for adding cards to deck
  private void addCardToDeck (String cardName, CardRarity rarity, int quantity) {
    for (int i = 0; i < quantity; i++)  {
      Card cardToAdd = new Card(cardName);
      cardToAdd.setCardRarity(rarity);
      cardToAdd.setText("test");
      classUnderTest.addCardToDeck(cardToAdd);
    }
  }
  
  private void testBanListValidation(String[] banList) {
    for (String s: banList) {
      addCardToDeck(s, CardRarity.SPECIAL, 1);
    }
    addCardToDeck("Island", CardRarity.BASIC_LAND, 100 - banList.length);
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(classUnderTest);
    List<String> validationErrors = validator.validateDeck(classUnderTest);
    assertFalse(validationErrors.isEmpty());
    assertEquals(banList.length, validationErrors.size());
  }
  
  @After
  public void teardown() {
    
  }
}
