package ui.clientui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.cardModels.Card;
import models.deckModels.Deck;
import models.deckModels.DeckCardDataObject;
import models.tableModels.DeckContentsTableModel;
import ui.clientui.DeckEditDialog;
import ui.clientui.customUiElements.CardDisplayingJTable;
import ui.shared.UIPanelBase;
import util.Constants;
import util.MTGHelper;

/**
 * Panel that is directly responsible for handling removals from decks
 */
public class DeckRemoveCardPanel extends UIPanelBase {
  
  private final Deck deckToEdit;
  private DeckContentsTableModel deckModel;
  private final DeckEditDialog parentPanel;

  // Swing Defined Variables
  private JLabel statusLabel;
  private JLabel deckContentsLabel;
  private JLabel currentSelectedLabel;
  private JLabel currentSelectedDataLabel;
  private JLabel removalQuantityLabel;

  private SpinnerNumberModel removalSpinModel;
  private JSpinner removalQuantitySpinner;
  private CardDisplayingJTable selectedDeckContentsTable;
  private JButton removeButton;
  
  // Default Constructor For This Panel
  public DeckRemoveCardPanel(DeckEditDialog parent) {
    super();
    this.parentPanel = parent;
    this.deckToEdit = parent.getDeckToEdit();
    initializePanel();
    populateLocal();
  }
  
  @Override
  protected void initVariables() {
    statusLabel = new JLabel();
    deckContentsLabel = new JLabel(Constants.DECKS_SELECT_CONTENTS_LABEL);
    currentSelectedLabel = new JLabel("Currently Selected Card:");
    currentSelectedDataLabel = new JLabel();
    removalQuantityLabel = new JLabel("Quantity to Remove:");
    
    removalSpinModel = new SpinnerNumberModel(1, 1, 1, 1);
    removalQuantitySpinner = new JSpinner(removalSpinModel);
    
    deckModel = new DeckContentsTableModel(deckToEdit);
    selectedDeckContentsTable = new CardDisplayingJTable(deckModel); 
    selectedDeckContentsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    removeButton = new JButton("Remove From Deck");
    removeButton.setEnabled(false);
  }

  @Override
  protected void placeUIElements() {
    int gridY = 0;
    addComponentToPanel(deckContentsLabel, 0, gridY, 4, 1, 1.0f, 0.0f);
    
    gridY++;
    JScrollPane deckTableWrapper = new JScrollPane(selectedDeckContentsTable);
    addComponentToPanel(deckTableWrapper, 0, gridY, 4, 1, 1.0f, 1.0f);
    
    gridY++;
    addComponentToPanel(currentSelectedLabel, 0, gridY, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(currentSelectedDataLabel, 1, gridY, 1, 1, 0.5f, 0.0f);
    addComponentToPanel(removalQuantityLabel, 2, gridY, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(removalQuantitySpinner, 3, gridY, 1, 1, 0.5f, 0.0f);
    
    gridY++;
    addComponentToPanel(statusLabel, 0, gridY, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(removeButton, 3, gridY, 1, 1, 0.0f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    removeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleRemoveButtonPressed();
      }
    });
    
    selectedDeckContentsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        handleTableSelection();
      }
    });
  }

  @Override
  protected void populateLocal() {
  }

  // Handles The Remove Button Prodcedure
  private void handleRemoveButtonPressed() {
    int selectedRow = selectedDeckContentsTable.getSelectedRow();
    if (selectedRow >= 0) {
      Card cardSelected = deckModel.getCardAtIndex(selectedRow);
      int spinnerValue = (int) removalQuantitySpinner.getValue();
      deckToEdit.removeCardFromDeck(cardSelected, spinnerValue);
      statusLabel.setText("Removed " + spinnerValue + "x " + cardSelected.getName());
      parentPanel.refreshData();
    }
  }

  public void refreshTable () {
    deckModel.setCardsInDeck(deckToEdit.getCardsWithinDeck());
    selectedDeckContentsTable.invalidate();
    selectedDeckContentsTable.repaint();
  }
  
  private void handleTableSelection() {
    int selectedRow = selectedDeckContentsTable.getSelectedRow();
    removeButton.setEnabled(selectedRow >= 0);
    if (selectedRow >= 0) {
      Card cardSelected = deckModel.getCardAtIndex(selectedRow);
      currentSelectedDataLabel.setText(cardSelected.getName());
      
      DeckCardDataObject deckCardDataObject = deckToEdit.getCardsWithinDeck().get(MTGHelper.generateCardKey(cardSelected));
      removalSpinModel.setMaximum(deckCardDataObject.getQuantityOfCard());
    }
  }
}
