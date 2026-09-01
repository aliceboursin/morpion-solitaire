package io.github.aliceboursin.morpionsolitaire.game;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Line;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the complete state of a Morpion Solitaire game
 * at a given point in time.
 */
public class GameState {

    private final Board board;
    private final List<Line> placedLines;
    private int score;

    public GameState() {
        this.board = new Board();
        this.placedLines = new ArrayList<>();
        this.score = 0;
    }

    public Board getBoard() {
        return board;
    }

    public List<Line> getPlacedLines() {
        return Collections.unmodifiableList(placedLines);
    }

    public int getScore() {
        return score;
    }

    public void addPlacedLine(Line line) {
        placedLines.add(line);
    }

    public void incrementScore() {
        score++;
    }

    public void removePlacedLine(Line line) {
        placedLines.remove(line);
    }

    public void decrementScore() {
        score--;
    }
}
