package application.ui.utils;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;

public class SoundUtils {
	
	private Media capture;
	private Media move;
	private Media receive;
	private Media send;
	private Media victory;
	private Media defeat;
	private AudioClip capturePlayer;
	private AudioClip movePlayer;
	private AudioClip receivePlayer;
	private AudioClip sendPlayer;
	private AudioClip victoryPlayer;
	private AudioClip defeatPlayer;
	
	
	public SoundUtils() {
		try {
			capture  = new Media(getClass().getResource("/resources/sounds/capture.wav").toURI().toString());
			move     = new Media(getClass().getResource("/resources/sounds/move.wav").toURI().toString());
			receive  = new Media(getClass().getResource("/resources/sounds/receive.wav").toURI().toString());
			send     = new Media(getClass().getResource("/resources/sounds/send.wav").toURI().toString());
			victory  = new Media(getClass().getResource("/resources/sounds/victory.wav").toURI().toString());
			defeat   = new Media(getClass().getResource("/resources/sounds/defeat.wav").toURI().toString());
			
			capturePlayer  = new AudioClip(capture.getSource());
			movePlayer     = new AudioClip(move.getSource());
			receivePlayer  = new AudioClip(receive.getSource());
			sendPlayer     = new AudioClip(send.getSource());
			victoryPlayer  = new AudioClip(victory.getSource());
			defeatPlayer   = new AudioClip(defeat.getSource());
			
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
