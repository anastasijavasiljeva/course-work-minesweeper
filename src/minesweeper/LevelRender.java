package minesweeper;

import static minesweeper.Constants.*;

public class LevelRender {
    private final GameLevel level;
    private final GameRules rules;
    public LevelRender(GameLevel level, GameRules rules) {
        this.level = level;
        this.rules = rules;
    }
    public void displayGameField() {
        printNumbers();
        for (int i = 0; i < level.getGameField().length(); i++) {
            printNumber(Integer.toString(i), true);
            if (i <= 9) System.out.print(" ");
            for (int j = 0; j < level.getGameField().length(); j++) {
                if (rules.isTileClicked(i, j))
                    printColoredSymbol(ANSI_CYAN, level.getGameField().getTile(i, j).getTile());
                else if (rules.isBombDisarmed(i, j))
                    printColoredSymbol(ANSI_YELLOW, level.getGameField().getTile(i, j).getTile());
                else printColoredSymbol(ANSI_WHITE, level.getGameField().getTile(i, j).getTile());
            }
            System.out.println();
        }
    }
    private void printNumbers() {
        System.out.print(" ");
        for (int i = 0; i < level.getGameField().length(); i++) {
            if (i <= 9) System.out.print(" ");
            printNumber(Integer.toString(i), false);
        }
        System.out.println();
    }
    public void displayGameFieldUncovered() {
        printNumbers();
        for (int i = 0; i < level.getGameField().length(); i++) {
            printNumber(Integer.toString(i), true);
            if (i<=9) System.out.print(" ");
            for (int j = 0; j < level.getGameField().length(); j++) {
                if (level.getGameField().getTile(i,j).isBomb())
                    printColoredSymbol(ANSI_RED, level.getGameField().getTile(i,j).getValue());
                else
                    printColoredSymbol(ANSI_WHITE, level.getGameField().getTile(i,j).getValue());
            }
            System.out.println();
        }
    }
    private void printColoredSymbol(String colorCode, char symbol) {
        System.out.print(colorCode + symbol + ANSI_RESET + "  ");
    }
    private void printNumber(String symbol, boolean spaceIsAfter) {
        if (!spaceIsAfter) System.out.print(" ");
        System.out.print(Constants.ANSI_BLUE + symbol + ANSI_RESET);
        if (spaceIsAfter) System.out.print(" ");
    }
}
