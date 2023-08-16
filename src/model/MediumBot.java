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
    public int score(Board tempBoard) {
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
        List<Integer> myBoardCol = tempBoard.getNumPiecesCol(piece);
        int myScore = 5 * hzPower(tempBoard, piece)         // TODO tune weights
                + 5 * vtPower(tempBoard, piece)
                + 5 * udPower(tempBoard, piece)
                + 5 * ldPower(tempBoard, piece)
                + 2 * (myBoardCol.get(0) + myBoardCol.get(6))
                + 3 * (myBoardCol.get(1) + myBoardCol.get(5))
                + 4 * (myBoardCol.get(2) + myBoardCol.get(4)) +
                + 5 * (myBoardCol.get(3));
        List<Integer> oppBoardCol = tempBoard.getNumPiecesCol(getOpponentPiece());
        int oppScore = 4 * hzPower(tempBoard, getOpponentPiece())
                + 4 * vtPower(tempBoard, getOpponentPiece())
                + 4 * udPower(tempBoard, getOpponentPiece())
                + 4 * ldPower(tempBoard, getOpponentPiece())
                + 1 * (oppBoardCol.get(0) + oppBoardCol.get(6))
                + 2 * (oppBoardCol.get(1) + oppBoardCol.get(5))
                + 3 * (oppBoardCol.get(2) + oppBoardCol.get(4)) +
                + 4 * (oppBoardCol.get(3));
        int total = myScore - oppScore;
//        System.out.println(total);
        return total;
    }

    // EFFECTS: gives a score for board based on horizontal connections
    private int hzPower(Board tempBoard, String s) {
        int hzPower = 0;
        for (int i = 0; i < 6; i++) {                       // all rows
            for (int j = 7 * i; j < 7 * (i+1) - 2; j++) {   // L to R
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j+1) == s) {
                    hzPower += 1;
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
                    vtPower += 1;
                }
            }
        }
        return vtPower;
    }

    // EFFECTS: gives a score for board based on upper diagonal connections
    private int udPower(Board tempBoard, String s) {
        int udPower = 0;
        for (int i = 1; i < 6; i++) {                       // row
            for (int j = 7 * i; j < 7 * (i+1) - 1; j++) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-6) == s) {
                    udPower += 1;
                }
            }
        }
        return udPower;
    }

    // EFFECTS: gives a score for board based on lower diagonal connections
    private int ldPower(Board tempBoard, String s) {
        int ldPower = 0;
        for (int i = 1; i < 6; i++) {                       // row
            for (int j = 7 * i + 1; j < 7 * (i+1); j++) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-8) == s) {
                    ldPower += 1;
                }
            }
        }
        return ldPower;
    }

    // EFFECTS: returns true if opponent has an immediate winning move
    public boolean opponentCanWin(Board tempBoard) {
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
