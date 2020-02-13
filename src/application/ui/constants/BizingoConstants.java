package application.ui.constants;

import javafx.scene.paint.Color;

public class BizingoConstants {
	
	// Colors
	public static Color COLOR_TRIANGLE_TYPE1 = Color.rgb(255,253,254);
	public static Color COLOR_TRIANGLE_TYPE2 = Color.rgb(65,167,107);
	public static Color COLOR_TRIANGLE_HIGHLIGHT = Color.rgb(0, 0, 0, 0.3);
	public static Color COLOR_TRIANGLE_STROKE = Color.DARKBLUE;
	
	public static Color COLOR_PLAYER1_CAP = Color.rgb(248,236,18);
	public static Color COLOR_PLAYER1 = Color.rgb(207,4,6);
	public static Color COLOR_PLAYER2_CAP = Color.rgb(108,0,213);
	public static Color COLOR_PLAYER2 = Color.rgb(6,2,6);
	public static Color COLOR_PLAYER_STROKE = Color.BLACK;
	
	public static Color BLACK = Color.BLACK;
	public static Color GREEN = Color.DARKGREEN;
	public static Color RED = Color.DARKRED;
	
	// Piece and Triangle
	public static Double PIECE_RADIUS = 10.0;
	public static Integer TRIANGLE_THICKNESS = 3;
	
	// Board Generator
	public static Double BOARD_SIZE = 60.0;
	public static Integer[] TYPE1_AMOUNT = new Integer[]{10,11,10,9,8,7,6,5,4,3,2};
	public static Integer[] TYPE2_AMOUNT = new Integer[]{9,10,11,10,9,8,7,6,5,4,3};
	public static Integer[] TYPE1_EDGE_INDEXES = new Integer[]{};
	public static Integer[] TYPE2_EDGE_INDEXES = new Integer[]{};

	// Utils
	public static Double MIN_DISTANCE_OUT_TRIANGLE = 30.0;
	public static Double MIN_DISTANCE_NEIGHBOUR_TRIANGLE = 60.0;
	public static Integer MIN_PIECES_AMOUNT_TO_END = 2;
	public static Integer AMOUNT_PIECES_SURROUND_TO_CAPTURE = 3;
	
	// Animation
	public static Integer MOVE_MILLIS = 500;
	
	// Sleep
	public static Integer THREAD_SLEEP_TIME_MILLIS = 100;
	
	// Text
	public static String TEXT_BUTTON_LEAVE = "L E A V E";
	public static String TEXT_BUTTON_RESTART = "R E S T A R T";
	public static String TEXT_LABEL_NAME_UP = "B I Z I N G O";
	public static String TEXT_LABEL_NAME_DOWN = "G A M E";
	public static String TEXT_LABEL_SCORE = "S C O R E";
	
	// Sys Messages
	public static String SYS_RESTART_REQUEST = "restart";
	public static String SYS_RESTART_RESPONSE_OK = "restart_ok";
	public static String SYS_RESTART_RESPONSE_FAIL = "restart_fail";
	
	
}
