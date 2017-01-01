package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class RookTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("Rook", new Coord(3, 3), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(3, 3)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("Rook moves upward.", board.movePiece(new Coord(3, 3), new Coord(0, 3)));
		assertTrue("Rook moves downward.", board.movePiece(new Coord(0, 3), new Coord(7, 3)));
		assertTrue("Rook moves left.", board.movePiece(new Coord(7, 3), new Coord(7, 0)));
		assertTrue("Rook moves right.", board.movePiece(new Coord(7, 0), new Coord(7, 7)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("Rook cannot move upleft.", board.movePiece(new Coord(3, 3), new Coord(0, 0)));
		assertFalse("Rook cannot move upright.", board.movePiece(new Coord(3, 3), new Coord(0, 6)));
		assertFalse("Rook cannot move downleft.", board.movePiece(new Coord(3, 3), new Coord(6, 0)));
		assertFalse("Rook cannot move downright.", board.movePiece(new Coord(3, 3), new Coord(7, 7)));
		assertFalse("Just invalid.", board.movePiece(new Coord(3, 3), new Coord(7, 5)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Rook", new Coord(3, 4), WHITE);
		assertFalse("Blocked by ally on square.", board.movePiece(new Coord(3, 3), new Coord(3, 4)));
		assertFalse("Blocked by ally behind square.", board.movePiece(new Coord(3, 3), new Coord(3, 7)));
		board.setPiece("Rook", new Coord(5, 3), BLACK);
		assertFalse("Blocked by enemy behind square.", board.movePiece(new Coord(3, 3), new Coord(6, 3)));
	}

	@Test
	public void CaptureEnemy() {
		board.setPiece("Rook", new Coord(3, 5), BLACK);
		assertTrue("Capture Enemy.", board.movePiece(new Coord(3, 3), new Coord(3, 5)));
	}

}
