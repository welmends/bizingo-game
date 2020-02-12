package application.ui.utils;

import application.ui.constants.ImageConstants;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;

public class AlertUtils {
	
	private String alert_title;
	
	public AlertUtils() {
		this.alert_title = "Bizingo Game Alerts";
	}
	
	
	// Login
	
	public void alertLoginInformation() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("Aguardando novo jogador!");
		alert.showAndWait();
	}
	
	public void alertLoginError() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("Conexão mal sucedida!");
		alert.showAndWait();
	}
	
	public void alertLoginWarning() {
		Alert alert = new Alert(Alert.AlertType.WARNING);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("Preencha os campos informando IP/PORTA!");
		alert.showAndWait();
	}

	// Bizingo
	
	public void alertWinner() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setGraphic(new ImageView(ImageConstants.WINNER_IMAGE));
		alert.setHeaderText("PARABÉNS!!! VOCÊ VENCEU A PARTIDA!!!");
	    alert.setOnHidden(evt -> Platform.exit());
	    alert.show(); 
	}
	
	public void alertLoser() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setGraphic(new ImageView(ImageConstants.LOSER_IMAGE));
		alert.setHeaderText("SINTO MUITO, VOCÊ PERDEU A PARTIDA!");
	    alert.setOnHidden(evt -> Platform.exit());
	    alert.show();
	}

	public void alertDisconnected() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("O outro jogador saiu do jogo!");
		alert.showAndWait();
	}
	
	public ButtonType alertLeaveGame() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("Deseja realmente sair da partida?");
		alert.showAndWait();
		return alert.getResult();
	}
	
	public ButtonType alertRestartGameRequest() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("Deseja realmente reiniciar a partida?");
		alert.showAndWait();
		return alert.getResult();
	}
	
	public ButtonType alertRestartGameResponse() {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("O outro jogador deseja reiniciar a partida. Você aceita?");
		alert.showAndWait();
		return alert.getResult();
	}
	
	public void alertRestartGameSucceeded() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("O outro jogador aceitou o reinicio da partida");
		alert.showAndWait();
	}
	
	public void alertRestartGameFailed() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(this.alert_title);
		alert.setResizable(false);
		alert.setHeaderText("O outro jogador negou o reinicio da partida");
		alert.showAndWait();
	}
	
}
