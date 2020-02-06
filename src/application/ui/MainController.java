package application.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;

public class MainController implements Initializable {
	
	// FXML Variables
	@FXML HBox mainHBox;
	
	// FXML Loaders
	FXMLLoader bizingoLoader;
	FXMLLoader chatLoader;
	
	// Controllers
	BizingoController bizingoController;
	ChatController chatController;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bizingoLoader = new FXMLLoader(getClass().getResource("/application/scenes/bizingo_scene.fxml"));
		chatLoader = new FXMLLoader(getClass().getResource("/application/scenes/chat_scene.fxml"));
		
		Scene bizingoScene = null;
		Scene chatScene = null;
		
		try {
			bizingoScene = new Scene(bizingoLoader.load());
			chatScene = new Scene(chatLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		mainHBox.getChildren().add(bizingoScene.getRoot());
		mainHBox.getChildren().add(chatScene.getRoot());
		
		bizingoController = bizingoLoader.getController();
		chatController = chatLoader.getController();
	}
}