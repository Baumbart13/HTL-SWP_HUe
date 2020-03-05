package date2020_02_27;

import miscForEverything.UI;

import java.time.LocalDate;

import static date2020_02_27.MenuOption.*;
import date2020_02_27.Menu;

public class Dateformats {
    private static String choice = "";
    private static Menu menu = new Menu();

    private static MenuOption read(){
        choice = UI.readString().toLowerCase();
        switch(choice){
            case "0": return HOW_MANY_MONDAYS_UNTIL;
            case "1": return HOW_MANY_MONADYS_SINCE_1900;
            case "2": return HOW_MANY_MONDAYS_SINCE_UNTIL;
            case "3": return LAST_MONDAY_OF_YEAR;
            case "4": return DAYS_UNTIL;
            case "5": return GET_WEEKDAY_OF_DATE;
            case "6": return PALINDROME_DATE;
            case "x": return END;
        }
        return INVALID;
    }

    private static void printOptions(){
        System.out.println(UI.ANSI_GREEN + "What do you want to do?");

        System.out.println("[0]\tHow many mondays are left until a certain date from now on?");
        System.out.println("[1]\tHow many mondays have passed since January 1st 1900?");
        System.out.println("[2]\tHow many mondays are there between two dates?");
        System.out.println("[3]\tGet the date of the last monday of a year, you choose");
        System.out.println("[4]\tHow many days, from now on, are left to a certain date you choose?");
        System.out.println("[5]\tWhich day of the week is a date you choose?");
        System.out.println("[6]\tWhich dates between two given are a palindrome");
        System.out.println(UI.ANSI_RED + "[x]\tEnd program" + UI.ANSI_RESET);
    }

    public static void mondaysUntil(){
        System.out.println("[YYYY-MM-DD]" + System.lineSeparator() + "To which date should the mondays be counted?");

        StringBuilder year = new StringBuilder(UI.readString());
        StringBuilder month = new StringBuilder(year.substring(5, 7));
        StringBuilder day = new StringBuilder(year.substring(8, year.length()));
        year.delete(4, year.length());

        LocalDate date = LocalDate.of(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()), Integer.parseInt(day.toString()));

        System.out.println("There are " + DateSearches.howManyMondays(date) + " mondays left" + System.lineSeparator());
    }

    public static void mondaysSinceUntil(){
        StringBuilder[] in = new StringBuilder[3];

        System.out.println("[YYYY-MM-DD]" + System.lineSeparator() + "From which date should the mondays be counted");
        in[0] = new StringBuilder(UI.readString());
        in[1] = new StringBuilder(in[0].substring(5, 7));
        in[2] = new StringBuilder(in[0].substring(8, in[0].length()));
        in[0].delete(4, in[0].length());

        LocalDate from = LocalDate.of(Integer.parseInt(in[0].toString()), Integer.parseInt(in[1].toString()), Integer.parseInt(in[2].toString()));

        System.out.println("[YYYY-MM-DD]" + System.lineSeparator() + "To which date shall the mondays be counted?");
        in[0] = new StringBuilder(UI.readString());
        in[1] = new StringBuilder(in[0].substring(5, 7));
        in[2] = new StringBuilder(in[0].substring(8, in[0].length()));
        in[0].delete(4, in[0].length());

        LocalDate to = LocalDate.of(Integer.parseInt(in[0].toString()), Integer.parseInt(in[1].toString()), Integer.parseInt(in[2].toString()));

        System.out.println("There are " + DateSearches.howManyMondays(from, to) + " mondays between these dates");
    }

    public static void mondaysSince1900(){
        System.out.println("[YYYY-MM-DD] - Empty to count mondays until now" + System.lineSeparator() + "To which date shall the mondays be counted?");
        StringBuilder text = new StringBuilder(UI.readString());

        int mondays;
        if(text.length() == 10){
            LocalDate date = LocalDate.of(Integer.parseInt(text.substring(4, text.length())), Integer.parseInt(text.substring(5, 7)), Integer.parseInt(text.substring(8, text.length())));
            mondays = DateSearches.howManyMondaysSince1900(date);
        }else{
            mondays = DateSearches.howManyMondaysSince1900();
        }

        System.out.println("There were " + mondays + " mondays since 1900-01-01");
    }

    public static void lastMondayOfYear(){
        System.out.println();
    }

    public static void daysUntil(){
        System.out.println();
    }

    public static void getDateWeekday(){
        System.out.println();
    }

    public static void palindromeDate(){
        System.out.println();
    }

    public static boolean menu(){
        printOptions();
        switch(read()){
            case INVALID:
                System.out.println("Invalid input!");
                break;  // 0
            case HOW_MANY_MONDAYS_UNTIL:
                mondaysUntil();
                break;  // 1
            case HOW_MANY_MONADYS_SINCE_1900:
                mondaysSince1900();
                break;  // 2
            case HOW_MANY_MONDAYS_SINCE_UNTIL:
                mondaysSinceUntil();
                break;  // 3
            case LAST_MONDAY_OF_YEAR:
                lastMondayOfYear();
                break;  // 4
            case DAYS_UNTIL:
                daysUntil();
                break;  // 5
            case GET_WEEKDAY_OF_DATE:
                getDateWeekday();
                break;  // 6
            case PALINDROME_DATE:
                palindromeDate();
                break;
            case END:
                return false;
        }
        return true;
    }

    public static void main(String[] args){
        while(!choice.equalsIgnoreCase("x")){
            if(!menu()){
                System.gc();
                break;
            }
        }
    }
}
