package application.ui.bizingostructure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BizingoTriangle {
	
	public int thickness;
	public Double x1, y1;
	public Double x2, y2;
	public Double x3, y3;
	public Color fillColor;
	public Color strokeColor;
	
	public BizingoTriangle(Double x1, Double y1, Double x2, Double y2, Double x3, Double y3, Color fillColor, Color strokeColor) {
		this.thickness = 3;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public BizingoTriangle(Double[] p1, Double[] p2, Double[] p3, Color fillColor, Color strokeColor) {
		this.thickness = 3;
		this.x1 = p1[0];
		this.y1 = p1[1];
		this.x2 = p2[0];
		this.y2 = p2[1];
		this.x3 = p3[0];
		this.y3 = p3[1];
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public BizingoTriangle(Double[] points, Color fillColor, Color strokeColor) {
		this.thickness = 3;
		this.x1 = points[0];
		this.y1 = points[1];
		this.x2 = points[2];
		this.y2 = points[3];
		this.x3 = points[4];
		this.y3 = points[5];
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
	}
	
	public void draw(GraphicsContext gc) {
    	gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillPolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, thickness);
        gc.strokePolygon(new double[]{x1, x2, x3}, new double[]{y1, y2, y3}, thickness);
	}
	
	public Double[] getCenter() {
		Double[] center = new Double[]{0.0, 0.0};
        center[0] = (x1+x2+x3)/3;
        center[1] = (y1+y2+y3)/3;
		return center;
	}
	
	public Double[] getPoints() {
		Double[] points = new Double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		points[0] = x1;
		points[1] = y1;
		points[2] = x2;
		points[3] = y2;
		points[4] = x3;
		points[5] = y3;
		return points;
	}
	
	public Double[] getPoint1() {
		Double[] point = new Double[]{0.0, 0.0};
		point[0] = x1;
		point[1] = y1;
		return point;
	}
	
	public Double[] getPoint2() {
		Double[] point = new Double[]{0.0, 0.0};
		point[0] = x2;
		point[1] = y2;
		return point;
	}
	
	public Double[] getPoint3() {
		Double[] point = new Double[]{0.0, 0.0};
		point[0] = x3;
		point[1] = y3;
		return point;
	}
}
