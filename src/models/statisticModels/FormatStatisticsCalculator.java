package models.statisticModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.cardModels.Card;
import models.cardModels.Format;
import models.deckModels.Deck;

import org.jfree.data.general.DefaultPieDataset;

import app.MTGAssistantClient;

public class FormatStatisticsCalculator extends BaseStatsCalculator {

  // Variables That Statistics Calculator Use
  private Map<String, FormatStatCardDO> creatureMap;
  private Map<String, FormatStatCardDO> spellsMap;
  private Map<String, Integer> typeMap;
  private DefaultPieDataset typeDataSet;
  private final MTGAssistantClient clientApp;

  // Default Constructor For The Calculator
  public FormatStatisticsCalculator(MTGAssistantClient clientApp) {
    this.clientApp = clientApp;
    initMaps();
  }

  @Override
  protected void initMaps() {
    creatureMap = new HashMap<String, FormatStatCardDO>();
    spellsMap = new HashMap<String, FormatStatCardDO>();
    typeMap = new HashMap<String, Integer>();
    typeDataSet = new DefaultPieDataset();
  }

  // Call To Calculate The Statistics
  public void calculateFormatStatistics(Format gameFormat) {
    initMaps();
    collectMapInformation(gameFormat);
    collectGraphDataSets();
  }

  // Collects Our Map Information
  protected void collectMapInformation(Format gameFormat) {
    List<Deck> decksInApp = clientApp.getDecksByFormat(gameFormat);
    for (Deck d : decksInApp) {
      clientApp.populateDeckContents(d);
      for (Card c : d.getCardsWithinDeck().keySet()) {
        Integer quantity = d.getCardsWithinDeck().get(c);
        if (!c.getTypes().contains("Land")) {
          Map<String, FormatStatCardDO> mapToUse = c.getTypes().contains("Creature") ? creatureMap : spellsMap;
          addOrIncrementToDOMap(mapToUse, c.getName(), quantity);
        }
        
        for (String s: c.getTypes()) {
          addOrIncrementMap(typeMap, s, quantity);
        }
      }
    }
  }

  // Uses Simple Logic To Add To Existing Map Values If They Exist
  protected void addOrIncrementToDOMap(Map<String, FormatStatCardDO> map, String key, Integer val) {
    FormatStatCardDO existing = map.get(key);
    if (existing == null) {
      FormatStatCardDO newDO = new FormatStatCardDO(key);
      newDO.appendToCardCount(val);
      newDO.incrementDeckCount();
      map.put(key, newDO);
    }
    else {
      existing.appendToCardCount(val);
      existing.incrementDeckCount();
    }
  }

  @Override
  protected void collectGraphDataSets() {
    for (String s : typeMap.keySet()) {
      typeDataSet.setValue(s, typeMap.get(s));
    }
  }
  
  /*********************************************************************************************************
   *                                    GETTERS AND SETTERS BELOW HERE                                     *
   *********************************************************************************************************/
  public List<FormatStatCardDO> getCreatureDetailsList (){
    List<FormatStatCardDO> returnVal = new ArrayList<>(creatureMap.values());
    Collections.sort(returnVal);
    return returnVal;
  }
  
  public List<FormatStatCardDO> getSpellDetailsList (){
    List<FormatStatCardDO> returnVal = new ArrayList<>(spellsMap.values());
    Collections.sort(returnVal);
    return returnVal;
  }

  public DefaultPieDataset getTypeDataSet() {
    return typeDataSet;
  }
}
