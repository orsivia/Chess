package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class CannonTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
		board.setPiece("Cannon", new Coord(3, 3), WHITE);
		assertTrue("setPiece failed.", board.getPiece(new Coord(3, 3)) != null);
	}

	@Test
	public void BasicMoves() {
		assertTrue("Cannon moves upward.", board.movePiece(new Coord(3, 3), new Coord(0, 3)));
		assertTrue("Cannon moves downward.", board.movePiece(new Coord(0, 3), new Coord(7, 3)));
		assertTrue("Cannon moves left.", board.movePiece(new Coord(7, 3), new Coord(7, 0)));
		assertTrue("Cannon moves right.", board.movePiece(new Coord(7, 0), new Coord(7, 7)));
	}

	@Test
	public void InvalidMoves() {
		assertFalse("Cannon cannot move upleft.", board.movePiece(new Coord(3, 3), new Coord(0, 0)));
		assertFalse("Cannon cannot move upright.", board.movePiece(new Coord(3, 3), new Coord(0, 6)));
		assertFalse("Cannon cannot move downleft.", board.movePiece(new Coord(3, 3), new Coord(6, 0)));
		assertFalse("Cannon cannot move downright.", board.movePiece(new Coord(3, 3), new Coord(7, 7)));
		assertFalse("Just invalid.", board.movePiece(new Coord(3, 3), new Coord(7, 5)));
		assertFalse("Out of board.", board.movePiece(new Coord(3, 3), new Coord(8, 8)));
	}

	@Test
	public void Blocked() {
		board.setPiece("Rook", new Coord(3, 5), WHITE);
		assertFalse("Cannon cannot capture ally.", board.movePiece(new Coord(3, 3), new Coord(3, 5)));
		assertFalse("Cannon is blocked by ally.", board.movePiece(new Coord(3, 3), new Coord(3, 6)));
		board.setPiece("Rook", new Coord(5, 3), BLACK);
		assertFalse("Cannon cannot capture enemy without stepping-stone.",
				board.movePiece(new Coord(3, 3), new Coord(5, 3)));
		assertFalse("Cannon is blocked by enemy.", board.movePiece(new Coord(3, 3), new Coord(6, 3)));
		board.setPiece("Rook", new Coord(6, 3), BLACK);
		board.setPiece("Rook", new Coord(7, 3), BLACK);
		assertFalse("Cannon has already attacked.", board.movePiece(new Coord(3, 3), new Coord(7, 3)));
	}

	@Test
	public void CaptureEnemy() {
		board.setPiece("Rook", new Coord(3, 5), WHITE);
		board.setPiece("Rook", new Coord(3, 6), BLACK);
		assertTrue("Capture enemy via ally stepping-stone.", board.movePiece(new Coord(3, 3), new Coord(3, 6)));
		board.setPiece("Rook", new Coord(4, 6), BLACK);
		board.setPiece("Rook", new Coord(6, 6), BLACK);
		assertTrue("Capture enemy via enemy stepping-stone.", board.movePiece(new Coord(3, 6), new Coord(6, 6)));
	}
}
