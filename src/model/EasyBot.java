package model;

import java.util.List;

public class EasyBot extends Player {

    public EasyBot(String piece) {
        super(piece);

    }

    @Override
    public void move() {
        List<Integer> empties = board.getEmptySpots();

    }
}
