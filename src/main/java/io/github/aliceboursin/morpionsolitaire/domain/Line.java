package io.github.aliceboursin.morpionsolitaire.domain;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents a five-point line on the Morpion Solitaire grid.
 */
public record Line(Point start, Direction direction) {

    public static final int LENGTH = 5;

    /**
     * Returns the five consecutive points forming this line.
     */
    public List<Point> points() {
        return IntStream.range(0, LENGTH)
                .mapToObj(distance -> direction.move(start, distance))
                .toList();
    }

    /**
     * Returns the last point of the line.
     */
    public Point end() {
        return direction.move(start, LENGTH - 1);
    }

    /**
     * Checks whether the given point belongs to this line.
     */
    public boolean contains(Point point) {
        return points().contains(point);
    }
}