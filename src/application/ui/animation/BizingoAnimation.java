package application.ui.animation;

import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import application.ui.constants.BizingoConstants;
import application.ui.utils.SoundUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class BizingoAnimation {
	
	public int millis;
	
	public SequentialTransition sequentialTransition;
	private Timeline timeline;
	private KeyValue kv_x;
	private KeyValue kv_y;
	private KeyFrame kf;
	private SoundUtils soundUtils;
	
	public BizingoAnimation() {
		this.millis = BizingoConstants.MOVE_MILLIS;
		soundUtils = new SoundUtils();
	}
	
	public void move(BizingoPiece piece, BizingoTriangle triangle) {
		Double[] dest_location = triangle.getCenter();
		
		sequentialTransition = new SequentialTransition();
		timeline = new Timeline();
		
		kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
		timeline.getKeyFrames().addAll(kf);
		
		sequentialTransition.getChildren().addAll(timeline);
		soundUtils.playMoveSound();
		sequentialTransition.play();
	}
	
	public void move(BizingoPiece piece, Double[] new_location) {
		Double[] dest_location = new_location;
		
		sequentialTransition = new SequentialTransition();
		timeline = new Timeline();
		
		kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
		timeline.getKeyFrames().addAll(kf);
		
		sequentialTransition.getChildren().addAll(timeline);
		soundUtils.playMoveSound();
		sequentialTransition.play();
	}
	
	public void move(BizingoPiece piece, Double x, Double y) {
		Double[] dest_location = new Double[]{x, y};
		
		sequentialTransition = new SequentialTransition();
		timeline = new Timeline();
		
		kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
		timeline.getKeyFrames().addAll(kf);
		
		sequentialTransition.getChildren().addAll(timeline);
		soundUtils.playMoveSound();
		sequentialTransition.play();
	}
}
