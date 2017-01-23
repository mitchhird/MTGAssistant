package ui;

import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.cardModels.CardRarity;
import models.cardModels.Format;

/**
 * Panel that is responsbile for holding all of the information regarding card lookups
 */
public class CardAdvancedSearchPanel extends JPanel {

  private JTextField cardNameSearchField;
  private JTextField cardSuperTypeField;
  private JTextField cardSubTypeField;
  private JComboBox<Set> setComboBox;
  private JComboBox<Format> formatComboBox;
  private JComboBox<CardRarity> rarityComboBox;
  
  
}
