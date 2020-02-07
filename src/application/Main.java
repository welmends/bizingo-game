package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	public static void main(String[] args) {  
		launch(args);
	}
	 
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("scenes/main_scene.fxml"));
		primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/resources/images/bizingo_icon.png")));
		primaryStage.setResizable(false);
		primaryStage.setTitle("Bizingo Game");
		primaryStage.setScene(new Scene(root, 1280, 720));
		primaryStage.show();
		
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override public void handle(WindowEvent t) {
		        Platform.exit();
		        System.exit(0);
		    }
		});
	}
}