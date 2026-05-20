package checkers;

import java.awt.*;

public class Square {

    public static final int SIZE = 60;

    private Piece   p      = null;
    private int     x, y;
    private boolean isDark;
    private boolean isSelected = false;

    public Square(int row, int col, boolean isDark) {
        x = col * SIZE;
        y = row * SIZE;
        this.isDark = isDark;
    }

    // ── Accessors ──────────────────────────────────────────────────────────

    public int     getX()        { return x; }
    public int     getY()        { return y; }
    public Piece   getPiece()    { return p; }
    public boolean isDark()      { return isDark; }
    public boolean isSelected()  { return isSelected; }
    public boolean hasPiece()    { return p != null; }

    public void addPiece(Piece p)    { this.p = p; }
    public void removePiece()        { p = null; }
    public void setSelected(boolean b) { isSelected = b; }

    // ── Drawing ────────────────────────────────────────────────────────────

    public void draw(Graphics g) {
        // Board square
        g.setColor(isDark ? new Color(0x769656) : new Color(0xEEEED2));
        g.fillRect(x, y, SIZE, SIZE);

        // Selection highlight
        if (isSelected) {
            g.setColor(new Color(246, 246, 105, 180));
            g.fillRect(x, y, SIZE, SIZE);
        }

        // Piece
        if (p != null) p.draw(g, x, y, SIZE, SIZE);
    }
}
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
