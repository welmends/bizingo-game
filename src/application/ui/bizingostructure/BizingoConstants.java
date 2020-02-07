package application.ui.bizingostructure;

import javafx.scene.paint.Color;

public class BizingoConstants {
	
	// Colors
	public static Color COLOR_TRIANGLE_TYPE1 = Color.rgb(255,253,254);
	public static Color COLOR_TRIANGLE_TYPE2 = Color.rgb(65,167,107);
	public static Color COLOR_TRIANGLE_SELECTED = Color.rgb(0, 0, 0, 0.5);
	public static Color COLOR_TRIANGLE_STROKE = Color.DARKBLUE;
	
	public static Color COLOR_PLAYER1_CAP = Color.rgb(248,236,18);
	public static Color COLOR_PLAYER1 = Color.rgb(207,4,6);
	public static Color COLOR_PLAYER2_CAP = Color.rgb(108,0,213);
	public static Color COLOR_PLAYER2 = Color.rgb(6,2,6);
	public static Color COLOR_PLAYER_STROKE = Color.BLACK;
	
	// Piece and Triangle
	public static Double PIECE_RADIUS = 10.0;
	public static int TRIANGLE_THICKNESS = 3;
	
	// Board Generator
	public static Integer[] TYPE1_AMOUNT = new Integer[]{10,11,10,9,8,7,6,5,4,3,2};
	public static Integer[] TYPE2_AMOUNT = new Integer[]{9,10,11,10,9,8,7,6,5,4,3};
	
	// Utils
	public static Double MIN_DISTANCE_OUT_TRIANGLE = 30.0;
		
	// Animation
	public static int MOVE_MILLIS = 500;
}
