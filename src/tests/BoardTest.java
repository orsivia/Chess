package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class BoardTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;

	@Before
	public void init() {
		board = new Board();
	}

	@Test
	public void InCheck() {
		board.setPiece("Rook", new Coord(0, 0), BLACK);
		board.setPiece("King", new Coord(0, 1), WHITE);
		assertTrue("White is in check!", board.check(WHITE));
	}

	@Test
	public void NotInCheckAlly() {
		board.setPiece("Bishop", new Coord(0, 0), WHITE);
		board.setPiece("King", new Coord(1, 1), WHITE);
		assertFalse("White is not in check!", board.check(WHITE));
	}

	@Test
	public void NotInCheckEnemy() {
		board.setPiece("Bishop", new Coord(0, 0), BLACK);
		board.setPiece("King", new Coord(0, 1), WHITE);
		assertFalse("White is not in check!", board.check(WHITE));
	}

	@Test
	public void InCheckmate() {
		board.setPiece("Rook", new Coord(0, 0), BLACK);
		board.setPiece("Rook", new Coord(1, 7), BLACK);
		board.setPiece("King", new Coord(0, 3), WHITE);
		assertTrue("White is in checkmate!", board.checkmate(WHITE));
	}

	@Test
	public void InStalemateNotOnlyKingLeft() {
		board.setPiece("King", new Coord(7, 3), WHITE);
		board.setPiece("Knight", new Coord(6, 3), WHITE);
		board.setPiece("Rook", new Coord(0, 2), BLACK);
		board.setPiece("Rook", new Coord(0, 4), BLACK);
		board.setPiece("Queen", new Coord(0, 3), BLACK);
		assertTrue("White is in stalemate!", board.stalemate(WHITE));
	}

	@Test
	public void InStalemateOnlyKingLeft() {
		board.setPiece("King", new Coord(0, 7), WHITE);
		board.setPiece("King", new Coord(1, 5), BLACK);
		board.setPiece("Queen", new Coord(2, 6), BLACK);
		assertTrue("White is in stalemate!", board.stalemate(WHITE));
	}

	@Test
	public void NotInCheckmateMoveKing() {
		board.setPiece("Rook", new Coord(1, 0), BLACK);
		board.setPiece("King", new Coord(0, 3), WHITE);
		assertFalse("White is not in checkmate!", board.checkmate(WHITE));
	}

	@Test
	public void NotInCheckmateMoveOther() {
		board.setPiece("Rook", new Coord(0, 0), BLACK);
		board.setPiece("Rook", new Coord(1, 7), BLACK);
		board.setPiece("King", new Coord(0, 3), WHITE);
		board.setPiece("Rook", new Coord(7, 1), WHITE);
		assertFalse("White is not in checkmate!", board.checkmate(WHITE));
	}

	@Test
	public void NotInStalemateKingCannotMove() {
		board.setPiece("King", new Coord(7, 7), WHITE);
		board.setPiece("Pawn", new Coord(7, 6), WHITE);
		board.setPiece("Pawn", new Coord(6, 6), WHITE);
		board.setPiece("Pawn", new Coord(6, 7), WHITE);
		assertFalse("White is not in stalemate!", board.stalemate(WHITE));
	}

	@Test
	public void NotInSatlemateKingCanMove() {
		board.setPiece("King", new Coord(0, 0), WHITE);
		board.setPiece("King", new Coord(7, 7), BLACK);
		assertFalse("White is not in stalemate!", board.stalemate(WHITE));
	}

	@Test
	public void CannotMoveKingCheck() {
		board.setPiece("King", new Coord(0, 0), WHITE);
		board.setPiece("Rook", new Coord(7, 1), BLACK);
		assertFalse("Cannot move King to that square because of check",
				board.movePiece(new Coord(0, 0), new Coord(0, 1)));
	}

	@Test
	public void CannotMoveIrrelevant() {
		board.setPiece("King", new Coord(0, 0), WHITE);
		board.setPiece("Rook", new Coord(7, 0), BLACK);
		board.setPiece("Pawn", new Coord(3, 3), WHITE);
		assertFalse("Have to move King", board.movePiece(new Coord(3, 3), new Coord(2, 3)));
	}
}
