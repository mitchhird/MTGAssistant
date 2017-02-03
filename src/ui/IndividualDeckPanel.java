package ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import models.cardModels.Format;
import models.deckModels.Deck;
import models.tableModels.DeckContentsTableModel;
import util.Constants;

/**
 * Panel That Contains Information Used Within A Selected Deck Context. Panel shows deck names, creator names, format,
 * descriptions, and overall content
 * 
 * @author Mitchell
 */
public class IndividualDeckPanel extends UIPanelBase {

  // Labels
  private JLabel selectedDeckNameLabel;
  private JLabel selectedCreateLabel;
  private JLabel selectedFormatLabel;
  private JLabel selectedDescriptionLabel;
  private JLabel selectedDeckContentsLabel;

  // Mutating Data
  private JTextField selectedCreatedField;
  private JTextField selectedDeckNameField;
  private JComboBox<Format> selectedFormatField;
  private JTextArea selectedDescriptionField;
  private JTable selectedDeckContentsTable;

  // Data for the retrieval of necessary for rendering
  private Deck currentlySelectedDeck;

  // Constructor When We Know If We Want Edit Everything Or Just The Available Subset
  public IndividualDeckPanel() {
    super();
  }

  // Sets The Editiablity Of All Applicable Items
  public void setEditablity(boolean editable) {
    selectedCreatedField.setEditable(editable);
  }

  @Override
  protected void initVariables() {
    selectedDeckNameLabel = new JLabel(Constants.DECKS_SELECT_NAME_LABEL + ":");
    selectedCreateLabel = new JLabel(Constants.DECKS_SELECT_CREATE_LABEL + ":");
    selectedFormatLabel = new JLabel(Constants.DECKS_SELECT_FORMAT_LABEL + ":");
    selectedDescriptionLabel = new JLabel(Constants.DECKS_SELECT_DESCRIPTION_LABEL + ":");
    selectedDeckContentsLabel = new JLabel(Constants.DECKS_SELECT_CONTENTS_LABEL + ":");

    selectedDeckNameField = new JTextField();
    selectedCreatedField = new JTextField();
    selectedCreatedField.setEditable(false);

    selectedFormatField = new JComboBox<>(Format.values());
    selectedDescriptionField = new JTextArea();

    DeckContentsTableModel contentsModel = new DeckContentsTableModel();
    selectedDeckContentsTable = new JTable(contentsModel);
    selectedDeckContentsTable.setAutoCreateRowSorter(true);
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(selectedCreateLabel, 0, 0, 1, 1, 0.1f, 0.1f);
    addComponentToPanel(selectedCreatedField, 1, 0, 3, 1, 1.0f, 0.1f);

    addComponentToPanel(selectedDeckNameLabel, 0, 1, 1, 1, 0.1f, 0.1f);
    addComponentToPanel(selectedDeckNameField, 1, 1, 3, 1, 1.0f, 0.1f);

    addComponentToPanel(selectedFormatLabel, 0, 2, 1, 1, 0.1f, 0.1f);
    addComponentToPanel(selectedFormatField, 1, 2, 3, 1, 1.0f, 0.1f);

    addComponentToPanel(selectedDescriptionLabel, 0, 3, 1, 1, 0.1f, 0.1f);

    JScrollPane descriptionScroller = new JScrollPane(selectedDescriptionField);
    addComponentToPanel(descriptionScroller, 1, 3, 3, 1, 1.0f, 0.2f);

    addComponentToPanel(selectedDeckContentsLabel, 0, 4, 1, 1, 0.1f, 0.1f);

    JScrollPane selectedDecksScroller = new JScrollPane(selectedDeckContentsTable);
    addComponentToPanel(selectedDecksScroller, 0, 5, 4, 1, 0.1f, 1.0f);
  }

  @Override
  protected void addActionListeners() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void populateLocal() {
    if (currentlySelectedDeck != null) {
      selectedDeckNameField.setText(currentlySelectedDeck.getDeckName());
      selectedCreatedField.setText(currentlySelectedDeck.getCreatingUser());
      selectedDescriptionField.setText(currentlySelectedDeck.getDeckDescription());
      selectedFormatField.setSelectedIndex(currentlySelectedDeck.getDeckFormat().ordinal());
    }
  }

  public void setCurrentlySelectedDeck(Deck incomingDeck) {
    if (incomingDeck != null) {
      currentlySelectedDeck = incomingDeck;
      populateLocal();
    }
  }

  @Override
  protected void applyLocal() {
    currentlySelectedDeck.setCreatingUser(selectedCreatedField.getText().trim());
    currentlySelectedDeck.setDeckDescription(selectedDescriptionField.getText().trim());
    currentlySelectedDeck.setDeckFormat(Format.values()[selectedFormatField.getSelectedIndex()]);
    currentlySelectedDeck.setDeckName(selectedDeckNameField.getText().trim());
  }

}
