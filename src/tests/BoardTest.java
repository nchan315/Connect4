package tests;

import model.Board;
import model.exceptions.FullColumnException;
import model.exceptions.InvalidColumnException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.fail;

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
        try {
            board.addPiece(0, "O");
            board.addPiece(0, "X");
            board.addPiece(4, "O");
            board.addPiece(5, "X");
            assertEquals("0045", board.getRecord());

            Board newBoard = new Board();
            newBoard.copyBoard(board.getRecord());
            assertEquals("O", newBoard.getPiece(35));
            assertEquals("X", newBoard.getPiece(28));
            assertEquals("O", newBoard.getPiece(39));
            assertEquals("X", newBoard.getPiece(40));
            assertEquals("0045", newBoard.getRecord());
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
    }

    @Test
    void testGetTurns() {
        try {
            assertEquals(0, board.getTurns());
            board.addPiece(0, "0");
            assertEquals(1, board.getTurns());
            board.addPiece(0, "X");
            assertEquals(1, board.getTurns());
            board.addPiece(0, "O");
            assertEquals(2, board.getTurns());
        } catch (InvalidColumnException e) {
            fail();
        } catch (FullColumnException e) {
            fail();
        }
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
        for (int i = 1; i < 6; i++) {
            temp.add(i);
        }
        try {
            for (int i = 1; i <= 6; i++) {
                board.addPiece(0, "X");
                board.addPiece(6, "O");
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
