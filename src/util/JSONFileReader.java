package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import models.cardModels.MagicSet;

public class JSONFileReader {

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
