package application.ui.bizingostructure;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

public class BizingoBoardGenerator {
	
	public Double size;
	
	public BizingoBoardGenerator(Double size) {
		this.size = size;

	}
	
	public void generateBoard(List<BizingoTriangle> triangles, List<BizingoPiece> pieces, GraphicsContext gc, AnchorPane parent) {
		
		generateTrianglesType1(triangles, gc);
		generateTrianglesType2(triangles, gc);
		generatePiecesType1(triangles, pieces);
		generatePiecesType2(triangles, pieces);
		
		drawBoard(triangles, pieces, gc, parent);
		
		return;
	}
	
	private void drawBoard(List<BizingoTriangle> triangles, List<BizingoPiece> pieces, GraphicsContext gc, AnchorPane parent) {
		for(int i=0; i<triangles.size(); i++) {
			triangles.get(i).draw(gc);
		}
		
		for(int i=0; i<pieces.size(); i++) {
			parent.getChildren().addAll(pieces.get(i).stack);
		}
		
		return;
	}
	
	private void generateTrianglesType1(List<BizingoTriangle> triangles, GraphicsContext gc) {
		Integer[] type1_amount = new Integer[]{10,11,10,9,8,7,6,5,4,3,2};
		
		Double x1, y1;
		Double x2, y2;
		Double x3, y3;
		
		Double base_x = size+50;
		Double base_y = gc.getCanvas().getHeight() - size;
		x1 = base_x;
		y1 = base_y;
		
		for(int i=0; i<type1_amount.length; i++) {
			x1 = base_x - (size/2)*(1+(type1_amount[i]-type1_amount[0]));
			y1 = y1 - (size-10);
			for(int j=0; j<type1_amount[i]; j++) {
				x1 = x1 + size;
				x2 = x1 - (size/2);
				y2 = y1 + (size-10);
				x3 = x1 - size;
				y3 = y1;
				triangles.add(new BizingoTriangle(true, x1, y1, x2, y2, x3, y3, BizingoConstants.color_triangle_type1, BizingoConstants.color_triangle_stroke));
				triangles.get(triangles.size() - 1).draw(gc);
			}
		}
		
		return;
	}
	    
	private void generateTrianglesType2(List<BizingoTriangle> triangles, GraphicsContext gc) {
    	Integer[] type2_amount = new Integer[]{9,10,11,10,9,8,7,6,5,4,3};
    	
    	Double x1, y1;
    	Double x2, y2;
    	Double x3, y3;
    	
    	Double base_x = size+20;
    	Double base_y = gc.getCanvas().getHeight() - size + (size-10);
    	x1 = base_x;
    	y1 = base_y;
    	
    	for(int i=0; i<type2_amount.length; i++) {
    		x1 = base_x - (size/2)*(1+(type2_amount[i]-type2_amount[0]));
    		y1 = y1 - (size-10);
    		for(int j=0; j<type2_amount[i]; j++) {
    			x1 = x1 + size;
    			x2 = x1 + (size/2);
    			y2 = y1 - (size-10);
    			x3 = x1 + size;
    			y3 = y1;
    			triangles.add(new BizingoTriangle(true, x1, y1, x2, y2, x3, y3, BizingoConstants.color_triangle_type2, BizingoConstants.color_triangle_stroke));
    			triangles.get(triangles.size() - 1).draw(gc);
    		}
    	}
    	
		return;
	}

	private void generatePiecesType1(List<BizingoTriangle> triangles, List<BizingoPiece> pieces){		
        pieces.add(new BizingoPiece(true, true, triangles.get(33).getCenter(), BizingoConstants.color_player1_cap, BizingoConstants.color_player_stroke));
        pieces.add(new BizingoPiece(true, true, triangles.get(37).getCenter(), BizingoConstants.color_player1_cap, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(13).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(14).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(15).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(16).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(17).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(23).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(24).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(25).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(26).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(27).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(28).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(32).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(34).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(35).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(36).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(38).getCenter(), BizingoConstants.color_player1, BizingoConstants.color_player_stroke));
		
		return;
	}
	
	
	private void generatePiecesType2(List<BizingoTriangle> triangles, List<BizingoPiece> pieces){
		pieces.add(new BizingoPiece(false, true, triangles.get(75+51).getCenter(), BizingoConstants.color_player2_cap, BizingoConstants.color_player_stroke));
        pieces.add(new BizingoPiece(false, true, triangles.get(75+54).getCenter(), BizingoConstants.color_player2_cap, BizingoConstants.color_player_stroke));
        pieces.add(new BizingoPiece(false, false, triangles.get(75+50).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
        pieces.add(new BizingoPiece(false, false, triangles.get(75+52).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
        pieces.add(new BizingoPiece(false, false, triangles.get(75+53).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+55).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+58).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+59).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+60).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+61).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+62).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+65).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+66).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+67).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+68).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+71).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+72).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+73).getCenter(), BizingoConstants.color_player2, BizingoConstants.color_player_stroke));
        
		return;
	}
}
