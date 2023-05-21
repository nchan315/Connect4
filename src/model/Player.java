package model;

public abstract class Player {
    private final String piece;
    protected Board board = Board.getBoard();

    public Player(String piece) {
        this.piece = piece;
    }

    // MODIFIES: BOARD
    // EFFECTS: adds the player move to the board
    public abstract void move();

    // EFFECTS: returns the player piece
    public String getPiece() {
        return piece;
    }
}
