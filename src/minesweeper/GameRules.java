package minesweeper;

import static minesweeper.Constants.ANSI_RED;
import static minesweeper.Constants.ANSI_RESET;

public class GameRules {
    private final GameLevel gameLevel;
    private final ConsoleInput consoleInput;
    private boolean inGame;
    private boolean firstMove;

    public GameRules (GameLevel level) {
        this.gameLevel = level;
        this.firstMove = true;
        this.consoleInput = new ConsoleInput();
    }
    public boolean isTileClicked(int x, int y) {
        return gameLevel.getGameField().getTile(x, y).isClicked();
    }
    public boolean isTileMarked(int x, int y) {
        return gameLevel.getGameField().getTile(x, y).isMarked();
    }
    public boolean isTileBomb(int x, int y) {
        return gameLevel.getGameField().getTile(x, y).isBomb();
    }
    public boolean isBombDisarmed(int x, int y) {
        return isTileBomb(x, y) && (isTileClicked(x, y)||isTileMarked(x, y));
    }
    public void printKeyHelper() {
        System.out.println("Available keys:");
        System.out.println("m x y - mark one bomb, which is on the position (x; y)");
        System.out.println("p x y - uncover one individual bomb, which is on the position (x;y)");
        System.out.println("u x y");
        System.out.println("g     - give up");
        System.out.println("e     - exit");
    }

    public boolean isGameOver() {
        return inGame;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void processUserInput(String key) {
        switch (key.substring(0, 1)) {
            case "g" -> {
                inGame = false;
                printEndingGiveUp();
                System.exit(0);
            }
            case "e" -> {
                inGame = false;
                printEndingExit();
                System.exit(0);
            }
            case "r" -> printKeyHelper();
            case "m" -> gameLevel.getGameField().markBomb(consoleInput.getX(key), consoleInput.getY(key));
            case "p" -> {
                gameLevel.getGameField().uncoverIndividualBomb(consoleInput.getX(key), consoleInput.getY(key));
                if (isFirstMove()) setFirstMove(false);
            }
            case "u" -> gameLevel.getGameField().uncoverAllNeighbors(consoleInput.getX(key), consoleInput.getY(key));
            default -> System.out.println("Sorry, it's impossible to do what you have asked. Consider pressing r to check possible key combinations. ");
        }
        checkIfGameWon();
        checkIfGameIsLost(consoleInput.getX(key), consoleInput.getY(key));

    }

    private void checkIfGameWon() {
        if (gameLevel.getGameField().allBombsMarkedRight(gameLevel.getBombNumber())) {
            System.out.println("CONGRATULATIONS");
            System.out.println("Through hard work and dedication you won this game!");
            inGame = false;
            System.exit(0);
        }
    }

    private void checkIfGameIsLost(int x, int y) {
        if (gameLevel.getGameField().bombIsClicked(x, y)||gameLevel.getGameField().checkForBombsUncovered()) {
            inGame = false;
            System.out.println(ANSI_RED + "YOU LOST" + ANSI_RESET);
            System.exit(0);
        }
    }

    private void printEndingExit() {
        System.out.println("Is this how it ends? ");
        System.out.println("Bombs laying uncovered, field staying abandoned?");
        System.out.println("Well, if this truly is yours desire, then good luck. ");
        System.out.println("I hope next time you will stay longer.");
    }

    private void printEndingGiveUp() {
        System.out.println("So you gave up. A pity, though, I bet you could have played more.");
        System.out.println("That's okay, though. Have a nice day (or evening, or whatever it is now at your place).");
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
