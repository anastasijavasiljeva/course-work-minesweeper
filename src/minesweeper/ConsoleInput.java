package minesweeper;

import java.util.Scanner;

public class ConsoleInput {
    public String getConsoleInput() {
        Scanner read = new Scanner(System.in); String key;
        key = read.nextLine();
        return key;
    }
    public int getX(String key) {
        String x = key.substring(key.indexOf(" ")+1, key.lastIndexOf(" "));
        return Integer.parseInt(x);
    }
    public int getY(String key) {
        String y = key.substring(key.lastIndexOf(" ")+1);
        return Integer.parseInt(y);
    }
}
