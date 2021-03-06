package application.ui.bizingostructure;

import application.ui.constants.BizingoConstants;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class BizingoPiece {
	
	public Double radius;
	
	public Boolean type;   //Is Server? or Is Type 1?
	public Boolean captain;//Is Captain?
	public Boolean exists; //Exists?
	
	public Double x1, y1;
	
	public Color fillColor;
	public Color strokeColor;
	
	public Circle circle;
	public StackPane stack;
	
	public BizingoPiece(Boolean type, Boolean captain, Double x1, Double y1, Color fillColor, Color strokeColor) {
		this.radius = BizingoConstants.PIECE_RADIUS;
		
		this.type = type;
		this.captain = captain;
		this.exists = true;
		
		this.x1 = x1;
		this.y1 = y1;
		
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		
		initGraphics();
	}
	
	public BizingoPiece(Boolean type, Boolean captain, Double[] center, Color fillColor, Color strokeColor) {
		this.radius = BizingoConstants.PIECE_RADIUS;
		
		this.type = type;
		this.captain = captain;
		this.exists = true;
		
		this.x1 = center[0];
		this.y1 = center[1];
		
		this.fillColor = fillColor;
		this.strokeColor = strokeColor;
		
		initGraphics();
	}
	
	public void initGraphics() {
		this.exists = true;
		
		circle = new Circle(this.radius, fillColor);
		circle.setEffect(new Lighting());
		stack = new StackPane();
        stack.getChildren().addAll(circle);
        stack.setPrefSize(radius, radius);
        stack.setLayoutX(x1-radius);
        stack.setLayoutY(y1-radius);
	}
	
	public void removeGraphics(AnchorPane bizingoPiecesPane) {
		this.exists = false;
		
		bizingoPiecesPane.getChildren().remove(stack);
	}
	
	public void draw(GraphicsContext gc) {
    	gc.setFill(fillColor);
        gc.setStroke(strokeColor);
        gc.fillOval(x1-radius/2, y1-radius/2, radius, radius);
        gc.strokeOval(x1-radius/2, y1-radius/2, radius, radius);
	}
	
	public void draw(GraphicsContext gc, Double radius) {
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
	
	public void setPosition(Double[] position) {
		this.x1 = position[0];
		this.y1 = position[1];
	}
	
	public void setPosition(Double x1, Double y1) {
		this.x1 = x1;
		this.y1 = y1;
	}
}