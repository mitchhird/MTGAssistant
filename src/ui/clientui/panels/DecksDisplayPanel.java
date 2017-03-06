package ui.clientui.panels;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import models.cardModels.Format;
import models.deckModels.Deck;
import models.validatorModels.DeckValidator;
import models.validatorModels.ValidatorFactory;
import ui.clientui.DeckEditDialog;
import ui.shared.DeckDisplayPanelBase;
import ui.shared.ImageManager;
import util.Constants;
import app.MTGAssistantClient;

/**
 * Panel for editing decks within the Java UI. Panel contains a picker where you can select a deck, and view the details
 * of the item in another panel
 * 
 * @author Mitchell
 * 
 */
public class DecksDisplayPanel extends DeckDisplayPanelBase {
  private JLabel statusLabel;
  private JToolBar deckOperationPanel;

  private JButton newDeckButton;
  private JButton editDeckButton;
  private JButton deleteDeckButton;
  private JButton applyButton;
  
  private final MTGAssistantClient clientApp;
  private static final long serialVersionUID = 1L;

  // Default Constructor For The Display Panel
  public DecksDisplayPanel(MTGAssistantClient mainApp) {
    super();
    this.clientApp = mainApp;
    initializePanel();
    populateLocal();
  }

  @Override
  protected void initVariables() {
    super.initVariables();
    statusLabel = new JLabel("");

    newDeckButton = new JButton(Constants.DECK_TOOL_NEW_DECK);
    editDeckButton = new JButton(Constants.DECK_TOOL_EDIT_DECK);
    deleteDeckButton = new JButton(Constants.DECK_TOOL_DELETE_DECK);
    applyButton = new JButton("Submit Deck");

    newDeckButton.setIcon(new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_NEW_KEY)));
    editDeckButton.setIcon(new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_EDIT_KEY)));
    deleteDeckButton.setIcon(new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_DELETE_KEY)));

    deckOperationPanel = new JToolBar();
    deckOperationPanel.add(newDeckButton);
    deckOperationPanel.add(editDeckButton);
    deckOperationPanel.add(deleteDeckButton);
    deckOperationPanel.setFloatable(false);
  }

  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(deckOperationPanel, 0, i, 4, 1, 0f, 0f);

    i++;
    gbc.anchor = GridBagConstraints.WEST;
    addComponentToPanel(formatLabel, 0, i, 1, 1, 0f, 0f);
    addComponentToPanel(deckFormatCombobox, 1, i, 3, 1, 1.0f, 0f);

    i++;
    gbc.anchor = GridBagConstraints.EAST;
    addComponentToPanel(deckLabel, 0, i, 1, 1, 0f, 0f);
    addComponentToPanel(deckComboBox, 1, i, 3, 1, 1.0f, 0f);

    i++;
    gbc.anchor = GridBagConstraints.CENTER;
    addComponentToPanel(deckPanel, 0, 3, 4, 1, 1.0f, 1.0f);

    i++;
    gbc.fill = GridBagConstraints.NONE;
    gbc.anchor = GridBagConstraints.SOUTHEAST;
    addComponentToPanel(new JLabel("Status:"), 0, i, 1, 1, 0f, 0f);
    addComponentToPanel(statusLabel, 1, i, 1, 1, 0.0f, 0f);
    addComponentToPanel(applyButton, 3, 4, 1, 1, 0f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    super.addActionListeners();
    
    newDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        System.out.println("New Deck Button Has Been Pressed");
        currentSelectedDeck = new Deck();
        deckPanel.setEnabled(true);
        deckPanel.setCurrentlySelectedDeck(currentSelectedDeck);
      }
    });

    editDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        System.out.println("Add Cards To Deck Button Has Been Pressed");
        deckPanel.populateDeckDetails(currentSelectedDeck);
        DeckEditDialog newDialog = new DeckEditDialog(deckPanel, false);
        newDialog.setVisible(true);
      }
    });

    deleteDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent paramActionEvent) {
        handleDeleteButton();
      }
    });
    
    applyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleApplyButton();
      }
    });
  }

  // Handles The Press Of The Delete Button
  private void handleDeleteButton() {
    System.out.println("Delete Button Has Been Pressed");
    int selectedIndex = deckComboBox.getSelectedIndex();
    if (selectedIndex >= 0) {
      Deck incomingDeck = deckComboBox.getItemAt(selectedIndex);
      dbController.deleteDeckFromDB(incomingDeck);
      deckComboBox.removeItemAt(selectedIndex);
      statusLabel.setText("Removed Deck (" + incomingDeck.getDeckName() + ") from system");
    }
  }
  
  // Handles The Press Of The Apply Button
  private void handleApplyButton () {
    Deck currentSelectedDeck = deckPanel.getCurrentlySelectedDeck();
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(currentSelectedDeck);
    if (validator.isDeckValid(currentSelectedDeck)) {
      attemptDeckSubmission(currentSelectedDeck);
    } else {
      displayDeckErrors(currentSelectedDeck, validator);
    }
  }

  private void displayDeckErrors(Deck currentSelectedDeck, DeckValidator validator) {
    StringBuilder sb = new StringBuilder("Validation Of Deck Failed. Please Fix The Following Errors");
    for (String s: validator.validateDeck(currentSelectedDeck)) {
      sb.append("\n" + s);
    }
    JOptionPane.showMessageDialog(this, sb.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
  }

  private void attemptDeckSubmission(Deck currentSelectedDeck) {
    deckPanel.populateDeckDetails(currentSelectedDeck);
    dbController.deleteDeckFromDB(currentSelectedDeck);
    dbController.addDeckToDB(currentSelectedDeck);
    statusLabel.setText("Deck (" + currentSelectedDeck.getDeckName() +") Has Been Added To The System");
  }
  
  @Override
  protected List<Deck> getDecksByFormat(Format selectedIndex) {
    return clientApp.getDecksByFormat(selectedIndex);
  }
}
