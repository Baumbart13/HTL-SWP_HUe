package miscForEverything;

import miscForEverything.CustomExceptions.CantCreateException;

import java.io.IOException;
import java.util.Scanner;

public class UI implements ConsoleSpecs{
    private static Scanner reader = new Scanner(System.in);

    private UI() throws CantCreateException {
        throw new CantCreateException();
    }

    public static void waitForKeypress(){
        try{
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
        reader = reader.reset();
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
        reader = reader.reset();
        return out;
    }

    public static String newLine(){
        return System.lineSeparator();
    }
}
