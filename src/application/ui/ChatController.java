package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

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
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class ChatController implements Initializable {
	
	// FXML Variables
	@FXML VBox chatVBox;
	@FXML Label chatLabel;
	@FXML ImageView chatImageView;
	@FXML ScrollPane chatScrollPane;
	@FXML TextFlow chatTextFlow;
	@FXML TextField chatTextField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font sixty26p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 26);
		
		chatLabel.setText("C H A T");
		chatLabel.setFont(sixty26p);
		
		chatImageView.setImage(new Image(this.getClass().getResourceAsStream("/resources/images/chat_icon.png"), 40, 40, true, true));
		
		setTextFieldKeyPressedBehavior();
	}

	private void setTextFieldKeyPressedBehavior() {
		chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
	        @Override
	        public void handle(KeyEvent key){
	            if (key.getCode().equals(KeyCode.ENTER)){
	            	
	            	Text txt1 = new Text();
	            	txt1.setStyle("-fx-fill: #4F8A10;-fx-font-weight:bold;");
	            	txt1.setText(chatTextField.getText()+"\n");
	            	
	                Text txt2 = new Text();
	                txt2.setStyle("-fx-fill: RED;-fx-font-weight:bold;");
	                txt2.setText(chatTextField.getText()+"\n");
	                
	                Text txt3 = new Text();
	                txt3.setStyle("-fx-fill: BLUE;-fx-font-weight:bold;");
	                txt3.setText(chatTextField.getText()+"\n");
	                
	                chatTextFlow.getChildren().addAll(txt1, txt2, txt3);
	                chatScrollPane.setVvalue(1.0);
	                
	                chatTextField.setText("");
	                
	            }
	        }
	        
	    });
	}
}