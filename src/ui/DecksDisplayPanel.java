package ui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import models.deckModels.Deck;
import util.Constants;
import db.DBPersistanceController;

/**
 * Panel for editing decks within the Java UI. Panel contains a picker where you can
 * select a deck, and view the details of the item in another panel
 * @author Mitchell
 *
 */
public class DecksDisplayPanel extends UIPanelBase {
  
  private JLabel currentDecksLabel;
  private JTable currentDecksTable;
  private JToolBar deckOperationPanel;
  
  private JButton newDeckButton;
  private JButton addCardsToDeckButton;
  private JButton deleteDeckButton;

  private JComboBox<Deck> deckComboBox;
  private IndividualDeckPanel deckPanel;
  
  public DecksDisplayPanel() {
    super();
    populateLocal();
  }

  @Override
  protected void initVariables() {
    currentDecksLabel = new JLabel(Constants.DECKS_CURRENT_LABEL + ":");
    
    deckComboBox = new JComboBox<Deck>();
    deckPanel = new IndividualDeckPanel();
    
    newDeckButton = new JButton(Constants.DECK_TOOL_NEW_DECK);
    addCardsToDeckButton = new JButton(Constants.DECK_TOOL_EDIT_DECK);
    deleteDeckButton = new JButton(Constants.DECK_TOOL_DELETE_DECK);
    
    newDeckButton.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
    addCardsToDeckButton.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
    deleteDeckButton.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
    
    deckOperationPanel = new JToolBar();
    deckOperationPanel.add(newDeckButton);
    deckOperationPanel.add(addCardsToDeckButton);
    deckOperationPanel.add(deleteDeckButton);
    deckOperationPanel.setFloatable(false);
    
    updateButtonEnable(false);
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(deckOperationPanel, 0, 0, 1, 1, 0.1f, 0f);
    
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    addComponentToPanel(currentDecksLabel, 0, 1, 1, 1, 1.0f, 0f);
    
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    addComponentToPanel(deckComboBox, 0, 2, 4, 1, 1.0f, 0f);
    
    addComponentToPanel(deckPanel, 0, 3, 4, 1, 1.0f, 1.0f);
  }

  @Override
  protected void addActionListeners() {
    deckComboBox.addActionListener(new ActionListener() {
      
      @Override
      public void actionPerformed(ActionEvent e) {
        updateButtonEnable(deckComboBox.getSelectedIndex() != 0);
        if (deckComboBox.getSelectedIndex() >= 0) {
          Deck deckToRender = deckComboBox.getItemAt(deckComboBox.getSelectedIndex());
          deckPanel.setCurrentlySelectedDeck(deckToRender);
        }
      }
    });
    
    newDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        System.out.println("New Deck Button Has Been Pressed");
        deckPanel.setCurrentlySelectedDeck(new Deck());
      }
    });
    
    addCardsToDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        System.out.println("Add Cards To Deck Button Has Been Pressed");
        Deck deckToEdit = deckComboBox.getItemAt(deckComboBox.getSelectedIndex());
        DeckEditDialog newDialog = new DeckEditDialog(deckToEdit, deckPanel, false);
        newDialog.setVisible(true);
      }
    });
    
    deleteDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        handleDeleteButton();
      }
    });
  }
  
  private void updateButtonEnable (boolean rowSelected) {
    newDeckButton.setEnabled(true);
    addCardsToDeckButton.setEnabled(rowSelected);
    deleteDeckButton.setEnabled(rowSelected);
  }

  @Override
  protected void populateLocal() {
    List<Deck> allDecksInDB = DBPersistanceController.getInstance().getAllDecksInDB();
    Collections.sort(allDecksInDB);
    DefaultComboBoxModel<Deck> displayModel = new DefaultComboBoxModel<Deck>();
    for (Deck d: allDecksInDB) {
      displayModel.addElement(d);
    }
    deckComboBox.setModel(displayModel);
  }

  @Override
  protected void applyLocal() {
    // TODO Auto-generated method stub
    
  }

  private void handleDeleteButton() {
    System.out.println("Delete Button Has Been Pressed");
    int selectedIndex = deckComboBox.getSelectedIndex();
    if (selectedIndex >= 0) {
      Deck incomingDeck = deckComboBox.getItemAt(selectedIndex);
      DBPersistanceController.getInstance().deleteDeckFromDB(incomingDeck);
    }
  }
}
