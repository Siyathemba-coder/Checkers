package checkers;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JPanel implements MouseListener {

    private static final int NUM = 8;

    private Square[][] squares = new Square[NUM][NUM];
    private Square firstSelected  = null;
    private Square secondSelected = null;
    private boolean isBlackTurn   = true;  // BLACK always goes first

    // Status message drawn at the bottom
    private String statusMsg = "Black's turn";

    public Board() {
        initSquares();
        initPieces();
        this.addMouseListener(this);
        setPreferredSize(new Dimension(Square.SIZE * NUM, Square.SIZE * NUM + 30));
    }

    // ── Board initialisation ───────────────────────────────────────────────

    private void initSquares() {
        for (int r = 0; r < NUM; r++)
            for (int c = 0; c < NUM; c++)
                squares[r][c] = new Square(r, c, (r + c) % 2 != 0);
    }

    /** Standard checkers starting position — 3 rows of pieces each side. */
    private void initPieces() {
        // Black pieces in rows 0-2 (top)
        for (int r = 0; r < 3; r++)
            for (int c = 0; c < NUM; c++)
                if ((r + c) % 2 != 0)
                    squares[r][c].addPiece(new Piece(Piece.BLACK));

        // Red pieces in rows 5-7 (bottom)
        for (int r = 5; r < NUM; r++)
            for (int c = 0; c < NUM; c++)
                if ((r + c) % 2 != 0)
                    squares[r][c].addPiece(new Piece(Piece.RED));
    }

    // ── Painting ───────────────────────────────────────────────────────────

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < NUM; r++)
            for (int c = 0; c < NUM; c++)
                squares[r][c].draw(g);

        // Highlight valid destination squares when a piece is selected
        if (firstSelected != null) {
            int[] rc = find(firstSelected);
            if (rc != null) drawMoveHints(g, rc[0], rc[1]);
        }

        // Status bar
        g.setColor(new Color(0x1A1A2E));
        g.fillRect(0, Square.SIZE * NUM, Square.SIZE * NUM, 30);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Georgia", Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();
        g.drawString(statusMsg,
                (Square.SIZE * NUM - fm.stringWidth(statusMsg)) / 2,
                Square.SIZE * NUM + 20);
    }

    private void drawMoveHints(Graphics g, int row, int col) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0x88FF88, false));
        Piece p = squares[row][col].getPiece();

        for (int[] move : getValidMoves(row, col, p.isCrown())) {
            int mx = move[1] * Square.SIZE + Square.SIZE / 2;
            int my = move[0] * Square.SIZE + Square.SIZE / 2;
            int r  = 10;
            g2.setColor(new Color(0x88FF88, false));
            g2.fillOval(mx - r, my - r, r * 2, r * 2);
        }
        for (int[] cap : getValidCaptures(row, col, p.isCrown())) {
            int mx = cap[1] * Square.SIZE + Square.SIZE / 2;
            int my = cap[0] * Square.SIZE + Square.SIZE / 2;
            int r  = 12;
            g2.setColor(new Color(0xFF4444, false));
            g2.fillOval(mx - r, my - r, r * 2, r * 2);
        }
    }

    // ── Mouse handling ─────────────────────────────────────────────────────

    @Override
    public void mouseClicked(MouseEvent e) {
        int col = e.getX() / Square.SIZE;
        int row = e.getY() / Square.SIZE;
        if (row < 0 || row >= NUM || col < 0 || col >= NUM) return;

        Square clicked = squares[row][col];

        if (firstSelected == null) {
            // First click: select a piece belonging to the current player
            Piece p = clicked.getPiece();
            if (p != null && isCurrentPlayerPiece(p)) {
                firstSelected = clicked;
                clicked.setSelected(true);
            }
        } else {
            // Second click
            if (clicked == firstSelected) {
                // Deselect
                clearSelection();
            } else if (clicked.hasPiece() && isCurrentPlayerPiece(clicked.getPiece())) {
                // Switch selection to another own piece
                clearSelection();
                firstSelected = clicked;
                clicked.setSelected(true);
            } else {
                secondSelected = clicked;
                int[] from = find(firstSelected);
                int[] to   = find(secondSelected);
                if (from != null && to != null) {
                    if (isCapture(from, to)) {
                        capture(from, to);
                    } else if (isValidMove(from, to)) {
                        move(from, to);
                    } else {
                        clearSelection();
                        repaint();
                        return;
                    }
                }
                clearSelection();
            }
        }
        repaint();
    }

    @Override public void mousePressed(MouseEvent e)  {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e)  {}
    @Override public void mouseExited(MouseEvent e)   {}

    // ── Move logic ─────────────────────────────────────────────────────────

    public void move(int[] from, int[] to) {
        Piece p = squares[from[0]][from[1]].getPiece();
        squares[to[0]][to[1]].addPiece(p);
        squares[from[0]][from[1]].removePiece();
        checkCrown(to[0], p);
        switchTurn();
        checkWin();
    }

    public void capture(int[] from, int[] to) {
        int midRow = (from[0] + to[0]) / 2;
        int midCol = (from[1] + to[1]) / 2;

        Piece p = squares[from[0]][from[1]].getPiece();
        squares[midRow][midCol].removePiece();
        squares[to[0]][to[1]].addPiece(p);
        squares[from[0]][from[1]].removePiece();
        checkCrown(to[0], p);

        // Check for multi-jump: if further captures exist from the new square
        if (!getValidCaptures(to[0], to[1], p.isCrown()).isEmpty()) {
            // Keep same player's turn and keep the piece selected
            firstSelected = squares[to[0]][to[1]];
            firstSelected.setSelected(true);
            repaint();
            return; // don't switch turn yet
        }

        switchTurn();
        checkWin();
    }

    // ── Validation ─────────────────────────────────────────────────────────

    /** Simple forward (or any direction for crowns) diagonal step — not a capture. */
    private boolean isValidMove(int[] from, int[] to) {
        Piece p = squares[from[0]][from[1]].getPiece();
        if (squares[to[0]][to[1]].hasPiece()) return false;
        if (!squares[to[0]][to[1]].isDark()) return false;

        int dr = to[0] - from[0];
        int dc = Math.abs(to[1] - from[1]);
        if (dc != 1 || Math.abs(dr) != 1) return false;

        if (p.isCrown()) return true;
        // Normal pieces: BLACK moves down (dr > 0), RED moves up (dr < 0)
        return p.getColor() == Piece.BLACK ? dr > 0 : dr < 0;
    }

    private boolean isCapture(int[] from, int[] to) {
        int dr = to[0] - from[0];
        int dc = to[1] - from[1];
        if (Math.abs(dr) != 2 || Math.abs(dc) != 2) return false;

        int midRow = (from[0] + to[0]) / 2;
        int midCol = (from[1] + to[1]) / 2;
        Piece mid = squares[midRow][midCol].getPiece();
        Piece src = squares[from[0]][from[1]].getPiece();

        if (mid == null || mid.getColor() == src.getColor()) return false;
        if (squares[to[0]][to[1]].hasPiece()) return false;
        if (!inBounds(to[0], to[1])) return false;

        if (src.isCrown()) return true;
        return src.getColor() == Piece.BLACK ? dr > 0 : dr < 0;
    }

    /** All valid (non-capture) moves for a piece at (row,col). */
    private java.util.List<int[]> getValidMoves(int row, int col, boolean crown) {
        java.util.List<int[]> list = new java.util.ArrayList<>();
        Piece p = squares[row][col].getPiece();
        int[][] dirs = crown
                ? new int[][]{{-1,-1},{-1,1},{1,-1},{1,1}}
                : p.getColor() == Piece.BLACK
                    ? new int[][]{{1,-1},{1,1}}
                    : new int[][]{{-1,-1},{-1,1}};
        for (int[] d : dirs) {
            int nr = row + d[0], nc = col + d[1];
            if (inBounds(nr, nc) && !squares[nr][nc].hasPiece() && squares[nr][nc].isDark())
                list.add(new int[]{nr, nc});
        }
        return list;
    }

    /** All valid capture moves for a piece at (row,col). */
    private java.util.List<int[]> getValidCaptures(int row, int col, boolean crown) {
        java.util.List<int[]> list = new java.util.ArrayList<>();
        Piece p = squares[row][col].getPiece();
        int[][] dirs = crown
                ? new int[][]{{-2,-2},{-2,2},{2,-2},{2,2}}
                : p.getColor() == Piece.BLACK
                    ? new int[][]{{2,-2},{2,2}}
                    : new int[][]{{-2,-2},{-2,2}};
        for (int[] d : dirs) {
            int nr = row + d[0], nc = col + d[1];
            if (inBounds(nr, nc)) {
                int[] from = {row, col}, to = {nr, nc};
                if (isCapture(from, to)) list.add(to);
            }
        }
        return list;
    }

    // ── Crown / kinging ────────────────────────────────────────────────────

    private void checkCrown(int row, Piece p) {
        if (!p.isCrown()) {
            if (p.getColor() == Piece.BLACK && row == NUM - 1) p.makeCrown();
            if (p.getColor() == Piece.RED   && row == 0)       p.makeCrown();
        }
    }

    // ── Win detection ──────────────────────────────────────────────────────

    public void checkWin() {
        int blackCount = 0, redCount = 0;
        for (int r = 0; r < NUM; r++)
            for (int c = 0; c < NUM; c++) {
                Piece p = squares[r][c].getPiece();
                if (p != null) {
                    if (p.getColor() == Piece.BLACK) blackCount++;
                    else                              redCount++;
                }
            }

        if (blackCount == 0) { announceWinner("Red wins!"); return; }
        if (redCount   == 0) { announceWinner("Black wins!"); return; }

        // Check if current player has no moves
        int currentColor = isBlackTurn ? Piece.BLACK : Piece.RED;
        boolean hasAnyMove = false;
        outer:
        for (int r = 0; r < NUM; r++)
            for (int c = 0; c < NUM; c++) {
                Piece p = squares[r][c].getPiece();
                if (p != null && p.getColor() == currentColor) {
                    if (!getValidMoves(r, c, p.isCrown()).isEmpty()
                            || !getValidCaptures(r, c, p.isCrown()).isEmpty()) {
                        hasAnyMove = true;
                        break outer;
                    }
                }
            }
        if (!hasAnyMove) {
            String winner = isBlackTurn ? "Red wins!" : "Black wins!";
            announceWinner(winner + " (no moves)");
        }
    }

    private void announceWinner(String msg) {
        statusMsg = "*** " + msg + " ***";
        repaint();
        JOptionPane.showMessageDialog(this, msg, "Game Over", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Helpers ────────────────────────────────────────────────────────────

    private void switchTurn() {
        isBlackTurn = !isBlackTurn;
        statusMsg   = isBlackTurn ? "Black's turn" : "Red's turn";
    }

    private void clearSelection() {
        if (firstSelected  != null) firstSelected.setSelected(false);
        if (secondSelected != null) secondSelected.setSelected(false);
        firstSelected  = null;
        secondSelected = null;
    }

    private boolean isCurrentPlayerPiece(Piece p) {
        return isBlackTurn ? p.getColor() == Piece.BLACK : p.getColor() == Piece.RED;
    }

    private boolean inBounds(int r, int c) {
        return r >= 0 && r < NUM && c >= 0 && c < NUM;
    }

    /** Find the [row, col] of a given Square reference. */
    private int[] find(Square sq) {
        for (int r = 0; r < NUM; r++)
            for (int c = 0; c < NUM; c++)
                if (squares[r][c] == sq) return new int[]{r, c};
        return null;
    }
}
