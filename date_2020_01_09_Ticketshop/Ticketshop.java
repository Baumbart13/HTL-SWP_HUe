package date_2020_01_09_Ticketshop;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import static date_2020_01_09_Ticketshop.res.Person.Sex.MALE;

public class Ticketshop {
    private static LinkedList<Customer> Customers = new LinkedList<Customer>();
    private static Customer currentUser = null;

    public static boolean checkInput(String input, LinkedList<String> Possibilities){
        for(String str : Possibilities){
            str.toLowerCase();
        }
        return Possibilities.contains(input.toLowerCase());
    }

    public static int menu(){

        while(true) {
            if(currentUser == null){
                System.out.println("Login..." + UI.newLine() + "Email:");
                String email = UI.readString();

            }

            System.out.println(UI.ANSI_GREEN + UI.ANSI_BLUE_BACKGROUND + "What do you want to do?" + UI.newLine() + "1. Enter Ticketshop" + UI.newLine() + "2. View Owned Tickets" + UI.newLine());
            // Change color if user can go to event
            if (currentUser.ticketAmount() < 1){
                System.out.println(UI.ANSI_RED + "3. Go to event" + UI.ANSI_GREEN);
            }else{
                System.out.println("3. Go to event");
            }
            System.out.print("4. Change user"); if(currentUser == null) System.out.println(" (recommended)");
break;
        }
        // Exit without Problem
        return 0;
    }

    public static void main(String[] args) {
        Customers.add(new Customer("Tobias", new GregorianCalendar(2002, 0, 30), new Address("Weyer"), MALE));
        System.out.println(Customers.get(0).money + " " + Customers.get(0).ticketAmount() + " " + Customers.get(0).getName() + " " + Customers.get(0).getBirthDate() + " " + Customers.get(0).getSex());
        System.out.println(Customers.get(0).money + " " + Customers.get(0).ticketAmount());

        System.out.println("I don know");
    }
}
