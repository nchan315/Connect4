package model;

import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.util.List;

public class MediumBot extends Player {

    public MediumBot(String piece, Board board) {
        super(piece, board);
    }

    @Override
    public boolean move() {
        // Finding a reasonably good move
        List<Integer> empties = board.getEmptyColumns();            // list of all legal moves
        int bestColumn = 0;                                         // position of best move so far
        int bestColumnScore = -1000;                                // score of best move so far (current worst case)
        for (int i = 0; i < empties.size(); i++) {                  // iterate through possible moves
            int move = empties.get(i);
            Board tempBoard = new Board();
            try {
                tempBoard.copyBoard(board.getRecord());
                tempBoard.addPiece(move, piece);
            } catch (InvalidColumnException e) {
                System.out.println("Error in MediumBoat.java");
            } catch (FullColumnException e) {
                System.out.println("Error in MediumBoat.java");
            }
            int tempBoardScore = score(tempBoard);                  // i-th board's score
            if (tempBoardScore > bestColumnScore) {                 // update best board/score
                bestColumn = move;
                bestColumnScore = tempBoardScore;
            }
        }

        // Actually doing the move
        try {
            board.addPiece(bestColumn, piece);
            System.out.println("Medium Bot's Move:");
            board.printBoard();
            return true;
        } catch (InvalidColumnException e) {
            System.out.println("Not a valid column (MediumBot)");
        } catch (FullColumnException e) {
            System.out.println("Column already full (MediumBot)");
        }
        return false;
    }

    // EFFECTS: evaluates the strength of a move/board
    private int score(Board tempBoard) {
        // board is full (no other move)
        if (tempBoard.isFull()) {
            return 0;
        }
        // winning is best move
        else if (tempBoard.win(piece)) {
            return 1000;
        }
        // block opponent from winning
        else if (opponentCanWin(tempBoard)) {
            return -1000;
        }
        // give score for 'power' of board based on connections
        int myScore = hzPower(tempBoard, piece) + vtPower(tempBoard, piece)
                + udPower(tempBoard, piece) + ldPower(tempBoard, piece);
        int oppScore = hzPower(tempBoard, getOpponentPiece()) + vtPower(tempBoard, getOpponentPiece())
                + udPower(tempBoard, getOpponentPiece()) + ldPower(tempBoard, getOpponentPiece());
        return myScore - oppScore;
    }

    // EFFECTS: gives a score for board based on horizontal connections
    private int hzPower(Board tempBoard, String s) {
        int hzPower = 0;
        for (int i = 0; i < 6; i++) {                       // all rows
            for (int j = 7 * i; j < 7 * (i+1) - 2; j++) {   // L to R
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j+1) == s) {
                    hzPower += 5;                           // TODO tune value
                }
            }
        }
        return hzPower;
    }

    // EFFECTS: gives a score for board based on vertical connections
    private int vtPower(Board tempBoard, String s) {
        int vtPower = 0;
        for (int i = 0; i < 7; i++) {                       // all columns
            for (int j = 35 + i; j > 6; j -= 7) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-7) == s) {
                    vtPower += 5;                           // TODO tune value
                }
            }
        }
        return vtPower;
    }

    // EFFECTS: gives a score for board based on upper diagonal connections
    private int udPower(Board tempBoard, String s) {
        return 0;
    }

    // EFFECTS: gives a score for board based on lower diagonal connections
    private int ldPower(Board tempBoard, String s) {
        return 0;
    }

    // EFFECTS: returns true if opponent has an immediate winning move
    private boolean opponentCanWin(Board tempBoard) {
        List<Integer> empties = tempBoard.getEmptyColumns();
        for (int i = 0; i < empties.size(); i++) {
            int move = empties.get(i);
            Board tempBoard2 = new Board();
            try {
                tempBoard2.copyBoard(tempBoard.getRecord());                // make a copy of tempBoard
                tempBoard2.addPiece(move, getOpponentPiece());
            } catch (Exception e) {
                System.out.println("Medium bot - opponentCanWin broken");
            }
            if (tempBoard2.win(getOpponentPiece())) {
                return true;
            }
        }
        return false;
    }
}
