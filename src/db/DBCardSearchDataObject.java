package db;

/**
 * Simple data store object that contains dynamic search parameter data
 * @author Mitchell
 */
public class DBCardSearchDataObject {
  private String DBColumnKey;
  private String DBSearchValue;
  private boolean andTerm;
  
  public DBCardSearchDataObject (String key, String value) {
     initVars(key, value, true);
  }
  
  public DBCardSearchDataObject (String key, String value, boolean andTerm) {
    initVars(key, value, andTerm);
  }

  private void initVars(String key, String value, boolean andTerm) {
      DBColumnKey = key;
     DBSearchValue = value;
     this.andTerm = andTerm;
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

  public boolean isAndTerm() {
    return andTerm;
  }
}
