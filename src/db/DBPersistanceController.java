package db;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import util.Constants;
import util.MTGHelper;
import util.JSONConvertTools.JSONCard;
import util.JSONConvertTools.JSONFileReader;
import util.JSONConvertTools.JSONSet;

/**
 * Main controller class that is responsible for handling persistent information. Uses both a singleton pattern
 * and a mediator pattern to mediate behaviour onto other tools that are connected to the system
 * @author Mitchell
 *
 */
public class DBPersistanceController {

  private Connection database;

  private DBSetTool setTool;
  private DBCardTool cardTool;
  private DBDeckTool deckTool;
  
  private List<DBTool> databaseTools;
  private static DBPersistanceController instance;

  // Default Constructor
  private DBPersistanceController() {
    initDatabase();
    initTools();
    createTablesIfNeeded();
  }

  // Method Responsible For Creating The DB
  private void initDatabase() {
      Path databaseLocation = Paths.get("database/" + Constants.CENTRAL_DB_NAME);
      initDatabase(databaseLocation);
  }
  
  // Initializes The Database For The Given Path
  private void initDatabase (Path creationPath) {
    try {
      Class.forName("org.sqlite.JDBC");
      database = DriverManager.getConnection("jdbc:sqlite:" + creationPath.toAbsolutePath().toString());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
 
  // Method Responsible For Initializing The Tools For The DB
  private void initTools() {
    setTool = new DBSetTool(this);
    cardTool = new DBCardTool(this);
    deckTool = new DBDeckTool(this);
    
    databaseTools = new ArrayList<>();
    databaseTools.add(setTool);
    databaseTools.add(cardTool);
    databaseTools.add(deckTool);
  }

  // Runs through the connection strings and creates all of the tables associated with them
  private void createTablesIfNeeded() {
    for (DBTool dTool : databaseTools) {
      for (String s : dTool.getDBCreationStrings()) {
        try (PreparedStatement p = getStatement(s);) {
          p.execute();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
  }

  // Closes The Connection To The Database When Called
  public void closeDBConnection() {
    try {
      database.close();
      instance = null;
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public PreparedStatement getStatement(String statementToExecute) throws SQLException {
    return database.prepareStatement(statementToExecute);
  }

  // Singleton Pattern For Accessing The DBPersistenceController
  public static DBPersistanceController getInstance() {
    if (instance == null) {
      instance = new DBPersistanceController();
    }
    return instance;
  }

  // Call To Add A Set To The DB
  public void addSetToDB(JSONSet mtgSet) {
    setTool.addJSONSetToDB(mtgSet);
  }
  
  // Call To Convert A JSON Card To 
  public void addCardToDB (JSONCard card) {
    cardTool.addJSONCard(card);
  }
  
  // Method Responsible For Loading In The JSON Library And Converting In It Into SQL
  public void loadInJSONDBIfNecessary () {
    Path rootPath = Paths.get("./external_resources/JSON_DB_FILES");
    List<Path> files = MTGHelper.collectFilesInMatchingExtension(rootPath, "json");
    for (Path p: files) {
      try {
        JSONSet nextJSONSet = JSONFileReader.readJSONFile(p, JSONSet.class);
        addSetToDB(nextJSONSet);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    System.out.println();
  }

  public static void main(String[] args) throws Exception {
    DBPersistanceController dpc = DBPersistanceController.getInstance();
    dpc.loadInJSONDBIfNecessary();
  }
}
