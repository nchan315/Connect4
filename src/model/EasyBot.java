package model;

import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.security.SecureRandom;
import java.util.List;

public class EasyBot extends Player {

    SecureRandom random = new SecureRandom();

    public EasyBot(String piece) {
        super(piece);
    }

    @Override
    public boolean move() {
        List<Integer> empties = board.getEmptyColumns();
        int size = empties.size();
        try {
            board.addPiece(random.nextInt(size), piece);
            System.out.println("Easy Bot's Move:");
            board.printBoard();
            return true;
        } catch (InvalidColumnException e) {
            System.out.println("Not a valid column");
        } catch (FullColumnException e) {
            System.out.println("Column already full");
        }
        return false;
    }
}
