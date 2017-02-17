package models;

import static org.junit.Assert.assertTrue;
import models.cardModels.Format;
import models.deckModels.Deck;
import models.validatorModels.ConstructedDeckValidator;
import models.validatorModels.DeckValidator;
import models.validatorModels.SingletonDeckValidator;
import models.validatorModels.ValidatorFactory;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorFactoryTest {

  private static ValidatorFactory classUnderTest;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {}

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Test
  public void testGetValidatorForSingletonDeck() {
    Format[] singletonFormats = { Format.HIGHLANDER, Format.COMMANDER };

    for (Format f : singletonFormats) {
      Deck deckToTest = new Deck();
      deckToTest.setDeckFormat(f);
      DeckValidator deckValidator = ValidatorFactory.getValidatorForDeck(deckToTest);
      assertTrue(deckValidator instanceof SingletonDeckValidator);
    }
  }


  @Test
  public void testGetValidatorForConstructedDeck() {
    Format[] constructedFormats = { Format.LEGACY, Format.MODERN, Format.STANDARD };
    for (Format f : constructedFormats) {
      Deck deckToTest = new Deck();
      deckToTest.setDeckFormat(f);
      DeckValidator deckValidator = ValidatorFactory.getValidatorForDeck(deckToTest);
      assertTrue(deckValidator instanceof ConstructedDeckValidator);
    }
  }
}
