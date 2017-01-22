package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

/**
 * Simple class that allows us easily provide layouts and constraints to those that need it
 * @author Mitchell
 */
public class UIFrameBase extends JFrame {

  // Complex Variable Definitions
  private final GridBagLayout layout;
  private final GridBagConstraints gbc;
  
  // Default Constructor For The UI Frames
  protected UIFrameBase() {
    layout = new GridBagLayout();
    gbc = new GridBagConstraints();
    setLayout(layout);
  }
}
