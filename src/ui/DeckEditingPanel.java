package ui;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import models.deckModels.Deck;
import models.tableModels.DeckTableModel;
import util.Constants;
import db.DBPersistanceController;

/**
 * Panel for editing decks within the Java UI. Panel contains a picker where you can
 * select a deck, and view the details of the item in another panel
 * @author Mitchell
 *
 */
public class DeckEditingPanel extends UIPanelBase {
  
  private JLabel currentDecksLabel;
  private JTable currentDecksTable;
  private DeckTableModel deckTableModel;
  private IndividualDeckPanel selectedDeckPanel;
  
  public DeckEditingPanel() {
    super();
  }

  @Override
  protected void initVariables() {
    currentDecksLabel = new JLabel(Constants.DECKS_CURRENT_LABEL + ":");    
    deckTableModel = new DeckTableModel();
    selectedDeckPanel = new IndividualDeckPanel(false);

    currentDecksTable = new JTable(deckTableModel);
    currentDecksTable.setAutoCreateRowSorter(true);

    deckTableModel.setDecksToRender(DBPersistanceController.getInstance().getAllDecksInDB());
  }

  @Override
  protected void placeUIElements() {
    addComponentToPanel(currentDecksLabel, 0, 0, 1, 1, 0.1f, 0.1f);
    JScrollPane currentDecksScroller = new JScrollPane(currentDecksTable);
    addComponentToPanel(currentDecksScroller, 0, 1, 4, 1, 1.0f, 0.2f);
    addComponentToPanel(selectedDeckPanel, 0, 2, 4, 1, 1.0f, 0.8f);
  }

  @Override
  protected void addActionListeners() {
    currentDecksTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent paramListSelectionEvent) {
        int selectedRow = currentDecksTable.getSelectedRow();
        if (selectedRow >= 0) {
          Deck deckToRender = deckTableModel.getDeckAtRow(selectedRow);
          selectedDeckPanel.setCurrentlySelectedDeck(deckToRender);
        }
      }
    });
    
  }

  @Override
  protected void populateLocal() {
    
  }

  @Override
  protected void applyLocal() {
    // TODO Auto-generated method stub
    
  }
}
