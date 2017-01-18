package util;

import java.util.List;

import models.cardModels.MagicSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JSONSet extends MagicSet {
  private List<JSONCard> cards;

  /**
   * @return the cards
   */
  public List<JSONCard> getCards() {
    return cards;
  }

  /**
   * @param cards the cards to set
   */
  public void setCards(List<JSONCard> cards) {
    this.cards = cards;
  }
}
