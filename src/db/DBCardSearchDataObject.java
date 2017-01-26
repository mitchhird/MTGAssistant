package db;

public class DBCardSearchDataObject {
  private String DBColumnKey;
  private String DBSearchValue;
  
  public DBCardSearchDataObject (String key, String value) {
     DBColumnKey = key;
     DBSearchValue = value;
  }

  /**
   * @return the dBColumnKey
   */
  public String getDBColumnKey() {
    return DBColumnKey;
  }

  /**
   * @return the dBSearchValue
   */
  public String getDBSearchValue() {
    return DBSearchValue;
  }
}
