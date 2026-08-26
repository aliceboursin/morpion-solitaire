package io.github.aliceboursin.morpionsolitaire.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LineTest {

    @Test
    void shouldGenerateFiveConsecutivePoints() {
        Line line = new Line(
                new Point(2, 3),
                Direction.HORIZONTAL
        );

        List<Point> expected = List.of(
                new Point(2, 3),
                new Point(2, 4),
                new Point(2, 5),
                new Point(2, 6),
                new Point(2, 7)
        );

        assertEquals(expected, line.points());
    }

    @Test
    void shouldReturnCorrectEndPoint() {
        Line line = new Line(
                new Point(2, 3),
                Direction.VERTICAL
        );

        assertEquals(new Point(6, 3), line.end());
    }

    @Test
    void shouldContainPointBelongingToLine() {
        Line line = new Line(
                new Point(2, 3),
                Direction.DIAGONAL_DESCENDING
        );

        assertTrue(line.contains(new Point(4, 5)));
    }

    @Test
    void shouldNotContainPointOutsideLine() {
        Line line = new Line(
                new Point(2, 3),
                Direction.HORIZONTAL
        );

        assertFalse(line.contains(new Point(3, 4)));
    }

    @Test
    void shouldNotContainPointBeyondLineEnd() {
        Line line = new Line(
                new Point(2, 3),
                Direction.HORIZONTAL
        );

        assertFalse(line.contains(new Point(2, 8)));
    }

    @Test
    void shouldAlwaysContainExactlyFivePoints() {
        Line line = new Line(
                new Point(0, 0),
                Direction.DIAGONAL_ASCENDING
        );

        assertEquals(Line.LENGTH, line.points().size());
        assertEquals(5, Line.LENGTH);
    }
}