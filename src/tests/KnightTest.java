package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class KnightTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("Knight", new Coord(7, 0), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(7, 0)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("1 direction move", board.movePiece(new Coord(7, 0), new Coord(5, 1)));
		assertTrue("2 direction move", board.movePiece(new Coord(5, 1), new Coord(4, 3)));
		assertTrue("4 direction move", board.movePiece(new Coord(4, 3), new Coord(5, 5)));
		assertTrue("5 direction move", board.movePiece(new Coord(5, 5), new Coord(7, 6)));
		assertTrue("11 direction move", board.movePiece(new Coord(7, 6), new Coord(5, 5)));
		assertTrue("10 direction move", board.movePiece(new Coord(5, 5), new Coord(4, 3)));
		assertTrue("8 direction move", board.movePiece(new Coord(4, 3), new Coord(5, 1)));
		assertTrue("9 direction move", board.movePiece(new Coord(5, 1), new Coord(7, 0)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("Move vertically", board.movePiece(new Coord(7, 0), new Coord(5, 0)));
		assertFalse("Move horizontally", board.movePiece(new Coord(7, 0), new Coord(7, 2)));
		assertFalse("Just invalid", board.movePiece(new Coord(7, 0), new Coord(2, 6)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Queen", new Coord(5, 1), WHITE);
		assertFalse("1 direction blocked", board.movePiece(new Coord(7, 0), new Coord(5, 1)));
	}
	
	@Test
	public void Capture() {
		board.setPiece("Queen", new Coord(5, 1), BLACK);
		assertTrue("1 direction capture", board.movePiece(new Coord(7, 0), new Coord(5, 1)));
	}

}
