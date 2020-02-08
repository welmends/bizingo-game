package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.socket.SocketP2P;
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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class ChatController implements Initializable {
	
	// FXML Variables
	@FXML VBox chatVBox;
	@FXML Label chatLabel;
	@FXML ImageView chatImageView;
	@FXML ScrollPane chatScrollPane;
	@FXML VBox chatVBoxOnScroll;
	@FXML TextField chatTextField;
	
	// Socket
	SocketP2P soc_p2p;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font sixty26p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 26);
		
		chatLabel.setText("C H A T");
		chatLabel.setFont(sixty26p);
		
		chatImageView.setImage(new Image(this.getClass().getResourceAsStream("/resources/images/chat_icon.png"), 40, 40, true, true));
		
		chatScrollPane.setStyle("-fx-background-color:#d8e2eb; -fx-border-color: #7894ac; -fx-border-width: 3;");
		chatVBoxOnScroll.setStyle("-fx-background-color:#d8e2eb;");
		
		setTextFieldKeyPressedBehavior();
	}
	
	public void loadFromParent(SocketP2P soc_p2p) {
		this.soc_p2p = soc_p2p;
	}
	
	private void setTextFieldKeyPressedBehavior() {
		chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
	        @Override
	        public void handle(KeyEvent key){
	            if (key.getCode().equals(KeyCode.ENTER)){
	            	
	                Label txt1 = new Label(chatTextField.getText()+"\n");
	                txt1.setStyle("-fx-font-weight:bold; -fx-text-fill: green;");
	                txt1.setPrefWidth(480);
	                txt1.setPrefHeight(20); 
	                txt1.setAlignment(Pos.CENTER_LEFT);

	                chatVBoxOnScroll.getChildren().addAll(txt1);
	                chatScrollPane.setVvalue(1.0);
	                	                
	                chatTextField.setText("");
	                
	            }
	            if (key.getCode().equals(KeyCode.BACK_SPACE)){	            
	                
	                Label txt1 = new Label(chatTextField.getText()+"\n");
	                txt1.setStyle("-fx-font-weight:bold; -fx-fill: green;");
	                txt1.setPrefWidth(480);
	                txt1.setPrefHeight(20); 
	                txt1.setAlignment(Pos.CENTER_RIGHT);

	                chatVBoxOnScroll.getChildren().addAll(txt1);
	                chatScrollPane.setVvalue(1.0);
	                	                
	                chatTextField.setText("");
	                
	            }
	        }
	        
	    });
	}
}