package ui.clientui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import ui.shared.UIPanelBase;
import util.Constants;
import app.MTGAssistantClient;

/**
 * Panel That Governs All Of The Server Based Connection Information. Includes IP, PORT, current status of the
 * connection, and other things
 */
public class ClientConnectPanel extends UIPanelBase {

  private JLabel ipLabel;
  private JLabel portLabel;
  private JLabel userNameLabel;
  private JLabel passwordLabel;
  private JLabel currentStatusLabel;
  private JLabel currentStatusDataLabel;

  private JTextField ipAddressField;
  private JTextField userNameField;
  private JSpinner portSpinner;
  private JPasswordField passwordField;
  private JTable storedConnectionTable;

  private JButton connectButton;
  private JButton connectAndStoreButton;
  
  private final MTGAssistantClient clientApp;

  // Default Constructor For This Panel
  public ClientConnectPanel (MTGAssistantClient mainApp) {
    super();
    clientApp = mainApp;
    initializePanel();
  }

  @Override
  protected void initVariables() {
    ipLabel = new JLabel("Server IP:");
    portLabel = new JLabel("Server Port:");
    userNameLabel = new JLabel("User Name:");
    passwordLabel = new JLabel("Password:");
    currentStatusLabel = new JLabel("Current Connection Status:");
    currentStatusDataLabel = new JLabel("Disconnected");
    currentStatusDataLabel.setForeground(Color.RED);

    ipAddressField = new JTextField("127.0.0.1");
    userNameField = new JTextField();
    passwordField = new JPasswordField();
    storedConnectionTable = new JTable();

    SpinnerNumberModel spinnerData = new SpinnerNumberModel(Constants.SERVER_PORT_MIN, Constants.SERVER_PORT_MIN, Constants.SERVER_PORT_MAX, 1);
    portSpinner = new JSpinner(spinnerData);

    connectButton = new JButton("Connect To Server");
    connectAndStoreButton = new JButton("Connect and Store");
  }

  /**
   * Places The UI Elements On the Screen When Called
   */
  @Override
  protected void placeUIElements() {
    int i = 0;
    JScrollPane scrollWrapper = new JScrollPane(storedConnectionTable);
    addComponentToPanel(scrollWrapper, 0, i, 4, 1, 1.0f, 1.0f);

    i++;
    addComponentToPanel(ipLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(ipAddressField, 1, i, 3, 1, 1.0f, 0.0f);

    i++;
    addComponentToPanel(portLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(portSpinner, 1, i, 3, 1, 1.0f, 0.0f);

    i++;
    addComponentToPanel(userNameLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(userNameField, 1, i, 3, 1, 1.0f, 0.0f);

    i++;
    addComponentToPanel(passwordLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(passwordField, 1, i, 3, 1, 1.0f, 0.0f);

    i++;
    addComponentToPanel(currentStatusLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(currentStatusDataLabel, 1, i, 3, 1, 1.0f, 0.0f);

    i++;
    addComponentToPanel(connectButton, 0, i, 2, 1, 1.0f, 0.0f);
    addComponentToPanel(connectAndStoreButton, 2, i, 2, 1, 1.0f, 0.0f);
  }

  /*
   * (non-Javadoc)
   * 
   * @see ui.shared.UIPanelBase#addActionListeners()
   */
  @Override
  protected void addActionListeners() {
    connectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleConnectButton();
      }
    });
  }

  // Handles The Connect Button Press By Attempting To Connect To The Server Specifed
  private void handleConnectButton() {
    try {
      System.out.println("Connect To Server Button Has Been Pressed");
      String ipAddress = ipAddressField.getText().trim();
      int connectPort = (int) portSpinner.getValue();
      
      clientApp.connectToServer(ipAddress, connectPort);
      performPostConnection();
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  private void performPostConnection() {
    connectButton.setEnabled(false);
    connectAndStoreButton.setEnabled(false);
    JOptionPane.showMessageDialog(this, "Client Connection Succeeded");
  }

  /*
   * (non-Javadoc)
   * 
   * @see ui.shared.UIPanelBase#populateLocal()
   */
  @Override
  protected void populateLocal() {
    // TODO Auto-generated method stub
  }
}
