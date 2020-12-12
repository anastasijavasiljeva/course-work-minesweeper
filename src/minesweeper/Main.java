package minesweeper;

import java.io.IOException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        int size, bombs;
        Scanner read = new Scanner(System.in);
        System.out.println("Please enter the field size.");
        size = read.nextInt();
        System.out.println("Please enter the number of bombs.");
        bombs = read.nextInt();
        Minesweeper minesweeperLevel = Minesweeper.getLevel(size, bombs);
        minesweeperLevel.playLevel();
        System.out.println("Press any key to continue...");
        System.in.read();
    }
}
