package ui.panels;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import models.cardModels.Card;
import models.cardModels.CardRarity;
import models.cardModels.MagicSet;
import ui.UIPanelBase;
import util.Constants;
import db.DBCardSearchDataObject;
import db.DBPersistanceController;

/**
 * Panel that is responsbile for holding all of the information regarding card lookups
 */
public class CardAdvancedSearchPanel extends UIPanelBase {

  private JLabel cardNameSearchLabel;
  private JLabel cardNameTextLabel;
  private JLabel cardFlavourText;
  private JLabel cardArtistLabel;
  private JLabel cardNameSuperTypeLabel;
  private JLabel cardNameSubTypeLabel;
  private JLabel cardSetLabel;
  private JLabel cardRarityLabel;
  
  private JTextField cardNameSearchField;
  private JTextField cardNameTextField;
  private JTextField cardFlavourTextField;
  private JTextField cardArtistTextField;
  private JTextField cardSuperTypeField;
  private JTextField cardSubTypeField;
  private JList<MagicSet> setComboBox;
  private JList<CardRarity> rarityComboBox;

  private JButton searchButton;

  // Default Constructor That Is Responsible For Creating
  public CardAdvancedSearchPanel() {
    super();
    initializePanel();
    populateLocal();
  }

  @Override
  protected void initVariables() {
    cardNameSearchLabel = new JLabel(Constants.CARD_SEARCH_CARD_NAME);
    cardNameTextLabel = new JLabel(Constants.CARD_SEARCH_CARD_TEXT);
    cardFlavourText = new JLabel(Constants.CARD_SEARCH_CARD_FLAVOUR);
    cardArtistLabel = new JLabel(Constants.CARD_SEARCH_CARD_ARTIST);
    cardNameSubTypeLabel = new JLabel(Constants.CARD_SEARCH_CARD_SUB);
    cardNameSuperTypeLabel = new JLabel(Constants.CARD_SEARCH_CARD_SUPER);
    cardSetLabel = new JLabel(Constants.CARD_SEARCH_CARD_SET);
    cardRarityLabel = new JLabel(Constants.CARD_SEARCH_CARD_RARITY);

    cardNameSearchField = new JTextField();
    cardNameTextField = new JTextField();
    cardSubTypeField = new JTextField();
    cardSuperTypeField = new JTextField();
    cardFlavourTextField = new JTextField();
    cardArtistTextField = new JTextField();

    setComboBox = new JList<MagicSet>();
    rarityComboBox = new JList<CardRarity>();

    searchButton = new JButton(Constants.CARD_SEARCH_SEARCH_BUTTON);
  }

  @Override
  protected void placeUIElements() {
    int i = 0;

    gbc.anchor = GridBagConstraints.WEST;
    addComponentToPanel(cardNameSearchLabel, 0, i, 1, 1, 0.2f, 0.1f);

    gbc.anchor = GridBagConstraints.EAST;
    addComponentToPanel(cardNameSearchField, 1, i, 5, 1, 0.8f, 0.1f);

    i++;
    addComponentToPanel(cardNameTextLabel, 0, i, 1, 1, 0.2f, 0.1f);
    addComponentToPanel(cardNameTextField, 1, i, 5, 1, 0.8f, 0.1f);
    
    i++;
    addComponentToPanel(cardFlavourText, 0, i, 1, 1, 0.2f, 0.1f);
    addComponentToPanel(cardFlavourTextField, 1, i, 5, 1, 0.8f, 0.1f);
    
    i++;
    addComponentToPanel(cardArtistLabel, 0, i, 1, 1, 0.2f, 0.1f);
    addComponentToPanel(cardArtistTextField, 1, i, 5, 1, 0.8f, 0.1f);

    i++;
    addComponentToPanel(cardNameSuperTypeLabel, 0, i, 1, 1, 0.2f, 0.1f);
    addComponentToPanel(cardSuperTypeField, 1, i, 2, 1, 0.8f, 0.1f);
    addComponentToPanel(cardNameSubTypeLabel, 3, i, 1, 1, 0.2f, 0.1f);
    addComponentToPanel(cardSubTypeField, 4, i, 2, 1, 0.8f, 0.1f);

    i++;
    JScrollPane setScrollWrapper = new JScrollPane(setComboBox);
    addComponentToPanel(cardSetLabel, 0, i, 1, 1, 0.1f, 0.1f);
    addComponentToPanel(setScrollWrapper, 1, i, 2, 1, 0.4f, 0.1f);

    JScrollPane rarityScrollWrapper = new JScrollPane(rarityComboBox);
    addComponentToPanel(cardRarityLabel, 3, i, 1, 1, 0.1f, 0.1f);
    addComponentToPanel(rarityScrollWrapper, 4, i, 2, 1, 0.4f, 0.1f);

    i++;
    addComponentToPanel(searchButton, 0, i, 6, 1, 1.0f, 0.05f);
  }

