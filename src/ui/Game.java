package ui;

import model.Board;
import model.EasyBot;
import model.Person;
import model.Player;
import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;

import java.util.Scanner;

public class Game {
    private Board board = Board.getBoard();
    private Player player1 = new Person("O");
    private Player player2 = new EasyBot("X");

    private Scanner scanner = new Scanner(System.in);
    private boolean gameOn = true;

    public Game() {
        while (gameOn) {
            board.printBoard();
            player1.move();

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
