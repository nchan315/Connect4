package tests;

import model.Board;
import model.MediumBot;
import model.Person;
import model.Player;
import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MediumBotTest {
    private Player playerO;
    private MediumBot playerX;
    private Board board;

    @BeforeEach
    void setUp() {
        playerO = new Person("O", board);
        playerX = new MediumBot("X", board);
    }

    void OneMoveToWinBoard() {
        board = new Board();
        try {
            board.addPiece(4, "O");
            board.addPiece(1, "X");
            board.addPiece(4, "O");
            board.addPiece(2, "X");
            board.addPiece(4, "O");
            board.addPiece(3, "X");
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
    }

    void SomeRandomBoard() {
        board = new Board();
        try {
            board.addPiece(0, "O");
            board.addPiece(4, "X");
            board.addPiece(3, "O");
            board.addPiece(4, "X");
            board.addPiece(5, "O");
            board.addPiece(4, "X");
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
    }

    @Test
    void testOpponentCanWinTrue() {
        OneMoveToWinBoard();
        assertTrue(playerX.opponentCanWin(board));
    }

    @Test
    void TestOpponentCanWinFalse() {
        SomeRandomBoard();
        assertFalse(playerX.opponentCanWin(board));
    }
}
