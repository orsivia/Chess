package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class QueenTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("Queen", new Coord(3, 3), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(3, 3)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("Queen moves upward", board.movePiece(new Coord(3, 3), new Coord(0, 3)));
		assertTrue("Queen moves downward", board.movePiece(new Coord(0, 3), new Coord(3, 3)));
		assertTrue("Queen moves left", board.movePiece(new Coord(3, 3), new Coord(3, 0)));
		assertTrue("Queen moves right", board.movePiece(new Coord(3, 0), new Coord(3, 3)));
		assertTrue("Queen moves upleft", board.movePiece(new Coord(3, 3), new Coord(0, 0)));
		assertTrue("Queen moves downright", board.movePiece(new Coord(0, 0), new Coord(3, 3)));
		assertTrue("Queen moves upright", board.movePiece(new Coord(3, 3), new Coord(0, 6)));
		assertTrue("Queen moves downleft", board.movePiece(new Coord(0, 6), new Coord(3, 3)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("Just invalid #1", board.movePiece(new Coord(3, 3), new Coord(7, 6)));
		assertFalse("Just invalid #2", board.movePiece(new Coord(3, 3), new Coord(5, 4)));
		assertFalse("Just invalid #3", board.movePiece(new Coord(3, 3), new Coord(2, 1)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Rook", new Coord(3, 5), WHITE);
		assertFalse("Blocked by ally on square", board.movePiece(new Coord(3, 3), new Coord(3, 5)));
		assertFalse("Blocked by ally behind square", board.movePiece(new Coord(3, 3), new Coord(3, 6)));
		board.setPiece("Rook", new Coord(2, 2), BLACK);
		assertFalse("Blocked by enemy behind square", board.movePiece(new Coord(3, 3), new Coord(0, 0)));
	}

	@Test
	public void Capture() {
		board.setPiece("Rook", new Coord(0, 0), BLACK);
		assertTrue("Capture enemy diagonally", board.movePiece(new Coord(3, 3), new Coord(0, 0)));
		board.setPiece("Rook", new Coord(7, 0), BLACK);
		assertTrue("Capture enemy vertically", board.movePiece(new Coord(0, 0), new Coord(7, 0)));
		board.setPiece("Rook", new Coord(7, 7), BLACK);
		assertTrue("Capture enemy horizontally", board.movePiece(new Coord(7, 0), new Coord(7, 7)));
	}
}
