package io.github.aliceboursin.morpionsolitaire.domain;

import java.util.List;
import java.util.stream.IntStream;

/**
 * Represents a five-point line on the Morpion Solitaire board.
 */
public record Line(Point start, Direction direction) {

    public static final int LENGTH = 5;

    public List<Point> points() {
        return IntStream.range(0, LENGTH)
                .mapToObj(distance -> direction.move(start, distance))
                .toList();
    }

    public Point end() {
        return direction.move(start, LENGTH - 1);
    }

    public boolean contains(Point point) {
        return points().contains(point);
    }

    public long countCommonPoints(Line other) {
        return points().stream()
                .filter(other::contains)
                .count();
    }
}