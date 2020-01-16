package date_2020_01_09_Ticketshop;

import java.util.GregorianCalendar;

public class Ticket {
    String eventName;
    double price;
    GregorianCalendar date;
    GregorianCalendar buyingDate;
    Places location;

    public Ticket(String eventName, GregorianCalendar date, String location, double price, GregorianCalendar buyingDate) throws EnumConstantNotPresentException{
        this.eventName = eventName;
        this.date = date;
        this.price = price;
        this.buyingDate = buyingDate;
        if(Places.values().toString().toLowerCase().contains(location.toLowerCase())){
            this.location = Places.valueOf(location);
        }else{
            throw new EnumConstantNotPresentException(Places.class, location);
        }
    }

    public Ticket(Ticket ticket, GregorianCalendar buyingDate){
        this.eventName = ticket.eventName;
        this.date = ticket.date;
        this.price = ticket.price;
        this.location = ticket.location;
        this.buyingDate = buyingDate;
    }
}
