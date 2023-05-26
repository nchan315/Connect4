package model.exceptions;

import model.Board;
import model.Player;

import java.util.List;

public class MediumBot extends Player {

    public MediumBot(String piece, Board board) {
        super(piece, board);
    }

    @Override
    public boolean move() {
        List<Integer> empties = board.getEmptyColumns();
        int bestColumn = 0;
        int bestColumnScore = 0;
        for (int i = 0; i < empties.size(); i++) {
            Board tempBoard = new Board(); // TODO:
            try {
                tempBoard.addPiece(empties.get(i), piece);
            } catch (InvalidColumnException e) {
                System.out.println("Error in MediumBoat.java");
            } catch (FullColumnException e) {
                System.out.println("Error in MediumBoat.java");
            }
            int tempBoardScore = score(tempBoard);
            if (score(tempBoard) > bestColumnScore) {
                bestColumn = i;
                bestColumnScore = tempBoardScore;
            }
        }
        try {
            board.addPiece(bestColumn, piece);
            System.out.println("Medium Bot's Move:");
            board.printBoard();
            return true;
        } catch (InvalidColumnException e) {
            System.out.println("Not a valid column (MediumBot)");
        } catch (FullColumnException e) {
            System.out.println("Column already full (MediumBot");
        }
        return false;
    }

    // EFFECTS: evaluates the strength of a move/board
    private int score(Board board) {

        return 10;
    }
}
