package db;

import java.util.List;

/**
 * Base class for all database tools. The class is responsible for holding all common and abstract methods
 * @author Mitchell
 */
public abstract class DBTool {
  public static int DB_CHAR_COLUMN_LIMIT = 255;

  protected final DBPersistanceController parentController;
  
  // Default Constructor For The Base Class
  protected DBTool (DBPersistanceController parentController) {
    this.parentController = parentController;
  }
  
  public abstract List<String> getDBCreationStrings();
}
