package ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.cardModels.Card;
import models.deckModels.Deck;
import ui.DeckEditDialog;
import ui.UIPanelBase;
import ui.customUiElements.CardDisplayingJList;
import ui.listRenderers.BasicCardRenderer;
import util.Constants;
import db.DBCardSearchDataObject;
import db.DBPersistanceController;

public class DeckCardSearchPanel extends UIPanelBase {

  private JButton searchButton;
  private JButton addCardToDeckButton;

  private JLabel quantityLabel;
  private JLabel currentSelectionCardLabel;
  private JLabel currentSelectCardDataLabel;
  private JLabel statusLabel;

  private JList<Card> searchDisplayList;

  private JTextField searchField;

  private JSpinner quantitySpinner;
  private Deck deckToEdit;
  private final DeckEditDialog parentPanel;

  public DeckCardSearchPanel(DeckEditDialog parent) {
    super();
    this.parentPanel = parent;
  }

  @Override
  protected void initVariables() {
    searchButton = new JButton(Constants.CARD_SEARCH_SEARCH_BUTTON);
    searchField = new JTextField();

    quantityLabel = new JLabel("Quantity");
    currentSelectionCardLabel = new JLabel("Current Selected Card:");
    currentSelectCardDataLabel = new JLabel();
    statusLabel = new JLabel();

    addCardToDeckButton = new JButton("Add Card To Deck");
    quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));

    searchDisplayList = new CardDisplayingJList();
    searchDisplayList.setCellRenderer(new BasicCardRenderer());
  }

  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(searchField, 0, i, 5, 1, 1.0f, 0.0f);
    addComponentToPanel(searchButton, 5, i, 1, 1, 0.0f, 0.0f);

    i++;
    JScrollPane searchDisplayWrapper = new JScrollPane(searchDisplayList);
    addComponentToPanel(searchDisplayWrapper, 0, i, 6, 1, 1.0f, 1.0f);

    i++;
    addComponentToPanel(currentSelectionCardLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(currentSelectCardDataLabel, 1, i, 2, 1, 1.0f, 0.0f);
    addComponentToPanel(quantityLabel, 3, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(quantitySpinner, 4, i, 2, 1, 1.0f, 0.0f);

    i++;
    addComponentToPanel(addCardToDeckButton, 5, i, 1, 1, 0.0f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    addCardToDeckButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleAddButton();
      }
    });

    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleSearchButton();
      }
    });

    searchField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        searchButton.setEnabled(searchField.getText().trim().isEmpty());
      }
    });

    searchDisplayList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        Card selectedValue = searchDisplayList.getSelectedValue();
        if (selectedValue != null) {
          currentSelectCardDataLabel.setText(selectedValue.getName());
        }
      }
    });
  }

  private void handleSearchButton() {
    Set<DBCardSearchDataObject> searchList = new HashSet<>();
    searchList.add(new DBCardSearchDataObject("CARD_NAME", searchField.getText().trim()));
    List<Card> cards = DBPersistanceController.getInstance().getFilteredCards(searchList);

    DefaultListModel<Card> displayModel = new DefaultListModel<Card>();
    for (Card c : cards) {
      displayModel.addElement(c);
    }
    searchDisplayList.setModel(displayModel);
    searchDisplayList.repaint();
  }

  @Override
  protected void populateLocal() {
    // TODO Auto-generated method stub

  }

  @Override
  protected void applyLocal() {
    // TODO Auto-generated method stub

  }

  public void setDeckToEdit(Deck incomingDeck) {
    this.deckToEdit = incomingDeck;
  }

  private void handleAddButton() {
    Integer quantity = (Integer) quantitySpinner.getValue();
    Card cardToAdd = searchDisplayList.getSelectedValue();
    if (cardToAdd != null) {
      deckToEdit.addCardToDeck(cardToAdd, quantity);
      parentPanel.refreshData();
    }
  }
}
