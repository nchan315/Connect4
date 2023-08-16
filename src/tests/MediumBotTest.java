package tests;

import model.Board;
import model.MediumBot;
import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// Not a complete test suite, just testing what gives me pain
public class MediumBotTest {
    private MediumBot playerX;
    private MediumBot playerO;
    private Board board;

    void defaultBoard0() {
        try {
            board.addPiece(2, "X");
            board.addPiece(3, "O");
            board.addPiece(3, "X");
            board.addPiece(4, "O");
            board.addPiece(2, "X");
            board.addPiece(6, "O");
            board.addPiece(5, "X");
            board.addPiece(5, "O");
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
    }

    @BeforeEach
    void setup() {
        board = new Board();
        playerX = new MediumBot("X", board);
        playerO = new MediumBot("O", board);
    }

    @Test
    void testScoreB0() {
        defaultBoard0();
        assertEquals(9, playerX.score(board));
        assertEquals(5, playerO.score(board));
    }
}
