package application.ui;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import application.com.P2P;
import application.com.rmi.RMIP2P;
import application.com.socket.SocketP2P;
import application.ui.constants.FontConstants;
import application.ui.constants.LoginConstants;
import application.ui.utils.AlertUtils;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class LoginController extends Thread implements Initializable {
	
	// FXML Variables
	@FXML VBox loginVBox;
	@FXML Label titleLabel;
	@FXML Label ipLabel;
	@FXML TextField ipTextField;
	@FXML Label portLabel;
	@FXML TextField portTextField;
	@FXML Button connectButton;
	@FXML Label technologyLabel;
	@FXML ToggleButton socketTButton;
	@FXML ToggleButton rmiTButton;
	
	// P2P (Socket or RMI)
	P2P p2p;
	
	// Alerts
	AlertUtils alertUtils;
	
	// Fading nodes
	AnchorPane node1;
	HBox node2;
	
	// Controllers
	ChatController chat;
	BizingoController bizingo;
	
	public void loadFromParent(P2P p2p, BizingoController bizingo, ChatController chat, HBox mainHBox, AnchorPane loginAnchorPane) {
		this.p2p = p2p;
		
		this.bizingo = bizingo;
		this.chat = chat;
		
		this.node1 = loginAnchorPane;
		this.node2 = mainHBox;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Instantiating objects
		alertUtils = new AlertUtils();
		
		// Setup components
		setupComponents();
		
		// Buttons Pressed Behavior
		setButtonsPressedBehavior();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(LoginConstants.THREAD_SLEEP_TIME_MILLIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(p2p.isConnected()==true) {
				break;
			}
		}
		// Transition to game
		startGame();
	}
	
	private void setupComponents() {
		titleLabel.setText(LoginConstants.TEXT_LABEL_TITLE);
		titleLabel.setFont(FontConstants.sixty100p);
		
		ipLabel.setText(LoginConstants.TEXT_LABEL_IP);
		ipLabel.setFont(FontConstants.sixty30p);
		
		portLabel.setText(LoginConstants.TEXT_LABEL_PORT);
		portLabel.setFont(FontConstants.sixty30p);
		
		portLabel.setText(LoginConstants.TEXT_LABEL_PORT);
		portLabel.setFont(FontConstants.sixty30p);
		
		connectButton.setText(LoginConstants.TEXT_BUTTON_CONNECTION);
		connectButton.setFont(FontConstants.sixty30p);
		
		technologyLabel.setText(LoginConstants.TEXT_LABEL_TECHNOLOGY);
		technologyLabel.setFont(FontConstants.sixty16p);
		
		socketTButton.setText(LoginConstants.TEXT_TBUTTON_SOCKET);
		socketTButton.setFont(FontConstants.sixty14p);
		socketTButton.setSelected(true);
		
		rmiTButton.setText(LoginConstants.TEXT_TBUTTON_RMI);
		rmiTButton.setFont(FontConstants.sixty14p);
		rmiTButton.setSelected(false);
	}
	
	private void setButtonsPressedBehavior() {
		connectButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	tryConnection();
	        }
		});
		
		connectButton.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
	        @Override
	        public void handle(KeyEvent key){
	            if (key.getCode().equals(KeyCode.ENTER)){
	            	tryConnection();
	            }
	        }
	        
	    });
		
		socketTButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	if(socketTButton.isSelected()) {
	        		rmiTButton.setSelected(true);
	        	}
	        	else {
	        		rmiTButton.setSelected(false);
	        	}
	        }
	        
		});
		
		rmiTButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	if(rmiTButton.isSelected()) {
	        		socketTButton.setSelected(true);
	        	}
	        	else {
	        		socketTButton.setSelected(false);
	        	}
	        }
	        
		});
	}
	
	private void tryConnection() {
		if(ipTextField.getText().length()>0 && portTextField.getText().length()>0) {
    		connectButton.setDisable(true);
    		ipTextField.setDisable(true);
    		portTextField.setDisable(true);
    		
    		//IMPLEMENT STRATEGY PATTERN...
//    		if(socketTButton.isSelected()) {
//    			p2p = new SocketP2P();
//    		}else {
//    			try {
//					p2p = new RMIP2P();
//				} catch (RemoteException e) {
//					e.printStackTrace();
//				}
//    		}
    		
    		p2p.setup(ipTextField.getText(), Integer.valueOf(portTextField.getText()));
    		
    		if(p2p.connect()==true) {
    			if(p2p.isClient()) {
    				// Transition to game
    				startGame();
    			}else {
    				alertUtils.alertLoginInformation();
	        		
    				// Wait for connection
    				p2p.thread_call();
    				
    				// Trigger for client connection
    				start();
    			}
    		}else {
    			alertUtils.alertLoginError();
    	        Platform.exit();
    	        System.exit(0);
    		}
    		
    	}else {
    		alertUtils.alertLoginWarning();
    	}
	}
	
	private void startGame() {
		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(LoginConstants.FADING_TIME_MILLIS), node1);
		FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(LoginConstants.FADING_TIME_MILLIS), node2);
		node2.setVisible(false);
		fadeTransition1.setFromValue(1);
		fadeTransition1.setToValue(0);
		fadeTransition1.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
			    Platform.runLater(new Runnable() {
			        @Override
			        public void run() {
			        }
			    });
			    node1.setVisible(false);
				node2.setVisible(true);
				node2.setOpacity(0);
				fadeTransition2.setFromValue(0);
				fadeTransition2.setToValue(1);
				fadeTransition2.play();
				fadeTransition2.setOnFinished(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent arg0) {
						// Trigger to enable message receive
						bizingo.start();
						chat.start();
					}
				});
			}
		});
		fadeTransition1.play();
	}

}