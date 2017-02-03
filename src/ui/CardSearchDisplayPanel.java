package ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import models.cardModels.Card;
import ui.listRenderers.BasicCardRenderer;

/**
 * Panel that is directly responsible for displaying the results from a search 
 * @author Mitchell
 */
public class CardSearchDisplayPanel extends UIPanelBase {

  private JLabel resultsLabel;
  private JList<Card> displayList;
  private final List<Card> cardsToDisplay;
  private static final long serialVersionUID = 1L;
  
  public CardSearchDisplayPanel(Set<Card> cardsToDisplay) {
    super();
    this.cardsToDisplay = new ArrayList<>(cardsToDisplay);
    Collections.sort(this.cardsToDisplay);
    populateLocal();
  }
  
  @Override
  protected void initVariables() {
    resultsLabel = new JLabel();
    displayList = new JList<Card>();
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(resultsLabel, 0, 0, 1, 1, 0.1f, 0.0f);
    
    JScrollPane displayWrapper = new JScrollPane(displayList);
    displayWrapper.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    addComponentToPanel(displayWrapper, 0, 1, 1, 1, 0.9f, 1.0f);
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

    displayList.setCellRenderer(new BasicCardRenderer());
    displayList.setModel(displayModel);
    displayList.repaint();
    
    resultsLabel.setText("Number of Results Returned: " + displayModel.size());
  }

  @Override
  protected void applyLocal() { 
  }
}
