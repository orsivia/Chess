/**
 * This version undo two steps
 */

package graphics;

import java.util.ArrayList;

import java.awt.*;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;

import java.io.File;
import java.io.IOException;

import models.Board;
import models.Game;
import models.Coord;
import pieces.Piece;
import pieces.Pawn;

public class ChessBoard implements ActionListener {
	final static int board_width = 640, board_height = 640;

	// the bottom-most layer, will add grids and labels to this layer
	private static JPanel chessBoard = new JPanel(new BorderLayout(3, 3));

	private JButton[][] tiles = new JButton[8][8];
	private Image[][] images = new Image[2][8];
	final static int WHITE = 0, BLACK = 1, WHITE_TURN = 1, BLACK_TURN = -1, CUSTOMIZED = 0, CLASSIC = 1;
	final static int OWN = 0, OPP = 1, PIECETOMOVE = 0, PIECETOCAP = 1, STARTCOORD = 2, TARGETCOORD = 3, OLDICON = 4,
			CAPICON = 5;
	final int PAWN = 0, ROOK = 1, KNIGHT = 2, BISHOP = 3, QUEEN = 4, KING = 5, GUARD = 6, CANNON = 7;

	private static Coord startPos;
	private static Coord targetPos;
	private static Piece curPiece;
	private static Piece prePiece;
	private static int clickCount;

	// cache for undo
	private Object[][][] cache;

	private static int mode;
	private static Board board;

