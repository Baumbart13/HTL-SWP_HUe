package date2020_01_09_Ticketshop;

import date2020_01_09_Ticketshop.Exceptions.CantCreateException;

import java.util.GregorianCalendar;

public class Ticket {
    private static int newID = 0;
    protected int ID;
    protected String eventName;
    protected double price;
    protected GregorianCalendar date;
    protected GregorianCalendar buyingDate;
    protected Address location;

    public Ticket(String eventName, GregorianCalendar date, Address location, double price, GregorianCalendar buyingDate, int ID) throws CantCreateException {
        if(ID < 0){
            throw new CantCreateException("ID can't be less than 0");
        }else{
            this.ID = ID;
        }
        this.eventName = eventName;
        this.date = date;
        this.price = price;
        this.buyingDate = buyingDate;
        this.location = location;
        this.ID = newID++;
    }

    public Ticket(Ticket ticket){this.eventName = ticket.eventName;
        this.date = ticket.date;
        this.price = ticket.price;
        this.location = ticket.location;
        this.buyingDate = ticket.buyingDate;
        this.ID = ticket.ID;
    }

    public Ticket(Ticket ticket, GregorianCalendar buyingDate){
        this.eventName = ticket.eventName;
        this.date = ticket.date;
        this.price = ticket.price;
        this.location = ticket.location;
        this.buyingDate = buyingDate;
        this.ID = ticket.ID;
    }

    public int getID() {
        return ID;
    }

    public String getEventName() {
        return eventName;
    }

    public double getPrice() {
        return price;
    }

    public GregorianCalendar getDate() {
        return date;
    }

    public GregorianCalendar getBuyingDate() {
        return buyingDate;
    }

    public Address getLocation() {
        return location;
    }
}
