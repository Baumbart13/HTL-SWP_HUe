package date2020_02_27;

import miscForEverything.UI;

import java.time.LocalDate;
import java.util.Date;

import static date2020_02_27.MenuOption.*;

public class Menu {
    protected StringBuilder input;

    public Menu(){
        input = new StringBuilder();
    }

    protected static void printOptions(){
        System.out.println(UI.ANSI_GREEN + "What do you want to do?");

        System.out.println("[0]\tHow many mondays are left until a certain date from now on?");
        System.out.println("[1]\tHow many mondays are there in one year?");
        System.out.println("[2]\tHow many mondays have passed since January 1st 1900?");
        System.out.println("[3]\tHow many mondays are there between two dates?");
        System.out.println("[4]\tGet the date of the last monday of a year you choose");
        System.out.println("[5]\tHow many days, from now on, are left to a certain date you choose?");
        System.out.println("[6]\tWhich day of the week is a date you choose?");
        System.out.println("[7]\tWhich dates between two given are a palindrome");
        System.out.println(UI.ANSI_RED + "[x]\tEnd program" + UI.ANSI_RESET);
    }

    public MenuOption read(){
        String in = UI.readString();
        switch(in){
            case "0": return HOW_MANY_MONDAYS_UNTIL;
            case "1": return HOW_MANY_MONDAYS_IN_YEAR;
            case "2": return HOW_MANY_MONADYS_SINCE_1900;
            case "3": return HOW_MANY_MONDAYS_SINCE_UNTIL;
            case "4": return LAST_MONDAY_OF_YEAR;
            case "5": return DAYS_UNTIL;
            case "6": return GET_WEEKDAY_OF_DATE;
            case "7": return PALINDROME_DATE;
            case "x": return END;
        }
        return INVALID;
    }

    public boolean menu(){
        this.printOptions();
        switch(this.read()){
            case INVALID:
                System.out.println("Invalid Input!");
                break;
            case HOW_MANY_MONDAYS_UNTIL:
                this.howManyMondaysUntil();
                break;
            case HOW_MANY_MONDAYS_IN_YEAR:
                this.howManyMondaysInYear();
                break;
            case HOW_MANY_MONADYS_SINCE_1900:
                this.howManyMondaysSince1900();
                break;
            case HOW_MANY_MONDAYS_SINCE_UNTIL:
                this.howManyMondaysSinceUntil();
                break;
            case LAST_MONDAY_OF_YEAR:
                this.lastMondayOfYear();
                break;
            case DAYS_UNTIL:
                this.daysUntil();
                break;
            case GET_WEEKDAY_OF_DATE:
                this.getWeekdayOfDate();
                break;
            case PALINDROME_DATE:
                this.palindromeDate();
                break;
            case END:
                return false;
        }
        return true;
    }


    protected void howManyMondaysUntil(){
        System.out.println("[YYYY-MM-DD]" + System.lineSeparator() + "To which date should the mondays be counted?");

        StringBuilder year = new StringBuilder(UI.readString());
        StringBuilder month = new StringBuilder(year.substring(5, 7));
        StringBuilder day = new StringBuilder(year.substring(8, year.length()));
        year.delete(4, year.length());

        LocalDate date = LocalDate.of(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()), Integer.parseInt(day.toString()));

        System.out.println("There are " + DateSearches.howManyMondays(date) + " mondays left" + System.lineSeparator());
    }

    protected void howManyMondaysInYear(){
        System.out.println("[YYYY]" + System.lineSeparator() + "In which date shall the mondays be counted?");
        int in = UI.readInteger();
        System.out.println("There are " + DateSearches.howManyMondaysInYear(in) + " mondays in the year " + in);
    }

    protected void howManyMondaysSince1900(){
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

    protected void howManyMondaysSinceUntil(){
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

    protected void lastMondayOfYear(){
        System.out.println("[YYYY]" + System.lineSeparator() + "In which year should the date of the last monday be calculated?");
        int in = UI.readInteger();
        System.out.println("[a]\tAmerican format" + System.lineSeparator() + "[e]\tEuropean format");
        boolean format = false;
        if(UI.readString().equalsIgnoreCase("a")){
            format = true;
        }

        System.out.println("The last monday is at this date: " + DateSearches.lastMondayOfYear(in, format));
    }

    protected void daysUntil(){
        System.out.println("To which date shall the days be counted?");

        StringBuilder year = new StringBuilder(UI.readString());
        StringBuilder month = new StringBuilder(year.substring(5, 7));
        StringBuilder day = new StringBuilder(year.substring(8, year.length()));
        year.delete(4, year.length());

        System.out.println("There are " + DateSearches.daysUntil(LocalDate.of(Integer.parseInt(year.toString()), Integer.parseInt(month.toString()), Integer.parseInt(day.toString())), LocalDate.now()) + " days left");
    }

    protected void getWeekdayOfDate(){
        System.out.print("[YYYY-MM-DD]" + System.lineSeparator() + "Which weekday has the date: ");
        StringBuilder year = new StringBuilder(UI.readString());
        StringBuilder month = new StringBuilder(year.substring(5, 7));
        StringBuilder day = new StringBuilder(year.substring(8, year.length()));
        year.delete(4, year.length());

        LocalDate date = null;

        System.out.println(System.lineSeparator() + DateSearches.getWeekdayOfDate(date));
    }

    protected void palindromeDate(){

    }
}
