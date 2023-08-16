package model;

import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.util.ArrayList;
import java.util.List;

// Represents a 7 wide 6 high Connect 4 board
public class Board {
    private List<String> board;
    private String record;

    // EFFECTS: creates an empty board given no parameters
    public Board() {
        board = new ArrayList<>();
        fillBoard();
        record = "";
    }

    // EFFECTS: creates a board given the record
    public Board copyBoard(String record) {
        Board newBoard = new Board();
        boolean player1 = true;
        for (int i = 0; i < record.length(); i++) {
            char c = record.charAt(i);
            int col = c - '0';
            if (player1) {
                try {
                    newBoard.addPiece(col, "O");
                } catch (InvalidColumnException e) {
                    System.out.println("Code is broken in 2nd Board constructor");
                } catch (FullColumnException e) {
                    System.out.println("Code is broken in 2nd Board constructor");
                }
                player1 = false;
            } else {
                try {
                    newBoard.addPiece(col, "X");
                } catch (InvalidColumnException e) {
                    System.out.println("Code is broken in 2nd Board constructor");
                } catch (FullColumnException e) {
                    System.out.println("Code is broken in 2nd Board constructor");
                }
                player1 = true;
            }
        }
        return newBoard;
    }

    // MODIFIES: this
    // EFFECTS: fills the board with *
    private void fillBoard() {
        for (int i = 0; i < 42; i++) {
            board.add("*");
        }
    }

    // MODIFIES: this
    // EFFECTS: clears the board
    public void clearBoard() {
        board = new ArrayList<>();
        fillBoard();
        record = "";
    }

    // EFFECTS: prints the board in the console
    public void printBoard() {
        for (int i = 0; i < 42; i++) {
            for (int j = 0; j < 6; j++) {
                System.out.print(board.get(i) + "   ");
                i++;
            }
            System.out.println(board.get(i));
        }
        for (int k = 1; k <= 7; k++) {
            System.out.print(k + "   ");
        }
        System.out.println("");
    }

    // EFFECTS: returns the record
    public String getRecord() {
        return record;
    }

    // EFFECTS: gets the current turn number
    public int getTurns() {
        return (record.length() + 1) / 2;
    }

    // EFFECTS: gets the piece at given position
    public String getPiece(int pos) {
        return board.get(pos);
    }

