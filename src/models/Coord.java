package models;

public class Coord {
	private int row;
	private int col;
		
	public Coord(int r, int c){
		this.row = r;
		this.col = c;
	}
	
	public int getRow(){
		return this.row;
	}
	
	public int getCol(){
		return this.col;
	}
	
	public void setRow(int r){
		this.row = r;
	}
	
	public void setCol(int c){
		this.col = c;
	}
	
	public void setRC(int r, int c){
		this.setRow(r);
		this.setCol(c);
	}
	
	public void dup(Coord other){
		this.row = other.row;
		this.col = other.col;
	}
	
	public boolean equal(Coord other){
		return this.row==other.row && this.col==other.col;
	}
}
