package models.deckModels;

import java.util.HashMap;
import java.util.Map;

import models.cardModels.Card;
import models.cardModels.Format;

/**
 * Class that is the main focal point of the project, this simple model contains the deck that user
 * will be creating
 * @author Mitchell
 */
public class Deck {
  private final String creatingUser;
  private final String deckName;
  private final String deckDescription;
  private final Format deckFormat;
  private Map<Card, Integer> cardsWithinDeck;
  
  public Deck (String creatingUser, String description, String deckName, Format deckFormat) {
    this.creatingUser = creatingUser;
    this.deckName = deckName;
    this.deckDescription = description;
    this.deckFormat = deckFormat;
  }

  public String getCreatingUser() {
    return creatingUser;
  }

  public String getDeckDescription() {
    return deckDescription;
  }

  public Format getDeckFormat() {
    return deckFormat;
  }
  
  public String getDeckName() {
    return deckName;
  }

  // Adds A Card To The Deck, It The Card Already Exists, Then It's Quanity Value Is Updated
  public void addCardToDeck (Card incomingCard) {
    if (cardsWithinDeck.containsKey(incomingCard)) {
      Integer quanity = cardsWithinDeck.get(incomingCard);
      cardsWithinDeck.put(incomingCard, quanity + 1);
    } else {
      cardsWithinDeck.put(incomingCard, 1);
    }
  }

  public Map<Card, Integer> getCardsWithinDeck() {
    if (cardsWithinDeck == null) {
      cardsWithinDeck = new HashMap<>();
    }
    return cardsWithinDeck;
  }
}
