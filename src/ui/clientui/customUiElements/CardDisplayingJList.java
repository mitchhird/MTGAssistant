package ui.clientui.customUiElements;

import java.awt.event.MouseEvent;

import javax.swing.JList;

import models.cardModels.Card;

/**
 * Custom JList Renderer Which Uses A Small Bit Of Logic To Show The Currently Moused Over Card
 * @author Mitchell
 */
public class CardDisplayingJList extends JList<Card> {

  private static final long serialVersionUID = 1L;

  public CardDisplayingJList() {
    super();
  }
  
  @Override
  public String getToolTipText(MouseEvent event) {
    int index = locationToIndex(event.getPoint());
    if (index > -1) {
      Card item = getModel().getElementAt(index);
      String toolTipURL = "http://gatherer.wizards.com/Handlers/Image.ashx?multiverseid=" + item.getMultiverseID() + "&type=card";
      return "<html><body><img src='" + toolTipURL + "'>";
    }
    else {
      return null;
    }
  }
}
