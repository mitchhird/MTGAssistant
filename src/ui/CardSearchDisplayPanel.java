package ui;

import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import models.cardModels.Card;

/**
 * Panel that is directly responsible for displaying the results from a search 
 * @author Mitchell
 */
public class CardSearchDisplayPanel extends UIPanelBase {

  private JLabel resultsLabel;
  private JList<Card> displayList;
  private Set<Card> cardsToDisplay;
  
  public CardSearchDisplayPanel(Set<Card> cardsToDisplay) {
    super();
    this.cardsToDisplay = cardsToDisplay;
    populateLocal();
  }
  
  @Override
  protected void initVariables() {
    resultsLabel = new JLabel();
    displayList = new JList<Card>();
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(resultsLabel, 0, 0, 1, 1, 0.1f, 0.1f);
    
    JScrollPane displayWrapper = new JScrollPane(displayList);
    addComponentToPanel(displayWrapper, 0, 1, 1, 1, 0.9f, 0.9f);
  }

  @Override
  protected void addActionListeners() {
    // TODO Auto-generated method stub
  }

  @Override
  protected void populateLocal() {
    DefaultListModel<Card> displayModel = new DefaultListModel<Card>();
    for (Card c: cardsToDisplay) {
      displayModel.addElement(c);
    }
    displayList.setModel(displayModel);
    displayList.repaint();
    
    resultsLabel.setText("Number of Results Returned: " + displayModel.size());
  }

  @Override
  protected void applyLocal() { 
  }
}
