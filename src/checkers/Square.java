package checkers;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Square {
	
	public static final int SIZE = 60;
	private Piece p = null;
	private int x, y;
	private boolean isDark;
	
	public Square(int row, int col,boolean isDark) {
		x = col * SIZE;
		y = row * SIZE;
		this.isDark = isDark;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	public Piece getPiece() {
		return p;
	}

	public void addPiece(Piece p){
		this.p = p;
	}
	
	public void removePiece(){
		p = null;
	}

	public void draw(Graphics g) {
		// TODO: draw the square
	}	
}
