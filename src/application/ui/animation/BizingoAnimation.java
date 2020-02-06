package application.ui.animation;

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

	public BizingoAnimation() {
		this.millis = 1000;
	}
	
	public BizingoAnimation(int millis) {
		this.millis = millis;
	}
	
	public void move(BizingoPiece piece, Double[] dest_location) {
		SequentialTransition st = new SequentialTransition();
		Timeline timeline = new Timeline();
		
		final KeyValue kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		final KeyValue kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		final KeyFrame kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
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
	
	public void move(BizingoPiece piece, BizingoTriangle triangle) {
		Double[] dest_location = triangle.getCenter();
		
		SequentialTransition st = new SequentialTransition();
		Timeline timeline = new Timeline();
		
		final KeyValue kv_x = new KeyValue(piece.stack.layoutXProperty(), dest_location[0]-piece.radius);
		final KeyValue kv_y = new KeyValue(piece.stack.layoutYProperty(), dest_location[1]-piece.radius);
		final KeyFrame kf = new KeyFrame(Duration.millis(millis), kv_x, kv_y);
		
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
