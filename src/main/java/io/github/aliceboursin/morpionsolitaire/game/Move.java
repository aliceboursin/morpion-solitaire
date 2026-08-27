package io.github.aliceboursin.morpionsolitaire.game;

import io.github.aliceboursin.morpionsolitaire.domain.Line;
import io.github.aliceboursin.morpionsolitaire.domain.Point;

/**
 * Represents a move in a Morpion Solitaire game.
 *
 * @param point the new point added by the move
 * @param line the five-point line completed by the move
 */
public record Move(Point point, Line line) {
}