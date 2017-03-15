package models.deckModels;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import models.cardModels.Card;
import models.cardModels.Format;
import util.MTGHelper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class that is the main focal point of the project, this simple model contains the deck that user will be creating
 * 
 * @author Mitchell
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Deck implements Comparable<Deck>, Serializable {

  protected String creatingUser;
  protected String deckName;
  protected String deckArchetype;
  protected String deckDescription;
  protected Format deckFormat;
  protected Map<String, DeckCardDataObject> cardsWithinDeck;
  protected static final long serialVersionUID = 1L;

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
    addCardToDeck(incomingCard, 1);
  }

  public void addCardToDeck(Card incomingCard, int quantity) {
    if (cardsWithinDeck.containsKey(incomingCard)) {
      DeckCardDataObject cardInDeck = cardsWithinDeck.get(MTGHelper.generateCardKey(incomingCard));
      cardInDeck.setQuantityOfCard(cardInDeck.getQuantityOfCard() + quantity);
    }
    else {
      DeckCardDataObject newAddition = new DeckCardDataObject(incomingCard, quantity);
      cardsWithinDeck.put(MTGHelper.generateCardKey(incomingCard), newAddition);
    }
  }
  
  // Removes A Particular Quantity Of Card From A Deck. If The Card Isn't Present, Then Nothing Happens
  public void removeCardFromDeck (Card incomingCard, int quantity) {
    DeckCardDataObject cardInDeck = cardsWithinDeck.get(MTGHelper.generateCardKey(incomingCard));
    if (cardInDeck != null) {
      if (cardInDeck.getQuantityOfCard() - quantity <= 0) {
        cardsWithinDeck.remove(MTGHelper.generateCardKey(incomingCard));
      } else {
        cardInDeck.setQuantityOfCard(cardInDeck.getQuantityOfCard() - quantity);
      }
    }
  }

  public Map<String, DeckCardDataObject> getCardsWithinDeck() {
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
