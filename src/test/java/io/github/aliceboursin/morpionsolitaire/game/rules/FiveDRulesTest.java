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

class FiveDRulesTest extends RulesTestSupport {

    private final FiveDRules rules = new FiveDRules();

    @Test
    void shouldAllowMoveWhenDirectionHasNotBeenUsed() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );

        Move move = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.HORIZONTAL)
        );

        assertTrue(rules.isLegal(state, move));
    }

    @Test
    void shouldRejectReuseOfPointInSameDirection() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );

        state.getBoard()
                .getCell(new Point(0, 2))
                .incrementDirectionUse(Direction.HORIZONTAL);;

        Move move = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.HORIZONTAL)
        );

        assertFalse(rules.isLegal(state, move));
    }

    @Test
    void shouldAllowPointUsedInDifferentDirection() {
        GameState state = stateWithOccupiedPoints(
                new Point(0, 0),
                new Point(0, 1),
                new Point(0, 2),
                new Point(0, 3)
        );

        state.getBoard()
                .getCell(new Point(0, 2))
                .incrementDirectionUse(Direction.VERTICAL);

        Move move = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.HORIZONTAL)
        );

        assertTrue(rules.isLegal(state, move));
    }

    @Test
    void shouldAllowLinesToCrossAtSinglePointInDifferentDirections() {
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
                new Line(new Point(0, 0), Direction.HORIZONTAL)
        );

        state.getBoard()
                .getCell(new Point(0, 2))
                .incrementDirectionUse(Direction.HORIZONTAL);

        Move move = new Move(
                new Point(2, 2),
                new Line(new Point(-2, 2), Direction.VERTICAL)
        );

        assertTrue(rules.isLegal(state, move));
    }

    @Test
    void shouldRejectSameDirectionLinesTouchingAtSinglePointInFiveD() {
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
                new Line(new Point(0, 0), Direction.HORIZONTAL)
        );

        state.getBoard()
                .getCell(new Point(0, 4))
                .incrementDirectionUse(Direction.HORIZONTAL);

        Move move = new Move(
                new Point(0, 8),
                new Line(new Point(0, 4), Direction.HORIZONTAL)
        );

        assertFalse(rules.isLegal(state, move));
    }

    @Test
    void shouldNotFindMoveReusingSameDirectionInFiveD() {
        GameState state = new GameState();

        state.getBoard().occupy(new Point(0, 0));
        state.getBoard().occupy(new Point(0, 1));
        state.getBoard().occupy(new Point(0, 2));
        state.getBoard().occupy(new Point(0, 3));

        state.getBoard()
                .getCell(new Point(0, 2))
                .incrementDirectionUse(Direction.HORIZONTAL);

        Move forbiddenMove = new Move(
                new Point(0, 4),
                new Line(new Point(0, 0), Direction.HORIZONTAL)
        );

        List<Move> moves = rules.findLegalMoves(state);

        assertFalse(moves.contains(forbiddenMove));
    }
}