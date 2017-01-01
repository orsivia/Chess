//version 1.0

package models;

import java.util.ArrayList;
import pieces.Piece;
import pieces.Pawn;
import pieces.King;

public class Board {
	private Piece[][] board;
	private int width;
	private int height;

	/**
	 * Default constructor
	 */
	public Board() {
		this.board = new Piece[8][8];
		this.width = board.length;
		this.height = board[0].length;
	}

	/**
	 * Copy constructor
	 */
	public Board(Board other) {
		this.board = new Piece[other.width][other.height];
		for (int i = 0; i < other.width; i++) {
			for (int j = 0; j < other.height; j++) {
				this.board[i][j] = other.board[i][j];
			}
		}
		this.width = other.width;
		this.height = other.height;
	}
	
	/**
	 * empty the board
	 */
	public void reset(){
		for(int i=0; i<board.length; i++){
			for(int j=0; j<board[0].length; j++){
				board[i][j] = null;
			}
		}
	}

	public boolean outOfBoard(int row, int col) {
		return row < 0 || col < 0 || row >= board.length || col >= board[0].length;
	}

	/**
	 * @param pos
	 * @return Piece object at target position
	 */
	public Piece getPiece(Coord pos) {
		return board[pos.getRow()][pos.getCol()];
	}

	/**
	 * Update all pieces' valid moves
	 */
	public void updatePieces() {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (board[i][j] != null) {
					board[i][j].updateValidMoves(board, new Coord(i, j));
				}
			}
		}
	}

	/**
	 * set a piece at target position
	 * @param name
	 * @param pos
	 * @param color
	 */
	public void setPiece(String name, Coord pos, int color) {
		int row = pos.getRow();
		int col = pos.getCol();
		if (outOfBoard(row, col)) {
			return;
		}
		if (board[row][col] != null) {
			System.out.println("Piece already existed at target position.");
			return;
		}
		Piece newPiece = Piece.stringToPiece(name, color);
		board[row][col] = newPiece;
		this.updatePieces();
	}
	
	/**
	 * setPiece() with different signature
	 * @param p
	 * @param pos
	 */
	public void setPiece(Piece p, Coord pos){
		int row = pos.getRow();
		int col = pos.getCol();
		if (outOfBoard(row, col)) {
			return;
		}
		if (board[row][col] != null) {
			System.out.println("Piece already existed at target position.");
			return;
		}
		board[row][col] = p;
		this.updatePieces();		
	}
	
	/**
	 * remove a piece at target position
	 * @param pos
	 */
	public void removePiece(Coord pos){
		int row = pos.getRow();
		int col = pos.getCol();
		board[row][col] = null;
	}

	/**
	 * @param start
	 * @param target
	 * @return true if move a piece successfully
	 */
	public boolean movePiece(Coord start, Coord target) {
		int srow = start.getRow();
		int scol = start.getCol();
		int trow = target.getRow();
		int tcol = target.getCol();
		if (outOfBoard(srow, scol) || outOfBoard(trow, tcol)) {
			return false;
		}
		Piece pieceToMove = board[srow][scol];

		if (pieceToMove == null) {
			return false;
		}
		if (pieceToMove.isValidMove(target)) {
			Board copy = new Board(this);
			board[trow][tcol] = pieceToMove;
			board[srow][scol] = null;
			this.updatePieces();
			if (check(pieceToMove.getColor())) {
				this.board = copy.board;
				updatePieces();
				return false;
			}
			//once a pawn has been moved, it cannot move forward two tiles anymore
			if(pieceToMove instanceof Pawn){
				if(((Pawn) pieceToMove).getFirstMove()){
					((Pawn) pieceToMove).setFirstMove(false);
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param player
	 * @return true if opponent in check
	 */
	public boolean check(int player) {
		Coord kingPos = this.getKingCoord(player);
		if (kingPos == null)
			return false;
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (board[i][j] == null) {
					continue;
				}
				if (board[i][j].canCapture(kingPos)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * checkmate condition check
	 * @param player
	 * @return true if opponent in checkmate
	 */
	public boolean checkmate(int player) {
		if (!check(player))
			return false;
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				Piece cur = board[i][j];
				if (cur == null) {
					continue;
				}
				if (cur.getColor() == player && this.canMove(cur, new Coord(i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * stalemate condition check
	 * @param player
	 * @return true if opponent in stalemate
	 */
	public boolean stalemate(int player) {
		if (check(player)) {
			return false;
		}
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				Piece cur = board[i][j];
				if (cur == null) {
					continue;
				}
				if (cur.getColor() == player && this.canMove(cur, new Coord(i, j))) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * helper function for condition check
	 * @param cur
	 * @param curPos
	 * @return true if there exists any piece can move w/o ending the game
	 */
	public boolean canMove(Piece cur, Coord curPos) {
		if (cur == null)
			return false;
		Board copy = new Board(this);
		ArrayList<Coord> moves = cur.getValidMoves();
		for (int i = 0; i < moves.size(); i++) {
			Coord target = moves.get(i);
			if (movePiece(curPos, target)) {
				this.board = copy.board;
				updatePieces();
				return true;
			}
		}
		return false;
	}

	/**
	 * @param player
	 * @return king's position
	 */
	public Coord getKingCoord(int player) {
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				if (board[i][j] instanceof King && board[i][j].getColor() == player) {
					return new Coord(i, j);
				}
			}
		}
		return null;
	}

}
