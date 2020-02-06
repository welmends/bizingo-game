package application.ui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.ui.bizingostructure.BizingoBoardGenerator;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoTriangle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BizingoController implements Initializable {
	
	// FXML Variables
	@FXML AnchorPane bizingoPane;
	@FXML Rectangle bizingoRect;
	@FXML Canvas bizingoCanvas;
	
	// Variables
	List<BizingoTriangle> trianglesType1;
	List<BizingoTriangle> trianglesType2;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		bizingoRect.setArcWidth(30.0); 
		bizingoRect.setArcHeight(30.0);
		bizingoRect.setFill(Color.rgb(203,236,215));
		
		GraphicsContext gc = bizingoCanvas.getGraphicsContext2D();
		
		BizingoBoardGenerator boardGenerator = new BizingoBoardGenerator(60.0);
		trianglesType1 = boardGenerator.generateTrianglesType1(gc);
		trianglesType2 = boardGenerator.generateTrianglesType2(gc);
		
		BizingoPiece bp = new BizingoPiece(trianglesType1.get(0).getCenter(), 20.0, Color.BLACK, Color.BLACK);
		bp.draw(gc);
		
	}
}