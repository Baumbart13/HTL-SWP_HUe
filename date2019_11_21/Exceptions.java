package date2019_11_21;


import java.io.*;
import java.util.Scanner;

public class Exceptions {
    public static void main(String[] args){

        String fullPath = "\"programmieren-2019-11-07.txt\"";
        Scanner reader = new Scanner(System.in);

        try{
            reader = new Scanner(new File(fullPath));   // File not found Exception

            System.out.println(Integer.parseInt("SAMPLE")); // Number format Excpetion

            System.out.println(fullPath.charAt(fullPath.length()+20));    // Index out of range Exception

        }catch(FileNotFoundException fnfE) {
            System.out.println("File not found!\t" + fnfE.toString());
        }catch(NumberFormatException nfE) {
            System.out.println("Number Format fail!\t" + nfE.toString());
        }catch (IndexOutOfBoundsException ioobE) {
            System.out.println("Index out of range!\t" + ioobE.toString());
        }catch(Exception e){
            System.out.println("WTF, what happened?!");
        }finally{
            reader.close();
            System.gc();
            System.exit(1);
        }

        System.out.println("\n\nEnd of program");
    }
}
