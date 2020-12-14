package minesweeper;

import static minesweeper.SystemMessages.*;
import static minesweeper.Constants.*;

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
    private boolean isTileMarked(int x, int y) {
        return gameLevel.getGameField().getTile(x, y).isMarked();
    }
    private boolean isTileBomb(int x, int y) {
        return gameLevel.getGameField().getTile(x, y).isBomb();
    }
    public boolean isBombDisarmed(int x, int y) {
        return isTileBomb(x, y) && (isTileClicked(x, y)||isTileMarked(x, y));
    }
    public boolean isGameOver() {
        return inGame;
    }
    public boolean isFirstMove() {
        return firstMove;
    }
    public void processUserInput(String key) {
        switch (key.substring(0, 1)) {
            case giveUp -> {
                inGame = false;
                printEndingGiveUp();
                System.exit(0);
            }
            case exit -> {
                inGame = false;
                printEndingExit();
                System.exit(0);
            }
            case printRules -> printKeyHelper();
            case markBomb -> {
                if (inputOutOfBounds(key)) printOutOfBoundsException();
                else if (tooMuchBombsMarked()) printTooMuchFlagsException();
                else {
                    gameLevel.getGameField().markBomb(consoleInput.getX(key), consoleInput.getY(key));
                    checkIfGameWon();
                    checkIfGameIsLost(consoleInput.getX(key), consoleInput.getY(key));
                }
            }
            case uncoverOneCell -> {
                if (inputOutOfBounds(key)) printOutOfBoundsException();
                else {
                    gameLevel.getGameField().uncoverIndividualBomb(consoleInput.getX(key), consoleInput.getY(key));
                    if (isFirstMove()) setFirstMove(false);
                    checkIfGameWon();
                    checkIfGameIsLost(consoleInput.getX(key), consoleInput.getY(key));
                }
            }
            case uncoverCellGroup -> {
                if (inputOutOfBounds(key)) printOutOfBoundsException();
                else {
                    gameLevel.getGameField().uncoverAllNeighbors(consoleInput.getX(key), consoleInput.getY(key));
                    checkIfGameWon();
                    checkIfGameIsLost(consoleInput.getX(key), consoleInput.getY(key));
                }
            }
            default -> printUnknownCommandException();
        }
    }
    public boolean tooMuchBombsMarked() {
        return gameLevel.getBombNumber() < countMarkedCellsNumber();
    }
    public int countMarkedCellsNumber() {
        int marked = 0;
        for (int i = 0; i < gameLevel.getGameField().length(); i++) {
            for (int j = 0; j < gameLevel.getGameField().length(); j++) {
                if (gameLevel.getGameField().getTile(i, j).isMarked()) marked++;
            }
        }
        return marked;
    }
    public boolean inputOutOfBounds(String key) {
        return consoleInput.getX(key) > gameLevel.getGameField().length() ||consoleInput.getY(key) > gameLevel.getGameField().length();
    }
    private void checkIfGameWon() {
        if (gameLevel.getGameField().allBombsMarkedRight(gameLevel.getBombNumber())) {
            printCongratulations();
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
    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}