package miscForEverything;

import miscForEverything.CustomExceptions.CantCreateException;

import java.util.Scanner;

public class UI implements ConsoleSpecs{
    private static Scanner reader = new Scanner(System.in);

    private UI() throws CantCreateException {
        throw new CantCreateException();
    }

    public static String readString(){
        String in = "";
        try{
            in = reader.nextLine();
        }catch(Exception e){
            System.err.println("Bruh, what happened?");
            e.printStackTrace();
        }
        return in.trim();
    }

    public static int readInteger(){
        Integer in = 0;
        try{
            in = reader.nextInt();
        }catch(NumberFormatException e){
            System.err.println("Mate, that is not a number" + System.lineSeparator() + "Here another try:");
            return readInteger();
        }catch(Exception e){
            System.err.println("Bruh, what happened?");
            e.printStackTrace();
        }
        return in;
    }

    public static String newLine(){
        return System.lineSeparator();
    }
}
