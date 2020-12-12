package minesweeper;

public class Minesweeper {
    private static Minesweeper level = null;
    private final GameField gameField;
    private final GameLevel gameLevel;
    private final GameRules gameRules;
    private final LevelRender levelRender;
    private final ConsoleInput consoleInput;
    private final int bombNumber;
    private Minesweeper(int fieldSize, int bombNumber) {
        gameField = new GameField(fieldSize);
        gameLevel = new GameLevel(gameField, bombNumber);
        gameRules = new GameRules(gameLevel);
        levelRender = new LevelRender(gameLevel, gameRules);
        consoleInput = new ConsoleInput();
        this.bombNumber = bombNumber;
    }
    public static Minesweeper getLevel(int fieldSize, int bombNumber) {
        if (level == null) level = new Minesweeper(fieldSize, bombNumber);
        return level;
    }
    public void playLevel(){
        while (!gameRules.isGameOver()) {
            levelRender.displayGameField();
            String key = consoleInput.getConsoleInput();
            if (gameRules.isFirstMove()) {
                gameField.clearGameField();
                gameLevel.getGameField().generateBombs(bombNumber, consoleInput.getX(key), consoleInput.getY(key));
                gameRules.processUserInput(key);
                levelRender.displayGameFieldUncovered();
                gameRules.setFirstMove(false);
            }
            else gameRules.processUserInput(key);
        }
    }
}
