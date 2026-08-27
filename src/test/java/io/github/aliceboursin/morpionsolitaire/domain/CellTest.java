package io.github.aliceboursin.morpionsolitaire.domain;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {

    @Test
    void shouldBeEmptyByDefault() {
        Cell cell = new Cell(new Point(2, 3));

        assertEquals(CellStatus.EMPTY, cell.getStatus());
    }

    @Test
    void shouldKeepItsPosition() {
        Point position = new Point(2, 3);

        Cell cell = new Cell(position);

        assertEquals(position, cell.getPosition());
    }

    @Test
    void shouldBecomeOccupied() {
        Cell cell = new Cell(new Point(2, 3));

        cell.occupy();

        assertTrue(cell.isOccupied());
        assertEquals(CellStatus.OCCUPIED, cell.getStatus());
    }

    @Test
    void shouldIncrementDirectionUseCount() {
        Cell cell = new Cell(new Point(2, 3));

        cell.incrementDirectionUse(Direction.HORIZONTAL);
        cell.incrementDirectionUse(Direction.HORIZONTAL);

        assertEquals(2, cell.getDirectionUseCount(Direction.HORIZONTAL));
        assertTrue(cell.isUsedIn(Direction.HORIZONTAL));
    }

    @Test
    void shouldTrackDirectionsIndependently() {
        Cell cell = new Cell(new Point(2, 3));

        cell.incrementDirectionUse(Direction.HORIZONTAL);
        cell.incrementDirectionUse(Direction.VERTICAL);
        cell.incrementDirectionUse(Direction.VERTICAL);

        assertEquals(1, cell.getDirectionUseCount(Direction.HORIZONTAL));
        assertEquals(2, cell.getDirectionUseCount(Direction.VERTICAL));
    }

    @Test
    void shouldDecrementDirectionUseCount() {
        Cell cell = new Cell(new Point(2, 3));
        cell.incrementDirectionUse(Direction.HORIZONTAL);
        cell.incrementDirectionUse(Direction.HORIZONTAL);

        cell.decrementDirectionUse(Direction.HORIZONTAL);

        assertEquals(1, cell.getDirectionUseCount(Direction.HORIZONTAL));
        assertTrue(cell.isUsedIn(Direction.HORIZONTAL));
    }

    @Test
    void shouldRemoveDirectionWhenCountReachesZero() {
        Cell cell = new Cell(new Point(2, 3));
        cell.incrementDirectionUse(Direction.HORIZONTAL);

        cell.decrementDirectionUse(Direction.HORIZONTAL);

        assertEquals(0, cell.getDirectionUseCount(Direction.HORIZONTAL));
        assertFalse(cell.isUsedIn(Direction.HORIZONTAL));
    }

    @Test
    void shouldRejectDecrementOfUnusedDirection() {
        Cell cell = new Cell(new Point(2, 3));

        assertThrows(
                IllegalStateException.class,
                () -> cell.decrementDirectionUse(Direction.HORIZONTAL)
        );
    }

    @Test
    void shouldNotExposeMutableDirectionUseCounts() {
        Cell cell = new Cell(new Point(2, 3));
        cell.incrementDirectionUse(Direction.HORIZONTAL);

        Map<Direction, Integer> counts = cell.getDirectionUseCounts();

        assertThrows(
                UnsupportedOperationException.class,
                () -> counts.put(Direction.VERTICAL, 1)
        );
    }
}