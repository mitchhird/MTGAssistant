package models.validatorModels;

import java.util.List;

import models.cardModels.Card;
import models.cardModels.CardRarity;
import models.deckModels.Deck;
import models.deckModels.DeckCardDataObject;
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
  protected void testPlaysetQunanity(List<String> validationErrors, DeckCardDataObject cardsInDeck, int maxAllowed) {
    int quantity = cardsInDeck.getQuantityOfCard();
    Card cardToTest = cardsInDeck.getCardInDeck();
    
    boolean isAnUnlimitedCard = cardToTest.getName().equals("Relentless Rats") || cardToTest.getName().equals("Shadowborn Apostle");
    if (cardToTest.getCardRarity() != CardRarity.BASIC_LAND && quantity > maxAllowed && !isAnUnlimitedCard) {
      validationErrors.add("Validation Error: Number of " + cardToTest.getName() + " exceeds the limit of " + maxAllowed);
    }
  }
  
  /**
   * Tests all of the cards to see if their banned or exceeding the restricted listed on the format
   */
  protected void testBanAndRestrictedRules(Deck incomingDeck, List<String> validationErrors, DeckCardDataObject deckCard) {
    Card actualCard = deckCard.getCardInDeck();
    int cardQuantity = deckCard.getQuantityOfCard();
    
    if (!PM_CONTROLLER.isCardLegalInFormat(actualCard, incomingDeck.getDeckFormat())) {
      if (PM_CONTROLLER.isCardRestrictedInFormat(actualCard, incomingDeck.getDeckFormat())) {
        int quantity = cardQuantity;
        if (quantity > 1) {
          validationErrors.add("Validation Error: " + actualCard.getName() + " exceeds the restriction limit of 1");
        }
      } else {
        validationErrors.add("Validation Error: " + actualCard.getName() + " is banned in " + incomingDeck.getDeckFormat());
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
