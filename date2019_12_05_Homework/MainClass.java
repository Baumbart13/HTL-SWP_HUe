package date2019_12_05_Homework;

import java.util.Scanner;

public class MainClass {

    public static boolean checkInput(String input){

        return true || false;
    }

    public static void main(String[] args) {
        String input = "";
        String output = "";

        try {
            System.out.println("gimme number");
            input = UI.readString();
            checkInput(input);

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
