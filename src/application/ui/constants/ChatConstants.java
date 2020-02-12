package application.ui.constants;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;

public class ChatConstants {
	// Label
	public static String TEXT_LABEL_CHAT = "C H A T";
	
	// Sleep
	public static Integer THREAD_SLEEP_TIME_MILLIS = 100;
	
	// Message Receive
	public static Color COLOR_LABEL_TEXT_RECEIVE = Color.BLACK;
	public static String STYLE_LABEL_TEXT_RECEIVE = "-fx-font-weight:bold; -fx-background-color: #ffffff; -fx-background-radius: 20 20 20 0;";
	public static Insets PADDING_LABEL_TEXT_RECEIVE = new Insets(10, 10, 10, 10);
	public static Pos ALIGNMENT_LABEL_TEXT_RECEIVE = Pos.CENTER;
	
	public static Insets PADDING_STACK_PANE_RECEIVE = new Insets(0, 0, 5, 0);
	public static Pos ALIGNMENT_STACK_PANE_RECEIVE = Pos.CENTER_LEFT;
	
	// Message Send
	public static Color COLOR_LABEL_TEXT_SEND = Color.BLACK;
	public static String STYLE_LABEL_TEXT_SEND = "-fx-font-weight:bold; -fx-background-color: #e2ffc9; -fx-background-radius: 20 20 0 20;";
	public static Insets PADDING_LABEL_TEXT_SEND = new Insets(10, 10, 10, 10);
	public static Pos ALIGNMENT_LABEL_TEXT_SEND = Pos.CENTER;
	
	public static Insets PADDING_STACK_PANE_SEND = new Insets(0, 0, 5, 0);
	public static Pos ALIGNMENT_STACK_PANE_SEND = Pos.CENTER_RIGHT;
}