  @Override
  protected void addActionListeners() {
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleSearchButtonPressed();
      }
    });
  }

  // Handles The Search Button Press
  private void handleSearchButtonPressed() {
    Set<DBCardSearchDataObject> searchRequests = new HashSet<>();
    addSearchIfDefined(searchRequests, "CARD_NAME", cardNameSearchField.getText());
    addSearchIfDefined(searchRequests, "CARD_TEXT", cardNameTextField.getText());
    addSearchIfDefined(searchRequests, "CARD_SUPERTYPES", cardSuperTypeField.getText());
    addSearchIfDefined(searchRequests, "CARD_SUBTYPES", cardSubTypeField.getText());
    addSearchIfDefined(searchRequests, "FLAVOUR_TEXT", cardFlavourTextField.getText());
    addSearchIfDefined(searchRequests, "ARTIST", cardArtistTextField.getText());
    
    List<MagicSet> setSelected = setComboBox.getSelectedValuesList();
    addSearchIfDefined(searchRequests, "CODE", setSelected);

    List<CardRarity> raritiesSelected = rarityComboBox.getSelectedValuesList();
    addSearchIfDefined(searchRequests, raritiesSelected, "RARITY");

    List<Card> filteredCards = DBPersistanceController.getInstance().getFilteredCards(searchRequests);

    JDialog testDialog = new JDialog(new JFrame());
    CardSearchDisplayPanel display = new CardSearchDisplayPanel(filteredCards);
    testDialog.setSize(Constants.MAIN_APP_WIDTH, Constants.MAIN_APP_HEIGHT);
    testDialog.add(display);
    testDialog.setVisible(true);
  }

  // Adds Another Search Parameter If Is Defined
  private void addSearchIfDefined(Set<DBCardSearchDataObject> curSearch, String key, String value) {
    addSearchIfDefined(curSearch, key, value, true);
  }

  private void addSearchIfDefined(Set<DBCardSearchDataObject> curSearch, String key, String value, boolean andTerm) {
    if (value != null && !value.trim().isEmpty()) {
      curSearch.add(new DBCardSearchDataObject(key, value, andTerm));
    }
  }

  private void addSearchIfDefined(Set<DBCardSearchDataObject> curSearch, String key, List<MagicSet> values) {
    if (values != null) {
      for (int i = 0; i < values.size(); i++) {
        boolean isFirstTerm = i == 0;
        addSearchIfDefined(curSearch, key, values.get(i).getCode(), isFirstTerm);
      }
    }
  }
 
  private void addSearchIfDefined(Set<DBCardSearchDataObject> curSearch, List<CardRarity> values, String key) {
    if (values != null) {
      StringBuilder builder = new StringBuilder("(");
      for (int i = 0; i < values.size(); i++) {
        boolean isFirstTerm = i == 0;
        addSearchIfDefined(curSearch, key, values.get(i).name(), isFirstTerm);
      }
      builder.append(")");
    }
  }

  @Override
  protected void populateLocal() {
    List<MagicSet> mtgSets = DBPersistanceController.getInstance().getAllMagicSetsInDB();
    Collections.sort(mtgSets);
    MagicSet[] mtgSetsArray = mtgSets.toArray(new MagicSet[mtgSets.size()]);
    DefaultComboBoxModel<MagicSet> model = new DefaultComboBoxModel<MagicSet>(mtgSetsArray);
    setComboBox.setModel(model);
    
    DefaultComboBoxModel<CardRarity> rarities = new DefaultComboBoxModel<>(CardRarity.values());
    rarityComboBox.setModel(rarities);
  }
}
