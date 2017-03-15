package util;

import java.nio.file.Path;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A standard static access class that is responsible for serializing and deserializing models
 * @author Mitchell
 */
public class ModelHelper {

  private static final ObjectMapper OBJ_MAPPER = new ObjectMapper();
  
  /**
   * Returns a model instance from a provided set of JSON data
   * @param jsonString
   * @param modelToReturn
   * @return
   * @throws Exception
   */
  public static <T> T toModelFromJSON (String jsonString, Class<T> modelToReturn) throws Exception {
    T returnVal = OBJ_MAPPER.readValue(jsonString, modelToReturn);
    return returnVal;
  }
  
  /**
   * Returns A Model Instance From A Provided JSON File
   * @param fileToLoad
   * @param modelToReturn
   * @return
   * @throws Exception
   */
  public static <T> T toModelFromJSONFile (Path fileToLoad, Class<T> modelToReturn) throws Exception {
    T returnVal = OBJ_MAPPER.readValue(fileToLoad.toFile(), modelToReturn);
    return returnVal;
  }
  
  /**
   * Returns the JSON Representation Of A Model 
   * @param modelToParse
   * @return
   * @throws JsonProcessingException
   */
  public static <T> String toJSONFromModel (T modelToParse) {
    try {
      return OBJ_MAPPER.writeValueAsString(modelToParse);
    } catch (JsonProcessingException e) {
      return null;
    }
  }
}
