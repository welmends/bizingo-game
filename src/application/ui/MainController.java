package application.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.socket.SocketP2P;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController implements Initializable {
	
	// FXML Variables
	@FXML AnchorPane root;
	@FXML HBox mainHBox;
	@FXML AnchorPane loginAnchorPane;
	
	// Socket
	SocketP2P soc_p2p;
	
	// FXML Loaders
	FXMLLoader loginLoader;
	FXMLLoader bizingoLoader;
	FXMLLoader chatLoader;
	
	// Controllers
	LoginController loginController;
	BizingoController bizingoController;
	ChatController chatController;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Initialize Objects
		soc_p2p = new SocketP2P();
		
		Scene loginScene = null;
		Scene bizingoScene = null;
		Scene chatScene = null;
		
		loginLoader = new FXMLLoader(getClass().getResource("/application/scenes/login_scene.fxml"));
		bizingoLoader = new FXMLLoader(getClass().getResource("/application/scenes/bizingo_scene.fxml"));
		chatLoader = new FXMLLoader(getClass().getResource("/application/scenes/chat_scene.fxml"));
		
		//Load Scenes
		try {
			loginScene = new Scene(loginLoader.load());
			bizingoScene = new Scene(bizingoLoader.load());
			chatScene = new Scene(chatLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Add nodes to MainController holders 
		mainHBox.getChildren().add(bizingoScene.getRoot());
		mainHBox.getChildren().add(chatScene.getRoot());
		loginAnchorPane.getChildren().add(loginScene.getRoot());
		
		// Get Controller
		loginController = loginLoader.getController();
		bizingoController = bizingoLoader.getController();
		chatController = chatLoader.getController();
		
		// Load common objects from parent
		loginController.loadFromParent(soc_p2p, bizingoController, chatController, mainHBox, loginAnchorPane);
		bizingoController.loadFromParent(soc_p2p);
		chatController.loadFromParent(soc_p2p);
	}
}