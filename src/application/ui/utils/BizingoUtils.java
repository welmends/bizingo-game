package application.ui.utils;

import java.util.List;

import application.ui.bizingostructure.BizingoConstants;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import javafx.scene.canvas.GraphicsContext;

public class BizingoUtils {
	
	public BizingoUtils() {
		
	}
	
	public void paintPressedTriangle(GraphicsContext gc, Double[] points) {
    	BizingoTriangle pressed;
    	pressed = new BizingoTriangle(true, points, BizingoConstants.color_triangle_selected, BizingoConstants.color_triangle_stroke);
    	pressed.draw(gc);
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
    	
    	if(min_dist>30.0) {
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
	
	public int triangleHasPiece(BizingoTriangle triangle, List<BizingoPiece> pieces) {		
    	for(int i=0; i<pieces.size(); i++) {
//    		System.out.print(triangle.getCenter()[0].intValue());
//    		System.out.print(",");
//    		System.out.print(triangle.getCenter()[1].intValue());
//    		System.out.print("  -  ");
//    		System.out.print(pieces.get(i).getPosition()[0].intValue());
//    		System.out.print(",");
//    		System.out.println(pieces.get(i).getPosition()[1].intValue());
    		
    		if(triangle.getCenter()[0].intValue()==pieces.get(i).getPosition()[0].intValue() && 
    		   triangle.getCenter()[1].intValue()==pieces.get(i).getPosition()[1].intValue()) {
    			return i;
    		}
    	}
    	return -1;
	}
	
}
