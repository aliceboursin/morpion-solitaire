package io.github.aliceboursin.morpionsolitaire.game.setup;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Point;

import java.util.Set;

/**
 * Defines the standard initial configuration of Morpion Solitaire.
 *
 * The initial setup contains 36 occupied points arranged in a hollow cross.
 * Its bounding square ranges from (0, 0) to (9, 9).
 */
public final class StandardSetup {

    private static final Set<Point> INITIAL_POINTS = Set.of(
            // Left side
            new Point(0, 3),
            new Point(0, 4),
            new Point(0, 5),
            new Point(0, 6),

            new Point(1, 3),
            new Point(1, 6),

            new Point(2, 3),
            new Point(2, 6),

            // Lower and upper parts of the left-center section
            new Point(3, 0),
            new Point(3, 1),
            new Point(3, 2),
            new Point(3, 3),
            new Point(3, 6),
            new Point(3, 7),
            new Point(3, 8),
            new Point(3, 9),

            new Point(4, 0),
            new Point(4, 9),

            new Point(5, 0),
            new Point(5, 9),

            // Lower and upper parts of the right-center section
            new Point(6, 0),
            new Point(6, 1),
            new Point(6, 2),
            new Point(6, 3),
            new Point(6, 6),
            new Point(6, 7),
            new Point(6, 8),
            new Point(6, 9),

            new Point(7, 3),
            new Point(7, 6),

            new Point(8, 3),
            new Point(8, 6),

            // Right side
            new Point(9, 3),
            new Point(9, 4),
            new Point(9, 5),
            new Point(9, 6)
    );

    private StandardSetup() {}

    /**
     * Applies the standard 36-point starting configuration to a board.
     *
     * @param board board to initialize
     */
    public static void apply(Board board) {
        for (Point point : INITIAL_POINTS) {
            board.occupy(point);
        }
    }
}