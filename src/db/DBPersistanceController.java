package db;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.cardModels.MagicSet;
import util.JSONFileReader;

public class DBPersistanceController {

	private Connection database;
	
	private DBSetTool setTool;
	private List<DBTool> databaseTools;
	private static DBPersistanceController instance;
	
	// Constants
	private static final String DB_NAME = "MTG_Assistant_DB";
	
	// Default Constructor
	private DBPersistanceController() {
	  initDatabase();
	  initTools();
	  createTablesIfNeeded();
	}
	
	// Method Responsible For Creating The DB
	private void initDatabase() {
		try {
			Class.forName("org.sqlite.JDBC");
			Path databaseLocation = Paths.get("database/" + DB_NAME);
			database = DriverManager.getConnection("jdbc:sqlite:" + databaseLocation.toAbsolutePath().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Method Responsible For Initializing The Tools For The DB
	private void initTools() {
	  setTool = new DBSetTool(this);

	  databaseTools = new ArrayList<>();
	  databaseTools.add(setTool);
	}
	
	private void createTablesIfNeeded() {
	  for (DBTool dTool: databaseTools) {
	    for (String s: dTool.getDBCreationStrings()) {
	      try (PreparedStatement p = getStatement(s);){
	        p.execute();
	      } catch (SQLException e) {
          e.printStackTrace();
        }
	    }
	  }
	}
	
	// Closes The Connection To The Database When Called
	public void closeDBConnection () {
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
	
	public void addSetToDB (MagicSet mtgSet) {
	  setTool.addSetToDB(mtgSet);
	}
	
	public static void main (String[] args) throws Exception {
	  DBPersistanceController dpc = DBPersistanceController.getInstance();
	  Path p = Paths.get("./external_resources/JSON_DB_FILES/LEA.json");
	  MagicSet set = JSONFileReader.readJSONFile(p, MagicSet.class);
	  dpc.addSetToDB(set);
	}
}
