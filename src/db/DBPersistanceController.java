package db;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBPersistanceController {

	private Connection database;
	private static DBPersistanceController instance;
	
	// Constants
	private static final String DB_NAME = "MTG_Assistant_DB";
	
	// Default Constructor
	private DBPersistanceController() {
	  initDatabase();
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
	
	// Closes The Connection To The Database When Called
	public void closeDBConnection () {
	  try {
      database.close();
      instance = null;
    } catch (SQLException e) {
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
}
