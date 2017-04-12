package models.tableModels;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import models.statisticModels.FormatStatCardDO;

/**
 * Table model That Contains All Of The Data That Will Be Used Within The Format Statistics Model
 * @author Mitchell
 */
public class FormatStatisticsModel extends AbstractTableModel {
  private final List<FormatStatCardDO> listToRender;
  public FormatStatisticsModel(List<FormatStatCardDO> listToRender) {
    this.listToRender = listToRender;
  }
  
  @Override
  public int getRowCount() {
    return listToRender.size();
  }

  @Override
  public int getColumnCount() {
    return 3;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    switch (columnIndex) {
      case 0: return String.class;
      default : return Integer.class;
    }
  }
  
  @Override
  public Object getValueAt(int rowIndex, int columnIndex) {
    FormatStatCardDO formatStatCardDO = listToRender.get(rowIndex);
    if (formatStatCardDO != null) {
      switch (columnIndex) {
        case 0: return formatStatCardDO.getCardName();
        case 1: return formatStatCardDO.getCardCount();
        case 2: return formatStatCardDO.getDeckCount();
      }
    } 
    return "";
  }
  
  public String getCardNameAtIndex(int index) {
    if (index >= 0 && index < listToRender.size()) {
      return listToRender.get(index).getCardName();      
    } else {
      return "";
    }
  }
  
  @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
    return false;
  }
  
  @Override
  public String getColumnName(int column) {
    switch (column) {
      case 0: return "Card Name";
      case 1: return "Total Quantity";
      case 2: return "Decks With Card";
      default : return "";
    }
  }

}
