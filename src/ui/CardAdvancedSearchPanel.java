package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import models.cardModels.CardRarity;
import models.cardModels.MagicSet;
import util.Constants;
import db.DBPersistanceController;

/**
 * Panel that is responsbile for holding all of the information regarding card lookups
 */
public class CardAdvancedSearchPanel extends UIPanelBase {

  private JLabel cardNameSearchLabel;
  private JLabel cardNameTextLabel;
  private JLabel cardNameSuperTypeLabel;
  private JLabel cardNameSubTypeLabel;
  private JLabel cardSetLabel;
  private JLabel cardRarityLabel;

  private JTextField cardNameSearchField;
  private JTextField cardNameTextField;
  private JTextField cardSuperTypeField;
  private JTextField cardSubTypeField;
  private JComboBox<MagicSet> setComboBox;
  private JComboBox<CardRarity> rarityComboBox;

  private JButton searchButton;
  
  // Default Constructor That Is Responsible For Creating
  public CardAdvancedSearchPanel() {
    super();
  }

  @Override
  protected void initVariables() {
    cardNameSearchLabel = new JLabel(Constants.CARD_SEARCH_CARD_NAME);
    cardNameTextLabel = new JLabel(Constants.CARD_SEARCH_CARD_TEXT);
    cardNameSubTypeLabel = new JLabel(Constants.CARD_SEARCH_CARD_SUB);
    cardNameSuperTypeLabel = new JLabel(Constants.CARD_SEARCH_CARD_SUPER);
    cardSetLabel = new JLabel(Constants.CARD_SEARCH_CARD_SET);
    cardRarityLabel = new JLabel(Constants.CARD_SEARCH_CARD_RARITY);

    cardNameSearchField = new JTextField();
    cardNameTextField = new JTextField();
    cardSubTypeField = new JTextField();
    cardSuperTypeField = new JTextField();

    setComboBox = new JComboBox<MagicSet>();
    rarityComboBox = new JComboBox<CardRarity>(CardRarity.values());

    searchButton = new JButton(Constants.CARD_SEARCH_SEARCH_BUTTON);
  }

  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(cardNameSearchLabel, 0, i, 1, 1, 1.0f, 0.1f);
    addComponentToPanel(cardNameSearchField, 1, i, 5, 1, 1.0f, 0.1f);

    i++;
    addComponentToPanel(cardNameTextLabel, 0, i, 1, 1, 1.0f, 0.1f);
    addComponentToPanel(cardNameTextField, 1, i, 5, 1, 1.0f, 0.1f);
    
    i++;
    addComponentToPanel(cardNameSuperTypeLabel, 0, i, 1, 1, 1.0f, 0.1f);
    addComponentToPanel(cardSuperTypeField, 1, i, 2, 1, 1.0f, 0.1f);
    addComponentToPanel(cardNameSubTypeLabel, 3, i, 1, 1, 1.0f, 0.1f);
    addComponentToPanel(cardSubTypeField, 4, i, 2, 1, 1.0f, 0.1f);

    i++;
    addComponentToPanel(cardSetLabel, 0, i, 1, 1, 1.0f, 0.1f);
    addComponentToPanel(setComboBox, 1, i, 2, 1, 1.0f, 0.1f);
    addComponentToPanel(cardRarityLabel, 3, i, 1, 1, 1.0f, 0.1f);
    addComponentToPanel(rarityComboBox, 4, i, 2, 1, 1.0f, 0.1f);
    
    i++;
    addComponentToPanel(searchButton, 0, i, 6, 1, 1.0f, 0.05f);
  }

  @Override
  protected void addActionListeners() {
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Search Button Has Been Pressed");
      }
    });
  }

  @Override
  protected void populateLocal() {
    List<MagicSet> mtgSets = DBPersistanceController.getInstance().getAllMagicSetsInDB();
    MagicSet[] mtgSetsArray = mtgSets.toArray(new MagicSet[mtgSets.size()]);
    DefaultComboBoxModel<MagicSet> model = new DefaultComboBoxModel<MagicSet>(mtgSetsArray);
    setComboBox.setModel(model);
  }

  @Override
  protected void applyLocal() {
    // TODO Auto-generated method stub

  }
  
  public static void main (String[] args) {
    JFrame testFrame = new JFrame();
    testFrame.setSize(Constants.MAIN_APP_WIDTH, 200);
    testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    testFrame.setVisible(true);
    testFrame.setTitle("TEST Search Panel");
    
    CardAdvancedSearchPanel searchPanel = new CardAdvancedSearchPanel();
    testFrame.add(searchPanel);
    
  }
}
