package db;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import models.cardModels.Card;
import models.cardModels.Format;
import models.cardModels.MagicSet;
import models.deckModels.Deck;
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
  private DBLegalityTool legalTool;
  
  private List<DBTool> databaseTools;
  private static DBPersistanceController instance;

  private DBPersistanceController() {
    initDatabase(Constants.CLIENT_DB);
    initTools();
    createTablesIfNeeded();
  }
  
  // Default Constructor
  private DBPersistanceController(String dbName) {
    initDatabase(dbName);
    initTools();
    createTablesIfNeeded();
  }

  // Method Responsible For Creating The DB
  private void initDatabase(String dbName) {
      Path databaseLocation = Paths.get("database/" + dbName);
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
    legalTool = new DBLegalityTool(this);
    
    databaseTools = new ArrayList<>();
    databaseTools.add(setTool);
    databaseTools.add(cardTool);
    databaseTools.add(deckTool);
    databaseTools.add(legalTool);
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

  public void commitData() {
    try {
      database.commit();
    } catch (SQLException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  // Singleton Pattern For Accessing The DBPersistenceController
  public static DBPersistanceController getInstance() {
    if (instance == null) {
      instance = new DBPersistanceController();
    }
    return instance;
  }
  
  public static DBPersistanceController getInstance(String name) {
    if (instance == null) {
      instance = new DBPersistanceController(name);
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
  
  // Call To Add A Set To The DB
  public void addDeckToDB (Deck incomingDeck) {
    deckTool.addDeckToDB(incomingDeck);
  }
  
  // Call To Add Legalities To The DB
  public void addLegalitiesToDB(JSONCard card) {
    legalTool.insertIntoLegalTable(card);
  }

  // Call To Return All Decks Currently In The DB
  public Deck getIndividualDeck (String creatingUser, String deckName) {
    return deckTool.getIndividualDeck(creatingUser, deckName);
  }
 
  // Call To Return Whether Or Not A Deck Exists In The DB
  public boolean isDeckInDB (Deck testDeck) {
    return deckTool.doesDeckExist(testDeck.getCreatingUser(), testDeck.getDeckName());
  }
  
  // Call To Return Whether Or Not A Deck Exists In The DB
  public boolean isDeckInDB (String creatingUser, String deckName) {
    return deckTool.doesDeckExist(creatingUser, deckName);
  }
  
  // Call To Get The Deck Contents From The DB
  public void populateDeckContents (Deck incomingDeck) {
    deckTool.getDeckContentsFromDB(incomingDeck);
  }
  
  // Call To Return Decks That Match The Searc
  public List<Deck> getDecksByFormatNoContent (Format formatToSearch) {
    List<Deck> decksByFormat = deckTool.getDecksByFormatNoContent(formatToSearch);
    Collections.sort(decksByFormat);
    return decksByFormat;
  }
  
  // Call To Return All Decks Currently In The DB
  public List<Deck> getAllDecksInDB () {
    List<Deck> allDecksFromDB = deckTool.getAllDecksFromDB();
    Collections.sort(allDecksFromDB);
    return allDecksFromDB;
  }
  
  // Call To Return All Sets Currently In The Db
  public List<MagicSet> getAllMagicSetsInDB() {
    return setTool.getAllMagicSets();
  }
  
  // Call To Get A Specific List Of Cards Back From The DB
  public List<Card> getFilteredCards (Set<DBCardSearchDataObject> searchOptions) {
    return cardTool.getFilteredCards(searchOptions);
  }
  
  // Call To Return All Of Current Card Names
  public Set<String> getAllCardNames () {
    return cardTool.getAllCardNames();
  }
  
  // Returns Whether Or Not A Card Is Legal In A Given Format
  public boolean isCardLegalInFormat (Card incomingCard, Format formatToCheck) {
    return legalTool.isCardLegalInFormat(incomingCard, formatToCheck);
  }

  // Returns Whether Or Not A Card Is Banned In A Given Format
  public boolean isCardBannedInFormat (Card incomingCard, Format formatToCheck) {
    return legalTool.isCardBannedInFormat(incomingCard, formatToCheck);
  }
  
  // Returns Whether Or Not A Card Is Restricted In A Given Format
  public boolean isCardRestrictedInFormat (Card incomingCard, Format formatToCheck) {
    return legalTool.isCardRestrictedInFormat(incomingCard, formatToCheck);
  }
  
  // Call To Delete Deck From The DB
  public void deleteDeckFromDB (Deck incomingDeck) {
    deckTool.deleteDeckFromDB(incomingDeck);
  }
  
  // Call To Delete Deck From The DB
  public void deleteDeckFromDB (String creatingUser, String deckName) {
    deckTool.deleteDeckFromDB(creatingUser, deckName);
  }
    
  // Call To Run Clean Up Data From The Existing Database
  public void clearDatabase() {
    deckTool.deleteAllDecksFromDB();
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
    DBPersistanceController dpc = DBPersistanceController.getInstance(Constants.CLIENT_DB);
    dpc.database.setAutoCommit(false);
    dpc.loadInJSONDBIfNecessary();
  }
}
