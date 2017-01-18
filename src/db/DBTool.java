package db;

import java.util.List;

public abstract class DBTool {
  public static int DB_CHAR_COLUMN_LIMIT = 255;
  protected final DBPersistanceController parentController;
  
  protected DBTool (DBPersistanceController parentController) {
    this.parentController = parentController;
  }
  
  public abstract List<String> getDBCreationStrings();
}
