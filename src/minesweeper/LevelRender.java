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
            System.out.print(ANSI_BLUE + i + ANSI_RESET + " ");
            if (i <= 9) System.out.print(" ");
            for (int j = 0; j < level.getGameField().length(); j++) {
                if (rules.isTileClicked(i, j)) {
                    System.out.print(ANSI_CYAN + level.getGameField().getTile(i,j).getTile() + "  " + ANSI_RESET);
                }
                else if (rules.isBombDisarmed(i, j)) {
                    System.out.print(ANSI_YELLOW + level.getGameField().getTile(i,j).getTile() + "  " + ANSI_RESET);
                }
                else {
                    System.out.print(level.getGameField().getTile(i,j).getTile() + "  ");
                }
            }
            System.out.println();
        }
    }
    private void printNumbers() {
        System.out.print(" ");
        for (int i = 0; i < level.getGameField().length(); i++) {
            if (i <= 9) System.out.print(" ");
            printColoredSymbol(ANSI_BLUE, Integer.toString(i));
        }
        System.out.println();
    }

    public void displayGameFieldUncovered() {
        printNumbers();
        for (int i = 0; i < level.getGameField().length(); i++) {
            printColoredSymbol(ANSI_BLUE, Integer.toString(i));
            for (int j = 0; j < level.getGameField().length(); j++) {
                if (level.getGameField().getTile(i,j).isBomb()) printColoredSymbol(ANSI_RED, level.getGameField().getTile(i,j).getValue());
                else System.out.print(level.getGameField().getTile(i,j).getValue() + "  ");
            }
            System.out.println();
        }
    }
    private void printColoredSymbol(String colorCode, char symbol) {
        System.out.print(colorCode + symbol + ANSI_RESET + " ");
    }
    private void printColoredSymbol(String colorCode, String symbol) {
        System.out.print(colorCode + symbol + ANSI_RESET + " ");
    }
}
