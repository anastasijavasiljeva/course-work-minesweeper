import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameField {
    int fieldLength;
    Tile [][] gameField;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001b[31;1m";
    public static final String ANSI_BLUE = "\u001B[34;1m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_YELLOW = "\u001b[33;1m";
    public GameField() {
        fieldLength = 10;
        gameField = new Tile[fieldLength][fieldLength];
        for (int i = 0; i < fieldLength; i++) for (int j = 0; j < fieldLength; j++) gameField[i][j] = new Tile();
    }
    public void clearGameField() {
        for (int i = 0; i < fieldLength; i++) for (int j = 0; j < fieldLength; j++) gameField[i][j].setValue('0');
    }
    public void displayGameField() {
        System.out.print(" ");
        for (int i = 0; i < fieldLength; i++) System.out.print(" " + ANSI_BLUE + i + ANSI_RESET);
        System.out.println();
        for (int i = 0; i < fieldLength; i++) {
            System.out.print(ANSI_BLUE + i + ANSI_RESET + " ");
            for (int j = 0; j < fieldLength; j++) {
                if (gameField[i][j].isClicked()) System.out.print(ANSI_CYAN + gameField[i][j].getTile() + " " + ANSI_RESET);
                else if (gameField[i][j].isBomb() && (gameField[i][j].isMarked()||gameField[i][j].isClicked())) System.out.print(ANSI_YELLOW + gameField[i][j].getTile() + " " + ANSI_RESET);
                else System.out.print(gameField[i][j].getTile() + " ");
            }
            System.out.println();
        }
    }
    public void displayInitField() {
        System.out.print(" ");
        for (int i = 0; i < fieldLength; i++) System.out.print(" " + ANSI_BLUE + i + ANSI_RESET);
        System.out.println();
        for (int i = 0; i < fieldLength; i++) {
            System.out.print(ANSI_BLUE + i + ANSI_RESET + " ");
            for (int j = 0; j < fieldLength; j++) {
                System.out.print(gameField[i][j].getValue() + " ");
            }
            System.out.println();
        }
    }
    public int generateBomb() {
        Random random = new Random();
        int bomb = random.nextInt()%8;
        if (bomb < 0) bomb = bomb * (-1);
        return bomb;
    }
    public void uncover (int x, int y) {
        gameField[x][y].setClicked(true);
    }
    public void Coordinates () {
        for (int i = 0; i < fieldLength; i ++) {
            for (int j = 0; j < fieldLength; j++) {
                countTilePerimeter(i,j);
            }
        }
    }
    private void countTilePerimeter(int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x - 1 + i >= 0 && x - 1 + i <= 8 && y - 1 + j >= 0 && y - 1 + j < 8) {
                    if (gameField[x-1+i][y-1+j].getValue() == '*' && gameField[x][y].getValue() != '*') {
                        gameField[x][y].setValue((char) (gameField[x][y].getValue()+1));
                    }
                }
            }
        }
    }
    private void printFakeCover() {
        System.out.print(ANSI_BLUE + " ");
        for (int i = 0; i < fieldLength; i++) System.out.print(" " + i);
        System.out.println(ANSI_RESET);
        for (int i = 0; i < fieldLength; i++) {
            System.out.print(ANSI_BLUE + i + " " + ANSI_RESET);
            for (int j = 0; j < fieldLength; j++) {
                System.out.print("x ");
            }
            System.out.println();
        }
    }
    private void setBombs(int bombNum, int bombByLev, int tg_x, int tg_y) {
        while (bombNum != bombByLev) {
            int coordinate_x = generateBomb();
            int coordinate_y = generateBomb();
            if (gameField[coordinate_x][coordinate_y].isEmpty() && coordinate_x!=tg_x && coordinate_y != tg_y) {
                gameField[coordinate_x][coordinate_y].setBomb(true);
                bombNum++;
            }
        }
    }
    private boolean checkForBombsUncovered() {
        for (int i = 0; i < fieldLength; i++) {
            for (int j = 0; j < fieldLength; j++) {
                if (gameField[i][j].isClicked() && gameField[i][j].isBomb()) return true;
            }
        }
        return false;
    }
    private boolean allBombsMarked(int a) {
        int bombsMarked = 0;
        for (Tile[] tiles : gameField) {
            for (int j = 0; j < gameField.length; j++) {
                if (tiles[j].isBomb() && tiles[j].isMarked() && !tiles[j].isClicked()){
                    bombsMarked++;
                }
            }
        }
        return bombsMarked == a;
    }
    private boolean allNeighbouringBombsMarkedRight(int x, int y) {
        int realBombNum = gameField[x][y].getValue()-48; int bMarked = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x - 1 + i >= 0 && x - 1 + i <= 8 && y - 1 + j >= 0 && y - 1 + i <= 8) {
                    if (gameField[x - 1 + i][y - 1 + j].isMarked() && gameField[x - 1 + i][y - 1 + j].isBomb()) {
                        bMarked++;

                    }
                    int x1 = x - 1 + i;
                    int y1 =  y - 1 + j;
                    System.out.println(x1 + " " + y1+ " " + bMarked);
                }
            }
        }
        System.out.println(realBombNum + " " + bMarked);
        return realBombNum == bMarked;
    }

    public static void main(String[] args) {
        GameField field = new GameField();
        int bombNum = 0; int bombByLev = 15; boolean inGame = true; boolean firstMove = true; String cmd;
        Scanner read = new Scanner(System.in);
        int tg_x = 0; int tg_y = 0;
        while (inGame) {
            if (firstMove) {
                field.clearGameField();
                field.printFakeCover();
                cmd = read.next();
                if (cmd.equals("p")) {
                    String [] target = read.next().split(";");
                    tg_x= Integer.parseInt(target[0]);
                    tg_y = Integer.parseInt(target[1]);
                }
                field.setBombs(bombNum, bombByLev, tg_x, tg_y);
                field.Coordinates();
                field.uncover(tg_x, tg_y);
                firstMove = false;
            }
            //field.displayInitField();
            field.displayGameField();
            if (field.allBombsMarked(bombByLev)) {
                System.out.println("YOU WON");
                inGame = false;
                System.exit(0);
            }
            cmd = read.next();
            switch (cmd) {
                case "g" -> {
                    System.out.println("YOU GAVE UP");
                    inGame = false;
                    System.exit(0);
                }
                case "q" -> {
                    inGame = false;
                    System.exit(0);
                }
                case "p" -> {
                    String[] target = read.next().split(";");
                    tg_x = Integer.parseInt(target[0]);
                    tg_y = Integer.parseInt(target[1]);
                    if (field.gameField[tg_x][tg_y].isBomb() || field.checkForBombsUncovered()) {
                        field.gameField[tg_x][tg_y].setClicked(true);
                        System.out.println(ANSI_RED + "YOU LOST" + ANSI_RESET);
                        inGame = false;
                        System.exit(0);
                    }
                    else field.uncover(tg_x, tg_y);
                }
                case "m" -> {
                    String[] target = read.next().split(";");
                    tg_x = Integer.parseInt(target[0]); tg_y = Integer.parseInt(target[1]);
                    field.gameField[tg_x][tg_y].setMarked(!field.gameField[tg_x][tg_y].isMarked());
                }
                case "u" -> {
                    String[] target = read.next().split(";");
                    tg_x = Integer.parseInt(target[0]);
                    tg_y = Integer.parseInt(target[1]);
                    System.out.println(tg_x + " " + tg_y);
                    if (field.gameField[tg_x][tg_y].getValue() == '0') field.uncoverSquare(tg_x, tg_y);
                    else {
                        if (field.allNeighbouringBombsMarked(tg_x, tg_y)) {
                            if (field.allNeighbouringBombsMarkedRight(tg_x, tg_y)) {
                                field.uncoverNeighbours(tg_x, tg_y);
                            }
                            else {
                                field.gameField[tg_x][tg_y].setClicked(true);
                                System.out.println(ANSI_RED + "YOU LOST" + ANSI_RESET);
                                inGame = false;
                                System.exit(0);
                            }
                        }
                        else continue;
                    }
                }
                default -> System.out.println("Illegal command");
            }
        }
    }
    private void uncoverNeighbours(int x, int y) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (x - 1 + i >= 0 && x - 1 + i <= 8 && y - 1 + j >= 0 && y - 1 + j <= 8) {
                    if (gameField[x - 1 + i][y - 1 + j].isBomb() && gameField[x - 1 + i][y - 1 + j].isMarked()) gameField[x - 1 + i][y - 1 + j].setValue('m');
                    gameField[x - 1 + i][y - 1 + j].setClicked(true);
                }
            }
        }

    }
    private void uncoverSquare(int x, int y) {
            if(x <0 || y <0 || x>9 || y>9)return;
            if(gameField[x][y].isClicked())return;
            gameField[x][y].setClicked(true);
            if (gameField[x][y].getValue() != '0') return;
        uncoverSquare(x-1,y-1);
        uncoverSquare(x-1,y+1);
        uncoverSquare(x+1,y-1);
        uncoverSquare(x+1,y+1);
        uncoverSquare(x-1,y);
        uncoverSquare(x+1,y);
        uncoverSquare(x,y-1);
        uncoverSquare(x,y+1);

    }
    private boolean allNeighbouringBombsMarked(int x, int y) {
        int realBombNum = gameField[x][y].getValue()-48; int bMarked = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (x - 1 + i >= 0 && x - 1 + i <= 8 && y - 1 + j >= 0 && y - 1 + j < 8)
                    if (gameField[x - 1 + i][y - 1 + j].isBomb() && gameField[x - 1 + i][y - 1 + j].isMarked())
                        bMarked++;
        return realBombNum == bMarked;
    }
}