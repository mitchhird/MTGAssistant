package ui.clientui.panels;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import models.cardModels.Format;
import models.deckModels.Deck;
import models.statisticModels.DeckStatisticsCalculator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import ui.shared.UIPanelBase;
import util.Constants;
import app.MTGAssistantClient;

public class DeckStatisticsPanel extends UIPanelBase {

  private JLabel currentFormatLabel;
  private JLabel currentDeckLabel;
  private JLabel statsStatusLabel;
  private ChartPanel colourChart;
  private ChartPanel typeChart;
  private ChartPanel cmcChart;
  private JComboBox<Format> formatComboBox;
  private JComboBox<Deck> deckComboBox;
  
  private final MTGAssistantClient clientApp;
  private final DeckStatisticsCalculator statsCalculator;
  
  public DeckStatisticsPanel (MTGAssistantClient clientApp) {
    super();
    this.clientApp = clientApp;
    this.statsCalculator = new DeckStatisticsCalculator();
    initializePanel();
    handleFormatSelection();
  }
  
  @Override
  protected void initVariables() {
    currentFormatLabel = new JLabel(Constants.DECKS_SELECT_FORMAT_LABEL);
    currentDeckLabel = new JLabel("Deck");
    statsStatusLabel = new JLabel("Statistics Status");
    colourChart = getChartPanelForChart(ChartFactory.createXYLineChart("", "", "", new DefaultXYDataset()));
    typeChart = getChartPanelForChart(ChartFactory.createXYLineChart("", "", "", new DefaultXYDataset()));
    cmcChart = getChartPanelForChart(ChartFactory.createXYLineChart("", "", "", new DefaultXYDataset()));
    formatComboBox = new JComboBox<>(Format.values());
    deckComboBox = new JComboBox<>();
  }

  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(currentFormatLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(formatComboBox, 1, i, 3, 1, 1.0f, 0.0f);
    
    i++;
    addComponentToPanel(currentDeckLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(deckComboBox, 1, i, 3, 1, 1.0f, 0.0f);
    
    i++;
    addComponentToPanel(cmcChart, 0, i, 2, 1, 1.0f, 1.0f);
    addComponentToPanel(colourChart, 2, i, 2, 1, 1.0f, 1.0f);
    
    i++;
    addComponentToPanel(typeChart, 0, i, 4, 1, 1.0f, 1.0f);
    
    i++;
    gbc.anchor = GridBagConstraints.EAST;
    addComponentToPanel(statsStatusLabel, 0, i, 1, 1, 0.0f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    formatComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleFormatSelection();
        populateLocal();
      }
    });
    
    deckComboBox.addActionListener(new ActionListener() {    
      @Override
      public void actionPerformed(ActionEvent e) {
        populateLocal();
      }
    });
  }
  
  protected void handleFormatSelection () {
    Format selectedIndex = formatComboBox.getItemAt(formatComboBox.getSelectedIndex());
    List<Deck> allDecksInDB = clientApp.getDecksByFormat(selectedIndex);
    DefaultComboBoxModel<Deck> displayModel = new DefaultComboBoxModel<Deck>();
    for (Deck d : allDecksInDB) {
      displayModel.addElement(d);
    }
    deckComboBox.setModel(displayModel); 
  }

  @Override
  protected void populateLocal() {
      Runnable deckStatRun = new Runnable() {
        @Override
        public void run() {
          Deck deckToCalc = deckComboBox.getItemAt(deckComboBox.getSelectedIndex());
          if (deckToCalc != null) {
            statsStatusLabel.setText("Statistics Status: Calculating");
            clientApp.populateDeckContents(deckToCalc);
            statsCalculator.calcDeckStatistics(deckToCalc);
  
            JFreeChart colourChartData = ChartFactory.createBarChart("Colour Chart (" + deckToCalc.getDeckName() + ")", "Colour", "Quantity", statsCalculator.getColourDataSet());
            colourChartData.removeLegend();
            colourChart.setChart(colourChartData);
  
            JFreeChart cmcChartData = ChartFactory.createBarChart("CMC Chart (" + deckToCalc.getDeckName() + ")", "CMC (Converted Mana Cost)", "Quantity", statsCalculator.getCmcDataSet());
            cmcChartData.removeLegend();
            cmcChart.setChart(cmcChartData);
  
            JFreeChart typePieChartData = ChartFactory.createPieChart("Type Chart (" + deckToCalc.getDeckName() + ")", statsCalculator.getTypeDataSet(), true, true, true);
            typeChart.setChart(typePieChartData);
  
            statsStatusLabel.setText("Statistics Status: Finished");
          }
        }
      };
      Thread statCalcThread = new Thread(deckStatRun);
      statCalcThread.setName("Calculate Statisics Thread");
      statCalcThread.start();
  }
  
  protected ChartPanel getChartPanelForChart (JFreeChart chartData) {
    return new ChartPanel(chartData);
  }
}
