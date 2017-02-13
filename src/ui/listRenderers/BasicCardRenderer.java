package ui.listRenderers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

import models.cardModels.Card;
import ui.UIPanelBase;

public class BasicCardRenderer extends UIPanelBase implements ListCellRenderer<Card> {

  protected JLabel nameLabel;
  protected JLabel manaCostLabel;
  protected JLabel typeLabel;
  protected JLabel artistLabel;
  protected JTextArea textDisplayArea;

  public BasicCardRenderer() {

  }

  @Override
  public Component getListCellRendererComponent(JList<? extends Card> list, Card value, int index, boolean isSelected, boolean cellHasFocus) {
    nameLabel.setText(value.getName());
    typeLabel.setText(value.getType());
    textDisplayArea.setText(value.getText());
    
    setBorder(new LineBorder(Color.BLACK));
    if (isSelected) {
      setBackground(list.getSelectionBackground());
      setForeground(list.getSelectionForeground());
      textDisplayArea.setBackground(list.getSelectionBackground());
    }
    else {
      setBackground(list.getBackground());
      setForeground(list.getForeground());
      textDisplayArea.setBackground(list.getBackground());
    }
    return this;
  }

  @Override
  protected void initVariables() {
    setLayout(new BorderLayout());
    nameLabel = new JLabel();
    artistLabel = new JLabel();
    manaCostLabel = new JLabel();
    typeLabel = new JLabel();

    textDisplayArea = new JTextArea();
    textDisplayArea.setEditable(false);
  }

  @Override
  protected void placeUIElements() {
    add(nameLabel, BorderLayout.NORTH);
    add(typeLabel, BorderLayout.CENTER);
    add(textDisplayArea, BorderLayout.SOUTH);
  }

  @Override
  protected void addActionListeners() {
  }

  @Override
  protected void populateLocal() {
  }
}
