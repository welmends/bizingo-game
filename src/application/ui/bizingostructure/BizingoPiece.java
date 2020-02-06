package application.ui.bizingostructure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BizingoPiece {
	
	public Boolean captain;
	public Double radius;
	public Double x1, y1;
	public Color fillColor;
	public Color strokeColor;
	
	public BizingoPiece(Boolean captain, Double x1, Double y1, Color fillColor, Color strokeColor) {
		this.captain = captain;
		this.radius = 22.0;
		this.x1 = x1;
		this.y1 = y1;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public BizingoPiece(Boolean captain, Double[] center, Color fillColor, Color strokeColor) {
		this.captain = captain;
		this.radius = 22.0;
		this.x1 = center[0];
		this.y1 = center[1];
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