package io.github.aliceboursin.morpionsolitaire.game;

import io.github.aliceboursin.morpionsolitaire.domain.Direction;
import io.github.aliceboursin.morpionsolitaire.domain.Line;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void shouldStartWithEmptyBoard() {
        GameState state = new GameState();

        assertTrue(state.getBoard().getCells().isEmpty());
    }

    @Test
    void shouldStartWithNoPlacedLines() {
        GameState state = new GameState();

        assertTrue(state.getPlacedLines().isEmpty());
    }

    @Test
    void shouldStartWithZeroScore() {
        GameState state = new GameState();

        assertEquals(0, state.getScore());
    }

    @Test
    void shouldAddPlacedLine() {
        GameState state = new GameState();
        Line line = new Line(
                new Point(0, 0),
                Direction.HORIZONTAL
        );

        state.addPlacedLine(line);

        assertEquals(List.of(line), state.getPlacedLines());
    }

    @Test
    void shouldIncrementScore() {
        GameState state = new GameState();

        state.incrementScore();
        state.incrementScore();

        assertEquals(2, state.getScore());
    }

    @Test
    void shouldNotExposeMutablePlacedLines() {
        GameState state = new GameState();
        Line line = new Line(
                new Point(0, 0),
                Direction.HORIZONTAL
        );

        state.addPlacedLine(line);

        assertThrows(
                UnsupportedOperationException.class,
                () -> state.getPlacedLines().clear()
        );

        assertEquals(1, state.getPlacedLines().size());
    }
}