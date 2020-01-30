package date2020_01_09_Ticketshop;

import java.util.Scanner;
import date2020_01_09_Ticketshop.Exceptions.CantCreateException;

public class UI implements date2020_01_09_Ticketshop.res.ConsoleSpecs{
    private static Scanner reader= new Scanner(System.in);

    private UI() throws CantCreateException {
        throw new CantCreateException();
    }

    public static String readString(){
        String in = "";
        try{
            in = reader.nextLine();
        }catch(Exception e){
            System.out.println("Bruh, what happened?");
            e.printStackTrace();
        }
        return in.trim();
    }

    public static String newLine(){
        return System.lineSeparator();
    }
}
