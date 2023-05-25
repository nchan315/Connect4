package model;

public abstract class Player {
    protected final String piece;
    protected Board board;

    public Player(String piece, Board board) {
        this.piece = piece;
        this.board = board;
    }

    // MODIFIES: BOARD
    // EFFECTS: adds the player move to the board, returns true if move actually made
    public abstract boolean move();

    // EFFECTS: returns the player piece
    public String getPiece() {
        return piece;
    }
}
