package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ChatController implements Initializable {
	
	// FXML Variables
	@FXML VBox chatVBox;
	@FXML TextArea chatTextArea;
	@FXML TextField chatTextField;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}