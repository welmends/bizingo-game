package application.ui.bizingostructure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BizingoPiece {
	
	public Double x1, y1;
	public Double radius;
	public Color fillColor;
	public Color strokeColor;
	
	public BizingoPiece(Double x1, Double y1, Double radius, Color fillColor, Color strokeColor) {
		this.x1 = x1;
		this.y1 = y1;
		this.radius = radius;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public BizingoPiece(Double[] center, Double radius, Color fillColor, Color strokeColor) {
		this.x1 = center[0];
		this.y1 = center[1];
		this.radius = radius;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public void draw(GraphicsContext gc) {
    	gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(x1-radius/2, y1-radius/2, radius, radius);
        gc.strokeOval(x1-radius/2, y1-radius/2, radius, radius);
	}
	
	public Double[] getPosition() {
		Double[] center = new Double[]{0.0,0.0};
		
        center[0] = x1;
        center[1] = y1;
		
		return center;
	}
}