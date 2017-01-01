package pieces;

import java.util.ArrayList;
import models.Coord;

/*
 * Cannon can only attack pieces behind one another
 * Cannot attack diagonally
 */

public class Cannon extends Piece {
	/**
	 * Constructor
	 */
	public Cannon(){
		super.name = "Cannon";
	}
	
	@Override
	public void updateValidMoves(Piece[][] board, Coord pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		// empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());

		int dist = 1;
		boolean endU = false, endD = false, endL = false, endR = false;
		boolean capOnlyU = false, capOnlyD = false, capOnlyL = false, capOnlyR = false;
		while (!endU || !endD || !endL || !endR) {
			// Down
			if (!super.outOfBoard(board, row + dist, col) && !endD) {
				if (capOnlyD) {
					if (board[row + dist][col] != null) {
						if (super.addToValidMoves(board[row + dist][col], new Coord(row + dist, col)) != 0 && !endD) {
							endD = true;
						}
					}
				} else {
					if (board[row + dist][col] == null) {
						super.addToValidMoves(null, new Coord(row + dist, col));
					} else {
						capOnlyD = true;
					}
				}
			} else {
				endD = true;
			}
			// Up
			if (!super.outOfBoard(board, row - dist, col) && !endU) {
				if (capOnlyU) {
					if (board[row - dist][col] != null) {
						if (super.addToValidMoves(board[row - dist][col], new Coord(row - dist, col)) != 0 && !endU) {
							endU = true;
						}
					}
				} else {
					if (board[row - dist][col] == null) {
						super.addToValidMoves(null, new Coord(row - dist, col));
					} else {
						capOnlyU = true;
					}
				}
			} else {
				endU = true;
			}
			// Right
			if (!super.outOfBoard(board, row, col + dist) && !endR) {
				if (capOnlyR) {
					if (board[row][col + dist] != null) {
						if (super.addToValidMoves(board[row][col + dist], new Coord(row, col + dist)) != 0 && !endR) {
							endR = true;
						}
					}
				} else {
					if (board[row][col + dist] == null) {
						super.addToValidMoves(null, new Coord(row, col + dist));
					} else {
						capOnlyR = true;
					}
				}
			} else {
				endR = true;
			}
			// Left
			if (!super.outOfBoard(board, row, col - dist) && !endL) {
				if (capOnlyL) {
					if (board[row][col - dist] != null) {
						if (super.addToValidMoves(board[row][col - dist], new Coord(row, col - dist)) != 0 && !endL) {
							endL = true;
						}
					}
				} else {
					if (board[row][col - dist] == null) {
						super.addToValidMoves(null, new Coord(row, col - dist));
					} else {
						capOnlyL = true;
					}
				}
			} else {
				endL = true;
			}

			dist++;
		}

	}

}
