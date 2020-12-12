package minesweeper;

public class GameLevel {
    private final GameField gameField;
    private final int bombNumber;
    public GameLevel(GameField gameField, int bombs) {
        this.gameField = gameField;
        this.bombNumber = bombs;
    }
    public GameField getGameField() {
        return gameField;
    }
    public int getBombNumber() {
        return bombNumber;
    }
}
