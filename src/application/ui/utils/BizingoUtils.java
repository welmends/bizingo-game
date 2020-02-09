package application.ui.utils;

import java.util.ArrayList;
import java.util.List;

import application.ui.bizingostructure.BizingoConstants;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

public class BizingoUtils {
	
	public List<Integer> playables;
	
	public BizingoUtils() {
		playables = new ArrayList<>();
	}
	
	public void paintHighlightedTriangle(GraphicsContext gc, Double[] points) {
    	BizingoTriangle pressed;
    	pressed = new BizingoTriangle(true, points, BizingoConstants.COLOR_TRIANGLE_HIGHLIGHT, BizingoConstants.COLOR_TRIANGLE_STROKE);
    	pressed.draw(gc);
	}
	
	public void paintHighlightedPlayableTriangles(GraphicsContext gc, List<BizingoTriangle> triangles) {
    	BizingoTriangle pressed;
    	
    	for(int i=0; i<playables.size(); i++) {
        	pressed = new BizingoTriangle(true, triangles.get(playables.get(i)).getPoints(), BizingoConstants.COLOR_TRIANGLE_HIGHLIGHT, BizingoConstants.COLOR_TRIANGLE_STROKE);
        	pressed.draw(gc);
    	}
	}

	public int findPressedTriangle(double x, double y, List<BizingoTriangle> triangles) {
		int idx;
    	double min_dist, dist;
    	
    	idx = 0;
		min_dist = dist = Math.sqrt(Math.pow(x-triangles.get(0).getCenter()[0], 2) + Math.pow(y-triangles.get(0).getCenter()[1], 2));	  
    	for(int i=1; i<triangles.size(); i++) {
    		dist = Math.sqrt(Math.pow(x-triangles.get(i).getCenter()[0], 2) + Math.pow(y-triangles.get(i).getCenter()[1], 2));
    		if(dist<min_dist) {
    			idx = i;
    			min_dist = dist;
    		}
    	}
    	
    	if(min_dist>BizingoConstants.MIN_DISTANCE_OUT_TRIANGLE) {
    		idx = -1;
    	}
    	
    	return idx;
	}
	
	public int findPieceBoardPosition(List<BizingoTriangle> triangles, BizingoPiece piece) {
    	for(int i=0; i<triangles.size(); i++) {
    		if(triangles.get(i).getCenter()==piece.getPosition()) {
    			return i;
    		}
    	}
    	return -1;
	}

	public void findPlayableTriangles(List<BizingoTriangle> triangles, List<BizingoPiece> pieces, int idx_triangle) {
		playables = new ArrayList<>();
		
    	double dist, dif_x, dif_y;
    	
    	for(int i=0; i<triangles.size(); i++) {
    		if(triangles.get(idx_triangle).type==triangles.get(i).type && idx_triangle!=i) {
    			dif_x = triangles.get(idx_triangle).getCenter()[0]-triangles.get(i).getCenter()[0];
    			dif_y = triangles.get(idx_triangle).getCenter()[1]-triangles.get(i).getCenter()[1];
        		dist = Math.sqrt(Math.pow(dif_x, 2) + Math.pow(dif_y, 2));
        		if(dist<=BizingoConstants.MIN_DISTANCE_NEIGHBOUR_TRIANGLE) {
        			if(triangleHasPiece(triangles.get(i), pieces)==-1) {
        				playables.add(i);
        			}
        		}
    		}
    	}
		
		return;
	}
	
	public Boolean triangleIsPlayable(int idx_triangle) {
		for(int i=0; i<playables.size(); i++) {
			if(idx_triangle==playables.get(i)) {
				return true;
			}
		}
		return false;
	}
	
	public int triangleHasPiece(BizingoTriangle triangle, List<BizingoPiece> pieces) {		
    	for(int i=0; i<pieces.size(); i++) {
    		if(triangle.getCenter()[0].intValue()==pieces.get(i).getPosition()[0].intValue() && 
    		   triangle.getCenter()[1].intValue()==pieces.get(i).getPosition()[1].intValue()) {
    			return i;
    		}
    	}
    	return -1;
	}
	
	public int triangleHasPiece(Boolean peer_type, BizingoTriangle triangle, List<BizingoPiece> pieces) {		
    	for(int i=0; i<pieces.size(); i++) {
    		if(pieces.get(i).type == peer_type) {
        		if(triangle.getCenter()[0].intValue()==pieces.get(i).getPosition()[0].intValue() && 
                   triangle.getCenter()[1].intValue()==pieces.get(i).getPosition()[1].intValue()) {
        	    			return i;
        	    }
    		}
    	}
    	return -1;
	}
	
	public Boolean findCapturedPiece(Boolean peer_type, List<BizingoPiece> pieces, AnchorPane bizingoPiecesPane) {
		List<Integer> captured = new ArrayList<>();
		
    	double dist, dif_x, dif_y;
		int counter;
		
    	for(int i=0; i<pieces.size(); i++) {
    		counter = 0;
    		for(int j=0; j<pieces.size(); j++) {
    			if(pieces.get(i).type!=pieces.get(j).type) {
        			dif_x = pieces.get(i).getPosition()[0]-pieces.get(j).getPosition()[0];
        			dif_y = pieces.get(i).getPosition()[1]-pieces.get(j).getPosition()[1];
            		dist = Math.sqrt(Math.pow(dif_x, 2) + Math.pow(dif_y, 2));
            		if(dist<=BizingoConstants.MIN_DISTANCE_NEIGHBOUR_TRIANGLE) {
            			counter++;
            			if(counter==BizingoConstants.AMOUNT_PIECES_SURROUND_TO_CAPTURE) {
            				captured.add(i);
            				break;
            			}
            		}
        		}
    		}
    	}
    	
    	for(int i=0; i<captured.size(); i++) {
    		pieces.get(captured.get(i)).removeGraphics(bizingoPiecesPane);
    	}
    	
    	if(captured.size()>0) {
    		return true;
    	}else {
    		return false;
    	}
	}
	
	public int findWinnerAndLoser(List<BizingoPiece> pieces) {
		int pieces1 = 0;
		int pieces2 = 0;
		
		for(int i=0; i<pieces.size(); i++) {
			if(pieces.get(i).exists) {
				if(pieces.get(i).type) {
					pieces1++;
				}else {
					pieces2++;
				}
			}
		}
		
		if(pieces1==BizingoConstants.MIN_PIECES_AMOUNT_TO_END) {
			return 2;
		}
		else if(pieces2==BizingoConstants.MIN_PIECES_AMOUNT_TO_END) {
			return 1;
		}
		else {
			return -1;
		}
	}
	
}
