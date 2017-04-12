package ui.clientui.customUiElements;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import models.cardModels.Card;
import util.MTGHelper;

/**
 * Custom JList Renderer Which Uses A Small Bit Of Logic To Show The Currently Moused Over Card
 * @author Mitchell
 */
public class CardDisplayingJList extends JList<Card> {

  private static final long serialVersionUID = 1L;

  // Default Constructor For The Card Displaying List
  public CardDisplayingJList() {
    super();
  }
  
  @Override
  public String getToolTipText(MouseEvent event) {
    int index = locationToIndex(event.getPoint());
    if (index > -1) {
      Card item = getModel().getElementAt(index);
      String actualToolTip = MTGHelper.getToolTipForDisplay(item);
      return actualToolTip;
    }
    else {
      return null;
    }
  }
}
