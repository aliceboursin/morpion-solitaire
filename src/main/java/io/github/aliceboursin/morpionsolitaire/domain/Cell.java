package io.github.aliceboursin.morpionsolitaire.domain;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Represents the state of a position on the game grid.
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

    public void setStatus(CellStatus status) {
        this.status = status;
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