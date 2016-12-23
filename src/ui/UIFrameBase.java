package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

/**
 * Simple class that allows us easily provide layouts and constraints to those that need it
 * @author Mitchell
 *
 */
public class UIFrameBase {

  // Complex Variable Definitions
  private GridBagLayout layout;
  private GridBagConstraints gbc;
  
  // Default Constructor For The UI Frames
  private UIFrameBase() {
    layout = new GridBagLayout();
    gbc = new GridBagConstraints();
  }
}
