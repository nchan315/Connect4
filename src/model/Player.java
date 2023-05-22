package model;

public abstract class Player {
    protected final String piece;
    protected Board board = Board.getBoard();

    public Player(String piece) {
        this.piece = piece;
    }

    // MODIFIES: BOARD
    // EFFECTS: adds the player move to the board, returns true if move actually made
    public abstract boolean move();

    // EFFECTS: returns the player piece
    public String getPiece() {
        return piece;
    }
}
