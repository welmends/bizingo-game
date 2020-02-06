package application.ui.bizingostructure;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BizingoBoardGenerator {
	
	public Double size;
	public Color triangle_type1_color;
	public Color triangle_type2_color;
	public Color player1_color;
	public Color player1_cap_color;
	public Color player2_color;
	public Color player2_cap_color;
	
	public BizingoBoardGenerator(Double size) {
		this.size = size;
		this.triangle_type1_color = Color.rgb(255,253,254);
		this.triangle_type2_color = Color.rgb(65,167,107);
		this.player1_cap_color = Color.rgb(248,236,18);
		this.player1_color = Color.rgb(207,4,6);
		this.player2_cap_color = Color.rgb(108,0,213);
		this.player2_color = Color.rgb(6,2,6);
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
				trianglesType1.add(new BizingoTriangle(x1, y1, x2, y2, x3, y3, triangle_type1_color, Color.DARKBLUE));
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
    			trianglesType2.add(new BizingoTriangle(x1, y1, x2, y2, x3, y3, triangle_type2_color, Color.DARKBLUE));
    			trianglesType2.get(trianglesType2.size() - 1).draw(gc);
    		}
    	}
		return trianglesType2;
	}

	public List<BizingoPiece> generatePiecesPlayer1(GraphicsContext gc, List<BizingoTriangle> trianglesType1){
		List<BizingoPiece> piecesPlayer1 = new ArrayList<>();
		
        piecesPlayer1.add(new BizingoPiece(true, trianglesType1.get(33).getCenter(), player1_cap_color, Color.BLACK));
        piecesPlayer1.add(new BizingoPiece(true, trianglesType1.get(37).getCenter(), player1_cap_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(13).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(14).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(15).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(16).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(17).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(23).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(24).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(25).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(26).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(27).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(28).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(32).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(34).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(35).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(36).getCenter(), player1_color, Color.BLACK));
		piecesPlayer1.add(new BizingoPiece(false, trianglesType1.get(38).getCenter(), player1_color, Color.BLACK));
		
		for(int i=0; i<piecesPlayer1.size(); i++) {
			piecesPlayer1.get(i).draw(gc);
		}
		
		return piecesPlayer1;
	}
	
	public List<BizingoPiece> generatePiecesPlayer2(GraphicsContext gc, List<BizingoTriangle> trianglesType2){
		List<BizingoPiece> piecesPlayer2 = new ArrayList<>();
        
		piecesPlayer2.add(new BizingoPiece(true, trianglesType2.get(51).getCenter(), player2_cap_color, Color.BLACK));
        piecesPlayer2.add(new BizingoPiece(true, trianglesType2.get(54).getCenter(), player2_cap_color, Color.BLACK));
        piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(50).getCenter(), player2_color, Color.BLACK));
        piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(52).getCenter(), player2_color, Color.BLACK));
        piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(53).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(55).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(58).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(59).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(60).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(61).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(62).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(65).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(66).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(67).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(68).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(71).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(72).getCenter(), player2_color, Color.BLACK));
		piecesPlayer2.add(new BizingoPiece(false, trianglesType2.get(73).getCenter(), player2_color, Color.BLACK));
        
		for(int i=0; i<piecesPlayer2.size(); i++) {
			piecesPlayer2.get(i).draw(gc);
		}
		
		return piecesPlayer2;
	}
}
