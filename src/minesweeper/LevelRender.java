package minesweeper;

import static minesweeper.Constants.*;

public class LevelRender extends BasicRender{
    public LevelRender(GameLevel level, GameRules rules) {
        super(rules, level);
    }
    @Override
    public void displayGameField() {
        printNumbers();
        for (int i = 0; i < level.getGameField().length(); i++) {
            printColoredSymbol(Integer.toString(i), true);
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
    @Override
    public void printNumbers() {
        System.out.print(" ");
        for (int i = 0; i < level.getGameField().length(); i++) {
            if (i <= 9) System.out.print(" ");
            printColoredSymbol(Integer.toString(i), false);
        }
        System.out.println();
    }
    @Override
    public void displayGameFieldUncovered() {
        printNumbers();
        for (int i = 0; i < level.getGameField().length(); i++) {
            printColoredSymbol(Integer.toString(i), true);
            if (i <= 9) System.out.print(" ");
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
    private void printColoredSymbol(String symbol, boolean spaceIsAfter) {
        if (!spaceIsAfter) System.out.print(" ");
        System.out.print(Constants.ANSI_BLUE + symbol + ANSI_RESET);
        if (spaceIsAfter) System.out.print(" ");
    }
}
