package pieces;
import java.util.ArrayList;
import models.Coord;

/**
 * @author yizx
 * White player at bottom, black player at top
 * First move with possible two blocks forward
 */

public class Pawn extends Piece{
	private boolean firstMove;
	
	/**
	 * Constructor
	 */
	public Pawn(){
		firstMove = true;
		super.name = "Pawn";
	}
	
	public boolean getFirstMove(){
		return firstMove;
	}
	
	public void setFirstMove(boolean b){
		firstMove = b;
	}
	
	/*
	 * (non-Javadoc)
	 * @see pieces.Piece#updateValidMoves(pieces.Piece[][], models.Coord)
	 */
	public void updateValidMoves(Piece[][] board, Coord pos){
		int direction = super.getColor();
		//empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());
		
		moveForward(board, pos, direction);
		captureDiag(board, pos, direction);
	}
	
	/**
	 * @param board
	 * @param curPos - current position of the pawn
	 * @param dir - A multiplier. White pawns move upward(-1); black pawns move downward(+1)
	 * Helper function, move a pawn forward
	 * NOTICE that a pawn can only perform this move when its way is clear
	 */
	private void moveForward(Piece[][] board, Coord curPos, int dir){
		int row = curPos.getRow();
		int col = curPos.getCol();
		if(firstMove && !super.outOfBoard(board, row+2*dir, col)){
			if(board[row+dir*2][col]==null && board[row+dir][col]==null){
				super.addToValidMoves(board[row+dir*2][col], new Coord(row+dir*2, col));
			}
			//firstMove = false;
		}
		if(!super.outOfBoard(board, row+dir, col)){
			if(board[row+dir][col]==null)
				super.addToValidMoves(board[row+dir][col], new Coord(row+dir, col));
		}
	}

	/**
	 * @param board
	 * @param curPos - current position of the pawn
	 * @param dir - A multiplier. White pawns move upward(-1); black pawns move downward(+1)
	 * Helper function, move a pawn to capture enemy piece on diagonal
	 * NOTICE that a pawn can only perform this move when there is a piece on diagonal
	 */
	private void captureDiag(Piece[][] board, Coord curPos, int dir){
		int row = curPos.getRow();
		int col = curPos.getCol();
		if(!super.outOfBoard(board, row+dir, col-1) && board[row+dir][col-1]!=null)
			super.addToValidMoves(board[row+dir][col-1], new Coord(row+dir, col-1));
		if(!super.outOfBoard(board, row+dir, col+1) && board[row+dir][col+1]!=null)
			super.addToValidMoves(board[row+dir][col+1], new Coord(row+dir, col+1));
	}
}
