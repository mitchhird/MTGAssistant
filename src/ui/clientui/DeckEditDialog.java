package ui.clientui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTabbedPane;

import models.deckModels.Deck;
import ui.clientui.panels.DeckAddCardPanel;
import ui.clientui.panels.DeckRemoveCardPanel;
import ui.shared.ImageManager;
import ui.shared.IndividualDeckPanel;
import ui.shared.UIDialogBase;
import util.Constants;

public class DeckEditDialog extends UIDialogBase {

  protected Deck deckToEdit;
  protected IndividualDeckPanel editDetailsPanel;
  protected DeckAddCardPanel deckAddPanel;
  protected DeckRemoveCardPanel deckRemovePanel;
  protected JTabbedPane tabWrapper;
  protected JButton closeButton;
  protected JButton applyButton;
  
  public DeckEditDialog(IndividualDeckPanel panel, boolean newDeck) {
    super();
    this.deckToEdit = panel.getCurrentlySelectedDeck();
    initializeDialog();
    setModal(true);
    setTitle(newDeck ? "New Deck" : "Editing Existing Deck - " + deckToEdit.getDeckName());
    setIconImage(ImageManager.getInstance().getIconForKey(Constants.ICON_MAIN_ICON_KEY));
    populateLocal();
  }

  @Override
  protected void initVariables() {
    closeButton = new JButton("Finish");
    applyButton = new JButton("Apply");

    deckAddPanel = new DeckAddCardPanel(this);
    deckRemovePanel = new DeckRemoveCardPanel(this);
    editDetailsPanel = new IndividualDeckPanel();
    editDetailsPanel.setCurrentlySelectedDeck(deckToEdit);
    
    tabWrapper = new JTabbedPane();
    
    setModal(false);
    setSize(500, Constants.MAIN_APP_HEIGHT);
  }

  @Override
  protected void placeUIElements() {
    tabWrapper.add("Deck Details", editDetailsPanel);
    tabWrapper.add("Add Cards", deckAddPanel);
    tabWrapper.add("Remove Cards", deckRemovePanel);
    addComponentToPanel(tabWrapper, 0, 0, 6, 1, 0.9f, 1.0f);
    addComponentToPanel(closeButton, 5, 1, 1, 1, 0.05f, 0.0f);
  }

  @Override
  protected void addActionListeners() {
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        editDetailsPanel.populateDeckDetails(deckToEdit);
        dispose();
      }
    });
  }

  @Override
  protected void populateLocal() {
  }
  
  public void refreshData() {
    editDetailsPanel.refreshTable();
    deckRemovePanel.refreshTable();
  }
  
  public Deck getDeckToEdit() {
    return deckToEdit;
  }
}
