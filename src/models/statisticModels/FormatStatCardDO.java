package models.statisticModels;

/**
 * Simple Resource That Holds Data For The Format Statistics
 * @author Mitchell
 */
public class FormatStatCardDO implements Comparable<FormatStatCardDO>{

  private final String cardName;
  private int cardCount;
  private int deckCount;
  
  public FormatStatCardDO (String cardName) {
    this.cardName = cardName;
    this.cardCount = 0;
    this.deckCount = 0;
  }  
  
  public void appendToCardCount (int quantity) { 
    this.cardCount += quantity;
  }
  
  public void incrementDeckCount () {
    deckCount++;
  }

  public String getCardName() {
    return cardName;
  }

  public int getCardCount() {
    return cardCount;
  }

  public int getDeckCount() {
    return deckCount;
  }

  @Override
  public int compareTo(FormatStatCardDO o) {
    return o.cardCount - this.cardCount;
  }
}
