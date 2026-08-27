package io.github.aliceboursin.morpionsolitaire.game.rules;

import io.github.aliceboursin.morpionsolitaire.game.GameState;
import io.github.aliceboursin.morpionsolitaire.game.Move;

import java.util.List;

public interface GameRules {

    boolean isLegal(GameState state, Move move);

    List<Move> findLegalMoves(GameState state);
}