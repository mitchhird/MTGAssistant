package models.statisticModels;

import java.util.Map;

/**
 * Base Class For The Statistics Calculators
 * @author Mitchell
 */
public abstract class BaseStatsCalculator {
  protected abstract void initMaps();
  protected abstract void collectGraphDataSets();
  
  // Uses Generics To Put Everything Into One Method Call
  protected <T extends Object> void addOrIncrementMap(Map<T, Integer> map, T key, Integer val) {
    Integer existing = map.get(key);
    if (existing == null) {
      map.put(key, val);
    }
    else {
      map.put(key, existing + val);
    }
  }
}
