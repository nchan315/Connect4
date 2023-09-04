package model;

import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.util.ArrayList;
import java.util.List;

public class HardBot extends Player {

    static int DEPTH = 3;

    public HardBot(String piece, Board board) {
        super(piece, board);
    }

    @Override
    public boolean move() {
        // Find best move after DEPTH moves
        int bestMove = minimax(0, true, board.getRecord());

        // Actually doing the move
        try {
            board.addPiece(bestMove, piece);
            System.out.println("Medium Bot's Move:");
            board.printBoard();
            return true;
        } catch (InvalidColumnException e) {
            System.out.println("Not a valid column (HardBot)");
        } catch (FullColumnException e) {
            System.out.println("Column already full (HardBot)");
        }
        return false;
    }

    // minimax algorithm to return best move
    int minimax(int depth, boolean isMax, String record) {
        Board tempBoard = new Board();
        tempBoard.copyBoard(record);
        // reach bottom and evaluate (base case)
        if (depth == DEPTH) {
            return score(tempBoard);
        }
        // all possible moves
        List<Integer> emptyColumns = tempBoard.getEmptyColumns();
        List<String>  allRecords = new ArrayList<>();
        for (int i = 0; i < emptyColumns.size(); i++) {
            String copyRecord = record;
            allRecords.add(copyRecord.concat(String.valueOf(emptyColumns.get(i))));
        }
        List<Integer> scores = new ArrayList<>();
        for (int i = 0; i < emptyColumns.size(); i++) {
            scores.add(minimax(depth + 1, !isMax, allRecords.get(i)));
        }
        // maximizing move
        if (isMax) {
            int bestMoveIndex = 0;
            for (int i = 1; i < emptyColumns.size(); i++) {
                if (scores.get(bestMoveIndex) <= scores.get(i)) {
                    bestMoveIndex = i;
                }
            }
            return emptyColumns.get(bestMoveIndex);
        }
        // minimizing move
        else {
            int bestMoveIndex = 0;
            for (int i = 1; i < emptyColumns.size(); i++) {
                if (scores.get(bestMoveIndex) > scores.get(i)) {
                    bestMoveIndex = i;
                }
            }
            return emptyColumns.get(bestMoveIndex);
        }
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
        // opponent can win immediately
        else if (opponentCanWin(tempBoard)) {
            return -1000;
        }
        else {
            // give score for 'power' of board based on connections and winning possibilities
            int myScore = hzPowerTwo(tempBoard, piece) + hzPowerThree(tempBoard, piece)
                    + vtPowerTwo(tempBoard, piece) + vtPowerThree(tempBoard, piece)
                    + udPowerTwo(tempBoard, piece) + udPowerThree(tempBoard, piece)
                    + ldPowerTwo(tempBoard, piece) + ldPowerThree(tempBoard, piece);
            int oppScore = hzPowerTwo(tempBoard, getOpponentPiece()) + hzPowerThree(tempBoard, getOpponentPiece())
                    + vtPowerTwo(tempBoard, getOpponentPiece()) + vtPowerThree(tempBoard, getOpponentPiece())
                    + udPowerTwo(tempBoard, getOpponentPiece()) + udPowerThree(tempBoard, getOpponentPiece())
                    + ldPowerTwo(tempBoard, getOpponentPiece()) + ldPowerThree(tempBoard, getOpponentPiece());
            return myScore - oppScore;
        }
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