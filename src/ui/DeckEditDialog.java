package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import models.deckModels.Deck;

public class DeckEditDialog extends UIDialogBase {

  protected Deck deckToEdit;
  protected IndividualDeckPanel deckDisplayPanel;
  protected DeckCardSearchPanel deckAddPanel;
  protected JTabbedPane tabWrapper;
  protected JButton closeButton;
  protected JButton applyButton;
  
  public DeckEditDialog(Deck deckToEdit, boolean newDeck) {
    super();
    this.deckToEdit = deckToEdit;
    deckDisplayPanel.setEditablity(newDeck);
    setTitle(newDeck ? "New Deck" : "Editing Existing Deck - " + deckToEdit.getDeckName());
    populateLocal();
  }

  @Override
  protected void initVariables() {
    closeButton = new JButton("Close");
    applyButton = new JButton("Apply");
    
    deckDisplayPanel = new IndividualDeckPanel();
    deckAddPanel = new DeckCardSearchPanel(this);
    tabWrapper = new JTabbedPane();
    setModal(false);
    setSize(500, 400);
  }

  @Override
  protected void placeUIElements() {
    tabWrapper.add("Deck Details", deckDisplayPanel);
    tabWrapper.add("Add Cards", deckAddPanel);
    addComponentToPanel(tabWrapper, 0, 0, 6, 1, 0.9f, 1.0f);
    addComponentToPanel(closeButton, 0, 1, 1, 1, 0.05f, 0.0f);
    addComponentToPanel(applyButton, 5, 1, 1, 1, 0.05f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });

    applyButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
  }

  @Override
  protected void populateLocal() {
    deckDisplayPanel.setCurrentlySelectedDeck(deckToEdit);
    deckAddPanel.setDeckToEdit(deckToEdit);
  }

  @Override
  protected void applyLocal() {
    // TODO Auto-generated method stub

  }
  
  public void refreshData() {
    deckDisplayPanel.refreshTable();
  }
}
