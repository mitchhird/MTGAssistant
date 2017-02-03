package models.tableModels;

import java.util.HashMap;
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

  private final Map<Card, Integer> cardsWithinDeck;
  private static final long serialVersionUID = 1L;
  
  public DeckContentsTableModel () {
    cardsWithinDeck = new HashMap<Card, Integer>();
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
    switch (columnIndex) {
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
