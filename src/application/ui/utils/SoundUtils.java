package application.ui.utils;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class SoundUtils {
	
	private Media capture;
	private Media move;
	private Media receive1;
	private Media receive2;
	private Media victory;
	private Media defeat;
	
	public SoundUtils() {
		try {
			capture  = new Media(getClass().getResource("/resources/sounds/capture.wav").toURI().toString());
			move     = new Media(getClass().getResource("/resources/sounds/move.wav").toURI().toString());
			receive1 = new Media(getClass().getResource("/resources/sounds/receive1.wav").toURI().toString());
			receive2 = new Media(getClass().getResource("/resources/sounds/receive2.wav").toURI().toString());
			victory = new Media(getClass().getResource("/resources/sounds/victory.wav").toURI().toString());
			defeat = new Media(getClass().getResource("/resources/sounds/defeat.wav").toURI().toString());
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
	
	public void playReceive1Sound() {
		playSound(receive1);
	}
	
	public void playReceive2Sound() {
		playSound(receive2);
	}
	
	public void playVictorySound() {
		playSound(victory);
	}
	
	public void playDefeatSound() {
		playSound(defeat);
	}
}
