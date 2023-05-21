package ui;

import model.Board;
import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.util.Scanner;

public class Game {
    private Board board = Board.getBoard();
    private boolean gameOn = true;
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        while (gameOn) {
            board.printBoard();
            int input1 = Integer.parseInt(scanner.nextLine()) - 1;
            try {
                board.addPiece(input1, "X");
                board.printBoard();
            } catch (InvalidColumnException e) {
                System.out.println("Not a valid column");
            } catch (FullColumnException e) {
                System.out.println("Column already full");
            }
            if (board.win("X")) {
                gameOn = false;
                System.out.println("X has won!");
            }

            int input2 = Integer.parseInt(scanner.nextLine()) - 1;
            try {
                board.addPiece(input2, "O");
                board.printBoard();
            } catch (InvalidColumnException e) {
                System.out.println("Not a valid column");
            } catch (FullColumnException e) {
                System.out.println("Column already full");
            }
            if (board.win("O")) {
                gameOn = false;
                System.out.println("O has won!");
            }
        }
    }
}
