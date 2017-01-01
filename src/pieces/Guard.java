package pieces;

import java.util.ArrayList;
import models.Coord;

/*
 * Guard can move like a Queen
 * but attack like a King
 */

public class Guard extends Piece {
	/**
	 * Constructor
	 */
	public Guard(){
		super.name = "Guard";
	}
	
	@Override
	public void updateValidMoves(Piece[][] board, Coord pos) {
		int row = pos.getRow();
		int col = pos.getCol();
		// empty old valid moves
		super.setValidMoves(new ArrayList<Coord>());

		int dist = 1;
		boolean endU = false, endD = false, endL = false, endR = false, endUL = false, endUR = false, endDL = false,
				endDR = false;
		while (!endU || !endD || !endL || !endR || !endUL || !endUR || !endDL || !endDR) {
			// capture range is 2
			if (dist <= 2) {
				// Down
				if (!super.outOfBoard(board, row + dist, col) && !endD) {
					if (super.addToValidMoves(board[row + dist][col], new Coord(row + dist, col)) != 0) {
						endD = true;
					}
				} else {
					endD = true;
				}
				// Up
				if (!super.outOfBoard(board, row - dist, col) && !endU) {
					if (super.addToValidMoves(board[row - dist][col], new Coord(row - dist, col)) != 0) {
						endU = true;
					}
				} else {
					endU = true;
				}
				// Right
				if (!super.outOfBoard(board, row, col + dist) && !endR) {
					if (super.addToValidMoves(board[row][col + dist], new Coord(row, col + dist)) != 0) {
						endR = true;
					}
				} else {
					endR = true;
				}
				// Left
				if (!super.outOfBoard(board, row, col - dist) && !endL) {
					if (super.addToValidMoves(board[row][col - dist], new Coord(row, col - dist)) != 0) {
						endL = true;
					}
				} else {
					endL = true;
				}
				// DownRight
				if (!super.outOfBoard(board, row + dist, col + dist) && !endDR) {
					if (super.addToValidMoves(board[row + dist][col + dist], new Coord(row + dist, col + dist)) != 0) {
						endDR = true;
					}
				} else {
					endDR = true;
				}
				// DownLeft
				if (!super.outOfBoard(board, row + dist, col - dist) && !endDL) {
					if (super.addToValidMoves(board[row + dist][col - dist], new Coord(row + dist, col - dist)) != 0) {
						endDL = true;
					}
				} else {
					endDL = true;
				}
				// UpRight
				if (!super.outOfBoard(board, row - dist, col + dist) && !endUR) {
					if (super.addToValidMoves(board[row - dist][col + dist], new Coord(row - dist, col + dist)) != 0) {
						endUR = true;
					}
				} else {
					endUR = true;
				}
				// UpLeft
				if (!super.outOfBoard(board, row - dist, col - dist) && !endUL) {
					if (super.addToValidMoves(board[row - dist][col - dist], new Coord(row - dist, col - dist)) != 0) {
						endUL = true;
					}
				} else {
					endUL = true;
				}
			} else {
				// Down
				if (!super.outOfBoard(board, row + dist, col) && !endD) {
					if (board[row + dist][col] == null) {
						super.addToValidMoves(board[row + dist][col], new Coord(row + dist, col));
					} else {
						endD = true;
					}
				} else {
					endD = true;
				}
				// Up
				if (!super.outOfBoard(board, row - dist, col) && !endU) {
					if (board[row - dist][col] == null) {
						super.addToValidMoves(board[row - dist][col], new Coord(row - dist, col));
					} else {
						endU = true;
					}
				} else {
					endU = true;
				}
				// Right
				if (!super.outOfBoard(board, row, col + dist) && !endR) {
					if (board[row][col + dist] == null) {
						super.addToValidMoves(board[row][col + dist], new Coord(row, col + dist));
					} else {
						endR = true;
					}
				} else {
					endR = true;
				}
				// Left
				if (!super.outOfBoard(board, row, col - dist) && !endL) {
					if (board[row][col - dist] == null) {
						super.addToValidMoves(board[row][col - dist], new Coord(row, col - dist));
					} else {
						endL = true;
					}
				} else {
					endL = true;
				}
				// DownRight
				if (!super.outOfBoard(board, row + dist, col + dist) && !endDR) {
					if (board[row + dist][col + dist] == null) {
						super.addToValidMoves(board[row + dist][col + dist], new Coord(row + dist, col + dist));
					} else {
						endDR = true;
					}
				} else {
					endDR = true;
				}
				// DownLeft
				if (!super.outOfBoard(board, row + dist, col - dist) && !endDL) {
					if (board[row + dist][col - dist] == null) {
						super.addToValidMoves(board[row + dist][col - dist], new Coord(row + dist, col - dist));
					} else {
						endDL = true;
					}
				} else {
					endDL = true;
				}
				// UpRight
				if (!super.outOfBoard(board, row - dist, col + dist) && !endUR) {
					if (board[row - dist][col + dist] == null) {
						super.addToValidMoves(board[row - dist][col + dist], new Coord(row - dist, col + dist));
					} else {
						endUR = true;
					}
				} else {
					endUR = true;
				}
				// UpLeft
				if (!super.outOfBoard(board, row - dist, col - dist) && !endUL) {
					if (board[row - dist][col - dist] == null) {
						super.addToValidMoves(board[row - dist][col - dist], new Coord(row - dist, col - dist));
					} else {
						endUL = true;
					}
				} else {
					endUL = true;
				}
			}
			dist++;
		}

	}

}
