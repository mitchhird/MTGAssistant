package ui.TabRenders;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 * An extension on the default tab renderer that comes with Swing by default. Instead of using
 * the normal plain looking tabs, this class uses logic to render text in a left centered manner
 */
public class DefaultTabRenderer extends AbstractTabRenderer implements PropertyChangeListener {

  private Component prototypeComponent;

  // Default Constructor For The Class
  public DefaultTabRenderer() {
      prototypeComponent = generateRendererComponent(getDisplayText(), getDisplayIcon(), getHorizontalTextAlignment());
      addPropertyChangeListener(this);
  }

  // Generates The Component To Be Rendered. Component Contains The Icon And The Alignment
  private Component generateRendererComponent(String text, Icon icon, int horizontalTabTextAlignmen) {
      JPanel rendererComponent = new JPanel(new GridBagLayout());
      rendererComponent.setOpaque(false);

      GridBagConstraints c = new GridBagConstraints();
      c.insets = new Insets(2, 4, 2, 4);
      c.fill = GridBagConstraints.HORIZONTAL;
      rendererComponent.add(new JLabel(icon), c);

      c.gridx = 1;
      c.weightx = 1;
      rendererComponent.add(new JLabel(text, horizontalTabTextAlignmen), c);

      return rendererComponent;
  }

  @Override
  public Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex) {
      Component rendererComponent = generateRendererComponent(text, icon, getHorizontalTextAlignment());
      int prototypeWidth = prototypeComponent.getPreferredSize().width;
      int prototypeHeight = prototypeComponent.getPreferredSize().height;
      rendererComponent.setPreferredSize(new Dimension(prototypeWidth, prototypeHeight));
      return rendererComponent;
  }

  @Override
  public void propertyChange(PropertyChangeEvent evt) {
      String propertyName = evt.getPropertyName();
      if ("prototypeText".equals(propertyName) || "prototypeIcon".equals(propertyName)) {
          this.prototypeComponent = generateRendererComponent(getDisplayText(), getDisplayIcon(), getHorizontalTextAlignment());
      }
  }
}