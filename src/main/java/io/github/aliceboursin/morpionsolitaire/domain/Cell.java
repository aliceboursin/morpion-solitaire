package io.github.aliceboursin.morpionsolitaire.domain;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Represents the state of a position on the game board.
 */
public class Cell {

    private final Point position;
    private CellStatus status;
    private final EnumSet<Direction> usedDirections;

    public Cell(Point position) {
        this.position = position;
        this.status = CellStatus.EMPTY;
        this.usedDirections = EnumSet.noneOf(Direction.class);
    }

    public Point getPosition() {
        return position;
    }

    public CellStatus getStatus() {
        return status;
    }

    public boolean isOccupied() {
        return status == CellStatus.OCCUPIED;
    }

    public void occupy() {
        status = CellStatus.OCCUPIED;
    }

    public void clear() {
        status = CellStatus.EMPTY;
        usedDirections.clear();
    }

    public Set<Direction> getUsedDirections() {
        return Collections.unmodifiableSet(usedDirections);
    }

    public void addUsedDirection(Direction direction) {
        usedDirections.add(direction);
    }

    public void removeUsedDirection(Direction direction) {
        usedDirections.remove(direction);
    }

    public boolean isUsedIn(Direction direction) {
        return usedDirections.contains(direction);
    }
}