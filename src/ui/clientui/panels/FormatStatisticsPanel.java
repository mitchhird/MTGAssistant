package ui.clientui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import models.cardModels.Format;
import models.statisticModels.FormatStatisticsCalculator;
import models.tableModels.FormatStatisticsModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.DefaultXYDataset;

import ui.shared.UIPanelBase;
import app.MTGAssistantClient;

/**
 * Panel That Contains All Of The UI That Is Responsible For Showing Format Statistics
 * @author Mitchell
 */
public class FormatStatisticsPanel extends UIPanelBase {

  private JLabel currentFormatLabel;
  private JLabel topCreaturesTableLabel;
  private JLabel topSpellsTableLabel;
  private JLabel statisicsStatusLabel;
  private JComboBox<Format> currentFormatCombo;
  private JTable creatureDataTable;
  private JTable spellDataTable;
  private ChartPanel typeChartPanel;
  
  private final FormatStatisticsCalculator statsCalculator;
  private final MTGAssistantClient clientApp;
  
  public FormatStatisticsPanel(MTGAssistantClient clientApp) {
    super();
    this.clientApp = clientApp;
    this.statsCalculator = new FormatStatisticsCalculator(clientApp);
    initializePanel();
  }
  
  @Override
  protected void initVariables() {
    currentFormatLabel = new JLabel("Format:");
    topCreaturesTableLabel = new JLabel("Top Creatures:");
    topSpellsTableLabel = new JLabel("Top Spells:");
    statisicsStatusLabel = new JLabel("Current Statistics Status: Ready");
    
    currentFormatCombo = new JComboBox<>(Format.values());
    creatureDataTable = new JTable();
    spellDataTable = new JTable();
    typeChartPanel = new ChartPanel(ChartFactory.createXYLineChart("", "", "", new DefaultXYDataset()));
  }

  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(currentFormatLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(currentFormatCombo, 1, i, 3, 1, 0.0f, 0.0f); 
    
    i++;
    addComponentToPanel(topCreaturesTableLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(topSpellsTableLabel, 3, i, 1, 1, 0.0f, 0.0f); 
    
    i++;
    JScrollPane createScrollWrapper = new JScrollPane(creatureDataTable);
    addComponentToPanel(createScrollWrapper, 0, i, 2, 1, 1.0f, 1.0f);
    
    JScrollPane spellScrollWrapper = new JScrollPane(spellDataTable);
    addComponentToPanel(spellScrollWrapper, 2, i, 2, 1, 1.0f, 1.0f);
    
    i++;
    addComponentToPanel(typeChartPanel, 0, i, 4, 1, 1.0f, 1.0f);
    
    i++;
    addComponentToPanel(statisicsStatusLabel, 0, i, 1, 1, 0.0f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    currentFormatCombo.addActionListener(new ActionListener() {  
      @Override
      public void actionPerformed(ActionEvent e) {
        handleComboSelection();
      }
    });
  }

  private void handleComboSelection() {
    Runnable formatFetchRunner = new Runnable() {     
      @Override
      public void run() {
        statisicsStatusLabel.setText("Current Statistics Status: Calculating");
        
        Format selectedFormat = currentFormatCombo.getItemAt(currentFormatCombo.getSelectedIndex());
        statsCalculator.calculateFormatStatistics(selectedFormat);
        
        FormatStatisticsModel createModel = new FormatStatisticsModel(statsCalculator.getCreatureDetailsList());
        creatureDataTable.setModel(createModel);

        FormatStatisticsModel spellModel = new FormatStatisticsModel(statsCalculator.getSpellDetailsList());
        spellDataTable.setModel(spellModel);
        spellDataTable.invalidate();
        spellDataTable.repaint();
        
        JFreeChart typePieChartData = ChartFactory.createPieChart("Type Chart (" + selectedFormat + ")", statsCalculator.getTypeDataSet(), true, true, true);
        typeChartPanel.setChart(typePieChartData);
        
        statisicsStatusLabel.setText("Current Statistics Status: Finished");
      }
    };
    Thread formatFetchingThread = new Thread(formatFetchRunner);
    formatFetchingThread.setName("Fomat Statistics Fetecher Thread");
    formatFetchingThread.start();
  }
  
  @Override
  protected void populateLocal() {
    // TODO Auto-generated method stub
  }
}
