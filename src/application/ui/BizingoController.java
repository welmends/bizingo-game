package application.ui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import application.socket.SocketP2P;
import application.ui.animation.BizingoAnimation;
import application.ui.bizingostructure.BizingoBoardGenerator;
import application.ui.bizingostructure.BizingoPiece;
import application.ui.bizingostructure.BizingoStatus;
import application.ui.bizingostructure.BizingoTriangle;
import application.ui.utils.BizingoUtils;
import application.ui.utils.FontUtils;
import application.ui.utils.SoundUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
	
	SoundUtils soundUtils;
	
	List<BizingoTriangle> triangles;
	List<BizingoPiece> pieces;
	
	Boolean turn;
	Boolean piece_selected;
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
		soundUtils = new SoundUtils();
		triangles = new ArrayList<>();
		pieces = new ArrayList<>();
		
		// Variables
		piece_selected = false;
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
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if(!soc_p2p.isConnected()) {
				alertDisconnected();
			}
			
			if(soc_p2p.gameMessageStackFull()) {
            	// Receive Messages
				String message_received = soc_p2p.getMessage();

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						turn = true;
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
						if(message_received.equals("restart")) {
							if(ButtonType.OK == alertGeneric("O outro jogador deseja reiniciar a partida. Você aceita?",Alert.AlertType.CONFIRMATION)) {
								soc_p2p.sendSysMessage("restart_ok");
								restartGame();
								bizingoRestart.setDisable(false);
							}else {
								soc_p2p.sendSysMessage("restart_fail");
								bizingoRestart.setDisable(false);
							}
						}
						else if(message_received.equals("restart_ok")) {
							alertGeneric("O outro jogador aceitou o reinicio da partida",Alert.AlertType.INFORMATION);
							restartGame();
							bizingoRestart.setDisable(false);
						}
						else if(message_received.equals("restart_fail")) {
							alertGeneric("O outro jogador negou o reinicio da partida",Alert.AlertType.INFORMATION);
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
		bizingoLeave.setText("L E A V E");
		bizingoLeave.setFont(FontUtils.sixty16p);
		
		bizingoRestart.setText("R E S T A R T");
		bizingoRestart.setFont(FontUtils.sixty14p);
		
		bizingoNameUp.setText("B I Z I N G O");
		bizingoNameUp.setFont(FontUtils.sixty40p);
		
		bizingoNameDown.setText("G A M E");
		bizingoNameDown.setFont(FontUtils.sixty40p);
		
		bizingoNameScore.setText("S C O R E");
		bizingoNameScore.setFont(FontUtils.sixty30p);
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
							        	bizingoTurnRect.setVisible(true);
							        	soc_p2p.sendGameMessage(encodeMove(idx_piece_last, idx_triangle));
							        	pieces.get(idx_piece_last).setPosition(triangles.get(idx_triangle).getCenter());
							        	if(utils.findCapturedPiece(soc_p2p.isServer(), pieces, bizingoPiecesPane)) {
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
				if(ButtonType.OK == alertGeneric("Deseja realmente sair da partida?",Alert.AlertType.CONFIRMATION)) {
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
				if(ButtonType.OK == alertGeneric("Deseja realmente reiniciar a partida?",Alert.AlertType.CONFIRMATION)) {
					soc_p2p.sendSysMessage("restart");
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
	        	if(utils.findCapturedPiece(!soc_p2p.isServer(), pieces, bizingoPiecesPane)) {
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
	}
	
	private void endGame(int winner) {
    	if(soc_p2p.isServer() && winner==1) {
    		alertWinner();
    	}
    	else if(soc_p2p.isServer() && winner==2) {
    		alertLoser();
    	}
    	else if(soc_p2p.isClient() && winner==1) {
    		alertLoser();
    	}
    	else if(soc_p2p.isClient() && winner==2) {
    		alertWinner();
    	}
	}
	
	private void alertWinner() {
		ImageView img_victory = null;
		try {
			img_victory = new ImageView(new Image(getClass().getResource("/resources/images/trophy.png").toURI().toString()));
		} catch (Exception e) {
			System.out.println(e);
		}
		img_victory.setFitHeight(100);
		img_victory.setFitWidth(100);
		
		soundUtils.playVictorySound();
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Bizingo Game Alerts");
		alert.setResizable(false);
		alert.setGraphic(img_victory);
		alert.setHeaderText("PARABÉNS!!! VOCÊ VENCEU A PARTIDA!!!");
	    alert.setOnHidden(evt -> Platform.exit());
	    alert.show(); 
	}
	
	private void alertLoser() {
		ImageView img_defeat = null;
		try {
			img_defeat = new ImageView(new Image(getClass().getResource("/resources/images/defeat.png").toURI().toString()));
		} catch (Exception e) {
			System.out.println(e);
		}
		img_defeat.setFitHeight(100);
		img_defeat.setFitWidth(100);
		
		soundUtils.playDefeatSound();
		
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Bizingo Game Alerts");
		alert.setResizable(false);
		alert.setGraphic(img_defeat);
		alert.setHeaderText("SINTO MUITO, VOCÊ PERDEU A PARTIDA!");
	    alert.setOnHidden(evt -> Platform.exit());
	    alert.show();
	}

	private void alertDisconnected() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				soc_p2p.disconnect();
				
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setTitle("Bizingo Game Alerts");
				alert.setResizable(false);
				alert.setHeaderText("O outro jogador saiu do jogo!");
				alert.showAndWait();
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
	
	private ButtonType alertGeneric(String message, Alert.AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle("Bizingo Game Alerts");
		alert.setResizable(false);
		alert.setHeaderText(message);
		alert.showAndWait();
		return alert.getResult();
	}
	
}