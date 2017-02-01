package ui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.deckModels.Deck;
import models.tableModels.DeckTableModel;
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
  private JButton editDeckButton;
  private JButton deleteDeckButton;
  
  private DeckTableModel deckTableModel;
  
  public DecksDisplayPanel() {
    super();
  }

  @Override
  protected void initVariables() {
    currentDecksLabel = new JLabel(Constants.DECKS_CURRENT_LABEL + ":");
    
    deckTableModel = new DeckTableModel();
    currentDecksTable = new JTable(deckTableModel);
    currentDecksTable.setAutoCreateRowSorter(true);
    
    newDeckButton = new JButton(Constants.DECK_TOOL_NEW_DECK);
    editDeckButton = new JButton(Constants.DECK_TOOL_EDIT_DECK);
    deleteDeckButton = new JButton(Constants.DECK_TOOL_DELETE_DECK);
    
    newDeckButton.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
    editDeckButton.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
    deleteDeckButton.setIcon(UIManager.getIcon("OptionPane.informationIcon"));
    
    deckOperationPanel = new JToolBar();
    deckOperationPanel.add(newDeckButton);
    deckOperationPanel.add(editDeckButton);
    deckOperationPanel.add(deleteDeckButton);
    deckOperationPanel.setFloatable(false);
    
    updateButtonEnable(false);
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(deckOperationPanel, 0, 0, 1, 1, 0.1f, 0.01f);
    
    gbc.anchor = GridBagConstraints.NORTH;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    addComponentToPanel(currentDecksLabel, 0, 1, 1, 1, 1.0f, 0.19f);
    
    gbc.anchor = GridBagConstraints.CENTER;
    gbc.fill = GridBagConstraints.BOTH;
    JScrollPane currentDecksScroller = new JScrollPane(currentDecksTable);
    addComponentToPanel(currentDecksScroller, 0, 2, 4, 1, 1.0f, 0.8f);
  }

  @Override
  protected void addActionListeners() {
    currentDecksTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
        int selectedRow = currentDecksTable.getSelectedRowCount();
        updateButtonEnable(selectedRow == 1);
      }
    });    
    
    newDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        System.out.println("New Deck Button Has Been Pressed");
      }
    });
    
    editDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        System.out.println("Edit Deck Button Has Been Pressed");
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
    editDeckButton.setEnabled(rowSelected);
    deleteDeckButton.setEnabled(rowSelected);
  }

  @Override
  protected void populateLocal() {
    List<Deck> allDecksInDB = DBPersistanceController.getInstance().getAllDecksInDB();
    deckTableModel.setDecksToRender(allDecksInDB);
  }

  @Override
  protected void applyLocal() {
    // TODO Auto-generated method stub
    
  }

  private void handleDeleteButton() {
    int selectedRow = currentDecksTable.getSelectedRow();
    Deck incomingDeck = deckTableModel.getDeckAtRow(selectedRow);
    DBPersistanceController.getInstance().deleteDeckFromDB(incomingDeck);
    deckTableModel.removeDeckFromModel(selectedRow);
    currentDecksTable.repaint();
  }
}
