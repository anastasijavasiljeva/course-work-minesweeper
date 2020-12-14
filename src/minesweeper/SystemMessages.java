package minesweeper;

public class SystemMessages {
    public static void printEndingExit() {
        System.out.println("Is this how it ends? ");
        System.out.println("Bombs laying uncovered, field staying abandoned?");
        System.out.println("Well, if this truly is yours desire, then good luck. ");
        System.out.println("I hope next time you will stay longer.");
    }
    public static void printTooMuchFlagsException() {
        System.out.println("Wow, I really do appreciate your caution, but slow down a bit, won't you?");
    }
    public static void printUnknownCommandException() {
        System.out.println("Sorry, it's impossible to do what you have asked. Consider pressing r to check possible key combinations. ");
    }
    public static void printCongratulations() {
        System.out.println("CONGRATULATIONS");
        System.out.println("Through hard work and dedication you won this game!");
    }
    public static void printEndingGiveUp() {
        System.out.println("So you gave up. A pity, though, I bet you could have played more.");
        System.out.println("That's okay, though. Have a nice day (or evening, or whatever it is now at your place).");
    }
    public static void printOutOfBoundsException(){
        System.out.println("Oops, your ambitions are a bit too big. ");
        System.out.println("Slow down, please, and check the numbers on the board again :)");
    }
    public static void printKeyHelper() {
        System.out.println("Available keys:");
        System.out.println("m x y - mark one bomb, which is on the position (x; y)");
        System.out.println("p x y - uncover one individual cell, which is on the position (x;y)");
        System.out.println("u x y - uncover group of cells, starting from position (x;y)");
        System.out.println("g     - give up");
        System.out.println("e     - exit");
    }
}
