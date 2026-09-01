package io.github.aliceboursin.morpionsolitaire.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

    private static final Point ORIGIN = new Point(5, 5);

    @Test
    void shouldMoveHorizontally() {
        Point result = Direction.HORIZONTAL.move(ORIGIN, 2);

        assertEquals(new Point(7, 5), result);
    }

    @Test
    void shouldMoveVertically() {
        Point result = Direction.VERTICAL.move(ORIGIN, 2);

        assertEquals(new Point(5, 7), result);
    }

    @Test
    void shouldMoveAlongAscendingDiagonal() {
        Point result = Direction.DIAGONAL_ASCENDING.move(ORIGIN, 2);

        assertEquals(new Point(7, 7), result);
    }

    @Test
    void shouldMoveAlongDescendingDiagonal() {
        Point result = Direction.DIAGONAL_DESCENDING.move(ORIGIN, 2);

        assertEquals(new Point(7, 3), result);
    }

    @Test
    void shouldMoveInOppositeDirectionWithNegativeDistance() {
        Point result = Direction.HORIZONTAL.move(ORIGIN, -2);

        assertEquals(new Point(3, 5), result);
    }

    @Test
    void shouldNotMoveWithZeroDistance() {
        Point result = Direction.VERTICAL.move(ORIGIN, 0);

        assertEquals(ORIGIN, result);
    }
}