package checkers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements MouseListener {
	private static final int numRowCol = 8;
	private Square[][] squares = new Square[numRowCol][numRowCol];
	private Square firstSelected = null;
	private Square secondSelected = null;
	private boolean isBlackTurn = true;

	public Board() {
		// TODO create squares and add pieces to each square to match the default
		// configuration of the
		// board from the image
		this.addMouseListener(this);
		setPreferredSize(new Dimension(Square.SIZE * numRowCol, Square.SIZE * numRowCol));
	}

	public void paintComponent(Graphics g) {
		// TODO draw each square
	}

	// TODO Select the first Square and then select the second square. Execute the
	// move IF IT IS VALID
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void capture() {
		// TODO execute capture 
		// Hint: to reuse code, you can call move within this method
	}

	public void move() {
		// TODO execute move
	}
	
	public void checkWin() {
		// TODO check if one of the players has won the game after a move is executed 
	}

}
