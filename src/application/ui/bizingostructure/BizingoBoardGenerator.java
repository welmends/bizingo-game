package application.ui.bizingostructure;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BizingoBoardGenerator {
	
	public Double size;
	public BizingoBoardGenerator(Double size) {
		this.size = size;
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
				trianglesType1.add(new BizingoTriangle(x1, y1, x2, y2, x3, y3, Color.rgb(255,253,254), Color.DARKBLUE));
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
    			trianglesType2.add(new BizingoTriangle(x1, y1, x2, y2, x3, y3, Color.rgb(65,167,107), Color.DARKBLUE));
    			trianglesType2.get(trianglesType2.size() - 1).draw(gc);
    		}
    	}
		return trianglesType2;
	}
}