    // EFFECTS: returns an array with columns that have empty spots
    public List<Integer> getEmptyColumns() {
        List<Integer> empties = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            List<String> column = getColumn(i);
            if (column.contains("*")) {
                empties.add(i);
            }
        }
        return empties;
    }

    // EFFECTS: returns true if board is full
    public boolean isFull() {
        return (!board.contains("*"));
    }

    // MODIFIES: board
    // EFFECTS: given a column, adds the piece to board if possible
    public void addPiece(int col, String piece) throws InvalidColumnException, FullColumnException {
        if (col >= 7 || col < 0) {
            throw new InvalidColumnException();
        }
        List<String> column = getColumn(col);
        if (!column.contains("*")) {
            throw new FullColumnException();
        }
        int empty = getEmptySpots(column);
        int spot = (empty - 1) * 7 + col;
        board.remove(spot);
        board.add(spot, piece);
        record = record.concat(String.valueOf(col));
    }

    // REQUIRES: can only take a col >= 0 and <= 6
    // EFFECTS: returns the pieces in the column
    private List<String> getColumn(int col) {
        List<String> column = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            column.add(board.get(7 * i + col));
        }
        return column;
    }

    // EFFECTS: returns the pieces in the row
    private List<String> getRow(int r) {
        List<String> row = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            row.add(board.get(7 * r + i));
        }
        return row;
    }

    // EFFECTS: returns the number of empty spots in a column
    private int getEmptySpots(List<String> column) {
        int empty = 0;
        for (String s : column) {
            if (s.equals("*")) {
                empty++;
            }
        }
        return empty;
    }

    // EFFECTS: returns true if piece has won
    public boolean win(String piece) {
        return horizontalWin(piece) | verticalWin(piece) | diagonalWin(piece);
    }

    // EFFECTS: returns true if piece has 4 in a row
    private boolean horizontalWin(String piece) {
        for (int i = 0; i < 6; i++) {
            List<String> row = getRow(i);
            for (int j = 0; j < 4; j++) {
                if (row.get(j) == piece && row.get(j + 1) == piece
                        && row.get(j + 2) == piece && row.get(j + 3) == piece) {
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS: returns true if piece has 4 in a column
    private boolean verticalWin(String piece) {
        for (int i = 0; i < 7; i++) {
            List<String> column = getColumn(i);
            for (int j = 0; j < 3; j++) {
                if (column.get(j) == piece && column.get(j + 1) == piece
                        && column.get(j + 2) == piece && column.get(j + 3) == piece) {
                    return true;
                }
            }
        }
        return false;
    }

    // EFFECTS: returns true if piece has 4 in a diagonal
    private boolean diagonalWin(String piece) {
        return ((board.get(14).equals(piece) && board.get(22).equals(piece) && board.get(30).equals(piece) && board.get(38).equals(piece))
                || (board.get(7).equals(piece) && board.get(15).equals(piece) && board.get(23).equals(piece) && board.get(31).equals(piece))
                || (board.get(0).equals(piece) && board.get(8).equals(piece) && board.get(16).equals(piece) && board.get(24).equals(piece))
                || (board.get(15).equals(piece) && board.get(23).equals(piece) && board.get(31).equals(piece) && board.get(39).equals(piece))
                || (board.get(8).equals(piece) && board.get(16).equals(piece) && board.get(24).equals(piece) && board.get(32).equals(piece))
                || (board.get(1).equals(piece) && board.get(9).equals(piece) && board.get(17).equals(piece) && board.get(25).equals(piece))
                || (board.get(16).equals(piece) && board.get(24).equals(piece) && board.get(32).equals(piece) && board.get(40).equals(piece))
                || (board.get(9).equals(piece) && board.get(17).equals(piece) && board.get(25).equals(piece) && board.get(33).equals(piece))
                || (board.get(2).equals(piece) && board.get(10).equals(piece) && board.get(18).equals(piece) && board.get(26).equals(piece))
                || (board.get(17).equals(piece) && board.get(25).equals(piece) && board.get(33).equals(piece) && board.get(41).equals(piece))
                || (board.get(10).equals(piece) && board.get(18).equals(piece) && board.get(26).equals(piece) && board.get(34).equals(piece))
                || (board.get(3).equals(piece) && board.get(11).equals(piece) && board.get(19).equals(piece) && board.get(27).equals(piece))
                //
                || (board.get(20).equals(piece) && board.get(26).equals(piece) && board.get(32).equals(piece) && board.get(38).equals(piece))
                || (board.get(13).equals(piece) && board.get(19).equals(piece) && board.get(25).equals(piece) && board.get(31).equals(piece))
                || (board.get(6).equals(piece) && board.get(12).equals(piece) && board.get(18).equals(piece) && board.get(24).equals(piece))
                || (board.get(19).equals(piece) && board.get(25).equals(piece) && board.get(31).equals(piece) && board.get(37).equals(piece))
                || (board.get(12).equals(piece) && board.get(18).equals(piece) && board.get(24).equals(piece) && board.get(30).equals(piece))
                || (board.get(5).equals(piece) && board.get(11).equals(piece) && board.get(17).equals(piece) && board.get(23).equals(piece))
                || (board.get(18).equals(piece) && board.get(24).equals(piece) && board.get(30).equals(piece) && board.get(36).equals(piece))
                || (board.get(11).equals(piece) && board.get(17).equals(piece) && board.get(23).equals(piece) && board.get(29).equals(piece))
                || (board.get(4).equals(piece) && board.get(10).equals(piece) && board.get(16).equals(piece) && board.get(22).equals(piece))
                || (board.get(17).equals(piece) && board.get(23).equals(piece) && board.get(29).equals(piece) && board.get(35).equals(piece))
                || (board.get(10).equals(piece) && board.get(16).equals(piece) && board.get(22).equals(piece) && board.get(28).equals(piece))
                || (board.get(3).equals(piece) && board.get(9).equals(piece) && board.get(15).equals(piece) && board.get(21).equals(piece)));
    }
}
