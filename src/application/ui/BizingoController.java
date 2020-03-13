package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.com.socket.SocketP2P;
import application.ui.animation.BizingoAnimation;
import application.ui.bizingostructure.BizingoBoardGenerator;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoStatus;
import application.ui.bizingostructure.BizingoTriangle;
import application.ui.constants.BizingoConstants;
import application.ui.constants.FontConstants;
import application.ui.utils.AlertUtils;
import application.ui.utils.BizingoUtils;
import application.ui.utils.SoundUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class BizingoController extends Thread implements Initializable {
	
	// FXML Variables
	@FXML AnchorPane bizingoPane;
	@FXML Rectangle bizingoRect;
	@FXML Canvas bizingoCanvasBackground;
	@FXML Canvas bizingoCanvasBoard;
	@FXML Canvas bizingoCanvasStatusDown;
	@FXML Canvas bizingoCanvasStatusUp;
	@FXML AnchorPane bizingoPiecesPane;
	@FXML Button bizingoLeave;
	@FXML Button bizingoRestart;
	@FXML Label bizingoNameUp;
	@FXML Label bizingoNameDown;
	@FXML Label bizingoNameScore;
	@FXML Label bizingoNameTurn;
	@FXML Rectangle bizingoTurnRect;
	
	// Socket
	SocketP2P soc_p2p;
	
	// Variables
	GraphicsContext gc_background;
	GraphicsContext gc_board;
	GraphicsContext gc_status_down;
	GraphicsContext gc_status_up;
	
	BizingoBoardGenerator boardGen;
	BizingoStatus status;
	BizingoUtils utils;
	BizingoAnimation animator;
	
	AlertUtils alertUtils;
	SoundUtils soundUtils;
	
	List<BizingoTriangle> triangles;
	List<BizingoPiece> pieces;
	
	Boolean turn;
	Boolean piece_selected;
	
	int turn_idx;
	int idx_triangle, idx_triangle_last;
	int idx_piece, idx_piece_last;
	
	public void loadFromParent(SocketP2P soc_p2p) {
		this.soc_p2p = soc_p2p;
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Instantiating objects
		gc_background  = bizingoCanvasBackground.getGraphicsContext2D();
		gc_board = bizingoCanvasBoard.getGraphicsContext2D();
		gc_status_down = bizingoCanvasStatusDown.getGraphicsContext2D();
		gc_status_up = bizingoCanvasStatusUp.getGraphicsContext2D();
		boardGen = new BizingoBoardGenerator();
		status = new BizingoStatus();
		utils = new BizingoUtils();
		animator = new BizingoAnimation();
		
		alertUtils = new AlertUtils();
		soundUtils = new SoundUtils();
		
		triangles = new ArrayList<>();
		pieces = new ArrayList<>();
		
		// Variables
		piece_selected = false;
		turn_idx = 0;
		idx_triangle = -1;
		idx_triangle_last = -1;
		idx_piece = -1;
		idx_piece_last = -1;
		
		// Generate Board
		boardGen.generateBoard(triangles, pieces, gc_background, bizingoPiecesPane);
		
		// Components setup
		setupComponents();
		
		// Canvas Mouse Pressed
		setCanvasMousePressedBehavior();
		
		// Leave Button Pressed
		setLeaveButtonBehavior();
		
		// Restart Button Pressed
		setRestartButtonBehavior();
	}
	
	@Override
	public void run() {
		if(soc_p2p.isServer()) {
			turn = true;
			bizingoTurnRect.setVisible(false);
		}else {
			turn = false;
			bizingoTurnRect.setVisible(true);
		}
		status.draw_cover(gc_status_down, gc_status_up, soc_p2p.isServer());
		
		while(true) {
			try {
				Thread.sleep(BizingoConstants.THREAD_SLEEP_TIME_MILLIS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(!soc_p2p.isConnected()) {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						soc_p2p.disconnect();
						alertUtils.alertDisconnected();
				        Platform.exit();
				        System.exit(0);
					}
				});
				try {
					this.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			if(soc_p2p.gameMessageStackFull()) {
            	// Receive Messages
				String message_received = soc_p2p.getMessage();

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						turn = true;
						bizingoNameTurn.setText(BizingoConstants.TEXT_LABEL_TURN+String.valueOf(++turn_idx));
						bizingoTurnRect.setVisible(false);
						decodeMove(message_received);
					}
				});
			}
			
			if(soc_p2p.sysMessageStackFull()) {
            	// Receive Messages
				String message_received = soc_p2p.getMessage();
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						if(message_received.equals(BizingoConstants.SYS_RESTART_REQUEST)) {
							if(ButtonType.OK == alertUtils.alertRestartGameResponse()) {
								soc_p2p.sendSysMessage(BizingoConstants.SYS_RESTART_RESPONSE_OK);
								restartGame();
								bizingoRestart.setDisable(false);
							}else {
								soc_p2p.sendSysMessage(BizingoConstants.SYS_RESTART_RESPONSE_FAIL);
								bizingoRestart.setDisable(false);
							}
						}
						else if(message_received.equals(BizingoConstants.SYS_RESTART_RESPONSE_OK)) {
							alertUtils.alertRestartGameSucceeded();
							restartGame();
							bizingoRestart.setDisable(false);
						}
						else if(message_received.equals(BizingoConstants.SYS_RESTART_RESPONSE_FAIL)) {
							alertUtils.alertRestartGameFailed();
							if(turn) {
								bizingoTurnRect.setVisible(false);
							}
							bizingoRestart.setDisable(false);
						}
					}
				});
				
			}
		}
	}
	
	private void setupComponents() {
		bizingoLeave.setText(BizingoConstants.TEXT_BUTTON_LEAVE);
		bizingoLeave.setFont(FontConstants.sixty16p);
		
		bizingoRestart.setText(BizingoConstants.TEXT_BUTTON_RESTART);
		bizingoRestart.setFont(FontConstants.sixty14p);
		
		bizingoNameUp.setText(BizingoConstants.TEXT_LABEL_NAME_UP);
		bizingoNameUp.setFont(FontConstants.sixty40p);
		
		bizingoNameDown.setText(BizingoConstants.TEXT_LABEL_NAME_DOWN);
		bizingoNameDown.setFont(FontConstants.sixty40p);
		
		bizingoNameScore.setText(BizingoConstants.TEXT_LABEL_SCORE);
		bizingoNameScore.setFont(FontConstants.sixty30p);
		
		bizingoNameTurn.setText(BizingoConstants.TEXT_LABEL_TURN+String.valueOf(++turn_idx));
		bizingoNameTurn.setFont(FontConstants.sixty25p);
	}
	
	private void setCanvasMousePressedBehavior() {
		bizingoCanvasBoard.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
	        	if(turn==false) { return; }
	        	
	        	gc_board.clearRect(0, 0, bizingoCanvasBoard.getWidth(), bizingoCanvasBoard.getHeight());
	        		        	
	        	double x = event.getX();
	        	double y = event.getY();
	        	
	        	idx_triangle=-1;
	        	idx_piece=-1;
	        	
	        	idx_triangle = utils.findPressedTriangle(x, y, triangles);
	        	
	        	if(piece_selected) {
	        		if(idx_triangle==-1) {
	        			piece_selected = false;
		        	}else {
			        	idx_piece = utils.triangleHasPiece(soc_p2p.isServer(), triangles.get(idx_triangle), pieces);
			        	
			        	if(idx_piece==-1) {
			        		if(utils.triangleIsPlayable(idx_triangle)) {
			        			animator.move(pieces.get(idx_piece_last), triangles.get(idx_triangle));
			        			animator.sequentialTransition.setOnFinished((new EventHandler<ActionEvent>() {
			        				@Override
			        				public void handle(ActionEvent event) {
			        					piece_selected = false;
							        	turn = false;
							        	bizingoNameTurn.setText(BizingoConstants.TEXT_LABEL_TURN+String.valueOf(++turn_idx));
							        	bizingoTurnRect.setVisible(true);
							        	soc_p2p.sendGameMessage(encodeMove(idx_piece_last, idx_triangle));
							        	pieces.get(idx_piece_last).setPosition(triangles.get(idx_triangle).getCenter());
							        	if(utils.findCapturedPiece(idx_piece_last, triangles, pieces, bizingoPiecesPane)) {
							        		soundUtils.playCaptureSound();
							        		status.update_status(gc_status_up, soc_p2p.isServer(), pieces);
							        		endGame(utils.findWinnerAndLoser(pieces));
							        	}
			        				}
			        			}));
			        		}
			        		else {
			        			piece_selected = false;
			        		}
			        	}else {
			        		piece_selected = true;
				        	utils.findPlayableTriangles(triangles, pieces, idx_triangle);
				        	utils.paintHighlightedPlayableTriangles(gc_board, triangles);
				        	utils.paintHighlightedTriangle(gc_board, triangles.get(idx_triangle).getPoints());
				        	idx_triangle_last = idx_triangle;
				        	idx_piece_last = idx_piece;
			        	}
		        	}
	        	}else {
	        		if(idx_triangle==-1) {
	        			piece_selected = false;
		        	}else {
		        		idx_piece = utils.triangleHasPiece(soc_p2p.isServer(), triangles.get(idx_triangle), pieces);
			        	
			        	if(idx_piece==-1) {
			        		piece_selected = false;
			        	}else {
				        	piece_selected = true;
				        	utils.findPlayableTriangles(triangles, pieces, idx_triangle);
				        	utils.paintHighlightedPlayableTriangles(gc_board, triangles);
				        	utils.paintHighlightedTriangle(gc_board, triangles.get(idx_triangle).getPoints());
				        	idx_triangle_last = idx_triangle;
				        	idx_piece_last = idx_piece;
			        	}
		        	}
	        	}
	    		
	        }
	    });
		
	}
	
	private void setLeaveButtonBehavior() {
		bizingoLeave.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
				if(ButtonType.OK == alertUtils.alertLeaveGame()) {
		    		soc_p2p.disconnect();
    		        Platform.exit();
    		        System.exit(0);
				}
	        }
	        
		});
	}
	
	private void setRestartButtonBehavior() {
		bizingoRestart.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){

	        @Override
	        public void handle(MouseEvent event) {
				if(ButtonType.OK == alertUtils.alertRestartGameRequest()) {
					soc_p2p.sendSysMessage(BizingoConstants.SYS_RESTART_REQUEST);
					bizingoRestart.setDisable(true);
					bizingoTurnRect.setVisible(true);
				}
	        }
	        
		});
	}
	
	private String encodeMove(int idx_piece_last, int idx_triangle) {
		String move = String.valueOf(idx_piece_last) + "&" + String.valueOf(idx_triangle);
		return move;
	}
	
	private void decodeMove(String move) {
		int idx_piece_last = Integer.valueOf(move.substring(0,move.indexOf("&")));
		int idx_triangle = Integer.valueOf(move.substring(move.indexOf("&")+1,move.length()));
		
		animator.move(pieces.get(idx_piece_last), triangles.get(idx_triangle));
		animator.sequentialTransition.setOnFinished((new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
	        	pieces.get(idx_piece_last).setPosition(triangles.get(idx_triangle).getCenter());
	        	if(utils.findCapturedPiece(idx_piece_last, triangles, pieces, bizingoPiecesPane)) {
	        		soundUtils.playCaptureSound();
	        		status.update_status(gc_status_up, soc_p2p.isServer(), pieces);
	        		endGame(utils.findWinnerAndLoser(pieces));
	        	}
			}
		}));
		
		return;
	}
	
	private void restartGame() {
		// Instantiating objects
		boardGen = new BizingoBoardGenerator();
		status = new BizingoStatus();
		utils = new BizingoUtils();
		pieces = new ArrayList<>();
		
		// Variables
		piece_selected = false;
		turn_idx = 0;
		idx_triangle = -1;
		idx_triangle_last = -1;
		idx_piece = -1;
		idx_piece_last = -1;
		
		// Re-Generate Board
		bizingoPiecesPane.getChildren().clear();
		boardGen.generateBoard(triangles, pieces, bizingoPiecesPane);
		
		// Update status
		if(soc_p2p.isServer()) {
			turn = true;
			bizingoTurnRect.setVisible(false);
		}else {
			turn = false;
			bizingoTurnRect.setVisible(true);
		}
		
		status.update_status(gc_status_up, soc_p2p.isServer(), pieces);
		
		// Update turn label
		bizingoNameTurn.setText(BizingoConstants.TEXT_LABEL_TURN+String.valueOf(++turn_idx));
	}
	
	private void endGame(int winner) {
    	if(soc_p2p.isServer() && winner==1) {
    		soundUtils.playVictorySound();
    		alertUtils.alertWinner();
    	}
    	else if(soc_p2p.isServer() && winner==2) {
    		soundUtils.playDefeatSound();
    		alertUtils.alertLoser();
    	}
    	else if(soc_p2p.isClient() && winner==1) {
    		soundUtils.playDefeatSound();
    		alertUtils.alertLoser();
    	}
    	else if(soc_p2p.isClient() && winner==2) {
    		soundUtils.playVictorySound();
    		alertUtils.alertWinner();
    	}
	}
	
}