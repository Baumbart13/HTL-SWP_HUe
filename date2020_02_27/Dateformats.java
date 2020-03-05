package date2020_02_27;

import miscForEverything.UI;

import java.time.LocalDate;

import static date2020_02_27.MenuOption.*;
import date2020_02_27.Menu;

public class Dateformats {
    private static String choice = "";
    private static Menu menu = new Menu();

    public static void main(String[] args){
        while(!menu.input.toString().equalsIgnoreCase("x")){
            if(!menu.menu()){
                System.gc();
                break;
            }
        }
    }
}
