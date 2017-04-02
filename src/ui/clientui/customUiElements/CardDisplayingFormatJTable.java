package ui.clientui.customUiElements;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

import models.tableModels.FormatStatisticsModel;
import util.MTGHelper;
import app.MTGAssistantClient;

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
