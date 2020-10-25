public class Tile {
    private char value;
    private char cover;
    private boolean isClicked;
    private boolean isMarked;
    private boolean isBomb;
    public Tile() {
        value = 48;
        cover = 'x';
        isClicked = false;
    }
    public boolean isMarked() {
        return isMarked;
    }
    public void setMarked(boolean marked) {
        isMarked = marked;
        cover = (isMarked)?'b':'x';
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
        return value == 48;
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