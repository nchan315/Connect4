package model;

import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class HardBot extends Player {

    static int DEPTH = 5;
    static List<Integer> SCORES;
    private SecureRandom random = new SecureRandom();

    public HardBot(String piece, Board board) {
        super(piece, board);
    }

    @Override
    public boolean move() {
        // All scores after DEPTH moves
        SCORES = getScores(board.getRecord(), 0, DEPTH);
        // Find best move after DEPTH moves
        int bestMove = bestMove();
        System.out.println(bestMove);

        // Actually doing the move
        try {
            board.addPiece(bestMove, piece);
            System.out.println("Hard Bot's Move:");
            board.printBoard();
            return true;
        } catch (InvalidColumnException e) {
            System.out.println("Not a valid column (HardBot)");
        } catch (FullColumnException e) {
            System.out.println("Column already full (HardBot)");
        }
        return false;
    }

    // initial call to minimax, stops invalid moves
    public int bestMove() {
        List<Integer> bestMoves = new ArrayList<>();
        bestMoves.add(0);
        int bestScore = -1000;

        for (int i = 0; i < 7; i++) {
            int score = minimax(1, i, false);
            if (score == bestScore && board.hasSpace(i)) {
                bestMoves.add(i);
            } else if (score > bestScore && board.hasSpace(i)) {
                bestMoves = new ArrayList<>();
                bestMoves.add(i);
                bestScore = score;
            }
        }
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    // gets scores from all possible boards after DEPTH moves
    public List<Integer> getScores(String record, int depth, int maxDepth) {
        Board tempBoard = new Board();
        tempBoard.copyBoard(record);
        List<Integer> listScores = new ArrayList<>();

        // base case
        if (depth == maxDepth) {
            listScores.add(score(tempBoard));
            return listScores;
        }

        for (int i = 0; i < 7; i++) {
            String s = record;
            String recordCopy = s.concat(String.valueOf(i));
            List<Integer> tempScore = getScores(recordCopy, depth + 1, maxDepth);
            listScores.addAll(tempScore);
        }
        return listScores;
    }

    // minimax algorithm to return best move
    private int minimax(int depth, int nodeIndex, boolean isMax) {
        // base case
        if (depth == DEPTH) {
            return SCORES.get(nodeIndex);
        }

        // maximizing move
        if (isMax) {
             return findMax(minimax(depth+1, 7*nodeIndex, false),
                    minimax(depth+1, 7*nodeIndex+1, false),
                    minimax(depth+1, 7*nodeIndex+2, false),
                    minimax(depth+1, 7*nodeIndex+3, false),
                    minimax(depth+1, 7*nodeIndex+4, false),
                    minimax(depth+1, 7*nodeIndex+5, false),
                    minimax(depth+1, 7*nodeIndex+6, false));
        }

        // minimizing move
        else {
            return findMin(minimax(depth+1, 7*nodeIndex, true),
                    minimax(depth+1, 7*nodeIndex+1, true),
                    minimax(depth+1, 7*nodeIndex+2, true),
                    minimax(depth+1, 7*nodeIndex+3, true),
                    minimax(depth+1, 7*nodeIndex+4, true),
                    minimax(depth+1, 7*nodeIndex+5, true),
                    minimax(depth+1, 7*nodeIndex+6, true));
        }
    }

    int findMax(int a, int b, int c, int d, int e, int f, int g) {
        return max(a, max(b, max(c, max(d, max(e, max(f, g))))));
    }

    int findMin(int a, int b, int c, int d, int e, int f, int g) {
        return min(a, min(b, min(c, min(d, min(e, min(f, g))))));
    }

    // EFFECTS: evaluates the strength of a move/board
    public int score(Board tempBoard) {
        // board is full (no other move)
//        if (tempBoard.isFull()) {
//            return 0;
//        }
        // opponent has won
        if (tempBoard.win(getOpponentPiece())) {
            return -1000;
        }
        // hard bot has won
        else if (tempBoard.win(piece)) {
            return 1000;
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
