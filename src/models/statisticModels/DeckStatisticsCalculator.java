package models.statisticModels;

import java.util.HashMap;
import java.util.Map;

import models.cardModels.Card;
import models.deckModels.Deck;
import models.deckModels.DeckCardDataObject;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class DeckStatisticsCalculator extends BaseStatsCalculator {

  // Variables That Statistics Calculator Use
  private Map<String, Integer> colourMap;
  private Map<String, Integer> typeMap;
  private Map<Integer, Integer> cmcMap;
  private DefaultCategoryDataset colourDataSet;
  private DefaultPieDataset typeDataSet;
  private DefaultCategoryDataset cmcDataSet;

  // Default Constructor For The Calculator
  public DeckStatisticsCalculator() {
    initMaps();
  }

  @Override
  protected void initMaps() {
    colourMap = new HashMap<String, Integer>();
    cmcMap = new HashMap<Integer, Integer>();
    typeMap = new HashMap<String, Integer>();
    colourDataSet = new DefaultCategoryDataset();
    typeDataSet = new DefaultPieDataset();
    cmcDataSet = new DefaultCategoryDataset();
  }

  // Used To Calculate Deck Statistics
  public void calcDeckStatistics(Deck incomingDeck) {
    initMaps();
    collectMapInformation(incomingDeck);
    collectGraphDataSets();
  }

  // Collects Our Graphing Datasets, So We Can Graph Them...
  @Override
  protected void collectGraphDataSets() {
    for (String s : colourMap.keySet()) {
      colourDataSet.addValue(colourMap.get(s), "", s);
    }
    for (String s : typeMap.keySet()) {
      typeDataSet.setValue(s, typeMap.get(s));
    }
    for (Integer i : cmcMap.keySet()) {
      cmcDataSet.addValue(cmcMap.get(i), "", i);
    }
  }

  // Collects The Information That We Will Be Using Within Our Map
  private void collectMapInformation(Deck incomingDeck) {
    for (String c : incomingDeck.getCardsWithinDeck().keySet()) {
      DeckCardDataObject deckCardDataObject = incomingDeck.getCardsWithinDeck().get(c);
      Card cardInDeck = deckCardDataObject.getCardInDeck();
      int quantity = deckCardDataObject.getQuantityOfCard();
      
      for (String color : cardInDeck.getColors()) {
        addOrIncrementMap(colourMap, color, quantity);
      }

      for (String type : cardInDeck.getTypes()) {
        addOrIncrementMap(typeMap, type, quantity);
      }

      addOrIncrementMap(cmcMap, cardInDeck.getCardCMC(), quantity);
    }
  }

  /*********************************************************************************************************
   *                                    GETTERS AND SETTERS BELOW HERE                                     *
   *********************************************************************************************************/
  public Map<String, Integer> getColourMap() {
    return colourMap;
  }

  public Map<String, Integer> getTypeMap() {
    return typeMap;
  }

  public Map<Integer, Integer> getCmcMap() {
    return cmcMap;
  }

  public DefaultCategoryDataset getColourDataSet() {
    return colourDataSet;
  }

  public DefaultPieDataset getTypeDataSet() {
    return typeDataSet;
  }

  public DefaultCategoryDataset getCmcDataSet() {
    return cmcDataSet;
  }
  
  
}
