package ui.clientui.panels;

import javax.swing.JTabbedPane;

import app.MTGAssistantClient;
import ui.shared.UIPanelBase;

/**
 * Panel that contains all of the statistical collection information
 * @author Mitchell
 */
public class StatisticsTabbedPanel extends UIPanelBase {

  private DeckStatisticsPanel statisticsPanel;
  private FormatStatisticsPanel formatStatPanel;
  private final MTGAssistantClient clientApp;
  
  // Default Constructor For The Tabbed Panel Object
  public StatisticsTabbedPanel(MTGAssistantClient parentApp) {
    super();
    this.clientApp = parentApp;
    initializePanel();
  }
  
  @Override
  protected void initVariables() {
    statisticsPanel = new DeckStatisticsPanel(clientApp);
    formatStatPanel = new FormatStatisticsPanel(clientApp);
  }

  @Override
  protected void placeUIElements() {
    JTabbedPane statsTabbedPane = new JTabbedPane();
    statsTabbedPane.add("Individual Deck Statistics", statisticsPanel);
    statsTabbedPane.add("Format Deck Statistics", formatStatPanel);
    addComponentToPanel(statsTabbedPane, 0, 0, 1, 1, 1.0f, 1.0f);
  }

  @Override
  protected void addActionListeners() {
  }

  @Override
  protected void populateLocal() { 
  }
}
