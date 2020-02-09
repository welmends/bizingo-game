package application.ui.animation;

import application.ui.bizingostructure.BizingoConstants;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

public class BizingoAnimation {
	
	public int millis;
	
	public SequentialTransition st;
	public Timeline timeline;
	public KeyValue kv_x;
	public KeyValue kv_y;
	public KeyFrame kf;
	
	public BizingoAnimation() {
		this.millis = BizingoConstants.MOVE_MILLIS;
	}
	
	public void move(BizingoPiece piece, BizingoTriangle triangle) {
		Double[] dest_location = triangle.getCenter();
		
		st = new SequentialTransition();
		timeline = new Timeline();
		
		kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
		timeline.getKeyFrames().addAll(kf);
		
		st.getChildren().addAll(timeline);
		st.play();
		st.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				piece.setPosition(dest_location);
			}
		});
	}
	
	public void move(BizingoPiece piece, Double[] new_location) {
		Double[] dest_location = new_location;
		
		st = new SequentialTransition();
		timeline = new Timeline();
		
		kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
		timeline.getKeyFrames().addAll(kf);
		
		st.getChildren().addAll(timeline);
		st.play();
		st.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				piece.setPosition(dest_location);
			}
		});
	}
	
	public void move(BizingoPiece piece, Double x, Double y) {
		Double[] dest_location = new Double[]{x, y};
		
		st = new SequentialTransition();
		timeline = new Timeline();
		
		kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
		timeline.getKeyFrames().addAll(kf);
		
		st.getChildren().addAll(timeline);
		st.play();
		st.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				piece.setPosition(dest_location);
			}
		});
	}
}
