package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class BishopTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("Bishop", new Coord(6, 1), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(6, 1)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("Bishop moves upleft.", board.movePiece(new Coord(6, 1), new Coord(4, 3)));
		assertTrue("Bishop moves upright.", board.movePiece(new Coord(4, 3), new Coord(1, 0)));
		assertTrue("Bishop moves downright.", board.movePiece(new Coord(1, 0), new Coord(4, 3)));
		assertTrue("Bishop moves downleft.", board.movePiece(new Coord(4, 3), new Coord(7, 0)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("Bishop cannot move upward.", board.movePiece(new Coord(6, 1), new Coord(3, 1)));
		assertFalse("Bishop cannot move downward.", board.movePiece(new Coord(6, 1), new Coord(7, 1)));
		assertFalse("Bishop cannot move left.", board.movePiece(new Coord(6, 1), new Coord(6, 0)));
		assertFalse("Bishop cannot move right.", board.movePiece(new Coord(6, 1), new Coord(6, 6)));
		assertFalse("Just invalid.", board.movePiece(new Coord(6, 1), new Coord(3, 7)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Queen", new Coord(3, 4), WHITE);
		assertFalse("Blocked by ally on square.", board.movePiece(new Coord(6, 1), new Coord(3, 4)));
		assertFalse("Blocked by ally behind square.", board.movePiece(new Coord(6, 1), new Coord(2, 5)));
		board.setPiece("Queen", new Coord(5, 2), BLACK);
		assertFalse("Blocked by enemy behind square.", board.movePiece(new Coord(6, 1), new Coord(4, 3)));
	}

	@Test
	public void Capture() {
		board.setPiece("Queen", new Coord(4, 3), BLACK);
		assertTrue("Capture enemy.", board.movePiece(new Coord(6, 1), new Coord(4, 3)));
	}
}
