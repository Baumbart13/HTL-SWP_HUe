package miscForEverything;

import miscForEverything.CustomExceptions.CantCreateException;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class is used
 */
public class UI {
    private static Scanner reader = new Scanner(System.in);
    private static Random random;

    public UI() throws CantCreateException{
        throw new CantCreateException();
    }

    private static void resetScanner(){
        reader = reader.reset();
    }
    private static void reseedRandom(){
        random.setSeed(System.nanoTime());
    }

    public static double getRandomDouble(){
        return getRandomDouble(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static long getRandomLong(){
        return getRandomLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static double getRandomDouble(double lowerBound, double upperBound){
        double value = lowerBound + (upperBound-lowerBound)*random.nextDouble();
        reseedRandom();
        return value;
    }

    public static long getRandomLong(long lowerBound, long upperBound){
        long value = lowerBound + (long)(random.nextGaussian() * ((upperBound-lowerBound) + 1L));
        reseedRandom();
        return value;
    }

    public static void waitForKeypress(){
        try{
            System.out.print("Press any key to continue...");
            System.in.read();
        }catch(IOException e){
            System.err.println("Nothing special to worry about" + System.lineSeparator() + e.getMessage());
        }
    }

    public static String readString(){
        String in = "";
        try{
            in = reader.nextLine();
        }catch(Exception e){
            System.err.println("Bruh, what happened?");
            e.printStackTrace();
        }
        resetScanner();
        return in.trim();
    }

    public static int readInteger(){
        Integer out = 0;
        try{
            out = Integer.parseInt(reader.nextLine().trim());
        }catch(NumberFormatException e){
            System.err.println("Mate, this is not a number" + System.lineSeparator() + "Here another try:");
            return readInteger();
        }catch(Exception e){
            System.err.println("Bruh, what happened?");
            e.printStackTrace();
        }
        resetScanner();
        return out;
    }


    private interface Console{
        public static final String ANSI_RESET = "\u001B[0m";
        public static final String ANSI_BLACK = "\u001B[30m";
        public static final String ANSI_RED = "\u001B[31m";
        public static final String ANSI_GREEN = "\u001B[32m";
        public static final String ANSI_YELLOW = "\u001B[33m";
        public static final String ANSI_BLUE = "\u001B[34m";
        public static final String ANSI_PURPLE = "\u001B[35m";
        public static final String ANSI_CYAN = "\u001B[36m";
        public static final String ANSI_WHITE = "\u001B[37m";

        public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
        public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
        public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
        public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
        public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
        public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
        public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
        public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    }

    public static String RESET  (String s){ return Console.ANSI_RESET + s; }
    public static String RESET  (){ return Console.ANSI_RESET; }

    public static String BLACK  (String s){ return Console.ANSI_BLACK + s; }
    public static String RED    (String s){ return Console.ANSI_RED + s; }
    public static String GREEN  (String s){ return Console.ANSI_GREEN + s; }
    public static String YELLOW (String s){ return Console.ANSI_YELLOW + s; }
    public static String BLUE   (String s){ return Console.ANSI_BLUE + s; }
    public static String PURPLE (String s){ return Console.ANSI_PURPLE + s; }
    public static String CYAN   (String s){ return Console.ANSI_CYAN + s; }
    public static String WHITE  (String s){ return Console.ANSI_WHITE + s; }

    public static String BLACK  (){ return Console.ANSI_BLACK; }
    public static String RED    (){ return Console.ANSI_RED; }
    public static String GREEN  (){ return Console.ANSI_GREEN; }
    public static String YELLOW (){ return Console.ANSI_YELLOW; }
    public static String BLUE   (){ return Console.ANSI_BLUE; }
    public static String PURPLE (){ return Console.ANSI_PURPLE; }
    public static String CYAN   (){ return Console.ANSI_CYAN; }
    public static String WHITE  (){ return Console.ANSI_WHITE; }

    public static String BLACK_BACKGROUND   (String s){ return Console.ANSI_BLACK_BACKGROUND + s; }
    public static String RED_BACKGROUND     (String s){ return Console.ANSI_RED_BACKGROUND + s; }
    public static String GREEN_BACKGROUND   (String s){ return Console.ANSI_GREEN_BACKGROUND + s; }
    public static String YELLOW_BACKGROUND  (String s){ return Console.ANSI_YELLOW_BACKGROUND + s; }
    public static String BLUE_BACKGROUND    (String s){ return Console.ANSI_BLUE_BACKGROUND + s; }
    public static String PURPLE_BACKGROUND  (String s){ return Console.ANSI_PURPLE_BACKGROUND + s; }
    public static String CYAN_BACKGROUND    (String s){ return Console.ANSI_CYAN_BACKGROUND + s; }
    public static String WHITE_BACKGROUND   (String s){ return Console.ANSI_WHITE_BACKGROUND + s; }

    public static String BLACK_BACKGROUND   (){ return Console.ANSI_BLACK_BACKGROUND; }
    public static String RED_BACKGROUND     (){ return Console.ANSI_RED_BACKGROUND; }
    public static String GREEN_BACKGROUND   (){ return Console.ANSI_GREEN_BACKGROUND; }
    public static String YELLOW_BACKGROUND  (){ return Console.ANSI_YELLOW_BACKGROUND; }
    public static String BLUE_BACKGROUND    (){ return Console.ANSI_BLUE_BACKGROUND; }
    public static String PURPLE_BACKGROUND  (){ return Console.ANSI_PURPLE_BACKGROUND; }
    public static String CYAN_BACKGROUND    (){ return Console.ANSI_CYAN_BACKGROUND; }
    public static String WHITE_BACKGROUND   (){ return Console.ANSI_WHITE_BACKGROUND; }
}
