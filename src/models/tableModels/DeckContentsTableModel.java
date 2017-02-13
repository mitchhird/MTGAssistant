package models.tableModels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import models.cardModels.Card;
import util.Constants;

/**
 * Table model that is responsible for rendering out JTable
 * @author Mitchell
 */
public class DeckContentsTableModel extends AbstractTableModel{

  private List<Card> displayList;
  private Map<Card, Integer> cardsWithinDeck;
  private static final long serialVersionUID = 1L;

  public DeckContentsTableModel () {
    setCardsInDeck(new HashMap<Card, Integer>());
  }

  public void setCardsInDeck(Map<Card,Integer> newMap) {
    cardsWithinDeck = newMap;
    displayList = new ArrayList<Card>(cardsWithinDeck.keySet());
    Collections.sort(displayList);
  }
  
  @Override
  public int getRowCount() {
    return cardsWithinDeck.keySet().size();
  }

  @Override
  public int getColumnCount() {
    return 3;
  }

  @Override
  public String getColumnName(int columnIndex) {
    switch (columnIndex) {
      case 0: return Constants.DECK_CONTENT_CARD_NAME;
      case 1: return Constants.DECK_CONTENT_CARD_TYPE;
      case 2: return Constants.DECK_CONTENT_CARD_QUANTITY;
      default: return "";
    }
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
      case 2: return Integer.class;
      default: return String.class;
    }
  }

  @Override
  public boolean isCellEditable(int paramInt1, int paramInt2) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Card card = displayList.get(rowIndex);
    switch (columnIndex) {
      case 0: return card.getName();
      case 1: return card.getType();
      case 2: return cardsWithinDeck.get(card);
      default: return "";
    }
  }

  @Override
  public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void addTableModelListener(TableModelListener paramTableModelListener) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void removeTableModelListener(TableModelListener paramTableModelListener) {
    // TODO Auto-generated method stub
    
  }

}
