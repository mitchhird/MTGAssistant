package models.deckModels;

import models.cardModels.Card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DeckCardDataObject {
  protected Card cardInDeck;
  protected int quantityOfCard;
  
  public DeckCardDataObject() {
    // TODO Auto-generated constructor stub
  }
  
  public DeckCardDataObject(Card incomingCard, int quantity) {
    this.cardInDeck = incomingCard;
    this.quantityOfCard = quantity;
  }

  /**
   * @return the cardInDeck
   */
  public Card getCardInDeck() {
    return cardInDeck;
  }

  /**
   * @param cardInDeck the cardInDeck to set
   */
  public void setCardInDeck(Card cardInDeck) {
    this.cardInDeck = cardInDeck;
  }

  /**
   * @return the quantityOfCard
   */
  public int getQuantityOfCard() {
    return quantityOfCard;
  }

  /**
   * @param quantityOfCard the quantityOfCard to set
   */
  public void setQuantityOfCard(int quantityOfCard) {
    this.quantityOfCard = quantityOfCard;
  }
  
  @Override
  public String toString() {
    return "Card[" + cardInDeck.getName() + ", " + quantityOfCard + "]";
  }
}
