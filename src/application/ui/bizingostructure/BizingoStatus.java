package application.ui.bizingostructure;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;

public class BizingoStatus {
	
	private Double radius_pieces;
	
	private BizingoPiece p1, p1_c;
	private BizingoPiece p2, p2_c;
	
	private Font sixty25p;
	
	public BizingoStatus() {
		this.radius_pieces = 25.0;
		
		p1_c = new BizingoPiece(true, true, 30.0, 60.0 , BizingoConstants.COLOR_PLAYER1_CAP, BizingoConstants.COLOR_PLAYER_STROKE);
		p1   = new BizingoPiece(true, false, 40.0, 60.0 , BizingoConstants.COLOR_PLAYER1, BizingoConstants.COLOR_PLAYER_STROKE);
		p2_c = new BizingoPiece(true, true, 30.0, 100.0 , BizingoConstants.COLOR_PLAYER2_CAP, BizingoConstants.COLOR_PLAYER_STROKE);
		p2   = new BizingoPiece(true, false, 40.0, 100.0 , BizingoConstants.COLOR_PLAYER2, BizingoConstants.COLOR_PLAYER_STROKE);
		
		sixty25p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 25);
	}
	
	public void draw_cover(GraphicsContext gc) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		p1_c.draw(gc, radius_pieces);
		p1.draw(gc, radius_pieces);
		p2_c.draw(gc, radius_pieces);
		p2.draw(gc, radius_pieces);
		
		gc.setFont(sixty25p);
		
		gc.fillText("18", Math.round(150), Math.round(65), 50);
		gc.fillText("18", Math.round(150), Math.round(105));
	}
	
	public void update_status(GraphicsContext gc, int pieces1, int pieces2) {
		gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
		
		p1_c.draw(gc, radius_pieces);
		p1.draw(gc, radius_pieces);
		p2_c.draw(gc, radius_pieces);
		p2.draw(gc, radius_pieces);
		
		gc.setFont(sixty25p);
		
		gc.fillText(String.valueOf(pieces1), Math.round(150), Math.round(65), 50);
		gc.fillText(String.valueOf(pieces2), Math.round(150), Math.round(105));
	}
}
