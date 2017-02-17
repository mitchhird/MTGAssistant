package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import models.cardModels.Card;
import ui.panels.CardSearchDisplayPanel;
import util.Constants;

public class CardAdvancedSearchResultDialog extends UIDialogBase {
  private final CardSearchDisplayPanel displayPanel;
  private JButton closeButton;
  
  public CardAdvancedSearchResultDialog (List<Card> incomingCards) {
    super();
    displayPanel = new CardSearchDisplayPanel(incomingCards);
    initializeDialog();
  }
  
  @Override
  protected void initVariables() {
    closeButton = new JButton("Close Search");
    setModal(true);
    setTitle("Advanced Search Results");
    setSize(Constants.MAIN_APP_WIDTH, Constants.MAIN_APP_HEIGHT);
    setIconImage(ImageManager.getInstance().getIconForKey(Constants.ICON_MAIN_ICON_KEY));
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(displayPanel, 0, 0, 1, 1, 1.0f, 1.0f);
    addComponentToPanel(closeButton, 0, 1, 1, 1, 0, 0);
  }

  @Override
  protected void addActionListeners() {
    closeButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        dispose();
      }
    });
  }

  @Override
  protected void populateLocal() {
    // TODO Auto-generated method stub
    
  }

}
