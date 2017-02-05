package models.validatorModels;

import java.util.List;
import java.util.Map;

import models.cardModels.Card;
import models.cardModels.CardRarity;
import models.deckModels.Deck;
import db.DBPersistanceController;

/**
 * Base Class For The Deck Validators
 * @author Mitchell
 */
public abstract class DeckValidator {
  protected static DBPersistanceController PM_CONTROLLER = DBPersistanceController.getInstance();
  
  /**
   * Tests if the deck's contents exceeds the playset limit on the format
   * @param validationErrors
   * @param cardsInDeck
   * @param c
   */
  protected void testPlaysetQunanity(List<String> validationErrors, Map<Card, Integer> cardsInDeck, Card c, int maxAllowed) {
    int quantity = cardsInDeck.get(c);
    boolean isAnUnlimitedCard = c.getName().equals("Relentless Rats") || c.getName().equals("Shadowborn Apostle");
    if (c.getCardRarity() != CardRarity.BASIC_LAND && quantity > maxAllowed && !isAnUnlimitedCard) {
      validationErrors.add("Validation Error: Number of " + c.getName() + " exceeds the limit of " + maxAllowed);
    }
  }
  
  /**
   * Tests all of the cards to see if their banned or exceeding the restricted listed on the format
   */
  public void testBanAndRestrictedRules(Deck incomingDeck, List<String> validationErrors, Map<Card, Integer> cardsInDeck, Card c) {
    if (!PM_CONTROLLER.isCardLegalInFormat(c, incomingDeck.getDeckFormat())) {
      if (PM_CONTROLLER.isCardRestrictedInFormat(c, incomingDeck.getDeckFormat())) {
        int quantity = cardsInDeck.get(c);
        if (quantity > 1) {
          validationErrors.add("Validation Error: " + c.getName() + " exceeds the restriction limit of 1");
        }
      } else if (PM_CONTROLLER.isCardBannedInFormat(c, incomingDeck.getDeckFormat())) {
        validationErrors.add("Validator Error: " + c.getName() + " is banned in " + incomingDeck.getDeckFormat());
      }
    }
  }
  
  /**
   * Simple boolean call that returns if the deck is valid or not
   * @param incomingDeck
   * @return
   */
  public boolean isDeckValid(Deck incomingDeck) {
    return validateDeck(incomingDeck).isEmpty();
  }
  
  /*****************************************************************************************************************************************
   *                                                  ABSTRACT METHODS                                                                     *
   *****************************************************************************************************************************************/
  public abstract List<String> validateDeck (Deck incomingDeck);
  protected abstract void testDeckSize (List<String> errors, int currentCardCount);
}
