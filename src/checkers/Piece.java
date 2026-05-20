package checkers;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Piece {

    public static final int BLACK = 0;
    public static final int RED   = 1;

    private int    color;
    private Image  image;
    private boolean isCrown = false;

    public Piece(int color) {
        this.color = color;
        loadImage(color == BLACK ? "images/black.gif" : "images/red.gif");
    }

    private void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            image = null; // will fall back to painted circle
        }
    }

    public int     getColor() { return color; }
    public boolean isCrown()  { return isCrown; }

    public void draw(Graphics g, int x, int y, int width, int height) {
        if (image != null) {
            g.drawImage(image, x + 4, y + 4, width - 8, height - 8, null);
        } else {
            // Fallback: solid circle
            g.setColor(color == BLACK ? Color.DARK_GRAY : new Color(0xCC2200));
            g.fillOval(x + 6, y + 6, width - 12, height - 12);
            g.setColor(Color.BLACK);
            g.drawOval(x + 6, y + 6, width - 12, height - 12);
            if (isCrown) {
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Serif", Font.BOLD, 18));
                g.drawString("♛", x + width / 2 - 9, y + height / 2 + 7);
            }
        }
        if (isCrown) {
            // Gold crown border overlay
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0xFFD700, false));
            g2.setStroke(new BasicStroke(3f));
            g2.drawOval(x + 6, y + 6, width - 12, height - 12);
            g2.setStroke(new BasicStroke(1f));
        }
    }

    public void makeCrown() {
        if (isCrown) return;
        isCrown = true;
        String path = (color == BLACK) ? "images/blackcrown.gif" : "images/redcrown.png";
        loadImage(path);
    }
}
package checkers;

import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class Piece {

    public static final int BLACK = 0;
    public static final int RED   = 1;

    private int    color;
    private Image  image;
    private boolean isCrown = false;

    public Piece(int color) {
        this.color = color;
        loadImage(color == BLACK ? "images/black.gif" : "images/red.gif");
    }

    private void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            image = null; // will fall back to painted circle
        }
    }

    public int     getColor() { return color; }
    public boolean isCrown()  { return isCrown; }

    public void draw(Graphics g, int x, int y, int width, int height) {
        if (image != null) {
            g.drawImage(image, x + 4, y + 4, width - 8, height - 8, null);
        } else {
            // Fallback: solid circle
            g.setColor(color == BLACK ? Color.DARK_GRAY : new Color(0xCC2200));
            g.fillOval(x + 6, y + 6, width - 12, height - 12);
            g.setColor(Color.BLACK);
            g.drawOval(x + 6, y + 6, width - 12, height - 12);
            if (isCrown) {
                g.setColor(Color.YELLOW);
                g.setFont(new Font("Serif", Font.BOLD, 18));
                g.drawString("♛", x + width / 2 - 9, y + height / 2 + 7);
            }
        }
        if (isCrown) {
            // Gold crown border overlay
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(new Color(0xFFD700, false));
            g2.setStroke(new BasicStroke(3f));
            g2.drawOval(x + 6, y + 6, width - 12, height - 12);
            g2.setStroke(new BasicStroke(1f));
        }
    }

    public void makeCrown() {
        if (isCrown) return;
        isCrown = true;
        String path = (color == BLACK) ? "images/blackcrown.gif" : "images/redcrown.png";
        loadImage(path);
    }
}
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
