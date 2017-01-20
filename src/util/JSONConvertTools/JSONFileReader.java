package util.JSONConvertTools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import util.ModelHelper;

/**
 * Static Helper Class That Helps Us Read In Files That We Wish To Convert
 * @author Mitchell
 */
public class JSONFileReader {

  // Reads The JSON File If It Exists And Returns An Instance Of The Class We Need
  public static <T> T readJSONFile(Path fileToRead, Class<T> returnClazz) throws Exception {
    if (Files.exists(fileToRead)) {
      T testSet = ModelHelper.toModelFromJSONFile(fileToRead, returnClazz);
      return testSet;
    }
    else {
      throw new IOException("File to read is not available");
    }
  }
}
