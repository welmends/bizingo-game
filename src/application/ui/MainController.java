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
	
	// FXML Loaders
	FXMLLoader loginLoader;
	FXMLLoader bizingoLoader;
	FXMLLoader chatLoader;
	
	// Controllers
	LoginController loginController;
	BizingoController bizingoController;
	ChatController chatController;
	
	// Socket
	SocketP2P soc_p2p;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loginLoader = new FXMLLoader(getClass().getResource("/application/scenes/login_scene.fxml"));
		bizingoLoader = new FXMLLoader(getClass().getResource("/application/scenes/bizingo_scene.fxml"));
		chatLoader = new FXMLLoader(getClass().getResource("/application/scenes/chat_scene.fxml"));
		
		Scene loginScene = null;
		Scene bizingoScene = null;
		Scene chatScene = null;
		
		try {
			loginScene = new Scene(loginLoader.load());
			bizingoScene = new Scene(bizingoLoader.load());
			chatScene = new Scene(chatLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		mainHBox.getChildren().add(bizingoScene.getRoot());
		mainHBox.getChildren().add(chatScene.getRoot());
		loginAnchorPane.getChildren().add(loginScene.getRoot());
		
		loginController = loginLoader.getController();
		bizingoController = bizingoLoader.getController();
		chatController = chatLoader.getController();
		
		loginController.loadFromParent(mainHBox, loginAnchorPane);
	}
}