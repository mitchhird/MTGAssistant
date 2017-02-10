package ui;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import util.Constants;

/**
 * Manager class that is responsible for handling all image based operations. Uses a singleton pattern so it can be used
 * throughout the project with ease
 * 
 * @author Mitchell
 */
public class ImageManager {
  private BufferedImage mtgSymbolSpriteSheet;
  private Map<String, BufferedImage> iconMapping;
  private Map<String, BufferedImage> textToManaSymbolMap;

  private static final String FILE_PREFIX = "external_resources";
  private static ImageManager instance;

  private ImageManager() {
    initIconMap();
  }

  // Initializes Our Icon Map With All Of The Icons We Are Going To Use
  private void initIconMap() {
    iconMapping = new HashMap<String, BufferedImage>();
    iconMapping.put(Constants.ICON_DECKS_KEY, getSafeImageFromFile("icons/decks_icon_32.png"));
    iconMapping.put(Constants.ICON_DELETE_KEY, getSafeImageFromFile("icons/delete_32.png"));
    iconMapping.put(Constants.ICON_EDIT_KEY, getSafeImageFromFile("icons/edit_32.png"));
    iconMapping.put(Constants.ICON_MAIN_ICON_KEY, getSafeImageFromFile("icons/main_app_icon.png"));
    iconMapping.put(Constants.ICON_NEW_KEY, getSafeImageFromFile("icons/new_32.png"));
    iconMapping.put(Constants.ICON_SEARCH_KEY, getSafeImageFromFile("icons/search_icon_32.png"));
  }

  // Initializes The Sprite Sheet If It Is Needed
  private void initMTGSymbolSpriteSheet() {
    if (mtgSymbolSpriteSheet == null) {
      mtgSymbolSpriteSheet = getSafeImageFromFile("Mana.png");
      textToManaSymbolMap = new HashMap<String, BufferedImage>();
      textToManaSymbolMap.put("{U}", mtgSymbolSpriteSheet.getSubimage(500, 200, 100, 100));
    }
  }

  // Returns A Image From A File
  private BufferedImage getSafeImageFromFile(String file) {
    try {
      Path fileToLoad = Paths.get(FILE_PREFIX, file);
      Path absolutePath = fileToLoad.toAbsolutePath();
      return ImageIO.read(absolutePath.toFile());
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public BufferedImage getImageForManaSymbol(String manaSymbol) {
    initMTGSymbolSpriteSheet();
    return (textToManaSymbolMap.containsKey(manaSymbol)) ? textToManaSymbolMap.get(manaSymbol) : textToManaSymbolMap.get("{U}");
  }

  public BufferedImage getIconForKey(String imageKey) {
    return iconMapping.get(imageKey);
  }

  // Singleton Accessor Method
  public static ImageManager getInstance() {
    if (instance == null) {
      instance = new ImageManager();
    }
    return instance;
  }
}
