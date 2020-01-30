package date2020_01_09_Ticketshop;

import date2020_01_09_Ticketshop.res.WebsiteAccess;

import java.util.GregorianCalendar;
import java.util.LinkedList;

import static date2020_01_09_Ticketshop.res.Person.Sex.MALE;

public class Ticketshop {
    private static TicketManager ticketManager = new TicketManager();
    private static long currentUser = 1;

    public static boolean checkInput(String input, LinkedList<String> Possibilities){
        for(String str : Possibilities){
            str.toLowerCase();
        }
        return Possibilities.contains(input.toLowerCase());
    }

    public static int menu(){
        boolean hasTickets = ticketManager.getTicketAmountOf(currentUser) < 1;
        while(true) {
            if(currentUser < 0){


            }

            System.out.println(UI.ANSI_GREEN + UI.ANSI_BLUE_BACKGROUND + "What do you want to do?" + UI.newLine() + "1. Enter Ticketshop" + UI.newLine() + "2. View Owned Tickets" + UI.newLine());
            // Change color if user can go to event
            if(hasTickets){
                System.out.println(UI.ANSI_RED + "3. Go to event" + UI.ANSI_RESET);
            }

            switch(Integer.valueOf(UI.readString())){
                case 1:
                    return 1;
                case 2:
                    return 2;
                case 3:
                    if(!hasTickets) break;
                    return 3;
                default:
                    return 0;
            }


           /* if (currentUser.ticketAmount() < 1){
                System.out.println(UI.ANSI_RED + "3. Go to event" + UI.ANSI_GREEN);
            }else{
                System.out.println("3. Go to event");
            }
            System.out.print("4. Change user"); if(currentUser == null) System.out.println(" (recommended)");*/
        }
        // Exit without Problem
        //return 0;
    }

    public static void main(String[] args) {
        //ticketManager.addCustomer(new Customer("Tobias", new GregorianCalendar(2002,0,30), new Address("Weyer"), MALE));
        Customer user = new Customer("Tobias", new GregorianCalendar(2002,1,30), new Address("Weyer"), MALE);
        //System.out.println(Customers.get(0).money + " " + Customers.get(0).ticketAmount() + " " + Customers.get(0).getName() + " " + Customers.get(0).getBirthDate() + " " + Customers.get(0).getSex());

        WebsiteAccess websiteAccess = new WebsiteAccess();
        menu();

        System.out.println("I don know");
    }
}
