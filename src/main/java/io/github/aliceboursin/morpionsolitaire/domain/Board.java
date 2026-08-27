package io.github.aliceboursin.morpionsolitaire.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents the spatial state of a Morpion Solitaire board.
 *
 * <p>The board is conceptually unbounded and uses sparse storage:
 * only cells that have been explicitly created are stored.</p>
 */
public class Board {

    private final Map<Point, Cell> cells = new HashMap<>();

    /**
     * Returns the cell at the given position, creating it if necessary.
     *
     * @param position the position of the cell
     * @return the existing or newly created cell
     */
    public Cell getOrCreateCell(Point position) {
        return cells.computeIfAbsent(position, Cell::new);
    }

    /**
     * Returns the cell at the given position.
     *
     * @param position the position to retrieve
     * @return the cell, or null if no cell has been created at this position
     */
    public Cell getCell(Point position) {
        return cells.get(position);
    }

    /**
     * Checks whether a position is occupied.
     *
     * <p>A position that has never been created is considered empty.</p>
     *
     * @param position the position to check
     * @return true if the position is occupied
     */
    public boolean isOccupied(Point position) {
        Cell cell = cells.get(position);
        return cell != null && cell.isOccupied();
    }

    /**
     * Marks a position as occupied, creating its cell if necessary.
     *
     * @param position the position to occupy
     */
    public void occupy(Point position) {
        getOrCreateCell(position).occupy();
    }

    /**
     * Returns all cells currently stored by the board.
     *
     * @return an unmodifiable collection of cells
     */
    public Collection<Cell> getCells() {
        return Collections.unmodifiableCollection(cells.values());
    }
}