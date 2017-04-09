package util;

/**
 * Simple File That Contains All Of The Constants To Be Used Throughout The Application
 * @author Mitchell
 *
 */
public interface Constants {

  // String Constants
  public static final String CLIENT_DB = "MTG_Assistant_DB_Client";
  public static final String SERVER_DB = "MTG_Assistant_DB_Server";
  public static final String MAIN_APPLICATION_NAME = "MTG Assistant Client";
  public static final String MAIN_SERVER_APPLICATION_NAME = "MTG Assistent Server";
  
  public static final String DECKS_CURRENT_LABEL = "Current Decks (Click A Row To View Details)";
  public static final String DECKS_SELECT_NAME_LABEL = "Deck Name";
  public static final String DECKS_SELECT_ARCHETYPE_LABEL = "Deck Archetype";
  public static final String DECKS_SELECT_CREATE_LABEL = "Created By";
  public static final String DECKS_SELECT_DESCRIPTION_LABEL = "Deck Description";
  public static final String DECKS_SELECT_CONTENTS_LABEL = "Deck Contents (Hover Over Row To View Card Image)";
  public static final String DECKS_SELECT_FORMAT_LABEL = "Deck Format";
  
  public static final String CARD_SEARCH_CARD_NAME = "Name";
  public static final String CARD_SEARCH_CARD_TEXT = "Text";
  public static final String CARD_SEARCH_CARD_FLAVOUR = "Flavour Text";
  public static final String CARD_SEARCH_CARD_ARTIST = "Artist";
  public static final String CARD_SEARCH_CARD_SUPER = "Supertype";
  public static final String CARD_SEARCH_CARD_SUB = "Subtype";
  public static final String CARD_SEARCH_CARD_FORMAT = "Format";
  public static final String CARD_SEARCH_CARD_RARITY = "Rarity";
  public static final String CARD_SEARCH_CARD_SET = "Set";
  public static final String CARD_SEARCH_SEARCH_BUTTON = "Search";
  
  public static final String DECK_CONTENT_CARD_NAME = "Card Name";
  public static final String DECK_CONTENT_CARD_TYPE = "Card Type";
  public static final String DECK_CONTENT_CARD_TEXT = "Card Text";
  public static final String DECK_CONTENT_CARD_QUANTITY = "Quantity";
  
  public static final String DECK_TOOL_NEW_DECK = "New Deck";
  public static final String DECK_TOOL_EDIT_DECK = "Edit Deck";
  public static final String DECK_TOOL_DELETE_DECK = "Delete Deck";
  
  public static final String SERVER_START_BUTTON_TEXT = "Start Server";
  public static final String SERVER_STATUS_TEXT = "Server Status:";
  public static final String SERVER_GOOD_REPLY = "200 OK";
  public static final String SERVER_BAD_REPLY = "500 ERROR";
  public static final String DELIMITER="@@@@";
  
  // Image Icon Constants
  public static final String ICON_NEW_KEY = "NEW_ICON";
  public static final String ICON_EDIT_KEY = "EDIT_ICON";
  public static final String ICON_DELETE_KEY = "DELETE_ICON";
  public static final String ICON_DECKS_KEY = "DECK_ICON";
  public static final String ICON_SEARCH_KEY = "SEARCH_ICON";
  public static final String ICON_NETWORK_KEY = "NETWORK_ICON";
  public static final String ICON_STAT_KEY = "STATISTICS_ICON";
  public static final String ICON_MAIN_ICON_KEY = "MAIN_ICON";
  
  // Integer Constants
  public static final int MAIN_APP_WIDTH = 800;
  public static final int MAIN_APP_HEIGHT = 600;
  public static final int CONSTRUCTED_MAX_PLAY_SET = 4;
  public static final int CONSTRUCTED_MIN_DECK_SIZE = 60;
  public static final int SINGLETON_DECK_SIZE = 100;
  public static final int TOOLTIP_DISPLAY_DELAY = 500;
  public static final int SERVER_PORT_MIN = 15000;
  public static final int SERVER_PORT_MAX = 66560;
  public static final int STATUS_UPDATE_INTERVAL = 5000;
}
