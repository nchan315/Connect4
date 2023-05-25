package tests;

import model.Board;
import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

// Not a complete test suite, just testing what gives me pain
public class BoardTest {
    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
    }

    @Test
    void testDefaultConstructor() {
        assertEquals("", board.getRecord());
        assertEquals("*", board.getPiece(0));
        assertEquals("*", board.getPiece(25));
        assertEquals("*", board.getPiece(41));
    }

    @Test
    void testOtherConstructor() {
        // TODO: I'm scared
    }

    @Test
    void testClearBoard() {
        try {
            board.addPiece(0, "O");
            board.addPiece(0, "X");
            assertEquals("O", board.getPiece(35));
            assertEquals("X", board.getPiece(28));
            assertEquals("00", board.getRecord());
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
        board.clearBoard();
        assertEquals("*", board.getPiece(35));
        assertEquals("*", board.getPiece(28));
        assertEquals("", board.getRecord());
    }

    @Test
    void getEmptyColumns() {
        List<Integer> temp = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            temp.add(i);
        }
        try {
            for (int i = 1; i <= 6; i++) {
                board.addPiece(0, "X");
            }
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
        assertEquals(temp, board.getEmptyColumns());
    }

    @Test
    void testAddPieceExceptions() {
        try {
            board.addPiece(7, "X");
            fail();
        } catch (InvalidColumnException e) {
            // good
        } catch (FullColumnException e) {
            fail();
        }
        try {
            for (int i = 0; i <= 10; i++) {
                board.addPiece(4, "O");
            }
            fail();
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            // good
        }
    }

    @Test
    void testAddPieces() {
        try {
            board.addPiece(4, "X");
            board.addPiece(2, "O");
            board.addPiece(4, "X");
            assertEquals("424", board.getRecord());
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
    }
}