	/**
	 * Constructor
	 */
	public ChessBoard() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			// silently ignore
		}

		initializeVars();

		JFrame window = new JFrame("This is a CHESS GAME");
		window.setSize(board_width, board_height);
		setupMenu(window);

		loadImage();
		boardInit();
		window.setContentPane(chessBoard);

		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * initialize all position-related variables
	 */
	private void initializeVars() {
		board = Game.getBoard();
		startPos = new Coord(-1, -1);
		targetPos = new Coord(-1, -1);
		clickCount = 0;

		cache = new Object[2][2][6];
	}

	/**
	 * reset all position-related variables
	 */
	private void resetVars() {
		startPos.setRC(-1, -1);
		targetPos.setRC(-1, -1);
		curPiece = null;
		prePiece = null;
		clickCount = 0;
	}

	/**
	 * refactoring; helper function for loadImage()
	 * 
	 * @param color
	 * @param piece
	 * @param path
	 */
	void loadImageHelper(int color, int piece, String path) {
		try {
			images[color][piece] = ImageIO.read(new File(path));
		} catch (IOException e) {
			// silently ignore
		}
	}

	/**
	 * Load images to local storage and resize
	 */
	private void loadImage() {
		loadImageHelper(WHITE, PAWN, "images/Pawn-WHITE.png");
		loadImageHelper(WHITE, ROOK, "images/Rook-WHITE.png");
		loadImageHelper(WHITE, KNIGHT, "images/Knight-WHITE.png");
		loadImageHelper(WHITE, BISHOP, "images/Bishop-WHITE.png");
		loadImageHelper(WHITE, QUEEN, "images/Queen-WHITE.png");
		loadImageHelper(WHITE, KING, "images/King-WHITE.png");
		loadImageHelper(WHITE, GUARD, "images/Guard-WHITE.png");
		loadImageHelper(WHITE, CANNON, "images/Cannon-WHITE.png");
		loadImageHelper(BLACK, PAWN, "images/Pawn-BLACK.png");
		loadImageHelper(BLACK, ROOK, "images/Rook-BLACK.png");
		loadImageHelper(BLACK, KNIGHT, "images/Knight-BLACK.png");
		loadImageHelper(BLACK, BISHOP, "images/Bishop-BLACK.png");
		loadImageHelper(BLACK, QUEEN, "images/Queen-BLACK.png");
		loadImageHelper(BLACK, KING, "images/King-BLACK.png");
		loadImageHelper(BLACK, GUARD, "images/Guard-BLACK.png");
		loadImageHelper(BLACK, CANNON, "images/Cannon-BLACK.png");

		// resize images to fit JButtons
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 8; j++) {
				images[i][j] = images[i][j].getScaledInstance(board_width / 8, board_height / 8, Image.SCALE_SMOOTH);
			}
		}
	}

	/**
	 * initialize all images depending on player's mode choice
	 * default is classical mode
	 * @param mode
	 */
	private void initializeImages(int mode) {
		if (mode == CUSTOMIZED) {
			// pawns, guards, cannons and empty tiles
			for (int i = 0; i < 8; i++) {
				for (int j = 2; j < 6; j++) {
					tiles[j][i].setIcon(null);
				}
				if (i == 3 || i == 4) {
					tiles[1][i].setIcon(new ImageIcon(images[WHITE][GUARD]));
					tiles[6][i].setIcon(new ImageIcon(images[BLACK][GUARD]));
					tiles[2][i].setIcon(new ImageIcon(images[WHITE][CANNON]));
					tiles[5][i].setIcon(new ImageIcon(images[BLACK][CANNON]));
				} else {
					tiles[1][i].setIcon(new ImageIcon(images[WHITE][PAWN]));
					tiles[6][i].setIcon(new ImageIcon(images[BLACK][PAWN]));
				}
			}
		} else {
			// pawns and empty tiles
			for (int i = 0; i < 8; i++) {
				tiles[1][i].setIcon(new ImageIcon(images[WHITE][PAWN]));
				tiles[6][i].setIcon(new ImageIcon(images[BLACK][PAWN]));
				for (int j = 2; j < 6; j++) {
					tiles[j][i].setIcon(null);
				}
			}
		}
		// other white pieces
		tiles[0][0].setIcon(new ImageIcon(images[WHITE][ROOK]));
		tiles[0][7].setIcon(new ImageIcon(images[WHITE][ROOK]));
		tiles[0][1].setIcon(new ImageIcon(images[WHITE][KNIGHT]));
		tiles[0][6].setIcon(new ImageIcon(images[WHITE][KNIGHT]));
		tiles[0][2].setIcon(new ImageIcon(images[WHITE][BISHOP]));
		tiles[0][5].setIcon(new ImageIcon(images[WHITE][BISHOP]));
		tiles[0][4].setIcon(new ImageIcon(images[WHITE][KING]));
		tiles[0][3].setIcon(new ImageIcon(images[WHITE][QUEEN]));
		// other black pieces
		tiles[7][0].setIcon(new ImageIcon(images[BLACK][ROOK]));
		tiles[7][7].setIcon(new ImageIcon(images[BLACK][ROOK]));
		tiles[7][1].setIcon(new ImageIcon(images[BLACK][KNIGHT]));
		tiles[7][6].setIcon(new ImageIcon(images[BLACK][KNIGHT]));
		tiles[7][2].setIcon(new ImageIcon(images[BLACK][BISHOP]));
		tiles[7][5].setIcon(new ImageIcon(images[BLACK][BISHOP]));
		tiles[7][4].setIcon(new ImageIcon(images[BLACK][KING]));
		tiles[7][3].setIcon(new ImageIcon(images[BLACK][QUEEN]));
	}

	/**
	 * board Initialization
	 */
	private void boardInit() {
		JPanel grids = new JPanel(new GridLayout(0, 8));
		grids.setBorder(new LineBorder(Color.BLACK));
		chessBoard.add(grids);
		// create buttons
		Insets margin = new Insets(0, 0, 0, 0);
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[0].length; j++) {
				JButton tile = new JButton();
				tile.setMargin(margin);
				tile.setBorderPainted(false);
				tile.setOpaque(true);
				if (i % 2 == j % 2) {
					tile.setBackground(Color.GRAY);
				} else {
					tile.setBackground(Color.WHITE);
				}
				tiles[i][j] = tile;
			}
		}
		initializeImages(mode);
		// add tiles
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tiles[i][j].addActionListener(this);
				grids.add(tiles[i][j]);
			}
		}
	}

	/**
	 * menu setup
	 * @param window
	 */
	private void setupMenu(JFrame window) {
		JMenuBar menubar = new JMenuBar();
		JMenu game = new JMenu("Game");

		JMenuItem forfeit = new JMenuItem("Forfeit");
		forfeit.addActionListener(this);
		game.add(forfeit);

		JMenuItem undo = new JMenuItem("Undo");
		undo.addActionListener(this);
		game.add(undo);

		menubar.add(game);
		window.setJMenuBar(menubar);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Forfeit")) {
			int res = JOptionPane.showConfirmDialog(chessBoard, "Reset game?\nYour opponent will gain point.");
			if (res == 0) {
				mode = Game.resetGame(chessBoard);
				initializeImages(mode);
			}
		} else if (e.getActionCommand().equals("Undo")) {
			// player can only undo when:
			// 1. it is opponent's turn
			// 2. opponent has not made his move
			int turn = Game.getTurn();
			int player = translate(turn);

			if (cache[player][OWN][STARTCOORD] == null) {
				JOptionPane.showMessageDialog(chessBoard, "Opponent has made his move.\nCannot undo.");
			} else {
				undo(player);
			}
		} else {
			clickCount++;
			findAndMovePiece(e);
		}
	}

	/**
	 * undo wrapper
	 * @param player
	 */
	private void undo(int player) {
		//order matters, has to be opponent's recovery first
		undo_helper(player, OPP);
		undo_helper(player, OWN);
	}

	/**
	 * undo helper
	 * @param player
	 * @param side
	 */
	private void undo_helper(int player, int side) {
		if (cache[player][OWN][STARTCOORD] == null || cache[player][OWN][TARGETCOORD] == null) {
			return;
		}
		Coord start = (Coord) cache[player][side][STARTCOORD];
		Coord target = (Coord) cache[player][side][TARGETCOORD];
		board.removePiece(start);
		board.removePiece(target);
		// if the piece being recovered is a pawn
		// make sure its firstMove is also recovered
		pawnRecovery((Piece) cache[player][side][PIECETOMOVE], start);
		pawnRecovery((Piece) cache[player][side][PIECETOCAP], target);
		board.setPiece((Piece) cache[player][side][PIECETOMOVE], start);
		board.setPiece((Piece) cache[player][side][PIECETOCAP], target);
		tiles[start.getRow()][start.getCol()].setIcon((Icon) cache[player][side][OLDICON]);
		tiles[target.getRow()][target.getCol()].setIcon((Icon) cache[player][side][CAPICON]);
	}

	/**
	 * to recover a pawn's firstMove value
	 * @param p
	 * @param from
	 */
	private void pawnRecovery(Piece p, Coord from) {
		if (p instanceof Pawn) {
			if (from.getRow() == 1 || from.getRow() == 6) {
				((Pawn) p).setFirstMove(true);
			} else {
				((Pawn) p).setFirstMove(false);
			}
		}
	}

	/**
	 * as titled, find the target piece, then move it
	 * 
	 * @param e
	 */
	private void findAndMovePiece(ActionEvent e) {
		Coord found = new Coord(-1, -1);
		// find corresponding tile's coordinate
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (e.getSource().equals(tiles[i][j])) {
					found.setRC(i, j);
				}
			}
		}

		int curTurn = Game.getTurn();
		int nextTurn = -1 * curTurn;
		int curPlayer = translate(curTurn);

		// choose start position
		if (clickCount == 1) {
			if (found.getRow() == -1 || found.getCol() == -1) {
				return;
			}
			// do some recordings
			startPos.dup(found);
			prePiece = board.getPiece(found);

			cache[curPlayer][OWN][PIECETOMOVE] = prePiece;
			cache[curPlayer][OWN][STARTCOORD] = found;
			cache[curPlayer][OWN][OLDICON] = tiles[found.getRow()][found.getCol()].getIcon();
			cache[1 - curPlayer][OPP][PIECETOMOVE] = prePiece;
			cache[1 - curPlayer][OPP][STARTCOORD] = found;
			cache[1 - curPlayer][OPP][OLDICON] = tiles[found.getRow()][found.getCol()].getIcon();

			// if start position is empty, just ignore this click
			if (prePiece == null) {
				resetVars();
				clickCount = 0;
				// System.out.printf("(%d,%d) = null\n", found.getRow(),
				// found.getCol());
				return;
			} else {
				// highlight all valid moves and current selected tile
				ArrayList<Coord> validMoves = prePiece.getValidMoves();
				for (int i = 0; i < validMoves.size(); i++) {
					Coord cur = validMoves.get(i);
					int r = cur.getRow();
					int c = cur.getCol();
					if (r % 2 == c % 2) {
						tiles[r][c].setBackground(new Color(0, 204, 0));
					} else {
						tiles[r][c].setBackground(new Color(255, 255, 153));
					}
				}
				tiles[startPos.getRow()][startPos.getCol()].setBackground(new Color(51, 153, 255));
			}
		}

		// choose target position
		if (clickCount == 2) {
			// reset highlighted tiles' background colors
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (i % 2 == j % 2) {
						tiles[i][j].setBackground(Color.GRAY);
					} else {
						tiles[i][j].setBackground(Color.WHITE);
					}
				}
			}

			if (found.getRow() == -1 || found.getCol() == -1) {
				return;
			}
			// do some recordings
			targetPos.dup(found);
			curPiece = board.getPiece(found);

			cache[curPlayer][OWN][PIECETOCAP] = curPiece;
			cache[curPlayer][OWN][TARGETCOORD] = found;
			cache[curPlayer][OWN][CAPICON] = tiles[found.getRow()][found.getCol()].getIcon();
			cache[1 - curPlayer][OPP][PIECETOCAP] = curPiece;
			cache[1 - curPlayer][OPP][TARGETCOORD] = found;
			cache[1 - curPlayer][OPP][CAPICON] = tiles[found.getRow()][found.getCol()].getIcon();

			// reconsider which piece to move
			if (targetPos.equal(startPos)) {
				resetVars();
				clickCount = 0;
				return;
			}

			// move the piece only when it is the player's turn
			if (prePiece != null) {
				if (prePiece.getColor() == curTurn && !startPos.equals(new Coord(-1, -1))) {
					// try to move the piece
					if (board.movePiece(startPos, targetPos)) {
						updateImage();
						// in check?
						if (board.check(nextTurn)) {
							if (nextTurn == WHITE_TURN)
								JOptionPane.showMessageDialog(chessBoard, "White is in check!");
							else
								JOptionPane.showMessageDialog(chessBoard, "Black is in check!");
						}
						// in checkmate?
						if (board.checkmate(nextTurn)) {
							if (nextTurn == WHITE_TURN)
								JOptionPane.showMessageDialog(chessBoard, "White is in checkmate.\nBlack wins!");
							else
								JOptionPane.showMessageDialog(chessBoard, "Black is in checkmate.\nWhite wins!");
							int res = JOptionPane.showConfirmDialog(chessBoard, "Start a new game?");
							if (res == 0) {
								mode = Game.resetGame(chessBoard);
								initializeImages(mode);
								Game.nextTurn();
							}
						}
						// in stalemate?
						if (board.stalemate(nextTurn)) {
							JOptionPane.showMessageDialog(chessBoard, "Stalemate.\nDraw!");
							int res = JOptionPane.showConfirmDialog(chessBoard, "Start a new game?");
							if (res == 0) {
								mode = Game.resetGame(chessBoard);
								initializeImages(mode);
								Game.nextTurn();
							}
						} else {
							// game goes on
							Game.nextTurn();
						}
					} else {
						// System.out.printf("from (%d,%d) to (%d,%d)\n",
						// startPos.getRow(),startPos.getCol(),targetPos.getRow(),targetPos.getCol());
						JOptionPane.showMessageDialog(chessBoard, "Illegal move.\nTry again.");
					}
				} else {
					JOptionPane.showMessageDialog(chessBoard, "Not your turn yet.");
				}
			}
			resetVars();
			clickCount = 0;
		}
	}

	/**
	 * update the two tile's images after moving
	 */
	private void updateImage() {
		int startRow = startPos.getRow();
		int startCol = startPos.getCol();
		int targetRow = targetPos.getRow();
		int targetCol = targetPos.getCol();
		tiles[targetRow][targetCol].setIcon(tiles[startRow][startCol].getIcon());
		tiles[startRow][startCol].setIcon(null);
	}

	/**
	 * translate macro COLOR_TURN(turn) into COLOR(player) for array use
	 * 
	 * @param input
	 */
	private static int translate(int turn) {
		int res = (turn == WHITE_TURN) ? WHITE : BLACK;
		return res;
	}

	/**
	 * main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		mode = Game.initialize(chessBoard);
		new ChessBoard();
		Game.startGame();
	}
}

