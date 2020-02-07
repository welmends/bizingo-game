package application.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class LoginController implements Initializable {
	
	// FXML Variables
	@FXML VBox loginVBox;
	@FXML Label titleLabel;
	@FXML Label ipLabel;
	@FXML TextField ipTextField;
	@FXML Label portLabel;
	@FXML TextField portTextField;
	@FXML Button connectButton;
	
	AnchorPane node1;
	HBox node2;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Font sixty = null;
		try {
			sixty = Font.loadFont(new FileInputStream(new File("./fonts/sixty.ttf")), 100);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		titleLabel.setText("B I Z I N G O    G A M E");
		titleLabel.setFont(sixty);
		
		connectButton.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	makeFadeTransition();
	        }
		});
	}
	
	public void loadFromParent(HBox mainHBox, AnchorPane loginAnchorPane) {
		this.node1 = loginAnchorPane;
		this.node2 = mainHBox;
	}
	
	private void makeFadeTransition() {
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