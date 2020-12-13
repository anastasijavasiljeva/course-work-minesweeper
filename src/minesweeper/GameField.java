package minesweeper;

import java.util.Random;

import static minesweeper.Constants.bombDisarmed;
import static minesweeper.Constants.noBombsNearby;

public class GameField {
    private final FieldTile [][] gameField;
    public GameField (int size) {
        gameField = new FieldTile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                gameField[i][j] = new FieldTile();
            }
        }
    }
    public int length() {
        return gameField.length;
    }
    public FieldTile getTile(int x, int y) {
        return gameField[x][y];
    }
    public void generateBombs(int bombs, int tg_x, int tg_y) {
        int actualBombNum = 0;
        while (actualBombNum != bombs) {
            int coordinateX = generateBomb();
            int coordinateY = generateBomb();
            if (gameField[coordinateX][coordinateY].isEmpty() && coordinateX != tg_x && coordinateY != tg_y) {
                gameField[coordinateX][coordinateY].setBomb(true);
                actualBombNum++;
            }
        }
        countNeighborCoordinates();
    }
    public void clearGameField() {
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                getTile(i, j).setValue(noBombsNearby);
            }
        }
    }
    private int generateBomb() {
        Random random = new Random();
        int bomb = random.nextInt()% gameField.length;
        while (bomb >= gameField.length) bomb--;
        if (bomb < 0) bomb = bomb * (-1);
        return bomb;
    }
    private void countNeighborCoordinates() {
        for (int i = 0; i < gameField.length; i ++) for (int j = 0; j < gameField.length; j++) countTilePerimeter(i, j);
    }
    private void countTilePerimeter(int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isOnTheBoard(x, y, i, j)) {
                    if (gameField[x-1+i][y-1+j].getValue() == '*' && gameField[x][y].getValue() != '*') {
                        gameField[x][y].setValue((char) (gameField[x][y].getValue()+1));
                    }
                }
            }
        }
    }
    private boolean isOnTheBoard(int x, int y, int i, int j) {
        return x - 1 + i >= 0 && x - 1 + i <= gameField.length-1 && y - 1 + j >= 0 && y - 1 + j <= gameField.length-1;
    }
    private boolean isInBorders(int x, int y) {
        return x>=0 && x <= gameField.length-1 && y>=0 && y <= gameField.length-1;
    }
    public void markBomb(int x, int y) {
        gameField[x][y].setMarked(!gameField[x][y].isMarked());
    }
    public void uncoverIndividualBomb(int x, int y) {
        gameField[x][y].setClicked(true);
    }
    public void uncoverAllNeighbors(int x, int y) {
        if (gameField[x][y].getValue() == '0') uncoverZeroes(x, y);
        else {
            if (allNeighbouringBombsMarked(x, y)) {
                if (allNeighbouringBombsMarkedRight(x, y)) uncoverNeighbours(x, y);
            }
        }
    }
    public boolean allBombsMarkedRight(int bombNumber) {
        int bombsMarked = 0;
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField.length; j++) {
                if (bombIsMarkedRight(i, j)) bombsMarked++;
            }
        }
        return bombsMarked == bombNumber;
    }
    private boolean bombIsMarked(int x, int y) {
        return gameField[x][y].isMarked() && gameField[x][y].isBomb();
    }
    private boolean bombIsMarkedRight(int x, int y) {
        return gameField[x][y].isMarked() && gameField[x][y].isBomb() && !gameField[x][y].isClicked();
    }
    public void disarmBomb(int x, int y) {
        gameField[x][y].setValue(bombDisarmed);
        gameField[x][y].setClicked(true);
    }
    private void uncoverZeroes(int x, int y) {
        if(!isInBorders(x, y))return;
        if(gameField[x][y].isClicked()||gameField[x][y].getValue() != '0')return;
        gameField[x][y].setClicked(true);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                uncoverZeroes(x-1+i, y-1+j);
            }
        }

    }
    public boolean checkForBombsUncovered() {
        for (FieldTile[] fieldTiles : gameField) {
            for (int j = 0; j < gameField.length; j++) {
                if (fieldTiles[j].isClicked() && fieldTiles[j].isBomb()) return true;
            }
        }
        return false;
    }
    private void uncoverNeighbours(int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isOnTheBoard(x, y, i, j)) {
                    if (bombIsMarked(x - 1 + i, y - 1 + j)) disarmBomb(x-1+i, y-1+j);
                    gameField[x - 1 + i][y - 1 + j].setClicked(true);
                }
            }
        }

    }
    private boolean allNeighbouringBombsMarked(int x, int y) {
        int realBombNum = gameField[x][y].getValue()-48; int bMarked = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (isOnTheBoard(x, y, i, j)) if (bombIsMarked(x - 1 + i,y - 1 + j)) bMarked++;
        return realBombNum == bMarked;
    }
    private boolean allNeighbouringBombsMarkedRight(int x, int y) {
        int realBombNum = gameField[x][y].getValue()-noBombsNearby; int bMarked = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (isOnTheBoard(x, y, i, j)) if (bombIsMarkedRight(x - 1 + i, y - 1 + j)) bMarked++;
        return realBombNum == bMarked;
    }
    public boolean bombIsClicked (int x, int y) {
        return gameField[x][y].isBomb() && gameField[x][y].isClicked();
    }
}
