package util;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import models.cardModels.Card;

/**
 * Static Helper Class That Helps Varying Parts Of The Code Base 
 */
public class MTGHelper {
  public static String generateCardKey (Card incomingCard) {
    return incomingCard.getName();
  }
  
  // Returns A Listing Of All Files Matching A Particular Extension
  public static List<Path> collectFilesInMatchingExtension (Path dir, String extension) {
    List<Path> files = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{"+ extension + "}")) {
        for (Path entry: stream) {
          files.add(entry);
        }
    } catch (IOException x) {}
    return files;
  }
}
