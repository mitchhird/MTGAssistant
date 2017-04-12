package ui.clientui.customUiElements;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

import app.MTGAssistantClient;
import models.tableModels.FormatStatisticsModel;
import util.MTGHelper;

/**
 * Format Statistics Table That Is Responsible For Showing The Cards When The User Hovers Over Them
 * 
 * @author Mitchell
 */
public class CardDisplayingFormatJTable extends JTable{
  private FormatStatisticsModel tableModel;
  private final MTGAssistantClient client;
  
  public CardDisplayingFormatJTable (MTGAssistantClient client) {
    super();
    this.client = client;
  }
  
  @Override
  public String getToolTipText(MouseEvent event) {
    int index = rowAtPoint(event.getPoint());
    if (index > -1) {
      int id = client.getMultiverseID(tableModel.getCardNameAtIndex(index));
      String actualToolTip = MTGHelper.getToolTipForDisplay(id);
      return actualToolTip;
    }
    else {
      return null;
    }
  }
  
  public void setStatisticModel (FormatStatisticsModel model) {
    this.tableModel = model;
    setModel(model);
  }
}
