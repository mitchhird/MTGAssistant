package models.deckModels;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import models.cardModels.Card;
import models.cardModels.Format;

/**
 * Class that is the main focal point of the project, this simple model contains the deck that user
 * will be creating
 * @author Mitchell
 */
public class Deck {
  private String creatingUser;
  private String deckName;
  private String deckArchetype;
  private String deckDescription;
  private Format deckFormat;
  private Map<Card, Integer> cardsWithinDeck;
  
  public Deck () {
    this.creatingUser = "";
    this.deckName = "";
    this.deckDescription = "";
    this.deckArchetype = "";
    this.deckFormat = Format.STANDARD;
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
  
  public String getDeckArchetype() {
    return deckArchetype;
  }

  public void setDeckArchetype(String deckArchetype) {
    this.deckArchetype = deckArchetype;
  }

  public void setCreatingUser(String creatingUser) {
    this.creatingUser = creatingUser;
  }

  public void setDeckName(String deckName) {
    this.deckName = deckName;
  }

  public void setDeckDescription(String deckDescription) {
    this.deckDescription = deckDescription;
  }

  public void setDeckFormat(Format deckFormat) {
    this.deckFormat = deckFormat;
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
