package tests;

import model.Board;
import model.HardBot;
import model.Person;
import model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class HardBotTest {
    private Player playerO;
    private HardBot playerX;
    private Board board;

    @BeforeEach
    void setUp() {
        playerO = new Person("O", board);
        playerX = new HardBot("X", board);
        board = new Board();
    }

    @Test
    void testGetScoresD1() {
        List<Integer> getScores = playerX.getScores("4433", 0, 1);
        assertEquals(7, getScores.size());
        for (int i : getScores) {
            System.out.println(i);
        }
    }

    @Test
    void testGetScoresD5() {
        List<Integer> getScores = playerX.getScores("4433", 0, 5);
        assertEquals(16807, getScores.size());
        for (int i : getScores) {
            System.out.println(i);
        }
    }

    @Test
    void testScore4433() {
        List<Integer> scores = new ArrayList<>();
        String rec = "4433";
        for (int i = 0; i < 7; i++) {
            String s = rec.concat(String.valueOf(i));
            assertEquals("4433", rec);
            board.copyBoard(s);
            board.printBoard();
            scores.add(playerX.score(board));
        }
        for (int i : scores) {
            System.out.println(i);
        }
    }

    @Test
    void testTimeD5() {
        long start = System.currentTimeMillis();
        List<Integer> getScores = playerX.getScores("", 0, 5);
        long elapsed = System.currentTimeMillis() - start;
        System.out.println(elapsed);
    }

}

