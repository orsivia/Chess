package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class KingTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("King", new Coord(3, 3), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(3, 3)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("King moves upward", board.movePiece(new Coord(3, 3), new Coord(2, 3)));
		assertTrue("King moves downward", board.movePiece(new Coord(2, 3), new Coord(3, 3)));
		assertTrue("King moves left", board.movePiece(new Coord(3, 3), new Coord(3, 2)));
		assertTrue("King moves right", board.movePiece(new Coord(3, 2), new Coord(3, 3)));
		assertTrue("King moves upleft", board.movePiece(new Coord(3, 3), new Coord(2, 2)));
		assertTrue("King moves downright", board.movePiece(new Coord(2, 2), new Coord(3, 3)));
		assertTrue("King moves upright", board.movePiece(new Coord(3, 3), new Coord(2, 4)));
		assertTrue("King moves downleft", board.movePiece(new Coord(2, 4), new Coord(3, 3)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("King cannot move more than 1 square vertically",
				board.movePiece(new Coord(3, 3), new Coord(1, 3)));
		assertFalse("King cannot move more than 1 square horizontally",
				board.movePiece(new Coord(3, 3), new Coord(3, 5)));
		assertFalse("King cannot move more than 1 square diagonally",
				board.movePiece(new Coord(3, 3), new Coord(5, 5)));
		assertFalse("Just invalid", board.movePiece(new Coord(3, 3), new Coord(7, 5)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Queen", new Coord(3, 4), WHITE);
		assertFalse("Block by ally", board.movePiece(new Coord(3, 3), new Coord(3, 4)));
	}

	@Test
	public void Capture() {
		board.setPiece("Queen", new Coord(3, 4), BLACK);
		assertTrue("Capture enemy", board.movePiece(new Coord(3, 3), new Coord(3, 4)));
	}

}
