package io.github.aliceboursin.morpionsolitaire.domain;

/**
 * Represents an immutable position on the game grid.
 *
 * @param x the row coordinate
 * @param y the column coordinate
 */
public record Point(int x, int y) {
}