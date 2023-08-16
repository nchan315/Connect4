package tests;

import model.Board;
import model.Person;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.*;

import static org.junit.Assert.assertEquals;

// Not a complete test suite, just testing what gives me pain
public class PlayerTest {
    private Player playerX;
    private Player playerO;
    private Board board;

    @BeforeEach
    void setup() {
        board = new Board();
        playerX = new Person("X", board);
        playerO = new Person("O", board);
    }

    @Test
    void testGetOpponentPiece() {
        assertEquals("X", playerO.getOpponentPiece());
        assertEquals("O", playerX.getOpponentPiece());
    }
}