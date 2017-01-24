package ui;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.cardModels.CardRarity;
import models.cardModels.Format;
import models.cardModels.MagicSet;

/**
 * Panel that is responsbile for holding all of the information regarding card lookups
 */
public class CardAdvancedSearchPanel extends JPanel {

  private JTextField cardNameSearchField;
  private JTextField cardSuperTypeField;
  private JTextField cardSubTypeField;
  private JComboBox<MagicSet> setComboBox;
  private JComboBox<Format> formatComboBox;
  private JComboBox<CardRarity> rarityComboBox;
  
  
}
