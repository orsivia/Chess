package tests;

import java.util.Scanner;
import models.Board;
import models.Coord;

public class UserTest {
	final static int WHITE = 1;
	final static int BLACK = -1;

	public static void main(String[] args) {
		Board board = new Board();

		System.out.println("Board created. Please set piece using format 'PieceName,row,column,color'");
		System.out.println("Type 'finish' to finish setup.");

		Scanner scan = new Scanner(System.in);
		while (true) {
			String pieceInfo = scan.next();
			if (pieceInfo.equals("finish")) {
				break;
			}
			String[] parsedInfo = pieceInfo.split(",");
			while (parsedInfo.length != 4) {
				System.out.println("Wrong input format. Please re-enter.");
				pieceInfo = scan.next();
				parsedInfo = pieceInfo.split(",");
			}
			String pieceName = parsedInfo[0];
			String pieceRow = parsedInfo[1];
			String pieceCol = parsedInfo[2];
			String pieceColor = parsedInfo[3];
			int color = WHITE;
			if (pieceColor.equals("white")) {
				color = WHITE;
			} else {
				color = BLACK;
			}
			board.setPiece(pieceName, new Coord(Integer.parseInt(pieceRow), Integer.parseInt(pieceCol)), color);
		}

		System.out.println("Set move using format 'startRow,startCol,targetRow,targetCol'");
		System.out.println("Type 'quit' to end simulation.");

		while (true) {
			String moveInfo = scan.next();
			if (moveInfo.equals("quit")) {
				break;
			}
			String[] parsedMove = moveInfo.split(",");
			while (parsedMove.length != 4) {
				System.out.println("Wrong input format. Please re-enter.");
				moveInfo = scan.next();
				parsedMove = moveInfo.split(",");
			}
			int startRow = Integer.parseInt(parsedMove[0]);
			int startCol = Integer.parseInt(parsedMove[1]);
			int targetRow = Integer.parseInt(parsedMove[2]);
			int targetCol = Integer.parseInt(parsedMove[3]);
			if (!board.movePiece(new Coord(startRow, startCol), new Coord(targetRow, targetCol))) {
				System.out.println("Invalid move!");
			} else {
				System.out.println("Move successfully.");
			}
		}
		scan.close();
	}

}
