package ui.clientui.panels;

import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import models.cardModels.Card;
import ui.clientui.customUiElements.CardDisplayingJList;
import ui.clientui.listRenderers.BasicCardRenderer;
import ui.shared.UIPanelBase;

/**
 * Panel that is directly responsible for displaying the results from a search 
 * @author Mitchell
 */
public class CardSearchDisplayPanel extends UIPanelBase {

  private JLabel resultsLabel;
  private JLabel returnAmountLabel;
  private JList<Card> displayList;
  private final List<Card> cardsToDisplay;
  private static final long serialVersionUID = 1L;
  
  public CardSearchDisplayPanel(List<Card> cardsToDisplay) {
    super();
    this.cardsToDisplay = cardsToDisplay;
    initializePanel();
    populateLocal();
  }
  
  @Override
  protected void initVariables() {
    resultsLabel = new JLabel();
    returnAmountLabel = new JLabel();
    displayList = new CardDisplayingJList();
    displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(resultsLabel, 0, 0, 1, 1, 0.f, 0.0f);
    addComponentToPanel(resultsLabel, 1, 0, 1, 1, 0.f, 0.0f);

    JScrollPane displayWrapper = new JScrollPane(displayList);
    displayWrapper.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    addComponentToPanel(displayWrapper, 0, 1, 2, 1, 1.0f, 1.0f);
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
    resultsLabel.setText("Search Display (Hover Over Item To View)");
    returnAmountLabel.setText("Results Returned: " + displayModel.size());
  }
}
