package util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import models.cardModels.Card;

/**
 * Static Helper Class That Helps Varying Parts Of The Code Base. This class in particular is responsbile for
 * collecting data related to MTG as whole, and not the actual application.
 */
public class MTGHelper {

  private static Map<String, BufferedImage> cardImageCache = new HashMap<String, BufferedImage>();

  public static String generateCardKey(Card incomingCard) {
    return incomingCard.getName();
  }

  // Returns An Image Of The Card That Is Requested
  public static BufferedImage getCardImage(Card incomingCard) {
    try {
      BufferedImage existingImage = cardImageCache.get(generateCardKey(incomingCard));
      if (existingImage == null) {
        String baseImageFetchURL = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=";
        baseImageFetchURL += incomingCard.getMultiverseID();
        baseImageFetchURL += "&type=card";

        URL url = new URL(baseImageFetchURL);
        BufferedImage c = ImageIO.read(url);
        
        cardImageCache.put(generateCardKey(incomingCard), c);
        return c;
      } else {
        return existingImage;
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }
  }

  // Returns A Listing Of All Files Matching A Particular Extension
  public static List<Path> collectFilesInMatchingExtension(Path dir, String extension) {
    List<Path> files = new ArrayList<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.{" + extension + "}")) {
      for (Path entry : stream) {
        files.add(entry);
      }
    } catch (IOException x) {}
    return files;
  }
  
  // Returns A Tool Tip Display For Card When Requested
  public static String getToolTipForDisplay(Card item) {
    String toolTipURL = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + item.getMultiverseID() + "&type=card";
    String actualToolTip = "<html><body><img src='" + toolTipURL + "'>";
    return actualToolTip;
  }
}
