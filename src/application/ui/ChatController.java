package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
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
	@FXML TextArea chatTextArea;
	@FXML TextField chatTextField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		Font sixty26p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 26);
		
		chatLabel.setText("C H A T");
		chatLabel.setFont(sixty26p);
		
		chatImageView.setImage(new Image(this.getClass().getResourceAsStream("/resources/images/chat_icon.png"), 40, 40, true, true));
		
		chatTextArea.setWrapText(true);
		setTextAreaScrollBehavior();
		
		setTextFieldKeyPressedBehavior();
	}

	private void setTextAreaScrollBehavior() {
		chatTextArea.textProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				chatTextArea.setScrollTop(Double.MAX_VALUE);
			}
			
		});
	}

	private void setTextFieldKeyPressedBehavior() {
		chatTextField.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
	        @Override
	        public void handle(KeyEvent key){
	            if (key.getCode().equals(KeyCode.ENTER)){
	            	
	            	chatTextArea.setText(chatTextArea.getText()+"\n"+chatTextField.getText());
	            	chatTextArea.appendText("");
	            	chatTextField.setText("");
	            }
	        }
	        
	    });
	}
}