package application.ui.bizingostructure;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class BizingoBoardGenerator {
	
	public Double size;
	
	public Color color_triangle_type1;
	public Color color_triangle_type2;
	public Color color_triangle_selected;
	public Color color_triangle_stroke;
	
	public Color color_player1;
	public Color color_player1_cap;
	public Color color_player2;
	public Color color_player2_cap;
	public Color color_player_stroke;
	
	public BizingoBoardGenerator(Double size) {
		this.size = size;
		this.color_triangle_type1 = Color.rgb(255,253,254);
		this.color_triangle_type2 = Color.rgb(65,167,107);
		this.color_triangle_selected = Color.rgb(0, 0, 0, 0.5);
		this.color_triangle_stroke = Color.DARKBLUE;
		
		this.color_player1_cap = Color.rgb(248,236,18);
		this.color_player1 = Color.rgb(207,4,6);
		this.color_player2_cap = Color.rgb(108,0,213);
		this.color_player2 = Color.rgb(6,2,6);
		this.color_player_stroke = Color.BLACK;
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
				triangles.add(new BizingoTriangle(true, x1, y1, x2, y2, x3, y3, color_triangle_type1, color_triangle_stroke));
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
    			triangles.add(new BizingoTriangle(true, x1, y1, x2, y2, x3, y3, color_triangle_type2, color_triangle_stroke));
    			triangles.get(triangles.size() - 1).draw(gc);
    		}
    	}
    	
		return;
	}

	private void generatePiecesType1(List<BizingoTriangle> triangles, List<BizingoPiece> pieces){		
        pieces.add(new BizingoPiece(true, true, triangles.get(33).getCenter(), color_player1_cap, color_player_stroke));
        pieces.add(new BizingoPiece(true, true, triangles.get(37).getCenter(), color_player1_cap, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(13).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(14).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(15).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(16).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(17).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(23).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(24).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(25).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(26).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(27).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(28).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(32).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(34).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(35).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(36).getCenter(), color_player1, color_player_stroke));
		pieces.add(new BizingoPiece(true, false, triangles.get(38).getCenter(), color_player1, color_player_stroke));
		
		return;
	}
	
	
	private void generatePiecesType2(List<BizingoTriangle> triangles, List<BizingoPiece> pieces){
		pieces.add(new BizingoPiece(false, true, triangles.get(75+51).getCenter(), color_player2_cap, color_player_stroke));
        pieces.add(new BizingoPiece(false, true, triangles.get(75+54).getCenter(), color_player2_cap, color_player_stroke));
        pieces.add(new BizingoPiece(false, false, triangles.get(75+50).getCenter(), color_player2, color_player_stroke));
        pieces.add(new BizingoPiece(false, false, triangles.get(75+52).getCenter(), color_player2, color_player_stroke));
        pieces.add(new BizingoPiece(false, false, triangles.get(75+53).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+55).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+58).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+59).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+60).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+61).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+62).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+65).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+66).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+67).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+68).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+71).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+72).getCenter(), color_player2, color_player_stroke));
		pieces.add(new BizingoPiece(false, false, triangles.get(75+73).getCenter(), color_player2, color_player_stroke));
        
		return;
	}
}
