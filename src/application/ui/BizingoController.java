package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.ui.bizingostructure.BizingoBoardGenerator;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import application.ui.utils.BizingoUtils;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BizingoController implements Initializable {
	
	// FXML Variables
	@FXML AnchorPane bizingoPane;
	@FXML Rectangle bizingoRect;
	@FXML Canvas bizingoCanvasFixed;
	@FXML Canvas bizingoCanvasActive;
	@FXML AnchorPane bizingoAnchorPane;
	
	// Variables
	GraphicsContext gc_fixed;
	GraphicsContext gc_active;
	
	BizingoBoardGenerator boardGen;
	BizingoUtils utils;
	
	List<BizingoTriangle> triangles;
	List<BizingoPiece> pieces;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Instantiating objects
		gc_fixed  = bizingoCanvasFixed.getGraphicsContext2D();
		gc_active = bizingoCanvasActive.getGraphicsContext2D();
		boardGen = new BizingoBoardGenerator(60.0);
		utils = new BizingoUtils();
		triangles = new ArrayList<>();
		pieces = new ArrayList<>();
		
		// Generate Board
		boardGen.generateBoard(triangles, pieces, gc_fixed, bizingoAnchorPane);
		
		// Canvas Mouse Pressed - Event Handler
		bizingoCanvasActive.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	gc_active.clearRect(0, 0, bizingoCanvasActive.getWidth(), bizingoCanvasActive.getHeight());
	        	
	        	double x = event.getX();
	        	double y = event.getY();
	        	
	        	int cPiece = utils.findPressedTriangle(x, y, triangles);
	        	
	        	BizingoTriangle spot_triangle;
	        	BizingoTriangle mark_triangle;
	        	BizingoPiece    mark_piece;
	        	mark_triangle = triangles.get(cPiece);
	        	spot_triangle = new BizingoTriangle(true, mark_triangle.getPoints(), boardGen.color_triangle_selected, boardGen.color_triangle_stroke);
	        	spot_triangle.draw(gc_active);
	        	mark_piece = pieces.get(0);
	    		
	    		SequentialTransition st = new SequentialTransition();
	    		Timeline timeline = new Timeline();
	    		
	    		final KeyValue kv_x = new KeyValue(mark_piece.stack.layoutXProperty(), 10);
	    		final KeyValue kv_y = new KeyValue(mark_piece.stack.layoutYProperty(), 10);
	    		final KeyFrame kf = new KeyFrame(Duration.millis(1000), kv_x, kv_y);
	    		
	    		timeline.getKeyFrames().addAll(kf);
	    		timeline.setOnFinished(new EventHandler<ActionEvent>() {
	    			@Override
	    			public void handle(ActionEvent event) {
	    				System.out.println("Timeline ended");
	    			}
	    		});
	    		
	    		st.getChildren().addAll(timeline);
	    		st.play();
	    		st.setOnFinished(new EventHandler<ActionEvent>() {
	    			@Override
	    			public void handle(ActionEvent event) {
	    				System.out.println("SequentialTransition ended");
	    			}
	    		});
	        }
	    });
	}
	
	
}