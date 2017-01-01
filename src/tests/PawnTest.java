package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.Before;

import models.Board;
import models.Coord;

public class PawnTest {
	final static int WHITE = 1;
	final static int BLACK = -1;
	Board board;
	
	@Before
	public void init() throws Exception{
		board = new Board();
		board.setPiece("Pawn", new Coord(6,1), BLACK);
	}
	
	@Test
	public void MoveOneForward(){
		assertTrue("New pawn has valid moves.", board.getPiece(new Coord(6,1)).getValidMoves()!=null);
		assertTrue("Pawn moves 1 square forward", board.movePiece(new Coord(6,1), new Coord(5,1)));
	}
	
	@Test
	public void FirstMoveTwoForward(){
		assertTrue("Pawn moves 2 squares forward for its first move.", board.movePiece(new Coord(6,1), new Coord(4,1)));
	}
	
	@Test
	public void NotFirstMoveTwoForward(){
		board.movePiece(new Coord(6,1), new Coord(4,1));
		assertFalse("Pawn cannot move 2 squares forward after its first move.", board.movePiece(new Coord(4,1), new Coord(2,1)));
	}
	
	@Test
	public void BlockedMove(){
		board.setPiece("Queen", new Coord(5,1), WHITE);
		assertFalse("Pawn cannot move 2 blocks forward since it is being blocked.", board.movePiece(new Coord(6,1), new Coord(4,1)));
		assertFalse("Pawn cannot move 1 block forward since it is being blocked.", board.movePiece(new Coord(6,1), new Coord(5,1)));
	}
	
	@Test
	public void CaptureDiagWhenPresent(){
		board.setPiece("Queen", new Coord(5,0), WHITE);
		assertTrue("Pawn captures enemy on diagonal(L).", board.movePiece(new Coord(6,1), new Coord(5,0)));
		board.setPiece("Queen", new Coord(4,1), WHITE);
		assertTrue("Pawn captures enemy on diagonal(R).", board.movePiece(new Coord(5,0), new Coord(4,1)));
	}
	
	@Test
	public void CaptureDiagWhenAlly(){
		board.setPiece("Queen", new Coord(5,0), BLACK);
		board.setPiece("Queen", new Coord(5,2), BLACK);
		assertFalse("Pawn cannot capture ally on diagonal(L).", board.movePiece(new Coord(6,1), new Coord(5,0)));
		assertFalse("Pawn cannot capture ally on diagonal(R).", board.movePiece(new Coord(6,1), new Coord(5,2)));
	}
	
	@Test
	public void CaptureDiagWhenEmpty(){
		assertFalse("Pawn cannot capture on diagonal when empty(L).", board.movePiece(new Coord(6,1), new Coord(5,0)));
		assertFalse("Pawn cannot capture on diagonal when empty(R).", board.movePiece(new Coord(6,1), new Coord(5,2)));
	}
	
	@Test
	public void MoveBackward(){
		assertFalse("Pawn cannot move backward.", board.movePiece(new Coord(6,1), new Coord(7,1)));
	}
	
	@Test
	public void MoveHorizontally(){
		assertFalse("Pawn cannot move horizontally(L).", board.movePiece(new Coord(6,1), new Coord(6,0)));
		assertFalse("Pawn cannot move horizontally(R).", board.movePiece(new Coord(6,1), new Coord(6,2)));
	}
}
