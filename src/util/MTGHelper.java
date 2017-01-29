package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import models.cardModels.Card;

/**
 * Static Helper Class That Helps Varying Parts Of The Code Base 
 */
public class MTGHelper {
  
  private static BufferedImage mtgSymbolSpriteSheet;
  private static Map<String, BufferedImage> textToManaSymbolMap;
  
  // Initializes The Sprite Sheet If It Is Needed
  private static void initMTGSymbolSpriteSheet () {
    if (mtgSymbolSpriteSheet == null) {
      try {
        Path fileToLoad = Paths.get("external_resources/Mana.png");
        mtgSymbolSpriteSheet = ImageIO.read(fileToLoad.toFile());
        initTextManaSymbolMap();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
  
  //
  private static void initTextManaSymbolMap () {
    textToManaSymbolMap = new HashMap<String, BufferedImage>();
    textToManaSymbolMap.put("{U}", mtgSymbolSpriteSheet.getSubimage(500, 200, 100, 100));
  }
  
  public static BufferedImage getImageForManaSymbol (String manaSymbol) {
    initMTGSymbolSpriteSheet();
    return (textToManaSymbolMap.containsKey(manaSymbol)) ? textToManaSymbolMap.get(manaSymbol) : textToManaSymbolMap.get("{U}");
  }
  
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
