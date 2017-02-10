package models.deckModels;

import java.util.LinkedHashMap;
import java.util.Map;

import models.cardModels.Card;
import models.cardModels.Format;

/**
 * Class that is the main focal point of the project, this simple model contains the deck that user will be creating
 * 
 * @author Mitchell
 */
public class Deck implements Comparable<Deck> {
  private String creatingUser;
  private String deckName;
  private String deckArchetype;
  private String deckDescription;
  private Format deckFormat;
  private Map<Card, Integer> cardsWithinDeck;

  public Deck() {
    this.creatingUser = "";
    this.deckName = "";
    this.deckDescription = "";
    this.deckArchetype = "";
    this.deckFormat = Format.STANDARD;
    cardsWithinDeck = new LinkedHashMap<>();
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

  // Adds A Card To The Deck, It The Card Already Exists, Then It's Quantity Value Is Updated
  public void addCardToDeck(Card incomingCard) {
    if (cardsWithinDeck.containsKey(incomingCard)) {
      Integer quanity = cardsWithinDeck.get(incomingCard);
      cardsWithinDeck.put(incomingCard, quanity + 1);
    }
    else {
      cardsWithinDeck.put(incomingCard, 1);
    }
  }

  public void addCardToDeck(Card incomingCard, int quantity) {
    cardsWithinDeck.put(incomingCard, quantity);
  }

  public Map<Card, Integer> getCardsWithinDeck() {
    if (cardsWithinDeck == null) {
      cardsWithinDeck = new LinkedHashMap<>();
    }
    return cardsWithinDeck;
  }

  @Override
  public String toString() {
    return deckName + " (" + deckFormat + "): Created by: " + creatingUser;
  }

  @Override
  public int compareTo(Deck o) {
    int deckFormatComparision = o.deckFormat.compareTo(deckFormat);
    if (deckFormatComparision != 0)
      return deckFormatComparision;

    int deckNameComparision = o.deckName.compareTo(deckName);
    return deckNameComparision;
  }
}
