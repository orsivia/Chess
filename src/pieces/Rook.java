package pieces;
import java.util.ArrayList;
import models.Coord;

public class Rook extends Piece{
	/**
	 * Constructor
	 */
	public Rook(){
		super.name = "Rook";
	}
	
	/*
	 * (non-Javadoc)
	 * @see pieces.Piece#updateValidMoves(pieces.Piece[][], models.Coord)
	 * 4 possible directions for a Rook(Up, Down, Left, Right)
	 * Stop checking when meeting an obstacle or out of board
	 */
	public void updateValidMoves(Piece[][] board, Coord pos){
		int row = pos.getRow();
		int col = pos.getCol();
		//empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());
		
		int dist = 1;
		boolean endU = false, endD = false, endL = false, endR = false;
		while(!endU || !endD || !endL || !endR){
			if(!super.outOfBoard(board, row+dist, col) && !endD){
				if(super.addToValidMoves(board[row+dist][col], new Coord(row+dist, col))!=0){
					endD = true;
				}
			}
			else{
				endD = true;
			}
			if(!super.outOfBoard(board, row-dist, col) && !endU){
				if(super.addToValidMoves(board[row-dist][col], new Coord(row-dist, col))!=0){
					endU = true;
				}
			}
			else{
				endU = true;
			}
			if(!super.outOfBoard(board, row, col+dist) && !endR){
				if(super.addToValidMoves(board[row][col+dist], new Coord(row, col+dist))!=0){
					endR = true;
				}
			}
			else{
				endR = true;
			}
			if(!super.outOfBoard(board, row, col-dist) && !endL){
				if(super.addToValidMoves(board[row][col-dist], new Coord(row, col-dist))!=0){
					endL = true;
				}
			}
			else{
				endL = true;
			}

			dist++;
		}
	}
}
