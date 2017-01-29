package ui.TabRenders;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

public interface TableRendererInter {
    public Component getTabRendererComponent(JTabbedPane tabbedPane, String text, Icon icon, int tabIndex);
}
