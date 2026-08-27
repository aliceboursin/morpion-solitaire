package io.github.aliceboursin.morpionsolitaire.game.rules;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Cell;
import io.github.aliceboursin.morpionsolitaire.domain.Direction;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.GameState;
import io.github.aliceboursin.morpionsolitaire.game.Move;

public class FiveDRules extends AbstractFiveRules {

    @Override
    protected boolean respectsVariantRules(
            GameState state,
            Move move
    ) {
        Board board = state.getBoard();
        Direction direction = move.line().direction();

        // A point already used by a placed line cannot be reused by another line in the same direction.
        for (Point point : move.line().points()) {
            Cell cell = board.getCell(point);

            if (cell != null && cell.isUsedIn(direction)) {
                return false;
            }
        }

        return true;
    }
}