package ui;

import model.*;

import java.util.Scanner;

// Contains the loop that runs the game
public class Game {
    private Scanner scanner = new Scanner(System.in);
    private Board board;
    private Player player1;
    private Player player2;

    public Game() {
        board = new Board();
        player1 = new Person("O", board);
        setUpOpp();
        playGame();
    }

    void setUpOpp() {
        boolean opp = false;
        System.out.println("Choose opponent: X");
        System.out.println("1 - Easy bot");
        System.out.println("2 - Medium bot");
        System.out.println("3 - Hard bot");
        System.out.println("4 - Human");
        while (!opp) {
            String input = scanner.nextLine();
            switch (input) {
                case "1":
                    player2 = new EasyBot("X", board);
                    opp = true;
                    break;
                case "2":
                    player2 = new MediumBot("X", board);
                    opp = true;
                    break;
                case "3":
                    player2 = new HardBot("X", board);
                    opp = true;
                    break;
                case "4":
                    player2 = new Person("X", board);
                    opp = true;
                    break;
                default:
                    System.out.println("Invalid input");
            }
        }
    }

    void playGame() {
        System.out.println("Player 1 goes first");
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
        playAgain();
    }

    void playAgain() {
        System.out.println("Play again?");
        System.out.println("1 - Change opponent");
        System.out.println("2 - Yes");
        System.out.println("Other - Quit");
        String input = scanner.nextLine();
        switch (input) {
            case "1":                 // new opponent, reset board, new game
                setUpOpp();
            case "2":                 // reset board, new game
                board.clearBoard();
                playGame();
                break;
            default:
                System.out.println("Have a nice day!");
        }
    }
}
