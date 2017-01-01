package models;

import javax.swing.JPanel;
import javax.swing.JOptionPane;

public class Game {
	final static int WHITE = 1;
	final static int BLACK = -1;
	final static int PLAYER1 = 0;
	final static int PLAYER2 = 1;

	private static Board board;

	private static int turn = 0;
	private static int[] scores = null;

	/**
	 * convenience for setting pieces
	 */
	private static final String[] pieceList = { "Rook", "Knight", "Bishop", "Queen", "King", "Bishop", "Knight",
			"Rook" };

	/**
	 * wrapper function; initialization
	 */
	public static int initialize(JPanel jp) {
		// initialize board
		board = new Board();
		int mode = initializeBoard(jp);
		// initialize scores
		scores = new int[2];
		scores[0] = scores[1] = 0;
		return mode;
	}

	/**
	 * initialize the chess board according to player's choice between
	 * customized mode and classical mode
	 * 
	 * @param jp
	 * @return 0 if customized mode; 1 if classical mode
	 */
	public static int initializeBoard(JPanel jp) {
		int res = JOptionPane.showConfirmDialog(jp, "Play with customized pieces?");
		if (res == 0) {
			for (int i = 0; i < 8; i++) {
				if (i == 3 || i == 4) {
					board.setPiece("Guard", new Coord(1, i), WHITE);
					board.setPiece("Guard", new Coord(6, i), BLACK);
					board.setPiece("Cannon", new Coord(2, i), WHITE);
					board.setPiece("Cannon", new Coord(5, i), BLACK);
				} else {
					board.setPiece("Pawn", new Coord(1, i), WHITE);
					board.setPiece("Pawn", new Coord(6, i), BLACK);
				}
				board.setPiece(pieceList[i], new Coord(0, i), WHITE);
				board.setPiece(pieceList[i], new Coord(7, i), BLACK);
			}
		} else {
			for (int i = 0; i < 8; i++) {
				board.setPiece("Pawn", new Coord(1, i), WHITE);
				board.setPiece("Pawn", new Coord(6, i), BLACK);
				board.setPiece(pieceList[i], new Coord(0, i), WHITE);
				board.setPiece(pieceList[i], new Coord(7, i), BLACK);
			}
		}
		return res;
	}
	
	/**
	 * scenario for testing stalemate
	 * @param jp
	 */
	public static void stalemateTestBoard(JPanel jp){
		board.reset();
		board.setPiece("King", new Coord(0,0), BLACK);
		board.setPiece("Bishop", new Coord(0,1), BLACK);
		board.setPiece("King", new Coord(2,1), WHITE);
		board.setPiece("Rook", new Coord(1,7), WHITE);
		turn = WHITE;		
		JOptionPane.showMessageDialog(jp, "This is stalemate test.\nWhite moves Rook to h8.");
	}

	/**
	 * return all pieces to their initial positions reset turn to white player
	 */
	public static int resetGame(JPanel jp) {
		board.reset();
		int res = initializeBoard(jp);
		turn = WHITE;
		return res;
	}

	/**
	 * white player moves first
	 */
	public static void startGame() {
		turn = WHITE;
	}

	/**
	 * check if startGame() has been called
	 * 
	 * @return
	 */
	public static boolean gameStarted() {
		return turn != 0;
	}

	/**
	 * switch turn
	 */
	public static void nextTurn() {
		turn *= -1;
	}

	/**
	 * set turn when undoing
	 */
	public static void setTurn(int t) {
		turn = t;
	}

	/**
	 * get current turn
	 * 
	 * @return
	 */
	public static int getTurn() {
		return turn;
	}

	public static Board getBoard() {
		return board;
	}

	/**
	 * update score list and show scores as message
	 * 
	 * @param player black=0, white=1
	 * @param jp
	 */
	public static void showScores(int player, JPanel jp) {
		if(player != -1){
			scores[player]++;
		}
		JOptionPane.showMessageDialog(jp, "Current scores:\nBlack:" + scores[PLAYER1] + "\nWhite:" + scores[PLAYER2]);
	}
	
	/**
	 * reset scores to 0 when starting a brand new game
	 */
	public static void resetScores(){
		scores[PLAYER1] = scores[PLAYER2] = 0;
	}
}
