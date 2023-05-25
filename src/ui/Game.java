package ui;

import model.Board;
import model.Person;
import model.Player;
import model.exceptions.MediumBot;

// Contains the loop that runs the game
public class Game {
    private Board board = new Board();
    private Player player1 = new Person("O", board);
    private Player player2 = new MediumBot("X", board);

    public Game() {
        board.printBoard();
        boolean validMove1 = false;
        boolean validMove2 = false;

        while (true) {
            while (!validMove1) {                   // forces a valid move
                if (player1.move()) {
                    validMove1 = true;
                }
            }
            validMove1 = false;                     // prepare for next loop
            if (board.win(player1.getPiece())) {
                System.out.println("Player 1 has won!");
                break;
            }

            while (!validMove2) {
                if (player2.move()) {
                    validMove2 = true;
                }
            }
            validMove2 = false;
            if (board.win(player2.getPiece())) {
                System.out.println("Player 2 has won!");
                break;
            }

            if (board.isFull()) {
                System.out.println("Match ends in draw");
                break;
            }
        }
    }
}
