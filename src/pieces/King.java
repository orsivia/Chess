package pieces;
import java.util.ArrayList;

import models.Coord;

public class King extends Piece{
	/**
	 * Constructor
	 */
	public King(){
		super.name = "King";
	}
	
	/*
	 * (non-Javadoc)
	 * @see pieces.Piece#updateValidMoves(pieces.Piece[][], models.Coord)
	 * 8 possible moves for a King
	 */
	public void updateValidMoves(Piece[][] board, Coord pos){
		int row = pos.getRow();
		int col = pos.getCol();
		//empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());
		
		if(!super.outOfBoard(board, row+1, col+1))
			super.addToValidMoves(board[row+1][col+1], new Coord(row+1, col+1));
		if(!super.outOfBoard(board, row+1, col))
			super.addToValidMoves(board[row+1][col], new Coord(row+1, col));
		if(!super.outOfBoard(board, row+1, col-1))
			super.addToValidMoves(board[row+1][col-1], new Coord(row+1, col-1));
		if(!super.outOfBoard(board, row, col-1))
			super.addToValidMoves(board[row][col-1], new Coord(row, col-1));
		if(!super.outOfBoard(board, row-1, col-1))
			super.addToValidMoves(board[row-1][col-1], new Coord(row-1, col-1));
		if(!super.outOfBoard(board, row-1, col))
			super.addToValidMoves(board[row-1][col], new Coord(row-1, col));
		if(!super.outOfBoard(board, row-1, col+1))
			super.addToValidMoves(board[row-1][col+1], new Coord(row-1, col+1));
		if(!super.outOfBoard(board, row, col+1))
			super.addToValidMoves(board[row][col+1], new Coord(row, col+1));
	}
}
