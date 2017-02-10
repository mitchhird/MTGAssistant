package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;

import models.cardModels.Format;
import models.deckModels.Deck;
import models.validatorModels.DeckValidator;
import models.validatorModels.ValidatorFactory;
import ui.DeckEditDialog;
import ui.ImageManager;
import ui.UIPanelBase;
import util.Constants;
import db.DBPersistanceController;

/**
 * Panel for editing decks within the Java UI. Panel contains a picker where you can select a deck, and view the details
 * of the item in another panel
 * 
 * @author Mitchell
 * 
 */
public class DecksDisplayPanel extends UIPanelBase {

  private JLabel formatLabel;
  private JLabel deckLabel;
  private JLabel statusLabel;

  private JToolBar deckOperationPanel;

  private JButton newDeckButton;
  private JButton addCardsToDeckButton;
  private JButton deleteDeckButton;
  private JButton applyButton;

  private JComboBox<Deck> deckComboBox;
  private JComboBox<Format> deckFormatCombobox;
  
  private IndividualDeckPanel deckPanel;

  public DecksDisplayPanel() {
    super();
    populateLocal();
  }

  @Override
  protected void initVariables() {
    formatLabel = new JLabel("Format");
    deckLabel = new JLabel("Deck");
    statusLabel = new JLabel("");

    deckComboBox = new JComboBox<Deck>();
    deckFormatCombobox = new JComboBox<>(Format.values());

    deckPanel = new IndividualDeckPanel();
    deckPanel.setBorder(BorderFactory.createTitledBorder("Deck Details"));

    newDeckButton = new JButton(Constants.DECK_TOOL_NEW_DECK);
    addCardsToDeckButton = new JButton(Constants.DECK_TOOL_EDIT_DECK);
    deleteDeckButton = new JButton(Constants.DECK_TOOL_DELETE_DECK);
    applyButton = new JButton("Submit Deck");

    newDeckButton.setIcon(new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_NEW_KEY)));
    addCardsToDeckButton.setIcon(new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_EDIT_KEY)));
    deleteDeckButton.setIcon(new ImageIcon(ImageManager.getInstance().getIconForKey(Constants.ICON_DELETE_KEY)));

    deckOperationPanel = new JToolBar();
    deckOperationPanel.add(newDeckButton);
    deckOperationPanel.add(addCardsToDeckButton);
    deckOperationPanel.add(deleteDeckButton);
    deckOperationPanel.setFloatable(false);

    updateButtonEnable(false);
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
    deckComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        populateDeckDetails();
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

    deckFormatCombobox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        populateLocal();
      }
    });
    
    applyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleApplyButton();
      }
    });
  }

  private void updateButtonEnable(boolean rowSelected) {
    newDeckButton.setEnabled(true);
    addCardsToDeckButton.setEnabled(rowSelected);
    deleteDeckButton.setEnabled(rowSelected);
  }

  private void populateDeckDetails() {
    updateButtonEnable(deckComboBox.getSelectedIndex() > -1);
    if (deckComboBox.getSelectedIndex() > -1) {
      Deck deckToRender = deckComboBox.getItemAt(deckComboBox.getSelectedIndex());
      deckPanel.setCurrentlySelectedDeck(deckToRender);
    }
    statusLabel.setText("");
  }

  @Override
  protected void populateLocal() {
    Format selectedIndex = deckFormatCombobox.getItemAt(deckFormatCombobox.getSelectedIndex());
    List<Deck> allDecksInDB = DBPersistanceController.getInstance().getDecksByFormat(selectedIndex);
    DefaultComboBoxModel<Deck> displayModel = new DefaultComboBoxModel<Deck>();
    for (Deck d : allDecksInDB) {
      displayModel.addElement(d);
    }
    deckComboBox.setModel(displayModel);
    populateDeckDetails();
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
      deckComboBox.removeItemAt(selectedIndex);
      statusLabel.setText("Removed Deck (" + incomingDeck.getDeckName() + ") from system");
    }
  }
  
  private void handleApplyButton () {
    Deck currentSelectedDeck = deckPanel.getCurrentlySelectedDeck();
    DeckValidator validator = ValidatorFactory.getValidatorForDeck(currentSelectedDeck);
    if (validator.isDeckValid(currentSelectedDeck)) {
      
    } else {
      StringBuilder sb = new StringBuilder("Validation Of Deck Failed. Please Fix The Following Errors");
      for (String s: validator.validateDeck(currentSelectedDeck)) {
        sb.append("\n" + s);
      }
      JOptionPane.showMessageDialog(this, sb.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
      
    }
  }
}
