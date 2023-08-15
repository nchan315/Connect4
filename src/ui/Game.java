package ui;

import model.*;

// Contains the loop that runs the game
public class Game {
    private Board board = new Board();
    private Player player1 = new Person("O", board); // human player
//    private Player player2 = new EasyBot("X", board); // bot players
    private Player player2 = new MediumBot("X", board);

    public Game() {
        board.printBoard();
        boolean validMove1 = false;
        boolean validMove2 = false;

        while (true) {
            // Player 1 Move
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

            // Player 2 Move
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

            // Check if draw
            if (board.isFull()) {
                System.out.println("Match ends in draw");
                break;
            }
        }
    }
}
