package ui.clientui.panels;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import networking.ClientConnection;
import ui.shared.UIPanelBase;
import util.Constants;
import app.MTGAssistantClient;

/**
 * Panel That Governs All Of The Server Based Connection Information. Includes IP, PORT, current status of the
 * connection, and other things
 */
public class ClientConnectPanel extends UIPanelBase implements Observer {

  private JLabel ipLabel;
  private JLabel portLabel;
  private JLabel currentStatusLabel;
  private JLabel currentStatusDataLabel;

  private JTextField ipAddressField;
  private JSpinner portSpinner;

  private JButton connectButton;
  private JButton disconnectButton;
  
  private ClientConnection clientConnection;
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
    currentStatusLabel = new JLabel("Current Connection Status:");
    currentStatusDataLabel = new JLabel("");
    
    ipAddressField = new JTextField("127.0.0.1");
    SpinnerNumberModel spinnerData = new SpinnerNumberModel(Constants.SERVER_PORT_MIN, Constants.SERVER_PORT_MIN, Constants.SERVER_PORT_MAX, 1);
    portSpinner = new JSpinner(spinnerData);

    connectButton = new JButton("Connect To Server");
    disconnectButton = new JButton("Disconnect From Server");
    setConnectedToServer(false);
  }

  /**
   * Places The UI Elements On the Screen When Called
   */
  @Override
  protected void placeUIElements() {
    
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel placeHolderPanel = new JPanel(new GridBagLayout());
    placeHolderPanel.setBorder(BorderFactory.createTitledBorder("Connection Details"));
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.BOTH;
    gbc.gridx = 0;
    gbc.gridy = 0;
    
    gbc.weightx = 0.0f;
    placeHolderPanel.add(ipLabel, gbc);
    
    gbc.gridx = 1;
    gbc.weightx = 1.0f;
    placeHolderPanel.add(ipAddressField, gbc);
    
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.weightx = 0.0f;
    placeHolderPanel.add(portLabel, gbc);
    
    gbc.gridx = 1;
    gbc.weightx = 1.0f;
    placeHolderPanel.add(portSpinner, gbc);
    
    int i = 0;
    addComponentToPanel(placeHolderPanel, 0, 0, 4, 1, 1.0f, 0.0f);

    i++;
    JLabel blankSpaceLabel = new JLabel();
    addComponentToPanel(blankSpaceLabel, 0, i, 4, 1, 0.0f, 1.0f);

    i++;
    addComponentToPanel(currentStatusLabel, 0, i, 1, 1, 0.0f, 0.0f);
    addComponentToPanel(currentStatusDataLabel, 1, i, 2, 1, 1.0f, 0.0f);
    
    i++;
    addComponentToPanel(disconnectButton, 0, i, 2, 1, 1.0f, 0.0f);
    addComponentToPanel(connectButton, 2, i, 1, 2, 1.0f, 0.0f);
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
    
    disconnectButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        handleDisconnectButton();
      }
    });
  }
  
  // Handles The Delete Button Press By Attempting To Disconnect The Connection
  private void handleDisconnectButton () {
    clientApp.disconnectFromServer();
    clientConnection = null;
    JOptionPane.showMessageDialog(this, "Client Connection Has Been Terminated");
  }

  // Handles The Connect Button Press By Attempting To Connect To The Server Specifed
  private void handleConnectButton() {
    try {
      String ipAddress = ipAddressField.getText().trim();
      int connectPort = (int) portSpinner.getValue();
      
      this.clientConnection = clientApp.connectToServer(ipAddress, connectPort);
      clientConnection.addObserver(this);
      setConnectedToServer(true);
      JOptionPane.showMessageDialog(this, "Client Connection Succeeded");
    } catch (IOException e) {
      e.printStackTrace();
      JOptionPane.showMessageDialog(this, e.getLocalizedMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
    }
  }

  public void setConnectedToServer (boolean connected) {
    String connectionStatus = connected ? "Connected" : "Disconnected";
    Color displayColour = connected ? Color.GREEN : Color.RED;
    currentStatusDataLabel.setForeground(displayColour);
    currentStatusDataLabel.setText(connectionStatus);
    connectButton.setEnabled(!connected);
    disconnectButton.setEnabled(connected);
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

  @Override
  public void update(Observable o, Object arg) {
    setConnectedToServer(clientConnection != null && clientConnection.isConnectedToServer());
  }
}
