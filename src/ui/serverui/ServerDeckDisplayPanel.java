package ui.serverui;

import java.awt.GridBagConstraints;

import ui.shared.DeckDisplayPanelBase;

/**
 * Model That Contains The Display Information For The
 * 
 * @author Mitchell
 */
public class ServerDeckDisplayPanel extends DeckDisplayPanelBase {

  // Default Constructor For The Display Panel
  public ServerDeckDisplayPanel() {
    super();
    initializePanel();
    populateLocal();
  }

  @Override
  protected void initVariables() {
    super.initVariables();
    deckPanel.setEnabled(true);
    deckPanel.setEditablity(false);
  }

  @Override
  protected void placeUIElements() {
    int i = 0;
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
  }

  @Override
  protected void updateDeckUIContents() {
    currentSelectedDeck = deckComboBox.getItemAt(deckComboBox.getSelectedIndex());
    if (currentSelectedDeck.getCardsWithinDeck().isEmpty()) {
      dbController.populateDeckContents(currentSelectedDeck);
    }
    deckPanel.setCurrentlySelectedDeck(currentSelectedDeck);
  }
}
