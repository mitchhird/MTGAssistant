package db;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// Tests For The DBPersistanceController Object
public class DBPersistanceControllerTest {
  
  private DBPersistanceController classUnderTest;
  
  @Before
  public void setup() {
    classUnderTest = DBPersistanceController.getInstance();
  }
  
  @Test
  public void basicTestOfTesting () {
    
  }
  
  @After
  public void teardown() {
    
  }
}
