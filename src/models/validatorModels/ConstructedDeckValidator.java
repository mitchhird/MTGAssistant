package models.validatorModels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import db.DBPersistanceController;
import models.deckModels.Deck;
import models.deckModels.DeckCardDataObject;
import util.Constants;

/**
 * Validator Responsible For Validating Each Constructed Deck Created By Users.
 * Constructed decks (IE: Modern, Vintage, Standard and Legacy) must adhere to the following rules:
 *  --- Each deck must contain NO CARDS that are BANNED in their respective formats
 *  --- Each deck must contain EXACTLY ONE of any RESTRICTED cards in their current list
 *  --- Each deck must contain 60 or more cards within it
 *  --- Each deck must contain UP TO FOUR of any card that is NOT A BASIC_LAND
 *
 * @author Mitchell
 */
public class ConstructedDeckValidator extends DeckValidator{

  // Default Constructor For The Deck Validator Class
  protected ConstructedDeckValidator(DBPersistanceController controller) {
    super(controller);
  }

  @Override
  public List<String> validateDeck(Deck incomingDeck) {
    int cardCounter = 0;
    List<String> validationErrors = new ArrayList<String>();
    
    Map<String, DeckCardDataObject> cardsInDeck = incomingDeck.getCardsWithinDeck();
    for (String c: cardsInDeck.keySet()) {
      DeckCardDataObject nextCard = cardsInDeck.get(c);
      testPlaysetQunanity(validationErrors, nextCard, Constants.CONSTRUCTED_MAX_PLAY_SET);
      testBanAndRestrictedRules(incomingDeck, validationErrors, nextCard);
      cardCounter += nextCard.getQuantityOfCard();
    }
    
    testDeckSize(validationErrors, cardCounter);
    return validationErrors;
  }

  /**
   * Make Sure We Don't Exceed The Min Deck Size
   * @param validationErrors
   * @param currentCardCount
   * @param minCardLimit
   */
  @Override
  protected void testDeckSize( List<String> validationErrors, int currentCardCount) {
    if (currentCardCount < Constants.CONSTRUCTED_MIN_DECK_SIZE) {
      validationErrors.add("Validation Error: Deck doesn't contain the minimum of "+ Constants.CONSTRUCTED_MIN_DECK_SIZE + " cards");
    }
  }

}
