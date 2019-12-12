package date2019_12_05_Homework;

import java.util.Scanner;

public class UI {
    static Scanner reader= new Scanner(System.in);

    private UI() throws CantCreateException{
        throw new CantCreateException();
    }

    public static String readString(){
        String in = "";
        try{
            in = reader.nextLine();
        }catch(Exception e){
            System.out.println("Bruh, what happened?");
            e.printStackTrace();
        }finally{
            reader.close();
            return in;
        }
    }
}
