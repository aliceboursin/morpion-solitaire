package io.github.aliceboursin.morpionsolitaire.game.rules;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Cell;
import io.github.aliceboursin.morpionsolitaire.domain.Direction;
import io.github.aliceboursin.morpionsolitaire.domain.Line;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.GameState;
import io.github.aliceboursin.morpionsolitaire.game.Move;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public abstract class AbstractFiveRules implements GameRules {

    @Override
    public boolean isLegal(GameState state, Move move) {
        Board board = state.getBoard();
        Point playedPoint = move.point();
        Line line = move.line();

        // The played point must belong to the selected five-point line.
        if (!line.contains(playedPoint)) {
            return false;
        }

        // A move must add exactly one new point.
        // Therefore, the played position must be empty before the move.
        if (board.isOccupied(playedPoint)) {
            return false;
        }

        // The move must complete an existing four-point pattern.
        // Every point of the line except the newly played point must already be occupied.
        for (Point point : line.points()) {
            if (!point.equals(playedPoint) && !board.isOccupied(point)) {
                return false;
            }
        }

        // A newly placed line must not overlap an existing line.
        // Sharing two or more points means that both line segments overlap.
        for (Line placedLine : state.getPlacedLines()) {
            if (line.countCommonPoints(placedLine) >= 2) {
                return false;
            }
        }

        //NB: Line validity (five consecutive aligned points) is guaranteed by the immutable Line representation itself.

        // Apply the additional constraints of the selected variant.
        return respectsVariantRules(state, move);
    }

    protected abstract boolean respectsVariantRules(
            GameState state,
            Move move
    );

    private Set<Point> findCandidatePoints(Board board) {
        Set<Point> candidates = new HashSet<>();

        for (Cell cell : board.getCells()) {
            Point occupiedPoint = cell.getPosition();

            for (Direction direction : Direction.values()) {
                Point before = direction.move(occupiedPoint, -1);
                Point after = direction.move(occupiedPoint, 1);

                if (!board.isOccupied(before)) {
                    candidates.add(before);
                }

                if (!board.isOccupied(after)) {
                    candidates.add(after);
                }
            }
        }

        return candidates;
    }

    private List<Line> findPossibleLines(Point candidate) {
        List<Line> lines = new ArrayList<>();

        for (Direction direction : Direction.values()) {
            for (int offset = 0; offset < Line.LENGTH; offset++) {
                Point start = direction.move(candidate, -offset);
                lines.add(new Line(start, direction));
            }
        }

        return lines;
    }

    @Override
    public List<Move> findLegalMoves(GameState state) {
        List<Move> legalMoves = new ArrayList<>();

        // A legal move must add a new point next to at least one already occupied point.
        Set<Point> candidatePoints = findCandidatePoints(state.getBoard());

        // For each candidate point, examine every five-point line that could contain it.
        for (Point candidate : candidatePoints) {
            for (Line line : findPossibleLines(candidate)) {
                Move move = new Move(candidate, line);

                // Keep every valid point/line combination.
                // The same point may therefore produce several legal moves, including several moves in the same direction.
                if (isLegal(state, move)) {
                    legalMoves.add(move);
                }
            }
        }

        return legalMoves;
    }
}