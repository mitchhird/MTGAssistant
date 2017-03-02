package ui.shared;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JFrame;

public abstract class UIFrameBase extends JFrame {
  // Complex Variable Definitions
  protected final GridBagLayout layout;
  protected final GridBagConstraints gbc;
  
  // Default Constructor For The UI Frames
  protected UIFrameBase() {
    layout = new GridBagLayout();
    gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.BOTH;
    
    setLayout(layout);
  }
  
  protected void initializePanel() {
    initVariables();
    placeUIElements();
    addActionListeners();
  }
  
  protected void addComponentToPanel (JComponent comp, int gridX, int gridY, int gridWidth, int gridHeight, float xWeight, float yWeight) {
    gbc.gridx = gridX;
    gbc.gridy = gridY;
    gbc.gridwidth = gridWidth;
    gbc.gridheight = gridHeight;
    gbc.weightx = xWeight;
    gbc.weighty = yWeight;
    add(comp, gbc);
  }
  
  // Abstract Methods
  protected abstract void initVariables ();
  protected abstract void placeUIElements();
  protected abstract void addActionListeners();
  protected abstract void populateLocal ();
}