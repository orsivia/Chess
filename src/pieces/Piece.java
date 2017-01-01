package pieces;

import java.util.ArrayList;
import models.Coord;

public abstract class Piece {
	final static int WHITE = 1;
	final static int BLACK = -1;

	protected int color;
	protected String name;
	protected ArrayList<Coord> validMoves;

	/**
	 * each unique piece need to implement this method
	 * @param board
	 * @param pos
	 */
	abstract public void updateValidMoves(Piece[][] board, Coord pos);

	/**
	 * go through the piece's valid moves array
	 * valid if target position is in the array
	 * @param target
	 * @return true if the move is valid
	 */
	public boolean isValidMove(Coord target) {
		if (this.validMoves == null)
			return false;
		for (int i = 0; i < this.validMoves.size(); i++) {
			if (target.equal(this.validMoves.get(i)))
				return true;
		}
		return false;
	}

	/**
	 * add a valid move to the piece's valid moves array
	 * need extra prerequisites for pawns & custom pieces
	 * @param atTarget
	 * @param target
	 * @return 0 if target position is empty, 1 if enemy, -1 if ally
	 */
	protected int addToValidMoves(Piece atTarget, Coord target) {
		if (atTarget == null) {
			this.validMoves.add(target);
			return 0;
		} else if (!this.samePlayer(atTarget)) {
			this.validMoves.add(target);
			return 1;
		}
		return -1;
	}
	
	/**
	 * @return color of the piece
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * set piece's color
	 * @param color
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * @return piece's valid moves array
	 */
	public ArrayList<Coord> getValidMoves() {
		return this.validMoves;
	}
	
	/**
	 * @return piece's name
	 */
	public String getName(){
		return this.name;
	}

	/**
	 * used to reset/copy piece's valid moves array
	 * @param newValidMoves
	 */
	protected void setValidMoves(ArrayList<Coord> newValidMoves) {
		this.validMoves = newValidMoves;
	}

	/**
	 * @param other
	 * @return true if two pieces have same color
	 */
	public boolean samePlayer(Piece other) {
		if (other == null) {
			return false;
		}
		return this.color == other.color;
	}

	/**
	 * toString function for setting pieces
	 * @param name
	 * @param color
	 * @return newly created Piece object
	 */
	public static Piece stringToPiece(String name, int color) {
		Piece newPiece = null;
		if (name.equals("King"))
			newPiece = new King();
		if (name.equals("Queen"))
			newPiece = new Queen();
		if (name.equals("Rook"))
			newPiece = new Rook();
		if (name.equals("Bishop"))
			newPiece = new Bishop();
		if (name.equals("Knight"))
			newPiece = new Knight();
		if (name.equals("Pawn"))
			newPiece = new Pawn();
		if (name.equals("Cannon"))
			newPiece = new Cannon();
		if (name.equals("Guard"))
			newPiece = new Guard();

		if (newPiece == null)
			return null;
		newPiece.setColor(color);
		return newPiece;
	}

	/**
	 * @param board
	 * @param row
	 * @param col
	 * @return true if the position is out of board(invalid)
	 */
	public static boolean outOfBoard(Piece[][] board, int row, int col) {
		return row < 0 || col < 0 || row >= board.length || col >= board[0].length;
	}

	/**
	 * used by board condition check
	 * target position cannot be null
	 * @param target
	 * @return true if piece at target position can be captured
	 */
	public boolean canCapture(Coord target) {
		for (int i = 0; i < this.validMoves.size(); i++) {
			if (this.validMoves.get(i).equal(target))
				return true;
		}
		return false;
	}
}
