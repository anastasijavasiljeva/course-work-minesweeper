package tests;

import minesweeper.GameField;
import minesweeper.GameLevel;
import minesweeper.GameRules;
import minesweeper.LevelRender;
import org.junit.Assert;
import org.junit.Test;

public class GameRulesTest {
    @Test
    public void checkIfGameIsLost() {
        GameField gameField = new GameField(15);
        GameLevel gameLevel = new GameLevel(gameField, 15);
        GameRules gameRules = new GameRules(gameLevel);
        LevelRender levelRender = new LevelRender(gameLevel, gameRules);
        gameField.clearGameField();
        gameField.generateBombs(15, 0, 0);
        gameRules.setFirstMove(false);
        for (int i = 0; i < gameField.length(); i++) {
            for (int j = 0; j < gameField.length(); j++) {
                if (gameField.getTile(i, j).isBomb()) {
                    gameRules.processUserInput("p " + i + " " + j);
                    boolean gameLost = gameRules.isGameOver();
                    Assert.assertTrue(gameLost);

                    break;
                }
            }
        }
        levelRender.displayGameFieldUncovered();
    }
}
