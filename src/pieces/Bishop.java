package pieces;
import java.util.ArrayList;
import models.Coord;

public class Bishop extends Piece{
	/**
	 * Constructor
	 */
	public Bishop(){
		super.name = "Bishop";
	}
	/*
	 * (non-Javadoc)
	 * @see pieces.Piece#updateValidMoves(pieces.Piece[][], models.Coord)
	 * 4 possible directions for a Bishop(UpLeft, UpRight, DownLeft, DownRight)
	 * Stop checking when meeting an obstacle or out of board
	 */
	public void updateValidMoves(Piece[][] board, Coord pos){
		int row = pos.getRow();
		int col = pos.getCol();
		//empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());
		
		int diagDist = 1;
		boolean endUL = false, endUR = false, endDL = false, endDR = false;
		while(!endUL || !endUR || !endDL || !endDR){
			if(!super.outOfBoard(board, row+diagDist, col+diagDist) && !endDR){
				if(super.addToValidMoves(board[row+diagDist][col+diagDist], new Coord(row+diagDist, col+diagDist))!=0){
					endDR = true;
				}
			}
			else{
				endDR = true;
			}
			if(!super.outOfBoard(board, row+diagDist, col-diagDist) && !endDL){
				if(super.addToValidMoves(board[row+diagDist][col-diagDist], new Coord(row+diagDist, col-diagDist))!=0){
					endDL = true;
				}
			}
			else{
				endDL = true;
			}
			if(!super.outOfBoard(board, row-diagDist, col+diagDist) && !endUR){
				if(super.addToValidMoves(board[row-diagDist][col+diagDist], new Coord(row-diagDist, col+diagDist))!=0){
					endUR = true;
				}
			}
			else{
				endUR = true;
			}
			if(!super.outOfBoard(board, row-diagDist, col-diagDist) && !endUL){
				if(super.addToValidMoves(board[row-diagDist][col-diagDist], new Coord(row-diagDist, col-diagDist))!=0){
					endUL = true;
				}
			}
			else{
				endUL = true;
			}
			diagDist++;
		}
	}
}
