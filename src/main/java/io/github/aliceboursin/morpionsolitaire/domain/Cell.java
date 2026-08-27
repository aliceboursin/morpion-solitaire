package io.github.aliceboursin.morpionsolitaire.domain;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Represents the state of a position on the game board.
 */
public class Cell {

    private final Point position;
    private CellStatus status;
    private final EnumMap<Direction, Integer> directionUseCount;

    public Cell(Point position) {
        this.position = position;
        this.status = CellStatus.EMPTY;
        this.directionUseCount = new EnumMap<>(Direction.class);
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
        directionUseCount.clear();
    }

    /**
     * Returns how many placed lines use this cell in the given direction.
     */
    public int getDirectionUseCount(Direction direction) {
        return directionUseCount.getOrDefault(direction, 0);
    }

    /**
     * Records that one additional placed line uses this cell
     * in the given direction.
     */
    public void incrementDirectionUse(Direction direction) {
        directionUseCount.merge(direction, 1, Integer::sum);
    }

    /**
     * Removes one usage of this cell in the given direction.
     *
     * @throws IllegalStateException if the direction is not currently used
     */
    public void decrementDirectionUse(Direction direction) {
        int currentCount = getDirectionUseCount(direction);

        if (currentCount == 0) {
            throw new IllegalStateException(
                    "Direction " + direction + " is not used at " + position
            );
        }

        if (currentCount == 1) {
            directionUseCount.remove(direction);
        } else {
            directionUseCount.put(direction, currentCount - 1);
        }
    }

    public boolean isUsedIn(Direction direction) {
        return getDirectionUseCount(direction) > 0;
    }

    /**
     * Returns an unmodifiable view of the direction usage counts.
     */
    public Map<Direction, Integer> getDirectionUseCounts() {
        return Collections.unmodifiableMap(directionUseCount);
    }
}