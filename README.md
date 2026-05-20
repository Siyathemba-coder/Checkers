[checkers-README.md](https://github.com/user-attachments/files/28035610/checkers-README.md)
# Checkers

A fully playable checkers game built in Java with a Swing GUI.

## Features

- Standard checkers rules on an 8x8 board
- Mandatory captures (jump over opponent pieces to take them)
- Multi-jump: if another capture is available after landing, your turn continues
- King promotion when a piece reaches the far rank (crown image + gold ring)
- Legal move hints: green dots for moves, red rings for captures
- Win detection: by capturing all opponent pieces or leaving them with no moves
- Resignation handled gracefully with a game-over dialog

## How to compile and run

```cmd
cd checkers-complete
mkdir bin
javac -d bin -sourcepath src src/checkers/Main.java
java -cp bin checkers.Main
```

## How to play

1. Black always moves first
2. Click one of your pieces to select it
   - Green dots show squares you can move to
   - Red rings show opponent pieces you can capture
3. Click a destination to move or jump
4. If a capture is available you must take it
5. After a capture, if another jump is possible from the same piece, you must take that too (multi-jump)
6. A piece that reaches the opposite back rank becomes a King and can move in all four diagonal directions
7. The game ends when one player has no pieces left or no legal moves

## Rules summary

| Rule | Detail |
|---|---|
| Movement | Diagonal steps only, one square at a time |
| Captures | Jump diagonally over an adjacent opponent piece to a vacant square beyond |
| Multi-jump | After capturing, keep jumping with the same piece if further captures exist |
| Kinging | Black pieces king on row 8 (bottom). Red pieces king on row 1 (top) |
| Win | Capture all opponent pieces, or leave them with no legal moves |

## Project structure

```
checkers/src/checkers/
  Main.java       Entry point - creates JFrame and adds the Board panel
  Board.java      Game logic and Swing panel: move validation, captures,
                  kinging, win detection, mouse handling, rendering
  Square.java     Individual square: color, piece reference, selection highlight
  Piece.java      Piece data: color, crown state, image loading and drawing
checkers/images/
  black.gif       Black piece image
  red.gif         Red piece image
  blackcrown.gif  Black king image
  redcrown.png    Red king image
```

## Controls

| Action | How |
|---|---|
| Select a piece | Click it |
| Move or capture | Click the destination square |
| Switch selection | Click another one of your own pieces |
| Deselect | Click an empty non-highlighted square |

@Siyathemba Msimang
