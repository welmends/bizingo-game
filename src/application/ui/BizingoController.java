package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.ui.animation.BizingoAnimation;
import application.ui.bizingostructure.BizingoBoardGenerator;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import application.ui.utils.BizingoUtils;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

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
	BizingoAnimation animator;
	
	List<BizingoTriangle> triangles;
	List<BizingoPiece> pieces;
	
	Boolean piece_selected;
	int idx_triangle_last;
	int idx_piece_last;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Instantiating objects
		gc_fixed  = bizingoCanvasFixed.getGraphicsContext2D();
		gc_active = bizingoCanvasActive.getGraphicsContext2D();
		boardGen = new BizingoBoardGenerator(60.0);
		utils = new BizingoUtils();
		animator = new BizingoAnimation();
		triangles = new ArrayList<>();
		pieces = new ArrayList<>();
		
		// Variables
		piece_selected = false;
		idx_triangle_last = -1;
		idx_piece_last = -1;
		
		// Generate Board
		boardGen.generateBoard(triangles, pieces, gc_fixed, bizingoAnchorPane);
		
		// Canvas Mouse Pressed - Event Handler
		bizingoCanvasActive.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	gc_active.clearRect(0, 0, bizingoCanvasActive.getWidth(), bizingoCanvasActive.getHeight());
	        		        	
	        	double x = event.getX();
	        	double y = event.getY();
	        	
	        	int idx_triangle=-1;
	        	int idx_piece=-1;
	        	
	        	idx_triangle = utils.findPressedTriangle(x, y, triangles);
	        	
	        	if(piece_selected) {
	        		if(idx_triangle==-1) {
	        			piece_selected = false;
		        	}else {
			        	idx_piece = utils.triangleHasPiece(triangles.get(idx_triangle), pieces);
			        	
			        	if(idx_piece==-1) {
				        	piece_selected = false;
				        	animator.move(pieces.get(idx_piece_last), triangles.get(idx_triangle));
			        	}else {
			        		piece_selected = false;
			        	}
		        	}
	        	}else {
	        		if(idx_triangle==-1) {
	        			piece_selected = false;
		        	}else {
		        		idx_piece = utils.triangleHasPiece(triangles.get(idx_triangle), pieces);
			        	
			        	if(idx_piece==-1) {
			        		piece_selected = false;
			        	}else {
				        	piece_selected = true;
				        	utils.paintPressedTriangle(gc_active, triangles.get(idx_triangle).getPoints());
				        	idx_triangle_last = idx_triangle;
				        	idx_piece_last = idx_piece;
			        	}
		        	}
	        	}
	    		
	        }
	    });
	}
	
	
}