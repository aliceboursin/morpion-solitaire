package io.github.aliceboursin.morpionsolitaire.domain;

/**
 * Represents one of the four possible line directions on the grid
 * as a two-dimensional vector.
 */
public enum Direction {

    HORIZONTAL(0, 1),
    VERTICAL(1, 0),
    DIAGONAL_ASCENDING(1, -1),
    DIAGONAL_DESCENDING(1, 1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the point reached by moving a given distance
     * from the specified point in this direction.
     */
    public Point move(Point point, int distance) {
        return new Point(
                point.x() + distance * dx,
                point.y() + distance * dy
        );
    }
}