package pieces;
import java.util.ArrayList;
import models.Coord;

public class Knight extends Piece{
	/*
	 * Constructor
	 */
	public Knight(){
		super.name = "Knight";
	}
	
	/*
	 * (non-Javadoc)
	 * @see pieces.Piece#updateValidMoves(pieces.Piece[][], models.Coord)
	 * 8 possible moves for a Knight
	 */
	public void updateValidMoves(Piece[][] board, Coord pos){
		int row = pos.getRow();
		int col = pos.getCol();
		//empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());
		
		if(!super.outOfBoard(board, row+2, col+1))
			super.addToValidMoves(board[row+2][col+1], new Coord(row+2, col+1));
		if(!super.outOfBoard(board, row+2, col-1))
			super.addToValidMoves(board[row+2][col-1], new Coord(row+2, col-1));
		if(!super.outOfBoard(board, row+1, col+2))
			super.addToValidMoves(board[row+1][col+2], new Coord(row+1, col+2));
		if(!super.outOfBoard(board, row+1, col-2))
			super.addToValidMoves(board[row+1][col-2], new Coord(row+1, col-2));
		if(!super.outOfBoard(board, row-1, col+2))
			super.addToValidMoves(board[row-1][col+2], new Coord(row-1, col+2));
		if(!super.outOfBoard(board, row-1, col-2))
			super.addToValidMoves(board[row-1][col-2], new Coord(row-1, col-2));
		if(!super.outOfBoard(board, row-2, col+1))
			super.addToValidMoves(board[row-2][col+1], new Coord(row-2, col+1));
		if(!super.outOfBoard(board, row-2, col-1))
			super.addToValidMoves(board[row-2][col-1], new Coord(row-2, col-1));
	}
}
