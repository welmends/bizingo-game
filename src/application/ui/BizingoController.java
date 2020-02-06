package application.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.ui.bizingostructure.BizingoBoardGenerator;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
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
import javafx.scene.paint.Color;
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
	
	List<BizingoTriangle> trianglesType1;
	List<BizingoTriangle> trianglesType2;
	
	List<BizingoPiece> piecesPlayer1;
	List<BizingoPiece> piecesPlayer2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bizingoRect.setArcWidth(30.0); 
		bizingoRect.setArcHeight(30.0);
		bizingoRect.setFill(Color.rgb(203,236,215));
		
		gc_fixed  = bizingoCanvasFixed.getGraphicsContext2D();
		gc_active = bizingoCanvasActive.getGraphicsContext2D();
		
		
		boardGen = new BizingoBoardGenerator(60.0);
		trianglesType1 = boardGen.generateTrianglesType1(gc_fixed);
		trianglesType2 = boardGen.generateTrianglesType2(gc_fixed);
		piecesPlayer1 = boardGen.generatePiecesPlayer1(bizingoAnchorPane, trianglesType1);
		piecesPlayer2 = boardGen.generatePiecesPlayer2(bizingoAnchorPane, trianglesType2);
		
		bizingoCanvasActive.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	
	        	double x = event.getX();
	        	double y = event.getY();
	        	
	        	gc_active.clearRect(0, 0, bizingoCanvasActive.getWidth(), bizingoCanvasActive.getHeight());
	        	
	        	int cType = 1;
	        	int cPiece = 0;
	        	double min_dist, dist;
	        	
				min_dist = dist = Math.sqrt(Math.pow(x-trianglesType1.get(0).getCenter()[0], 2) + Math.pow(y-trianglesType1.get(0).getCenter()[1], 2));	  
	        	for(int i=1; i<trianglesType1.size(); i++) {
	        		dist = Math.sqrt(Math.pow(x-trianglesType1.get(i).getCenter()[0], 2) + Math.pow(y-trianglesType1.get(i).getCenter()[1], 2));
	        		if(dist<min_dist) {
	        			cPiece = i;
	        			min_dist = dist;
	        		}
	        	}
	        	for(int i=0; i<trianglesType2.size(); i++) {
	        		dist = Math.sqrt(Math.pow(x-trianglesType2.get(i).getCenter()[0], 2) + Math.pow(y-trianglesType2.get(i).getCenter()[1], 2));
	        		if(dist<min_dist) {
	        			cType = 2;
	        			cPiece = i;
	        			min_dist = dist;
	        		}
	        	}
	        	
	        	BizingoTriangle spot_triangle;
	        	BizingoTriangle mark_triangle;
	        	BizingoPiece    mark_piece;
	        	if(cType==1) {
	        		mark_triangle = trianglesType1.get(cPiece);
	        		
	        	}else {
	        		mark_triangle = trianglesType2.get(cPiece);
	        	}
	        	spot_triangle = new BizingoTriangle(mark_triangle.getPoints(), boardGen.color_triangle_selected, boardGen.color_triangle_stroke);
	        	spot_triangle.draw(gc_active);
	        	mark_piece = piecesPlayer1.get(0);
	    		
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