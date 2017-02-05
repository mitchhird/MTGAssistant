package models.validatorModels;

import models.deckModels.Deck;

/**
 * Simple Factory Class That Is Responsible For Creating Validators For Given Decks
 * @author Mitchell
 */
public class ValidatorFactory {
  public static DeckValidator getValidatorForDeck(Deck deckToValidate) {
     switch (deckToValidate.getDeckFormat()) {
      case COMMANDER: return new SingletonDeckValidator();
      case HIGHLANDER: return new SingletonDeckValidator();
      default: return new ConstructedDeckValidator();
     }
  }
}
