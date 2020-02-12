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
	
	public SoundUtils() {
		try {
			capture  = new Media(getClass().getResource("/resources/sounds/capture.wav").toURI().toString());
			move     = new Media(getClass().getResource("/resources/sounds/move.wav").toURI().toString());
			receive  = new Media(getClass().getResource("/resources/sounds/receive.wav").toURI().toString());
			send     = new Media(getClass().getResource("/resources/sounds/send.wav").toURI().toString());
			victory  = new Media(getClass().getResource("/resources/sounds/victory.wav").toURI().toString());
			defeat   = new Media(getClass().getResource("/resources/sounds/defeat.wav").toURI().toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private void playSound(Media m) {
        MediaPlayer mediaPlayer = new MediaPlayer(m);
        mediaPlayer.play();
	}
	
	public void playCaptureSound() {
		playSound(capture);
	}
	
	public void playMoveSound() {
		playSound(move);
	}
	
	public void playReceiveSound() {
		playSound(receive);
	}
	
	public void playSendSound() {
		playSound(send);
	}
	
	public void playVictorySound() {
		playSound(victory);
	}
	
	public void playDefeatSound() {
		playSound(defeat);
	}
}
