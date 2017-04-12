package models.validatorModels;

import db.DBPersistanceController;
import models.deckModels.Deck;

/**
 * Simple Factory Class That Is Responsible For Creating Validators For Given Decks
 * @author Mitchell
 */
public class ValidatorFactory {
  public static DeckValidator getValidatorForDeck(DBPersistanceController dbControl, Deck deckToValidate) {
     switch (deckToValidate.getDeckFormat()) {
      case COMMANDER: return new SingletonDeckValidator(dbControl);
      case HIGHLANDER: return new SingletonDeckValidator(dbControl);
      case FREEFORM: return new FreeFormDeckValidator(dbControl);
      default: return new ConstructedDeckValidator(dbControl);
     }
  }
}
