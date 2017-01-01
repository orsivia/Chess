package pieces;
import java.util.ArrayList;
import models.Coord;

public class Queen extends Piece{
	/**
	 * Constructor
	 */
	public Queen(){
		super.name = "Queen";
	}
	
	/*
	 * (non-Javadoc)
	 * @see pieces.Piece#updateValidMoves(pieces.Piece[][], models.Coord)
	 * A Queen combines the possible moves of a Bishop and a Rook
	 * Simulate a Bishop and a Rook and gather their valid moves
	 */
	public void updateValidMoves(Piece[][] board, Coord pos){
		//empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());
		
		//Simulate a Bishop
		Piece bishop = new Bishop();
		bishop.setColor(super.color);
		bishop.updateValidMoves(board, pos);
		
		//Simulate a Rook
		Piece rook = new Rook();
		rook.setColor(super.color);
		rook.updateValidMoves(board, pos);
		
		//Gathering
		for(int i=0; i<bishop.getValidMoves().size(); i++){
			super.getValidMoves().add(bishop.getValidMoves().get(i));
		}
		for(int i=0; i<rook.getValidMoves().size(); i++){
			super.getValidMoves().add(rook.getValidMoves().get(i));
		}
	}
}
