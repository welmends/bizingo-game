package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.socket.SocketP2P;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
	
	// Socket
	SocketP2P soc_p2p;
	
	// Fading nodes
	AnchorPane node1;
	HBox node2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		setupComponentsFont();
		
		connectButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	if(ipTextField.getText().length()>0 && portTextField.getText().length()>0) {
	        		
	        		connectButton.setDisable(true);
	        		
	        		soc_p2p.setup(ipTextField.getText(), Integer.valueOf(portTextField.getText()));
	        		
	        		if(soc_p2p.connect()==true) {
	        			if(soc_p2p.isClient()) {
	        				startGame();
	        			}else {
			        		Alert alert = new Alert(Alert.AlertType.INFORMATION);
			        		alert.setTitle("Bizingo Game Alerts");
			        		alert.setResizable(false);
			        		alert.setHeaderText("Aguardando novo jogador!");
			        		alert.showAndWait();
			        		
	        				soc_p2p.start();
	        				start();
	        			}
	        		}else {
		        		Alert alert = new Alert(Alert.AlertType.ERROR);
		        		alert.setTitle("Bizingo Game Alerts");
		        		alert.setResizable(false);
		        		alert.setHeaderText("Conexão mal sucedida!");
		        		alert.showAndWait();
	        		}
	        		
	        	}else {
	        		Alert alert = new Alert(Alert.AlertType.WARNING);
	        		alert.setTitle("Bizingo Game Alerts");
	        		alert.setResizable(false);
	        		alert.setHeaderText("Preencha os campos informando IP/PORTA!");
	        		alert.showAndWait();
	        	}
	        }
		});
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(soc_p2p.isAlive()==false) {
				break;
			}
		}
		startGame();
	}
	
	public void loadFromParent(SocketP2P soc_p2p, HBox mainHBox, AnchorPane loginAnchorPane) {
		this.soc_p2p = soc_p2p;
		this.node1 = loginAnchorPane;
		this.node2 = mainHBox;
	}
	
	private void setupComponentsFont() {
		Font sixty_big = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 100);
		Font sixty_small = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 30);
		
		titleLabel.setText("B I Z I N G O    G A M E");
		titleLabel.setFont(sixty_big);
		
		ipLabel.setText("I P");
		ipLabel.setFont(sixty_small);
		
		portLabel.setText("P O R T");
		portLabel.setFont(sixty_small);
		
		connectButton.setText("C O N N E C T");
		connectButton.setFont(sixty_small);
	}
	
	private void startGame() {
		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(500), node1);
		FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(500), node2);
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
			}
		});
		fadeTransition1.play();
	}
}