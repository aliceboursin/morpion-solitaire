# Morpion Solitaire

A Java implementation of Morpion Solitaire, supporting both the 5T (touching)
and 5D (disjoint) rule variants.

The project aims to provide a complete Morpion Solitaire application built
around a rule-driven game engine, with automatic legal move generation,
player profiles and score history, automated solving strategies, and both
command-line and graphical interfaces.

## Project Status

This repository is a ground-up refactoring of a Morpion Solitaire application
originally developed as part of a university project.

The refactoring focuses on clearer domain modeling, separation of concerns,
testability and maintainability. The core game engine and both rule variants 
have been rebuilt and are currently playable through the command-line interface.

Additional features from the original project — including user accounts,
score history, automated solvers and a graphical interface — are being
redesigned progressively on top of the new architecture.

## Features

### Currently implemented

- Morpion Solitaire 5T and 5D rule variants
- Standard 36-point starting configuration
- Sparse, unbounded game board
- Automatic legal move generation
- Move validation according to the selected rule variant
- Scoring and placed-line tracking
- Undo support
- Playable command-line interface
- Automated test suite

## Tech Stack

- Java 21
- Maven
- JUnit 5
- JavaFX *(graphical interface planned)*

## Game Rules

Morpion Solitaire is a single-player puzzle played on an unbounded square grid.

The game starts from a standard configuration of 36 occupied points. On each
turn, the player must:

1. Place exactly one new point on an empty position.
2. Draw a straight line containing exactly five consecutive occupied points,
   including the newly placed point.

Lines can be horizontal, vertical, or diagonal.

This project supports the two main rule variants:

- **5T (touching):** two consecutive lines in the same direction may touch by
  sharing a single endpoint.
- **5D (disjoint):** a point cannot be reused by two lines in the same direction.

In both variants, lines in different directions may cross at a single point,
while overlapping lines sharing two or more points are not allowed.

The score corresponds to the number of successfully placed lines.

## Architecture

The refactored application separates the core game model, game lifecycle,
rule variants, setup logic, and presentation layer.

```text
src/main/java/io/github/aliceboursin/morpionsolitaire/
├── domain/
│   ├── Board
│   ├── Cell
│   ├── Direction
│   ├── Line
│   └── Point
│
├── game/
│   ├── Game
│   ├── GameState
│   ├── Move
│   │
│   ├── rules/
│   │   ├── GameRules
│   │   ├── AbstractFiveRules
│   │   ├── FiveTRules
│   │   └── FiveDRules
│   │
│   └── setup/
│       └── StandardSetup
│
└── console/
    └── ConsoleDemo
```

### Domain model

The `domain` package contains the concepts that describe the board itself.

- `Point` represents an immutable grid coordinate.
- `Direction` defines the four possible line directions using coordinate vectors.
- `Line` represents five consecutive points in one direction.
- `Cell` stores the state of a board position and tracks its use by direction.
- `Board` provides a sparse representation of the unbounded playing grid.

Using a sparse board means that only relevant positions need to be stored,
rather than allocating a fixed-size matrix for a game whose board has no
theoretical boundaries.

### Game lifecycle

`Game` is responsible for orchestrating a game without implementing the rules
itself.

It owns the current `GameState`, applies moves, maintains the score and move
history, and provides undo support.

Move validation and legal move generation are delegated to a `GameRules`
implementation:

```text
                  ┌───────────────┐
                  │     Game      │
                  └───────┬───────┘
                          │
                    delegates to
                          │
                  ┌───────▼───────┐
                  │   GameRules   │
                  └───────┬───────┘
                          │
               ┌──────────┴──────────┐
               │                     │
        ┌──────▼──────┐       ┌──────▼──────┐
        │ FiveTRules  │       │ FiveDRules  │
        └─────────────┘       └─────────────┘
```

The common five-point line rules and legal move generation are shared through
`AbstractFiveRules`, while `FiveTRules` and `FiveDRules` implement only their
variant-specific constraints.

This keeps the game lifecycle independent from the selected rule variant and
avoids maintaining separate game implementations for 5T and 5D.


## Getting Started

### Requirements

- Java 21
- Maven 3.9+

Check your installation with:

```bash
java -version
mvn -version
```

### Clone the repository

```bash
git clone https://github.com/aliceboursin/morpion-solitaire.git
cd morpion-solitaire
```

### Build and test

```bash
mvn clean test
```

### Run the console application

```bash
mvn exec:java
```

The console application lets you choose between the 5T and 5D variants, inspect
the board and available moves, play moves, view placed lines, and undo previous
moves.

Board symbols:

```text
X = occupied point
+ = legal point
. = empty position
```

## Tests

The game engine is covered by automated JUnit tests for the domain model,
game state, game lifecycle, initial setup, and both 5T and 5D rule variants.

Run the complete test suite with:

```bash
mvn clean test
```


## Roadmap

### Game engine

- [x] Redesign the domain model
- [x] Implement 5T and 5D rule variants
- [x] Implement legal move generation and validation
- [x] Add scoring and game lifecycle
- [x] Add undo support
- [x] Add automated tests
- [x] Build a playable console interface

### Application features

- [ ] Reintroduce user accounts and authentication
- [ ] Add persistent storage
- [ ] Add game and score history

### Solvers

- [ ] Redesign the solver architecture
- [ ] Implement automated solving strategies
- [ ] Add solver tests
- [ ] Integrate solvers into the application

### User interface

- [ ] Complete the end-to-end console workflow
- [ ] Redesign the JavaFX interface
- [ ] Add interactive board rendering
- [ ] Integrate user accounts, game history, and solvers into the graphical interface

### Distribution & documentation

- [ ] Add screenshots
- [ ] Document important architectural decisions
- [ ] Add continuous integration
- [ ] Package the application for easy execution
