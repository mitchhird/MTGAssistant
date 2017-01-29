package ui.TabRenders;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.Icon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 * Abstract Class Used For SubClassing. 
 */
public abstract class AbstractTabRenderer implements TableRendererInter {

  private String displayText = "";
  private Icon displayIcon = UIManager.getIcon("OptionPane.informationIcon");
  private int horizontalTextAlignment = SwingConstants.CENTER;
  private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

  // Default Constructor
  public AbstractTabRenderer() {
      super();
  }

  // Sets The Display Text For This Item. Fires Off A Property Change For All Those Listening
  public void setDisplayText(String text) {
      String oldText = this.displayText;
      this.displayText = text;
      firePropertyChange("prototypeText", oldText, text);
  }

  public String getDisplayText() {
      return displayText;
  }

  public Icon getDisplayIcon() {
      return displayIcon;
  }

  // Sets The Display Icon For This Item
  public void setDisplayIcon(Icon icon) {
      Icon oldIcon = this.displayIcon;
      this.displayIcon = icon;
      firePropertyChange("prototypeIcon", oldIcon, icon);
  }

  public int getHorizontalTextAlignment() {
      return horizontalTextAlignment;
  }

  public void setHorizontalTextAlignment(int horizontalTextAlignment) {
      this.horizontalTextAlignment = horizontalTextAlignment;
  }

  /****************************************************************************************************************
   *                                         Listener Methods                                                     *
   ****************************************************************************************************************/
  public PropertyChangeListener[] getPropertyChangeListeners() {
      return propertyChangeSupport.getPropertyChangeListeners();
  }

  public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
      return propertyChangeSupport.getPropertyChangeListeners(propertyName);
  }

  public void addPropertyChangeListener(PropertyChangeListener listener) {
      propertyChangeSupport.addPropertyChangeListener(listener);
  }

  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
  }

  protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
      PropertyChangeListener[] listeners = getPropertyChangeListeners();
      for (int i = listeners.length - 1; i >= 0; i--) {
          listeners[i].propertyChange(new PropertyChangeEvent(this, propertyName, oldValue, newValue));
      }
  }
}