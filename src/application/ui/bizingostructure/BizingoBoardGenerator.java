package application.ui.bizingostructure;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
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
	
	public List<BizingoTriangle> generateTrianglesType1(GraphicsContext gc) {
		List<BizingoTriangle> trianglesType1  = new ArrayList<>();
		Integer[] type1_amout = new Integer[]{10,11,10,9,8,7,6,5,4,3,2};
		
		Double x1, y1;
		Double x2, y2;
		Double x3, y3;
		
		Double base_x = size+50;
		Double base_y = gc.getCanvas().getHeight() - size;
		x1 = base_x;
		y1 = base_y;
		
		for(int triangles=0; triangles<type1_amout.length; triangles++) {
			x1 = base_x - (size/2)*(1+(type1_amout[triangles]-type1_amout[0]));
			y1 = y1 - (size-10);
			for(int triangle=0; triangle<type1_amout[triangles]; triangle++) {
				x1 = x1 + size;
				x2 = x1 - (size/2);
				y2 = y1 + (size-10);
				x3 = x1 - size;
				y3 = y1;
				trianglesType1.add(new BizingoTriangle(x1, y1, x2, y2, x3, y3, color_triangle_type1, color_triangle_stroke));
				trianglesType1.get(trianglesType1.size() - 1).draw(gc);
			}
		}
		
		return trianglesType1;
	}
	    
	public List<BizingoTriangle> generateTrianglesType2(GraphicsContext gc) {
		List<BizingoTriangle> trianglesType2 = new ArrayList<>();
    	Integer[] type2_amout = new Integer[]{9,10,11,10,9,8,7,6,5,4,3};
    	
    	Double x1, y1;
    	Double x2, y2;
    	Double x3, y3;
    	
    	Double base_x = size+20;
    	Double base_y = gc.getCanvas().getHeight() - size + (size-10);
    	x1 = base_x;
    	y1 = base_y;
    	
    	for(int triangles=0; triangles<type2_amout.length; triangles++) {
    		x1 = base_x - (size/2)*(1+(type2_amout[triangles]-type2_amout[0]));
    		y1 = y1 - (size-10);
    		for(int triangle=0; triangle<type2_amout[triangles]; triangle++) {
    			x1 = x1 + size;
    			x2 = x1 + (size/2);
    			y2 = y1 - (size-10);
    			x3 = x1 + size;
    			y3 = y1;
    			trianglesType2.add(new BizingoTriangle(x1, y1, x2, y2, x3, y3, color_triangle_type2, color_triangle_stroke));
    			trianglesType2.get(trianglesType2.size() - 1).draw(gc);
    		}
    	}
		return trianglesType2;
	}

	public List<BizingoPiece> generatePiecesPlayer1(GraphicsContext gc, List<BizingoTriangle> trianglesType1){
		List<BizingoPiece> piecesPlayer1 = new ArrayList<>();
		
        piecesPlayer1.add(new BizingoPiece(true, trianglesType1.get(33).getCenter(), color_player1_cap, color_player_stroke));
        piecesPlayer1.add(new BizingoPiece(true, trianglesType1.get(37).getCenter(), color_player1_cap, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(13).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(14).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(15).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(16).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(17).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(23).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(24).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(25).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(26).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(27).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(28).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(32).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(34).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(35).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(36).getCenter(), color_player1, color_player_stroke));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(38).getCenter(), color_player1, color_player_stroke));
		
		for(int i=0; i<piecesPlayer1.size(); i++) {
			piecesPlayer1.get(i).draw(gc);
		}
		
		return piecesPlayer1;
	}
	
	public List<BizingoPiece> generatePiecesPlayer2(GraphicsContext gc, List<BizingoTriangle> trianglesType2){
		List<BizingoPiece> piecesPlayer2 = new ArrayList<>();
        
		piecesPlayer2.add(new BizingoPiece(true, trianglesType2.get(51).getCenter(), color_player2_cap, color_player_stroke));
        piecesPlayer2.add(new BizingoPiece(true, trianglesType2.get(54).getCenter(), color_player2_cap, color_player_stroke));
        piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(50).getCenter(), color_player2, color_player_stroke));
        piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(52).getCenter(), color_player2, color_player_stroke));
        piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(53).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(55).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(58).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(59).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(60).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(61).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(62).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(65).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(66).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(67).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(68).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(71).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(72).getCenter(), color_player2, color_player_stroke));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(73).getCenter(), color_player2, color_player_stroke));
        
		for(int i=0; i<piecesPlayer2.size(); i++) {
			piecesPlayer2.get(i).draw(gc);
		}
		
		return piecesPlayer2;
	}
}
