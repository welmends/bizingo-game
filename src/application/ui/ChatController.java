package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import application.socket.SocketP2P;
import application.ui.constants.ChatConstants;
import application.ui.utils.FontUtils;
import application.ui.utils.SoundUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
	
	// Variables
	SoundUtils soundUtils;
	
	public void loadFromParent(SocketP2P soc_p2p) {
		this.soc_p2p = soc_p2p;
		soundUtils = new SoundUtils();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Setup components
		setupComponents();
		
		// VBox Scrolls Down Behavior
		setVBoxScrollsBehavior();
		
		// TextField Enter Key Pressed Behavior
		setTextFieldKeyPressedBehavior();
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				Thread.sleep(ChatConstants.THREAD_SLEEP_TIME_MILLIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(soc_p2p.chatMessageStackFull()) {
            	// Receive Messages
				
				// Receive Remote
				String message_received = soc_p2p.getMessage();

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
				        Label txt = new Label("");
				        txt.setText(message_received);
				        txt.setWrapText(true);
				        txt.setTextFill(ChatConstants.COLOR_LABEL_TEXT_RECEIVE);
				        txt.setStyle(ChatConstants.STYLE_LABEL_TEXT_RECEIVE);
				        txt.setPadding(ChatConstants.PADDING_LABEL_TEXT_RECEIVE);
				        txt.setAlignment(ChatConstants.ALIGNMENT_LABEL_TEXT_RECEIVE);
		            	
				        StackPane sp = new StackPane();
				        sp.setPadding(ChatConstants.PADDING_STACK_PANE_RECEIVE);
				        sp.getChildren().add(txt);
				        StackPane.setAlignment(txt, ChatConstants.ALIGNMENT_STACK_PANE_RECEIVE);
				        
				        // Receive Local
				        soundUtils.playReceiveSound();
						chatVBoxOnScroll.getChildren().addAll(sp);
						
						// Find the width and height of the component before the Stage has been shown
		                chatScrollPane.applyCss();
		                chatScrollPane.layout();
		                
		                // Limit the component height
		                sp.setMinHeight(sp.getHeight());
					}
				});
			}
		}
	}
	
	private void setupComponents() {
		chatLabel.setText(ChatConstants.TEXT_LABEL_CHAT);
		chatLabel.setFont(FontUtils.sixty26p);
		
		chatImageView.setImage(new Image(this.getClass().getResourceAsStream("/resources/images/chat_icon.png"), 40, 40, true, true));
		
		chatScrollPane.setStyle("-fx-background-color:#d8e2eb; -fx-background-radius: 10 10 10 10; -fx-border-color: #7894ac; -fx-border-width: 3; -fx-border-radius: 10 10 10 10;");
		
		chatVBoxOnScroll.setStyle("-fx-background-color:#d8e2eb;");
	}
	
	private void setTextFieldKeyPressedBehavior() {
		chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
	        @Override
	        public void handle(KeyEvent key){
	            if (key.getCode().equals(KeyCode.ENTER) && chatTextField.getText().length()>0){
	            	// Send Messages
			        Label txt = new Label("");
			        txt.setText(chatTextField.getText());
			        txt.setWrapText(true);
			        txt.setTextFill(ChatConstants.COLOR_LABEL_TEXT_SEND);
			        txt.setStyle(ChatConstants.STYLE_LABEL_TEXT_SEND);
			        txt.setPadding(ChatConstants.PADDING_LABEL_TEXT_SEND);
			        txt.setAlignment(ChatConstants.ALIGNMENT_LABEL_TEXT_SEND);
			        
			        StackPane sp = new StackPane();
			        sp.setPadding(ChatConstants.PADDING_STACK_PANE_SEND);
			        sp.getChildren().add(txt);
			        StackPane.setAlignment(txt, ChatConstants.ALIGNMENT_STACK_PANE_SEND);
			        
			        // Send Local
			        soundUtils.playSendSound();
	                chatVBoxOnScroll.getChildren().addAll(sp);
	                
	                // Find the width and height of the component before the Stage has been shown
	                chatScrollPane.applyCss();
	                chatScrollPane.layout();
	                
	                // Limit the component height
	                sp.setMinHeight(sp.getHeight());
	                
	                // Send Remote
	                soc_p2p.sendChatMessage(chatTextField.getText()); 
	                
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