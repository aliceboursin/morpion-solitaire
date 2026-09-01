package io.github.aliceboursin.morpionsolitaire.console;

import io.github.aliceboursin.morpionsolitaire.domain.Board;
import io.github.aliceboursin.morpionsolitaire.domain.Cell;
import io.github.aliceboursin.morpionsolitaire.domain.Line;
import io.github.aliceboursin.morpionsolitaire.domain.Point;
import io.github.aliceboursin.morpionsolitaire.game.Game;
import io.github.aliceboursin.morpionsolitaire.game.Move;
import io.github.aliceboursin.morpionsolitaire.game.rules.FiveDRules;
import io.github.aliceboursin.morpionsolitaire.game.rules.FiveTRules;
import io.github.aliceboursin.morpionsolitaire.game.rules.GameRules;

import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public final class ConsoleDemo {

    private static final Scanner SCANNER = new Scanner(System.in);

    private static final int CELL_WIDTH = 4;

    private ConsoleDemo() {
        // Utility class.
    }

    public static void main(String[] args) {
        System.out.println("=== Morpion Solitaire ===");
        System.out.println();

        Game game = new Game(chooseRules());

        runGame(game);
    }

    private static GameRules chooseRules() {
        while (true) {
            System.out.println("Choose a game variant:");
            System.out.println("1 - 5T (touching)");
            System.out.println("2 - 5D (disjoint)");
            System.out.print("> ");

            String input = SCANNER.nextLine().trim();

            switch (input) {
                case "1":
                    System.out.println();
                    System.out.println("Starting a 5T game.");
                    return new FiveTRules();

                case "2":
                    System.out.println();
                    System.out.println("Starting a 5D game.");
                    return new FiveDRules();

                default:
                    System.out.println("Invalid choice.");
                    System.out.println();
            }
        }
    }

    private static void runGame(Game game) {
        boolean running = true;

        while (running) {
            displayGame(game);

            if (game.getLegalMoves().isEmpty()) {
                System.out.println("No legal moves remain.");
                System.out.println("Final score: " + game.getState().getScore());
                return;
            }

            displayMenu(game);

            String input = SCANNER.nextLine().trim();

            switch (input.toLowerCase()) {
                case "p":
                    playMove(game);
                    break;

                case "l":
                    displayPlacedLines(game);
                    break;

                case "u":
                    undo(game);
                    break;

                case "q":
                    running = false;
                    break;

                default:
                    System.out.println("Unknown command.");
            }

            System.out.println();
        }

        System.out.println("Game ended.");
        System.out.println("Final score: " + game.getState().getScore());
    }

    private static void displayMenu(Game game) {
        System.out.println();
        System.out.println("Commands:");
        System.out.println("p - Play a move");
        System.out.println("l - Show placed lines");

        if (game.canUndo()) {
            System.out.println("u - Undo last move");
        }

        System.out.println("q - Quit");
        System.out.print("> ");
    }

    private static void displayPlacedLines(Game game) {
        List<Line> lines = game.getState().getPlacedLines();

        System.out.println();

        if (lines.isEmpty()) {
            System.out.println("No lines have been placed yet.");
            return;
        }

        System.out.println("Placed lines:");

        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            Point start = line.start();

            System.out.printf(
                    "%d - start (%d, %d), direction %s%n",
                    i + 1,
                    start.x(),
                    start.y(),
                    line.direction()
            );
        }
    }

    private static void playMove(Game game) {
        List<Move> legalMoves = game.getLegalMoves();

        List<Point> legalPoints = legalMoves.stream()
                .map(Move::point)
                .distinct()
                .sorted(
                        Comparator.comparingInt(Point::y)
                                .thenComparingInt(Point::x)
                )
                .toList();

        System.out.println();
        System.out.println("Legal points:");

        for (int i = 0; i < legalPoints.size(); i++) {
            Point point = legalPoints.get(i);

            long lineCount = legalMoves.stream()
                    .filter(move -> move.point().equals(point))
                    .count();

            System.out.printf(
                    "%d - (%d, %d) [%d possible line%s]%n",
                    i + 1,
                    point.x(),
                    point.y(),
                    lineCount,
                    lineCount > 1 ? "s" : ""
            );
        }

        System.out.println("0 - Cancel");
        System.out.print("> ");

        Integer pointIndex = readIndex(legalPoints.size());

        if (pointIndex == null || pointIndex == 0) {
            return;
        }

        Point selectedPoint = legalPoints.get(pointIndex - 1);

        List<Move> movesForPoint = legalMoves.stream()
                .filter(move -> move.point().equals(selectedPoint))
                .toList();

        Move selectedMove;

        if (movesForPoint.size() == 1) {
            selectedMove = movesForPoint.getFirst();
        } else {
            selectedMove = chooseLine(movesForPoint);

            if (selectedMove == null) {
                return;
            }
        }

        game.play(selectedMove);

        System.out.printf(
                "Played (%d, %d) - %s%n",
                selectedMove.point().x(),
                selectedMove.point().y(),
                describeLine(selectedMove.line())
        );
    }

    private static Move chooseLine(List<Move> moves) {
        System.out.println();
        System.out.println("Several lines are possible for this point:");

        for (int i = 0; i < moves.size(); i++) {
            System.out.printf(
                    "%d - %s%n",
                    i + 1,
                    describeLine(moves.get(i).line())
            );
        }

        System.out.println("0 - Cancel");
        System.out.print("> ");

        Integer index = readIndex(moves.size());

        if (index == null || index == 0) {
            return null;
        }

        return moves.get(index - 1);
    }

    private static String describeLine(Line line) {
        Point start = line.start();

        return String.format(
                "%s from (%d, %d)",
                line.direction(),
                start.x(),
                start.y()
        );
    }

    private static void undo(Game game) {
        if (!game.canUndo()) {
            System.out.println("Nothing to undo.");
            return;
        }

        game.undo();

        System.out.println("Last move undone.");
    }

    private static Integer readIndex(int max) {
        String input = SCANNER.nextLine().trim();

        try {
            int index = Integer.parseInt(input);

            if (index < 0 || index > max) {
                System.out.println("Invalid choice.");
                return null;
            }

            return index;

        } catch (NumberFormatException exception) {
            System.out.println("Please enter a number.");
            return null;
        }
    }

    private static void displayGame(Game game) {
        List<Move> legalMoves = game.getLegalMoves();

        long legalPointCount = legalMoves.stream()
                .map(Move::point)
                .distinct()
                .count();

        System.out.println();
        System.out.println("----------------------------------------");
        System.out.println("Score: " + game.getState().getScore());
        System.out.println("Legal points: " + legalPointCount);
        System.out.println("Legal moves: " + legalMoves.size());
        System.out.println();

        displayBoard(game.getState().getBoard(), legalMoves);
    }

    private static void displayBoard(Board board, List<Move> legalMoves) {
        List<Point> occupiedPoints = board.getCells().stream()
                .filter(Cell::isOccupied)
                .map(Cell::getPosition)
                .toList();

        if (occupiedPoints.isEmpty()) {
            System.out.println("(empty board)");
            return;
        }

        List<Point> legalPoints = legalMoves.stream()
                .map(Move::point)
                .distinct()
                .toList();

        int minX = occupiedPoints.stream()
                .mapToInt(Point::x)
                .min()
                .orElse(0) - 1;

        int maxX = occupiedPoints.stream()
                .mapToInt(Point::x)
                .max()
                .orElse(0) + 1;

        int minY = occupiedPoints.stream()
                .mapToInt(Point::y)
                .min()
                .orElse(0) - 1;

        int maxY = occupiedPoints.stream()
                .mapToInt(Point::y)
                .max()
                .orElse(0) + 1;

        // X-axis coordinates.
        System.out.print("    |");

        for (int x = minX; x <= maxX; x++) {
            System.out.printf("%4d", x);
        }

        System.out.println();

        // Separator.
        System.out.print("----+");

        for (int x = minX; x <= maxX; x++) {
            System.out.print("----");
        }

        System.out.println();

        // Board.
        for (int y = maxY; y >= minY; y--) {
            System.out.printf("%3d |", y);

            for (int x = minX; x <= maxX; x++) {
                Point point = new Point(x, y);

                char symbol;

                if (board.isOccupied(point)) {
                    symbol = 'X';
                } else if (legalPoints.contains(point)) {
                    symbol = '+';
                } else {
                    symbol = '.';
                }

                System.out.printf("%4c", symbol);
            }

            System.out.println();
        }

        System.out.println();
        System.out.println("X = occupied point");
        System.out.println("+ = legal point");
        System.out.println(". = empty position");
    }

}