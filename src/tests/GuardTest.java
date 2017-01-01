package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class GuardTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("Guard", new Coord(3, 3), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(3, 3)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("Guard moves upward", board.movePiece(new Coord(3, 3), new Coord(0, 3)));
		assertTrue("Guard moves downward", board.movePiece(new Coord(0, 3), new Coord(3, 3)));
		assertTrue("Guard moves left", board.movePiece(new Coord(3, 3), new Coord(3, 0)));
		assertTrue("Guard moves right", board.movePiece(new Coord(3, 0), new Coord(3, 3)));
		assertTrue("Guard moves upleft", board.movePiece(new Coord(3, 3), new Coord(0, 0)));
		assertTrue("Guard moves downright", board.movePiece(new Coord(0, 0), new Coord(3, 3)));
		assertTrue("Guard moves upright", board.movePiece(new Coord(3, 3), new Coord(0, 6)));
		assertTrue("Guard moves downleft", board.movePiece(new Coord(0, 6), new Coord(3, 3)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("Just invalid #1", board.movePiece(new Coord(3, 3), new Coord(7, 6)));
		assertFalse("Just invalid #2", board.movePiece(new Coord(3, 3), new Coord(5, 4)));
		assertFalse("Just invalid #3", board.movePiece(new Coord(3, 3), new Coord(2, 1)));
		assertFalse("Out of board.", board.movePiece(new Coord(3, 3), new Coord(8, 8)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Rook", new Coord(3, 5), WHITE);
		assertFalse("Blocked by ally on square", board.movePiece(new Coord(3, 3), new Coord(3, 5)));
		assertFalse("Blocked by ally behind square", board.movePiece(new Coord(3, 3), new Coord(3, 6)));
		board.setPiece("Rook", new Coord(6, 6), BLACK);
		assertFalse("Blocked by enemy on square, out of range", board.movePiece(new Coord(3, 3), new Coord(6, 6)));
		assertFalse("Blocked by enemy behind square", board.movePiece(new Coord(3, 3), new Coord(7, 7)));
	}

	public void CaptureEnemy() {
		board.setPiece("Rook", new Coord(3, 7), BLACK);
		assertFalse("Out of range.", board.movePiece(new Coord(3, 3), new Coord(3, 7)));
		board.movePiece(new Coord(3, 3), new Coord(3, 5));
		assertTrue("Capture enemy in range.", board.movePiece(new Coord(3, 5), new Coord(3, 7)));
	}
}
