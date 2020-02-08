package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.socket.SocketP2P;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ChatController extends Thread implements Initializable {
	
	// FXML Variables
	@FXML VBox chatVBox;
	@FXML Label chatLabel;
	@FXML ImageView chatImageView;
	@FXML ScrollPane chatScrollPane;
	@FXML VBox chatVBoxOnScroll;
	@FXML TextField chatTextField;
	
	// Socket
	SocketP2P soc_p2p;
	
	public void loadFromParent(SocketP2P soc_p2p) {
		this.soc_p2p = soc_p2p;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Components setup
		Font sixty26p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 26);
		
		chatLabel.setText("C H A T");
		chatLabel.setFont(sixty26p);
		
		chatImageView.setImage(new Image(this.getClass().getResourceAsStream("/resources/images/chat_icon.png"), 40, 40, true, true));
		
		chatScrollPane.setStyle("-fx-background-color:#d8e2eb; -fx-border-color: #7894ac; -fx-border-width: 3;");
		chatVBoxOnScroll.setStyle("-fx-background-color:#d8e2eb;");
		
		// VBox Scrolls Down Behavior
		setVBoxScrollsBehavior();
		
		// TextField Enter Key Pressed Behavior
		setTextFieldKeyPressedBehavior();
		
		// Trigger for message received
		this.start();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(soc_p2p.messageStackFull()) {
            	// Receive Messages
				String message_received = soc_p2p.get_message();    // Receive Remote

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
				        Label txt = new Label("");
				        txt.setText(message_received);
				        txt.setTextFill(Color.BLACK);
				        txt.setStyle("-fx-font-weight:bold; -fx-background-color: #ffffff; -fx-background-radius: 10 10 10 0;");
				        txt.setPrefWidth(message_received.length()*10+5);
						txt.setPrefHeight(40);
						txt.setAlignment(Pos.CENTER);
		            	
				        StackPane sp = new StackPane();
				        sp.setPrefWidth(480);
				        sp.setPrefHeight(45);
				        sp.getChildren().add(txt);
				        StackPane.setAlignment(txt, Pos.CENTER_LEFT);
						
						chatVBoxOnScroll.getChildren().addAll(sp); // Receive Local
					}
				});
			}
		}
	}
	
	private void setTextFieldKeyPressedBehavior() {
		chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
	        @Override
	        public void handle(KeyEvent key){
	            if (key.getCode().equals(KeyCode.ENTER)){
	            	// Send Messages
			        Label txt = new Label("");
			        txt.setText(chatTextField.getText());
			        txt.setTextFill(Color.BLACK);
			        txt.setStyle("-fx-font-weight:bold; -fx-background-color: #e2ffc9; -fx-background-radius: 10 10 0 10;");
			        
			        txt.setPrefWidth(txt.getText().length()*10+10);
					txt.setPrefHeight(40);
					txt.setAlignment(Pos.CENTER);
	            	
			        StackPane sp = new StackPane();
			        sp.setPrefWidth(480);
			        sp.setPrefHeight(45);
			        sp.getChildren().add(txt);
			        StackPane.setAlignment(txt, Pos.CENTER_RIGHT);
			        
	                chatVBoxOnScroll.getChildren().addAll(sp);    // Send Local
	                soc_p2p.send_message(chatTextField.getText()); // Send Remote                
	                
	                chatTextField.setText("");
	            }
	        }
	        
	    });
	}
	
	private void setVBoxScrollsBehavior() {
		chatVBoxOnScroll.heightProperty().addListener(new ChangeListener<Number>() {

	        @Override
	        public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
	        	if(arg1.intValue()!=0) {
	        		chatScrollPane.setVvalue(1.0);
	        	}
	        }
	        
		});
	}
	
	
}