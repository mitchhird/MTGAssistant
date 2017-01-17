package util;

import models.cardModels.Card;

/**
 * Static Helper Class That Helps Varying Parts Of The Code Base 
 */
public class MTGHelper {
  public static String generateCardKey (Card incomingCard) {
    return incomingCard.getName();
  }
}
