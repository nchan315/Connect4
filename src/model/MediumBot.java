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
            int tempBoardScore = score(tempBoard, move);                  // i-th board's score
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
    private int score(Board tempBoard, int move) {
        // board is full (no other move)
        if (tempBoard.isFull()) {
            System.out.println("Board full\n");
            return 0;
        }
        // winning is best move
        else if (tempBoard.win(piece)) {
            System.out.println("Win\n");
            return 1000;
        }
        // opponent can win immediately
        else if (opponentCanWin(tempBoard)) {
            System.out.println("Opponent Win\n");
            return -1000;
        }
        else {
            // give score for 'power' of board based on connections and winning possibilities
            int myScore = hzPowerTwo(tempBoard, piece) + hzPowerThree(tempBoard, piece)
                    + vtPowerTwo(tempBoard, piece) + vtPowerThree(tempBoard, piece)
                    + udPowerTwo(tempBoard, piece) + udPowerThree(tempBoard, piece)
                    + ldPowerTwo(tempBoard, piece) + ldPowerThree(tempBoard, piece);
//        int oppScore = hzPowerTwo(tempBoard, getOpponentPiece()) + hzPowerThree(tempBoard, getOpponentPiece())
//                + vtPowerTwo(tempBoard, getOpponentPiece()) + vtPowerThree(tempBoard, getOpponentPiece())
//                + udPowerTwo(tempBoard, getOpponentPiece()) + ldPower(tempBoard, getOpponentPiece());
            System.out.println("Calculated move\n");
            return myScore;
        }
    }

    // EFFECTS: returns true if move blocks opponent
    private boolean blocksOpponent(Board tempBoard, int move) {
        return false;
    }

    // EFFECTS: gives a score for board based on horizontal connections doubles
    private int hzPowerTwo(Board tempBoard, String s) {
        int hzPower = 0;
        for (int i = 0; i < 6; i++) {                       // all rows
            for (int j = 7 * i; j <= 7 * (i+1) - 2; j++) {  // L to R
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j+1) == s) {
                    hzPower++;
                }
            }
        }
        return hzPower;
    }

    // EFFECTS: gives a score for board based on horizontal connections triples
    private int hzPowerThree(Board tempBoard, String s) {
        int hzPower = 0;
        for (int i = 0; i < 6; i++) {                       // all rows
            for (int j = 7 * i; j < 7 * (i+1) - 2; j++) {   // L to R
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j+1) == s
                        && tempBoard.getPiece(j+2) == s) {
                    hzPower++;
                }
            }
        }
        return hzPower;
    }

    // EFFECTS: gives a score for board based on vertical connections double
    private int vtPowerTwo(Board tempBoard, String s) {
        int vtPower = 0;
        for (int i = 0; i < 7; i++) {                       // all columns
            for (int j = 35 + i; j > 6; j -= 7) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-7) == s) {
                    vtPower++;
                }
            }
        }
        return vtPower;
    }

    // EFFECTS: gives a score for board based on vertical connections triple
    private int vtPowerThree(Board tempBoard, String s) {
        int vtPower = 0;
        for (int i = 0; i < 7; i++) {                       // all columns
            for (int j = 35 + i; j > 13; j -= 7) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-7) == s
                        && tempBoard.getPiece(j-14) == s) {
                    vtPower++;
                }
            }
        }
        return vtPower;
    }

    // EFFECTS: gives a score for board based on upper diagonal connections double
    private int udPowerTwo(Board tempBoard, String s) {
        int udPower = 0;
        for (int i = 1; i < 6; i++) {                       // rows
            for (int j = 7 * i; j <= 7 * (i+1) - 2; j++) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-6) == s) {
                    udPower++;
                }
            }
        }
        return udPower;
    }

    // EFFECTS: gives a score for board based on upper diagonal connections triple
    private int udPowerThree(Board tempBoard, String s) {
        int udPower = 0;
        for (int i = 2; i < 6; i++) {                       // rows
            for (int j = 7 * i; j < 7 * (i+1) - 2; j++) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j-6) == s
                        && tempBoard.getPiece(j-12) == s) {
                    udPower++;
                }
            }
        }
        return udPower;
    }

    // EFFECTS: gives a score for board based on lower diagonal connections double
    private int ldPowerTwo(Board tempBoard, String s) {
        int ldPower = 0;
        for (int i = 0; i < 5; i++) {                       // rows
            for (int j = 7 * i; j <= 7 * (i+1) - 2; j++) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j+8) == s) {
                    ldPower++;
                }
            }
        }
        return ldPower;
    }

    // EFFECTS: gives a score for board based on lower diagonal connections triple
    private int ldPowerThree(Board tempBoard, String s) {
        int ldPower = 0;
        for (int i = 0; i < 4; i++) {                       // rows
            for (int j = 7 * i; j < 7 * (i+1) - 2; j++) {
                if (tempBoard.getPiece(j) == s && tempBoard.getPiece(j+8) == s
                        && tempBoard.getPiece(j+16) == s) {
                    ldPower++;
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
//                System.out.println(tempBoard2.getRecord() + "\n");
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
