package io.github.aliceboursin.morpionsolitaire.game.setup;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StandardSetupTest {

    @Test
    void shouldApplyStandardInitialSetup() {
        Board board = new Board();

        StandardSetup.apply(board);

        assertEquals(36, board.getCells().size());

        // Outer bounds of the initial setup.
        assertTrue(board.isOccupied(new Point(0, 3)));
        assertTrue(board.isOccupied(new Point(9, 6)));
        assertTrue(board.isOccupied(new Point(3, 0)));
        assertTrue(board.isOccupied(new Point(6, 9)));

        // Positions inside the hollow cross must remain empty.
        assertFalse(board.isOccupied(new Point(4, 4)));
        assertFalse(board.isOccupied(new Point(5, 5)));

        // The origin is the lower-left corner of the bounding square,
        // not an occupied starting point.
        assertFalse(board.isOccupied(new Point(0, 0)));
    }
}