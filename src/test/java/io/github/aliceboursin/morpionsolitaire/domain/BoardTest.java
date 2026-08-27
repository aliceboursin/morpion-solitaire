package io.github.aliceboursin.morpionsolitaire.domain;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void shouldConsiderUnknownPositionEmpty() {
        Board board = new Board();

        assertFalse(board.isOccupied(new Point(100, -50)));
    }

    @Test
    void shouldReturnNullForUnknownCell() {
        Board board = new Board();

        assertNull(board.getCell(new Point(4, 7)));
    }

    @Test
    void shouldCreateCellOnDemand() {
        Board board = new Board();
        Point position = new Point(4, 7);

        Cell cell = board.getOrCreateCell(position);

        assertNotNull(cell);
        assertEquals(position, cell.getPosition());
        assertEquals(CellStatus.EMPTY, cell.getStatus());
    }

    @Test
    void shouldReturnSameCellForSamePosition() {
        Board board = new Board();
        Point position = new Point(4, 7);

        Cell first = board.getOrCreateCell(position);
        Cell second = board.getOrCreateCell(position);

        assertSame(first, second);
    }

    @Test
    void shouldCreateDifferentCellsForDifferentPositions() {
        Board board = new Board();

        Cell first = board.getOrCreateCell(new Point(4, 7));
        Cell second = board.getOrCreateCell(new Point(4, 8));

        assertNotSame(first, second);
    }

    @Test
    void shouldOccupyPosition() {
        Board board = new Board();
        Point position = new Point(4, 7);

        board.occupy(position);

        assertTrue(board.isOccupied(position));
        assertTrue(board.getCell(position).isOccupied());
    }

    @Test
    void shouldSupportNegativeCoordinates() {
        Board board = new Board();
        Point position = new Point(-10, -25);

        board.occupy(position);

        assertTrue(board.isOccupied(position));
    }

    @Test
    void shouldSupportCoordinatesBeyondOriginalGridSize() {
        Board board = new Board();
        Point position = new Point(100, 250);

        board.occupy(position);

        assertTrue(board.isOccupied(position));
    }

    @Test
    void shouldStoreOnlyCreatedCells() {
        Board board = new Board();

        board.getOrCreateCell(new Point(1, 1));
        board.getOrCreateCell(new Point(50, 50));

        assertEquals(2, board.getCells().size());
    }

    @Test
    void shouldNotExposeMutableCellCollection() {
        Board board = new Board();
        board.getOrCreateCell(new Point(1, 1));

        Collection<Cell> cells = board.getCells();

        assertThrows(
                UnsupportedOperationException.class,
                cells::clear
        );

        assertEquals(1, board.getCells().size());
    }
}