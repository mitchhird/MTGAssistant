package ui.clientui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;

import ui.clientui.TabRenders.AbstractTabRenderer;
import ui.clientui.panels.CardAdvancedSearchPanel;
import ui.clientui.panels.DecksDisplayPanel;
import ui.shared.ImageManager;
import util.Constants;
/**
 * Main class that is responsible for showing our application's main UI
 * @author Mitchell
 */
public final class MainApplicationFrame extends JFrame {

  private JMenuItem exitFileMenuItem;
  private JMenuItem connectMenuItem;
  private static final long serialVersionUID = 1L;

  // Default Constructor For The Application
  public MainApplicationFrame () {
    super();
    initJFrameSettings();
    initMenuBar();
    addActionListeners();
    createAndShowUI();
    pack();
  }
  
  private void createAndShowUI () {
    JXTabbedPane tabbedPane = new JXTabbedPane(JTabbedPane.LEFT);
    
    AbstractTabRenderer renderer = (AbstractTabRenderer)tabbedPane.getTabRenderer();
    renderer.setDisplayText("This text is a prototype");
    renderer.setHorizontalTextAlignment(SwingConstants.LEADING);

    DecksDisplayPanel editingPane = new DecksDisplayPanel();
    CardAdvancedSearchPanel searchPane = new CardAdvancedSearchPanel();
    
    ImageIcon decksIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_DECKS_KEY));
    tabbedPane.addTab("Decks", decksIcon, editingPane, "Deck Browsing And Manipulation");
    
    ImageIcon searchIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_SEARCH_KEY));
    tabbedPane.addTab("Search", searchIcon, searchPane, "Advanced Search On Card DB");
    
    ImageIcon networkingIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_NETWORK_KEY));
    tabbedPane.addTab("Networking", networkingIcon, new JPanel(), "Networking Options And Status");
    
    ImageIcon statisticsIcon = new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_STAT_KEY));
    tabbedPane.addTab("Statistics", statisticsIcon, new JPanel(), "Deck and Format Statistics Information");
    
    tabbedPane.setEnabledAt(2, false);
    tabbedPane.setEnabledAt(3, false);
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
    setVisible(true);
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
    connectMenuItem.setEnabled(false);
    networkingMenu.add(connectMenuItem);
    
    menu.add(fileMenuItem);
    menu.add(networkingMenu);
    setJMenuBar(menu);
  }
}
