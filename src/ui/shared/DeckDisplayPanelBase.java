package ui.shared;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import models.cardModels.Format;
import models.deckModels.Deck;
import ui.clientui.panels.IndividualDeckPanel;
import db.DBPersistanceController;

/**
 * Panel for editing decks within the Java UI. Panel contains a picker where you can select a deck, and view the details
 * of the item in another panel
 * 
 * @author Mitchell
 * 
 */
public abstract class DeckDisplayPanelBase extends UIPanelBase {

  protected JLabel formatLabel;
  protected JLabel deckLabel;

  protected JComboBox<Deck> deckComboBox;
  protected JComboBox<Format> deckFormatCombobox;
  
  protected Deck currentSelectedDeck;
  protected IndividualDeckPanel deckPanel;
  protected final DBPersistanceController dbController;
  
  // Default Constructor For The Display Panel
  public DeckDisplayPanelBase() {
    super();
    dbController = DBPersistanceController.getInstance();
  }

  @Override
  protected void initVariables() {
    formatLabel = new JLabel("Format");
    deckLabel = new JLabel("Deck");

    deckComboBox = new JComboBox<Deck>();
    deckFormatCombobox = new JComboBox<>(Format.values());

    deckPanel = new IndividualDeckPanel();
    deckPanel.setEnabled(false);
    deckPanel.setBorder(BorderFactory.createTitledBorder("Deck Details"));
  }

  @Override
  protected void addActionListeners() {
    deckComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        populateDeckDetails();
      }
    });

    deckFormatCombobox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        populateLocal();
      }
    });
  }

  // Populates The Individual Deck Details Window With The Specified Items
  protected void populateDeckDetails() {
    if (deckComboBox.getSelectedIndex() > -1) {
      updateDeckUIContents();
    }
  }

  // Updates The Deck UI Contents With The Necessary Information. 
  protected void updateDeckUIContents() {
    deckPanel.setEnabled(true);
    currentSelectedDeck = deckComboBox.getItemAt(deckComboBox.getSelectedIndex());
    if (currentSelectedDeck.getCardsWithinDeck().isEmpty()) {
      dbController.populateDeckContents(currentSelectedDeck);
    }
    deckPanel.setCurrentlySelectedDeck(currentSelectedDeck);
  }

  @Override
  protected void populateLocal() {
    Runnable deckUpdateRunnable = new Runnable() {
      @Override
      public void run() {
        Format selectedIndex = deckFormatCombobox.getItemAt(deckFormatCombobox.getSelectedIndex());
        List<Deck> allDecksInDB = getDecksByFormat(selectedIndex);
        DefaultComboBoxModel<Deck> displayModel = new DefaultComboBoxModel<Deck>();
        for (Deck d : allDecksInDB) {
          displayModel.addElement(d);
        }
        deckComboBox.setModel(displayModel);
        populateDeckDetails();  
      }
    };
    
    Thread deckFetchThread = new Thread(deckUpdateRunnable);
    deckFetchThread.setName("Deck Fetcher Thread");
    deckFetchThread.start();
  }
  
  protected List<Deck> getDecksByFormat(Format selectedIndex) {
    List<Deck> allDecksInDB = dbController.getDecksByFormatNoContent(selectedIndex);
    return allDecksInDB;
  }
}
