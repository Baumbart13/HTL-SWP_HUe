package date2019_12_05_Homework;

import java.util.Scanner;

public class MainClass {

    public static String readString(){
        Scanner reader = new Scanner(System.in);
        String in = "";
        try {
            in = reader.nextLine();
        }catch(Exception e){
            System.out.println("Bruh, wat happened?");
            e.printStackTrace();
        }finally{
            reader.close();
            return in;
        }
    }

    public static void main(String[] args) {
        String input = "";
        String output = "";

        try {
            System.out.println("gimme number");
            input = readString();

            /*if (!(input.length() >= 8)) {
                throw new LengthException();
            }


            if(!input.matches("01")){
                throw new BaseException("Bruh");
            }*/

            output = Integer.toString(BaseConverter.convert(Integer.parseInt(input), 10, 2));
            System.out.println("\n\nDone\t" + output);
        }/*catch(LengthException lE){
            lE.printStackTrace();
        }*/catch(BaseException bE){
            bE.printStackTrace();
        }
    }
}
