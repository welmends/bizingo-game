package application.ui.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundUtils {
	
	private Media capture;
	private Media move;
	private Media receive;
	private Media send;
	private Media victory;
	private Media defeat;
	private MediaPlayer capturePlayer;
	private MediaPlayer movePlayer;
	private MediaPlayer receivePlayer;
	private MediaPlayer sendPlayer;
	private MediaPlayer victoryPlayer;
	private MediaPlayer defeatPlayer;
	
	
	public SoundUtils() {
		try {
			capture  = new Media(getClass().getResource("/resources/sounds/capture.wav").toURI().toString());
			move     = new Media(getClass().getResource("/resources/sounds/move.wav").toURI().toString());
			receive  = new Media(getClass().getResource("/resources/sounds/receive.wav").toURI().toString());
			send     = new Media(getClass().getResource("/resources/sounds/send.wav").toURI().toString());
			victory  = new Media(getClass().getResource("/resources/sounds/victory.wav").toURI().toString());
			defeat   = new Media(getClass().getResource("/resources/sounds/defeat.wav").toURI().toString());
			
			capturePlayer  = new MediaPlayer(capture);
			movePlayer     = new MediaPlayer(move);
			receivePlayer  = new MediaPlayer(receive);
			sendPlayer     = new MediaPlayer(send);
			victoryPlayer  = new MediaPlayer(victory);
			defeatPlayer   = new MediaPlayer(defeat);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public void playCaptureSound() {
		capturePlayer.play();
	}
	
	public void playMoveSound() {
		movePlayer.play();
	}
	
	public void playReceiveSound() {
		receivePlayer.play();
	}
	
	public void playSendSound() {
		sendPlayer.play();
	}
	
	public void playVictorySound() {
		victoryPlayer.play();
	}
	
	public void playDefeatSound() {
		defeatPlayer.play();
	}
}
