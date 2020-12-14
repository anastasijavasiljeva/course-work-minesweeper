package tests;

import minesweeper.GameField;
import minesweeper.GameLevel;
import minesweeper.GameRules;
import org.junit.jupiter.api.Test;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;

public class InputTests {
    @Test
    public void ExceptionMessagePrintedIfOutBounds(){
        GameField gameField = new GameField(15);
        GameLevel gameLevel = new GameLevel(gameField, 15);
        GameRules gameRules = new GameRules(gameLevel);
        gameField.clearGameField();
        gameField.generateBombs(15, 0, 0);
        gameRules.setFirstMove(false);
        String key = "p 150 1";
        boolean outOfBorders = gameRules.inputOutOfBounds(key);
        Assert.assertTrue(outOfBorders);
    }
    @Test
    public void unexpectedCommandLetter() {
        GameField gameField = new GameField(15);
        GameLevel gameLevel = new GameLevel(gameField, 15);
        GameRules gameRules = new GameRules(gameLevel);
        gameField.clearGameField();
        gameField.generateBombs(15, 0, 0);
        gameRules.setFirstMove(false);
        String key = "a";
        ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        gameRules.processUserInput(key);
        String result = out.toString();
        Assert.assertTrue(result.contains("Sorry, it's impossible to do what you have asked."));
    }
    @Test
    public void ExceptionMessagePrintedIfTooMuchFlagged() {
        GameField gameField = new GameField(15);
        GameLevel gameLevel = new GameLevel(gameField, 2);
        GameRules gameRules = new GameRules(gameLevel);
        gameField.clearGameField();
        gameField.generateBombs(3, 0, 0);
        gameRules.setFirstMove(false);
        for (int j = 0; j < 3; j++) {
            String key = "m " + 0 + " " + j;
            gameRules.processUserInput(key);
        }
        String errorKey = "m 0 4";
        ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        System.setOut(new java.io.PrintStream(out));
        gameRules.processUserInput(errorKey);
        String result = out.toString();
        Assert.assertTrue(result.contains("slow down a bit"));
    }
}
