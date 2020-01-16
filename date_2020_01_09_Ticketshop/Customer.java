package date_2020_01_09_Ticketshop;

import date_2020_01_09_Ticketshop.Exceptions.CantRemoveException;
import date_2020_01_09_Ticketshop.res.Person;
import java.util.*;

public class Customer extends Person {
    protected LinkedList<Ticket> Tickets;
    protected double money;

    public Customer(String name, GregorianCalendar birthDate, Address residence, Sex sex){
        super(name, birthDate, residence, sex);
        this.money = 0.000;
        this.Tickets = new LinkedList<Ticket>();
    }

    public Customer(String name, GregorianCalendar birthDate, Address residence, Sex sex, double money){
        super(name, birthDate, residence, sex);
        this.money = money;
        this.Tickets = new LinkedList<Ticket>();
    }

    public void addTicket(Ticket ticket, GregorianCalendar buyingDate){
        this.Tickets.add(new Ticket(ticket, buyingDate));
    }

    public LinkedList<Ticket> getTickets(){
        LinkedList<Ticket> out = new LinkedList<>();
        for(Ticket t : this.Tickets){
            out.add(t);
        }
        return out;
    }

    public int ticketAmount(){
        return this.Tickets.size();
    }

    public Ticket getTicket(int index){
        return this.Tickets.get(index);
    }

    public void removeTicket(String name) throws CantRemoveException {
        for(int i = 0; i < this.Tickets.size(); ++i){
            if(this.Tickets.get(i).eventName.equalsIgnoreCase(name)){
                this.Tickets.remove(i);
            }
        }
    }

    public void removeTicket(int index) throws CantRemoveException{
        this.Tickets.remove(index);
    }
}
