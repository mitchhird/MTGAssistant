package ui.serverui;

import java.awt.Dimension;

import javax.swing.BorderFactory;

import ui.shared.ImageManager;
import ui.shared.UIFrameBase;
import util.Constants;
import app.MTGAssistantServer;

/**
 * Main Frame For The Server Application. Contains A Simple Status UI and a current deck
 * list for the admin's to read if they need to
 * @author Mitchell
 */
public class MainServerUIFrame extends UIFrameBase {

  // Complex UI Items
  private ServerDeckDisplayPanel displayPanel;
  private ServerDetailsPanel detailsPanel;
  private final MTGAssistantServer mtgServer;
  
  // Default Constructor For This Class
  public MainServerUIFrame (MTGAssistantServer mtgServer) {
    super();
    this.mtgServer = mtgServer;
    initializePanel();
    populateLocal();
    initJFrameSettings();
  }
  
  // Main Method For Establishing Frame Settings
  private void initJFrameSettings() {
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(new Dimension(Constants.MAIN_APP_WIDTH, Constants.MAIN_APP_HEIGHT));
    setTitle(Constants.MAIN_SERVER_APPLICATION_NAME);
    setLocationByPlatform(true);
    setIconImage(ImageManager.getInstance().getIconForKey(Constants.ICON_MAIN_ICON_KEY));
  }
  
  
  @Override
  protected void initVariables() {
    displayPanel = new ServerDeckDisplayPanel(mtgServer);
    displayPanel.setBorder(BorderFactory.createTitledBorder("Deck Details"));
    
    detailsPanel = new ServerDetailsPanel();
    detailsPanel.setBorder(BorderFactory.createTitledBorder("Server Details"));
  }
  
  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(displayPanel, 0, i, 4, 1, 1.0f, 1.0f);
    
    i++;
    addComponentToPanel(detailsPanel, 0, i, 4, 1, 1.0f, 0.0f);
  }
  
  @Override
  protected void addActionListeners() {

  }
  
  @Override
  protected void populateLocal() {
    // TODO Auto-generated method stub
    
  }
}
