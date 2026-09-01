package io.github.aliceboursin.morpionsolitaire.game;

import io.github.aliceboursin.morpionsolitaire.domain.Cell;
import io.github.aliceboursin.morpionsolitaire.domain.Direction;
import io.github.aliceboursin.morpionsolitaire.domain.Line;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.rules.FiveTRules;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    void shouldStartWithStandardSetup() {
        Game game = new Game(new FiveTRules());

        assertEquals(36, game.getState().getBoard().getCells().size());
        assertEquals(0, game.getState().getScore());
        assertTrue(game.getState().getPlacedLines().isEmpty());
        assertFalse(game.canUndo());
    }

    @Test
    void shouldPlayLegalMove() {
        Game game = new Game(new FiveTRules());

        Move move = game.getLegalMoves().getFirst();

        game.play(move);

        GameState state = game.getState();

        assertTrue(state.getBoard().isOccupied(move.point()));
        assertTrue(state.getPlacedLines().contains(move.line()));
        assertEquals(1, state.getScore());
        assertTrue(game.canUndo());

        Direction direction = move.line().direction();

        for (Point point : move.line().points()) {
            Cell cell = state.getBoard().getCell(point);

            assertNotNull(cell);
            assertEquals(1, cell.getDirectionUseCount(direction));
        }
    }

    @Test
    void shouldRejectIllegalMove() {
        Game game = new Game(new FiveTRules());

        Point point = new Point(100, 100);
        Line line = new Line(point, Direction.HORIZONTAL);
        Move move = new Move(point, line);

        assertThrows(
                IllegalArgumentException.class,
                () -> game.play(move)
        );

        assertEquals(0, game.getState().getScore());
        assertTrue(game.getState().getPlacedLines().isEmpty());
        assertFalse(game.canUndo());
    }

    @Test
    void shouldUndoLastMove() {
        Game game = new Game(new FiveTRules());

        Move move = game.getLegalMoves().getFirst();
        Direction direction = move.line().direction();

        game.play(move);
        game.undo();

        GameState state = game.getState();

        // The game-level state is restored.
        assertEquals(0, state.getScore());
        assertFalse(state.getPlacedLines().contains(move.line()));
        assertFalse(game.canUndo());

        // The point added by the move no longer exists on the sparse board.
        assertNull(state.getBoard().getCell(move.point()));
        assertFalse(state.getBoard().isOccupied(move.point()));

        // The direction usage introduced by the move has been removed from the four original points.
        for (Point point : move.line().points()) {
            if (!point.equals(move.point())) {
                Cell cell = state.getBoard().getCell(point);

                assertNotNull(cell);
                assertEquals(0, cell.getDirectionUseCount(direction));
            }
        }
    }

    @Test
    void shouldRejectUndoWhenNoMoveHasBeenPlayed() {
        Game game = new Game(new FiveTRules());

        assertThrows(
                IllegalStateException.class,
                game::undo
        );

        assertEquals(0, game.getState().getScore());
        assertFalse(game.canUndo());
    }

    @Test
    void shouldUndoOnlyLastMove() {
        Game game = new Game(new FiveTRules());

        Move firstMove = game.getLegalMoves().getFirst();
        game.play(firstMove);

        Move secondMove = game.getLegalMoves().getFirst();
        game.play(secondMove);

        game.undo();

        GameState state = game.getState();

        assertEquals(1, state.getScore());

        assertTrue(state.getBoard().isOccupied(firstMove.point()));
        assertTrue(state.getPlacedLines().contains(firstMove.line()));

        assertFalse(state.getBoard().isOccupied(secondMove.point()));
        assertFalse(state.getPlacedLines().contains(secondMove.line()));

        assertTrue(game.canUndo());
    }
}