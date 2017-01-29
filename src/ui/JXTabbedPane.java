package ui;

import java.awt.Component;

import javax.swing.Icon;
import javax.swing.JTabbedPane;

import ui.TabRenders.DefaultTabRenderer;
import ui.TabRenders.TableRendererInter;

public class JXTabbedPane extends JTabbedPane {

    private TableRendererInter tabRenderer = new DefaultTabRenderer();

    public JXTabbedPane() {
        super();
    }

    public JXTabbedPane(int tabPlacement) {
        super(tabPlacement);
    }

    public JXTabbedPane(int tabPlacement, int tabLayoutPolicy) {
        super(tabPlacement, tabLayoutPolicy);
    }

    public TableRendererInter getTabRenderer() {
        return tabRenderer;
    }

    public void setTabRenderer(TableRendererInter tabRenderer) {
        this.tabRenderer = tabRenderer;
    }

    @Override
    public void addTab(String title, Component component) {
        this.addTab(title, null, component, null);
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        this.addTab(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        int tabIndex = getTabCount() - 1;
        Component tab = tabRenderer.getTabRendererComponent(this, title, icon, tabIndex);
        super.setTabComponentAt(tabIndex, tab);
    }
}
