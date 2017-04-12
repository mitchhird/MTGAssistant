package models.validatorModels;

import java.util.ArrayList;
import java.util.List;

import db.DBPersistanceController;
import models.deckModels.Deck;

/**
 * Validator That Is Used To Support Free-Range Formats
 * @author Mitchell
 */
public class FreeFormDeckValidator extends DeckValidator {

  protected FreeFormDeckValidator(DBPersistanceController controller) {
    super(controller);
  }

  @Override
  public List<String> validateDeck(Deck incomingDeck) {
    return new ArrayList<>();
  }

  @Override
  protected void testDeckSize(List<String> errors, int currentCardCount) {

  }

}
