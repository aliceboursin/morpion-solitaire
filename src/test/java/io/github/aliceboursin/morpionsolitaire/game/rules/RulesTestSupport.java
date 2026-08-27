package io.github.aliceboursin.morpionsolitaire.game.rules;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.GameState;

abstract class RulesTestSupport {

    protected GameState stateWithOccupiedPoints(Point... points) {
        GameState state = new GameState();
        Board board = state.getBoard();

        for (Point point : points) {
            board.occupy(point);
        }

        return state;
    }
}