package model;

import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.util.Scanner;

public class Person extends Player {
    private Scanner scanner = new Scanner(System.in);

    public Person(String piece) {
        super(piece);
    }

    @Override
    public boolean move() {
        int input1 = Integer.parseInt(scanner.nextLine()) - 1;
        try {
            board.addPiece(input1, piece);
            System.out.println("Person's Move:");
            board.printBoard();
            System.out.println();
            return true;
        } catch (InvalidColumnException e) {
            System.out.println("Not a valid column");
        } catch (FullColumnException e) {
            System.out.println("Column already full");
        }
        return false;
    }
}
