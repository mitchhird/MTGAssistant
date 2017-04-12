package unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import db.DBPersistanceController;
import models.cardModels.Format;
import models.deckModels.Deck;
import models.validatorModels.ConstructedDeckValidator;
import models.validatorModels.DeckValidator;
import models.validatorModels.FreeFormDeckValidator;
import models.validatorModels.SingletonDeckValidator;
import models.validatorModels.ValidatorFactory;

public class ValidatorFactoryTest {

  private static ValidatorFactory classUnderTest;
  private static DBPersistanceController persistanceController;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    persistanceController = DBPersistanceController.getInstance();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {}

  @Test
  public void testGetValidatorForSingletonDeck() {
    Format[] singletonFormats = { Format.HIGHLANDER, Format.COMMANDER };

    for (Format f : singletonFormats) {
      Deck deckToTest = new Deck();
      deckToTest.setDeckFormat(f);
      DeckValidator deckValidator = ValidatorFactory.getValidatorForDeck(persistanceController, deckToTest);
      assertTrue(deckValidator instanceof SingletonDeckValidator);
      assertFalse(deckValidator instanceof ConstructedDeckValidator);
      assertFalse(deckValidator instanceof FreeFormDeckValidator);
    }
  }
  
  @Test
  public void testGetValidatorForFreeFormDeck() {
    Format[] singletonFormats = { Format.FREEFORM };

    for (Format f : singletonFormats) {
      Deck deckToTest = new Deck();
      deckToTest.setDeckFormat(f);
      DeckValidator deckValidator = ValidatorFactory.getValidatorForDeck(persistanceController, deckToTest);
      assertTrue(deckValidator instanceof FreeFormDeckValidator);
      assertFalse(deckValidator instanceof ConstructedDeckValidator);
      assertFalse(deckValidator instanceof SingletonDeckValidator);
    }
  }



  @Test
  public void testGetValidatorForConstructedDeck() {
    Format[] constructedFormats = { Format.LEGACY, Format.MODERN, Format.STANDARD };
    for (Format f : constructedFormats) {
      Deck deckToTest = new Deck();
      deckToTest.setDeckFormat(f);
      DeckValidator deckValidator = ValidatorFactory.getValidatorForDeck(persistanceController, deckToTest);
      assertTrue(deckValidator instanceof ConstructedDeckValidator);
      assertFalse(deckValidator instanceof SingletonDeckValidator);
      assertFalse(deckValidator instanceof FreeFormDeckValidator);
    }
  }
}
