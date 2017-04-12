package ui.clientui.customUiElements;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

import models.cardModels.Card;
import models.tableModels.DeckContentsTableModel;
import util.MTGHelper;

/**
 * Table that is responsible for popping up card items when they are hovered over
 * @author Mitchell
 */
public class CardDisplayingJTable extends JTable {
  private final DeckContentsTableModel tableModel;
  
  public CardDisplayingJTable (DeckContentsTableModel tableDataModel) {
    super(tableDataModel);
    this.tableModel = tableDataModel;
  }
  
  @Override
  public String getToolTipText(MouseEvent event) {
    int index = rowAtPoint(event.getPoint());
    if (index > -1) {
      Card item = tableModel.getCardAtIndex(index);
      String actualToolTip = MTGHelper.getToolTipForDisplay(item);
      return actualToolTip;
    }
    else {
      return null;
    }
  }
}
