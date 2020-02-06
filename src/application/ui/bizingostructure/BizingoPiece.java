package application.ui.bizingostructure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BizingoPiece {
	
	public Boolean captain;
	public Double radius;
	public Double x1, y1;
	
	public Color fillColor;
	public Color strokeColor;
	
	public Circle circle;
	public StackPane stack;
	
	public BizingoPiece(Boolean captain, Double x1, Double y1, Color fillColor, Color strokeColor) {
		this.captain = captain;
		this.radius = 10.0;
		this.x1 = x1;
		this.y1 = y1;
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		
		initGraphics();
	}
	
	public BizingoPiece(Boolean captain, Double[] center, Color fillColor, Color strokeColor) {
		this.captain = captain;
		this.radius = 10.0;
		this.x1 = center[0];
		this.y1 = center[1];
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		
		initGraphics();
	}
	
	public void initGraphics() {
		circle = new Circle(this.radius, fillColor);
		circle.setEffect(new Lighting());
		stack = new StackPane();
        stack.getChildren().addAll(circle);
        stack.setPrefSize(radius, radius);
        stack.setLayoutX(x1-radius);
        stack.setLayoutY(y1-radius);
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