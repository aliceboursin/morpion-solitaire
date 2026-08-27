package io.github.aliceboursin.morpionsolitaire.game.rules;

import io.github.aliceboursin.morpionsolitaire.game.GameState;
import io.github.aliceboursin.morpionsolitaire.game.Move;

public class FiveTRules extends AbstractFiveRules {

    @Override
    protected boolean respectsVariantRules(
            GameState state,
            Move move
    ) {
        // Lines are allowed to touch at a single point, including when they have the same direction.
        return true;
    }
}