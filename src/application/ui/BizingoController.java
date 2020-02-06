package application.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Rectangle;

public class BizingoController implements Initializable {
	
	// FXML Variables
	@FXML Rectangle bizingoRect;
	@FXML Canvas bizingoCanvas;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bizingoRect.setArcWidth(30.0); 
		bizingoRect.setArcHeight(30.0);
	}
}