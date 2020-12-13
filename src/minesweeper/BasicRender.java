package minesweeper;

import static minesweeper.Constants.*;

public class BasicRender {
    protected final GameLevel level;
    protected final GameRules rules;
    public BasicRender(GameRules rules, GameLevel level) {
        this.level = level;
        this.rules = rules;
    }
    public void displayGameField(){
        System.out.print(" ");
        for (int i = 0; i < level.getGameField().length(); i++) System.out.println(" " + i);
        System.out.println();
        for (int i = 0; i < level.getGameField().length(); i++) {
            System.out.print(i + " ");
            if (i <= 9) System.out.print(" ");
            for (int j = 0; j < level.getGameField().length(); j++)
                System.out.print(level.getGameField().getTile(i, j).getTile() + " ");
            System.out.println();
        }
    }
    private void printNumbers() {
        System.out.print(" ");
        for (int i = 0; i < level.getGameField().length(); i++) {
            if (i <= 9) System.out.print(" ");
            System.out.println(" " + i);
        }
        System.out.println();
    }
    public void displayGameFieldUncovered() {
        printNumbers();
        for (int i = 0; i < level.getGameField().length(); i++) {
            System.out.print(i + " ");
            if (i<=9) System.out.print(" ");
            for (int j = 0; j < level.getGameField().length(); j++)
                System.out.print(level.getGameField().getTile(i, j).getValue() + " ");
            System.out.println();
        }
    }
}
