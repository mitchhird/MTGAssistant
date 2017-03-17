package ui.clientui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import ui.clientui.TabRenders.AbstractTabRenderer;
import ui.clientui.panels.CardAdvancedSearchPanel;
import ui.clientui.panels.ClientConnectPanel;
import ui.clientui.panels.DeckStatisticsPanel;
import ui.clientui.panels.DecksDisplayPanel;
import ui.clientui.panels.FormatStatisticsPanel;
import ui.shared.ImageManager;
import util.Constants;
import app.MTGAssistantClient;
/**
 * Main class that is responsible for showing our application's main UI
 * @author Mitchell
 */
public final class MainApplicationFrame extends JFrame {

  private final MTGAssistantClient clientApp;
  private JXTabbedPane tabbedPane;
  private JMenuItem exitFileMenuItem;
  private JMenuItem connectMenuItem;
  private static final long serialVersionUID = 1L;

  // Default Constructor For The Application
  public MainApplicationFrame (MTGAssistantClient clientApp) {
    super();
    this.clientApp = clientApp;
    initJFrameSettings();
    initMenuBar();
    addActionListeners();
    createAndShowUI();
    pack();
  }
  
  // Method That Simply Generates The UI That The Main Application Frame Will Be Using
  private void createAndShowUI () {
    tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);
    
    AbstractTabRenderer renderer = (AbstractTabRenderer)tabbedPane.getTabRenderer();
    renderer.setDisplayText("This text is a prototype");
    renderer.setHorizontalTextAlignment(SwingConstants.LEADING);

    DecksDisplayPanel editingPane = new DecksDisplayPanel(clientApp);
    CardAdvancedSearchPanel searchPane = new CardAdvancedSearchPanel(clientApp);
    ClientConnectPanel clientConnectPane = new ClientConnectPanel(clientApp);
    DeckStatisticsPanel statisticsPanel = new DeckStatisticsPanel(clientApp);
    FormatStatisticsPanel formatStatPanel = new FormatStatisticsPanel(clientApp);
    
    JTabbedPane statsTabbedPane = new JTabbedPane();
    statsTabbedPane.add("Individual Deck Statistics", statisticsPanel);
    statsTabbedPane.add("Format Deck Statistics", formatStatPanel);
    
    ImageIcon decksIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_DECKS_KEY));
    tabbedPane.addTab("Decks", decksIcon, editingPane, "Deck Browsing And Manipulation");
    
    ImageIcon searchIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_SEARCH_KEY));
    tabbedPane.addTab("Search", searchIcon, searchPane, "Advanced Search On Card DB");
    
    ImageIcon networkingIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_NETWORK_KEY));
    tabbedPane.addTab("Networking", networkingIcon, clientConnectPane, "Networking Options And Status");
    
    ImageIcon statisticsIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_STAT_KEY));
    tabbedPane.addTab("Statistics", statisticsIcon, statsTabbedPane, "Deck and Format Statistics Information");

    this.add(tabbedPane);
  }

  // Main Method For Establishing Frame Settings
  private void initJFrameSettings() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setPreferredSize(new Dimension(Constants.MAIN_APP_WIDTH, Constants.MAIN_APP_HEIGHT));
    setTitle(Constants.MAIN_APPLICATION_NAME);
    setLocationByPlatform(true);
    setIconImage(ImageManager.getInstance().getIconForKey(Constants.ICON_MAIN_ICON_KEY));
    ToolTipManager.sharedInstance().setInitialDelay(Constants.TOOLTIP_DISPLAY_DELAY);
  }
  
  // Method responsible for adding action listeners
  private void addActionListeners() {
    exitFileMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        System.exit(0);
      }
    });
    
    connectMenuItem.addActionListener(new ActionListener() {  
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Connect To Server Button Pressed");
        tabbedPane.setSelectedIndex(2);
      }
    });
  }
  
  // Setup Of The Menu Item
  private void initMenuBar () {
    JMenuBar menu = new JMenuBar();
    
    JMenu fileMenuItem = new JMenu("File");
    exitFileMenuItem = new JMenuItem("Exit");
    fileMenuItem.add(exitFileMenuItem);
    
    JMenu networkingMenu = new JMenu ("Networking");
    connectMenuItem = new JMenuItem ("Connect To Server");
    networkingMenu.add(connectMenuItem);
    
    menu.add(fileMenuItem);
    menu.add(networkingMenu);
    setJMenuBar(menu);
  }
}
