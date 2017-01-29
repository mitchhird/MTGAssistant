package models.tableModels;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import models.cardModels.Format;
import models.deckModels.Deck;
import util.Constants;

/**
 * Model that contains all information needed for rendering decks in tables
 * @author Mitchell
 */
public class DeckTableModel extends AbstractTableModel {

  private List<Deck> decksToRender;
  private static final long serialVersionUID = 1L;
  
  // Default Constructor For This Item
  public DeckTableModel() {
    this.decksToRender = new ArrayList<>();
  }
  
  @Override
  public void addTableModelListener(TableModelListener paramTableModelListener) {
    // Do Nothing
  }

  @Override
  public Class<?> getColumnClass(int columnNumber) {
    switch (columnNumber) {
      case 3: return Format.class;
      default : return String.class;
    }
  }

  @Override
  public int getColumnCount() {
    return 4;
  }

  @Override
  public String getColumnName(int columnNumber) {
    switch (columnNumber) {
      case 0: return Constants.DECKS_SELECT_CREATE_LABEL;
      case 1: return Constants.DECKS_SELECT_NAME_LABEL;
      case 2: return Constants.DECKS_SELECT_ARCHETYPE_LABEL;
      case 3: return Constants.DECKS_SELECT_FORMAT_LABEL;
      default : return "";
    }
  }

  @Override
  public int getRowCount() {
    return decksToRender.size();
  }

  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    Deck deckSelected = decksToRender.get(rowIndex);
    switch (columnIndex) {
      case 0: return deckSelected.getCreatingUser();
      case 1: return deckSelected.getDeckName();
      case 2: return deckSelected.getDeckArchetype();
      case 3: return deckSelected.getDeckFormat();
      default : return null;
    }
  }

  @Override
  public boolean isCellEditable(int paramInt1, int paramInt2) {
    return false;
  }

  @Override
  public void removeTableModelListener(TableModelListener paramTableModelListener) {
    // TODO Auto-generated method stub
  }

  @Override
  public void setValueAt(Object paramObject, int paramInt1, int paramInt2) {
  }

  public Deck getDeckAtRow(int rowIndex) {
    return decksToRender.get(rowIndex);
  }

  public void removeDeckFromModel (int rowIndex) {
    decksToRender.remove(rowIndex);
    fireTableDataChanged();
  }
  
  public void setDecksToRender(List<Deck> decksToRender) {
    this.decksToRender = decksToRender;
    fireTableDataChanged();
  }
  
}
