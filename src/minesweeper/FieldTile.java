package minesweeper;

import static minesweeper.Constants.*;

public class FieldTile {
    private char value;
    private char cover;
    private boolean isClicked;
    private boolean isMarked;
    private boolean isBomb;
    public FieldTile() {
        value = noBombsNearby;
        cover = cellCovered;
        isClicked = false;
    }
    public boolean isMarked() {
        return isMarked;
    }
    public void setMarked(boolean marked) {
        isMarked = marked;
        cover = (isMarked)?cellMarked:cellCovered;
    }
    public char getCover() {
        return cover;
    }
    public char getValue() {
        return value;
    }
    public void setValue(char value) {
        this.value = value;
    }
    public void setBomb(boolean bomb) {
        isBomb = bomb;
        value = '*';
    }
    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
    public boolean isEmpty () {
        return value == noBombsNearby;
    }
    public char getTile() {
        return (isClicked)? getValue() : getCover();
    }
    public boolean isBomb() {
        return isBomb;
    }
    public boolean isClicked() {
        return isClicked;
    }

}
