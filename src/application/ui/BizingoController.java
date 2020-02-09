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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class BizingoController extends Thread implements Initializable {
	
	// FXML Variables
	@FXML AnchorPane bizingoPane;
	@FXML Rectangle bizingoRect;
	@FXML Canvas bizingoCanvasBackground;
	@FXML Canvas bizingoCanvasBoard;
	@FXML Canvas bizingoCanvasStatus;
	@FXML AnchorPane bizingoAnchorPane;
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
	GraphicsContext gc_status;
	
	BizingoBoardGenerator boardGen;
	BizingoStatus status;
	BizingoUtils utils;
	BizingoAnimation animator;
	
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
		gc_status = bizingoCanvasStatus.getGraphicsContext2D();
		boardGen = new BizingoBoardGenerator(60.0);
		status = new BizingoStatus();
		utils = new BizingoUtils();
		animator = new BizingoAnimation();
		triangles = new ArrayList<>();
		pieces = new ArrayList<>();
		
		// Components setup
		Font sixty40p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 40);
		Font sixty30p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 30);
		Font sixty16p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 16);
		Font sixty14p = Font.loadFont(getClass().getResourceAsStream("/fonts/sixty.ttf"), 14);
		
		bizingoLeave.setText("L E A V E");
		bizingoLeave.setFont(sixty16p);
		
		bizingoRestart.setText("R E S T A R T");
		bizingoRestart.setFont(sixty14p);
		
		bizingoNameUp.setText("B I Z I N G O");
		bizingoNameUp.setFont(sixty40p);
		
		bizingoNameDown.setText("G A M E");
		bizingoNameDown.setFont(sixty40p);
		
		bizingoNameScore.setText("S C O R E");
		bizingoNameScore.setFont(sixty30p);
		
		status.draw_cover(gc_status);
		
		// Variables
		piece_selected = false;
		idx_triangle = -1;
		idx_triangle_last = -1;
		idx_piece = -1;
		idx_piece_last = -1;
		
		// Generate Board
		boardGen.generateBoard(triangles, pieces, gc_background, bizingoAnchorPane);
		
		// Canvas Mouse Pressed
		setCanvasMousePressedBehavior();
		
		// Leave Button Pressed
		setLeaveButtonBehavior();
		
		// Restart Button Pressed
		setRestartButtonBehavior();

//		AudioClip turnOnOff = new AudioClip(new File("/home/well/allProjects/java-projects/bizingo-sockets/src/resources/sounds/receive_msg.mp3").toURI().toString());
//		turnOnOff.setVolume(1.0);
//		turnOnOff.play();
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
				String message_received = soc_p2p.getMessage(); // Receive Remote

				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						turn = true;
						bizingoTurnRect.setVisible(false);
						decodeMove(message_received);
					}
				});
			}
		}
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
			        			animator.timeline.setOnFinished((new EventHandler<ActionEvent>() {
			        				@Override
			        				public void handle(ActionEvent event) {
			        					piece_selected = false;
							        	turn = false;
							        	bizingoTurnRect.setVisible(true);
							        	soc_p2p.sendGameMessage(encodeMove(idx_piece_last, idx_triangle));
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
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Bizingo Game Alerts");
				alert.setResizable(false);
				alert.setHeaderText("Deseja realmente sair do jogo?");
				alert.showAndWait();
				if(alert.getResult()==ButtonType.OK) {
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
	        	//Implement
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
		
		return;
	}
	
	private void alertDisconnected() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
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
	
}