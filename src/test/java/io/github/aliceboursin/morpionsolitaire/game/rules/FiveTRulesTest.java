package io.github.aliceboursin.morpionsolitaire.game.rules;

import io.github.aliceboursin.morpionsolitaire.domain.Direction;
import io.github.aliceboursin.morpionsolitaire.domain.Line;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.GameState;
import io.github.aliceboursin.morpionsolitaire.game.Move;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FiveTRulesTest extends RulesTestSupport {

    private final FiveTRules rules = new FiveTRules();

    @Test
    void shouldAllowMoveCompletingLineWithFourOccupiedPoints() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );

        Move move = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        assertTrue(rules.isLegal(state, move));
    }

    @Test
    void shouldRejectMoveWhenPlayedPointIsNotOnSelectedLine() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );

        Move move = new Move(
                new Point(10, 10),
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        assertFalse(rules.isLegal(state, move));
    }

    @Test
    void shouldRejectAlreadyOccupiedPlayedPoint() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4)
        );

        Move move = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        assertFalse(rules.isLegal(state, move));
    }

    @Test
    void shouldRejectMoveWhenAnotherPointInLineIsEmpty() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2)
        );

        Move move = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        assertFalse(rules.isLegal(state, move));
    }

    @Test
    void shouldRejectLineOverlappingExistingLineByTwoOrMorePoints() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4)
        );

        state.addPlacedLine(
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        Move move = new Move(
                new Point(0, 5),
                new Line(new Point(0, 1), Direction.VERTICAL)
        );

        assertFalse(rules.isLegal(state, move));
    }

    @Test
    void shouldAllowLinesToCrossAtSinglePoint() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4),

                new Point(-2, 2),
                new Point(-1, 2),
                new Point(1, 2)
        );

        state.addPlacedLine(
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        Move move = new Move(
                new Point(2, 2),
                new Line(new Point(-2, 2), Direction.HORIZONTAL)
        );

        assertTrue(rules.isLegal(state, move));
    }

    @Test
    void shouldAllowSameDirectionLinesToTouchAtSinglePointInFiveT() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3),
                new Point(0, 4),
                new Point(0, 5),
                new Point(0, 6),
                new Point(0, 7)
        );

        state.addPlacedLine(
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        Move move = new Move(
                new Point(0, 8),
                new Line(new Point(0, 4), Direction.VERTICAL)
        );

        assertTrue(rules.isLegal(state, move));
    }

    @Test
    void shouldFindNoLegalMoveWhenNotEnoughPointsAreOccupied() {
        GameState state = new GameState();
        state.getBoard().occupy(new Point(0, 0));

        List<Move> moves = rules.findLegalMoves(state);

        assertTrue(moves.isEmpty());
    }

    @Test
    void shouldFindLegalMoveAtEndOfLine() {
        GameState state = new GameState();
        state.getBoard().occupy(new Point(0, 0));
        state.getBoard().occupy(new Point(0, 1));
        state.getBoard().occupy(new Point(0, 2));
        state.getBoard().occupy(new Point(0, 3));

        Move expectedMove = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        List<Move> moves = rules.findLegalMoves(state);

        assertTrue(moves.contains(expectedMove));
    }

    @Test
    void shouldFindLegalMoveWhenNewPointIsInsideLine() {
        GameState state = new GameState();
        state.getBoard().occupy(new Point(0, 0));
        state.getBoard().occupy(new Point(0, 1));
        state.getBoard().occupy(new Point(0, 3));
        state.getBoard().occupy(new Point(0, 4));

        Move expectedMove = new Move(
                new Point(0, 2),
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        List<Move> moves = rules.findLegalMoves(state);

        assertTrue(moves.contains(expectedMove));
    }

    @Test
    void shouldFindMultipleLinesForSamePointInSameDirection() {
        GameState state = new GameState();
        state.getBoard().occupy(new Point(0, 0));
        state.getBoard().occupy(new Point(0, 1));
        state.getBoard().occupy(new Point(0, 3));
        state.getBoard().occupy(new Point(0, 4));
        state.getBoard().occupy(new Point(0, 5));

        Point playedPoint = new Point(0, 2);

        Move firstMove = new Move(
                playedPoint,
                new Line(new Point(0, 0), Direction.VERTICAL)
        );

        Move secondMove = new Move(
                playedPoint,
                new Line(new Point(0, 1), Direction.VERTICAL)
        );

        List<Move> moves = rules.findLegalMoves(state);

        assertTrue(moves.contains(firstMove));
        assertTrue(moves.contains(secondMove));
    }

    @Test
    void shouldFindMultipleMovesForSamePointInDifferentDirections() {
        GameState state = new GameState();

        // Vertical line around the future point (0, 0).
        state.getBoard().occupy(new Point(0, -2));
        state.getBoard().occupy(new Point(0, -1));
        state.getBoard().occupy(new Point(0, 1));
        state.getBoard().occupy(new Point(0, 2));

        // Horizontal line around the same future point.
        state.getBoard().occupy(new Point(-2, 0));
        state.getBoard().occupy(new Point(-1, 0));
        state.getBoard().occupy(new Point(1, 0));
        state.getBoard().occupy(new Point(2, 0));

        Point playedPoint = new Point(0, 0);

        Move verticalMove = new Move(
                playedPoint,
                new Line(new Point(0, -2), Direction.VERTICAL)
        );

        Move horizontalMove = new Move(
                playedPoint,
                new Line(new Point(-2, 0), Direction.HORIZONTAL)
        );

        List<Move> moves = rules.findLegalMoves(state);

        assertTrue(moves.contains(verticalMove));
        assertTrue(moves.contains(horizontalMove));
    }
}