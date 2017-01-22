package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import util.Constants;

/**
 * Main class that is responsible for showing our application's main UI
 * @author Mitchell
 */
public final class MainApplicationFrame extends UIFrameBase {

  private JMenuItem exitFileMenuItem;
  private JMenuItem connectMenuItem;
  private static final long serialVersionUID = 1L;

  // Default Constructor For The Application
  public MainApplicationFrame () {
    super();
    initJFrameSettings();
    initMenuBar();
    addActionListeners();
  }

  // Main Method For Establishing Frame Settings
  private void initJFrameSettings() {
    setVisible(true);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(Constants.MAIN_APP_WIDTH, Constants.MAIN_APP_HEIGHT);
    setTitle(Constants.MAIN_APPLICATION_NAME);
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
