package ui.serverui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import networking.ServerMainThread;
import ui.shared.UIPanelBase;
import util.Constants;

/**
 * Panel That Contains All Of The Information Relating To Server Starting / Stopping
 * @author Mitchell
 */
public class ServerDetailsPanel extends UIPanelBase {

  // Simple UI Items
  private JLabel portLabel;
  private JLabel serverStatusLabel;
  private JLabel serverStatusDataLabel;
  
  private JSpinner portSpinner;
  private JButton startServerButton;
  
  public ServerDetailsPanel() {
    super();
    initializePanel();
  }
  
  @Override
  protected void initVariables() {
    portLabel = new JLabel("Port:");
    serverStatusDataLabel = new JLabel();
    serverStatusLabel = new JLabel(Constants.SERVER_STATUS_TEXT);
    
    SpinnerNumberModel portSpinnerModel = new SpinnerNumberModel(Constants.SERVER_PORT_MIN, Constants.SERVER_PORT_MIN, Constants.SERVER_PORT_MAX, 1);
    portSpinner = new JSpinner(portSpinnerModel);
    startServerButton = new JButton(Constants.SERVER_START_BUTTON_TEXT);
  }
  
  @Override
  protected void placeUIElements() {
    int i = 0;
    addComponentToPanel(portLabel, 0, i, 1, 1, 0, 0);
    addComponentToPanel(portSpinner, 1, i, 3, 1, 1.0f, 0);
    
    i++;
    addComponentToPanel(serverStatusLabel, 0, i, 1, 1, 0, 0);
    addComponentToPanel(serverStatusDataLabel, 1, i, 2, 1, 1.0f, 0);
    addComponentToPanel(startServerButton, 3, i, 1, 1, 0, 0);
  }
  
  @Override
  protected void addActionListeners() {
    startServerButton.addActionListener(new ActionListener() {   
      @Override
      public void actionPerformed(ActionEvent e) {
        int portToLaunchServer = (int) portSpinner.getValue();
        ServerMainThread newServerThread = new ServerMainThread(portToLaunchServer);
        newServerThread.start();
        
        serverStatusDataLabel.setText("Server Running On Port " + portToLaunchServer);
        startServerButton.setEnabled(false);
        portSpinner.setEnabled(false);
      }
    });
  }
  
  @Override
  protected void populateLocal() {
    // TODO Auto-generated method stub
    
  }
}
