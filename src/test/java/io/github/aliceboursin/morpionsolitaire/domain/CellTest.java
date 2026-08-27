package io.github.aliceboursin.morpionsolitaire.domain;

import org.junit.jupiter.api.Test;

import java.util.Set;

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
    void shouldTrackUsedDirection() {
        Cell cell = new Cell(new Point(2, 3));

        cell.addUsedDirection(Direction.HORIZONTAL);

        assertTrue(cell.isUsedIn(Direction.HORIZONTAL));
    }

    @Test
    void shouldRemoveUsedDirection() {
        Cell cell = new Cell(new Point(2, 3));
        cell.addUsedDirection(Direction.HORIZONTAL);

        cell.removeUsedDirection(Direction.HORIZONTAL);

        assertFalse(cell.isUsedIn(Direction.HORIZONTAL));
    }

    @Test
    void shouldNotStoreDuplicateDirections() {
        Cell cell = new Cell(new Point(2, 3));

        cell.addUsedDirection(Direction.HORIZONTAL);
        cell.addUsedDirection(Direction.HORIZONTAL);

        assertEquals(1, cell.getUsedDirections().size());
    }

    @Test
    void shouldNotExposeMutableUsedDirections() {
        Cell cell = new Cell(new Point(2, 3));
        cell.addUsedDirection(Direction.HORIZONTAL);

        Set<Direction> directions = cell.getUsedDirections();

        assertThrows(
                UnsupportedOperationException.class,
                () -> directions.add(Direction.VERTICAL)
        );

        assertFalse(cell.isUsedIn(Direction.VERTICAL));
    }
}