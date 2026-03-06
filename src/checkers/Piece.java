package checkers;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Piece {

	public static final int BLACK = 0;
	public static final int RED = 1;

	private int color;
	private Image image = null;
	private boolean isCrown = false;

	public Piece(int color) {
		// TODO display image of correct piece in the square 
	}	

	public int getColor() {
		return color;
	}
	
	public boolean isCrown() {
		return isCrown;
	}

	public void draw(Graphics g, int x, int y, int height, int width) {
		// TODO draw the image in the square
	}
	
	public void makeCrown() {
		// TODO display image of the correct king piece in the square
	}
}
