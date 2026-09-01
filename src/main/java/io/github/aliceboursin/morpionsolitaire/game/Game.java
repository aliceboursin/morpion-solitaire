package io.github.aliceboursin.morpionsolitaire.game;

import io.github.aliceboursin.morpionsolitaire.domain.Cell;
import io.github.aliceboursin.morpionsolitaire.domain.Direction;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.rules.GameRules;
import io.github.aliceboursin.morpionsolitaire.game.setup.StandardSetup;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Manages the lifecycle of a Morpion Solitaire game.
 * A Game owns the current state and delegates move validation to the selected game rules.
 */
public class Game {

    private final GameRules rules;
    private final GameState state;
    private final Deque<Move> moveHistory;

    public Game(GameRules rules) {
        this.rules = rules;
        this.state = new GameState();
        this.moveHistory = new ArrayDeque<>();

        StandardSetup.apply(state.getBoard());
    }

    public GameState getState() {
        return state;
    }

    public List<Move> getLegalMoves() {
        return rules.findLegalMoves(state);
    }

    public void play(Move move) {
        if (!rules.isLegal(state, move)) {
            throw new IllegalArgumentException("Cannot play an illegal move.");
        }

        // Add the new point to the board.
        state.getBoard().occupy(move.point());

        Direction direction = move.line().direction();

        // Record that every cell of the line is now used
        // by one additional line in this direction.
        for (Point point : move.line().points()) {
            Cell cell = state.getBoard().getCell(point);
            cell.incrementDirectionUse(direction);
        }

        // Keep track of the line as part of the current game state.
        state.addPlacedLine(move.line());

        // One successfully placed line equals one point.
        state.incrementScore();

        // Keep the move so it can later be undone.
        moveHistory.push(move);

    }

    public boolean canUndo() {
        return !moveHistory.isEmpty();
    }

    public void undo() {
        if (!canUndo()) {
            throw new IllegalStateException("No move to undo.");
        }

        Move move = moveHistory.pop();
        Direction direction = move.line().direction();

        for (Point point : move.line().points()) {
            Cell cell = state.getBoard().getCell(point);
            cell.decrementDirectionUse(direction);
        }

        state.removePlacedLine(move.line());
        state.decrementScore();
        state.getBoard().remove(move.point());
    }
}