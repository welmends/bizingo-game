package application.ui.constants;

import javafx.scene.image.Image;

public class ImageConstants {
	public static Image MAIN_APPLICATION_ICON = new Image(ImageConstants.class.getResourceAsStream("/resources/images/bizingo_icon.png"));
	public static Image CHAT_TOP_ICON = new Image(ImageConstants.class.getResourceAsStream("/resources/images/chat_icon.png"), 40, 40, true, true);
	public static Image WINNER_IMAGE = new Image(ImageConstants.class.getResourceAsStream("/resources/images/trophy.png"), 100, 100, true, true);
	public static Image LOSER_IMAGE = new Image(ImageConstants.class.getResourceAsStream("/resources/images/defeat.png"), 100, 100, true, true);
}
