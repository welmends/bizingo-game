package application.ui.utils;

import java.util.List;

import application.ui.bizingostructure.BizingoTriangle;

public class BizingoUtils {
	
	public BizingoUtils() {
		
	}
	
	public int findPressedTriangle(double x, double y, List<BizingoTriangle> triangles) {
		int cPiece;
    	double min_dist, dist;
    	
    	cPiece = 0;
		min_dist = dist = Math.sqrt(Math.pow(x-triangles.get(0).getCenter()[0], 2) + Math.pow(y-triangles.get(0).getCenter()[1], 2));	  
    	for(int i=1; i<triangles.size(); i++) {
    		dist = Math.sqrt(Math.pow(x-triangles.get(i).getCenter()[0], 2) + Math.pow(y-triangles.get(i).getCenter()[1], 2));
    		if(dist<min_dist) {
    			cPiece = i;
    			min_dist = dist;
    		}
    	}
    	
    	return cPiece;
	}
}
