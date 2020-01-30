package date2020_01_09_Ticketshop;

import java.util.HashMap;
import java.util.LinkedList;

public class TicketManager {
    private LinkedList<Customer> customers;
    private HashMap<Long, LinkedList<Ticket>> map;

    public TicketManager(){
        this.map = new HashMap<Long, LinkedList<Ticket>>();
        this.customers = new LinkedList<Customer>();
    }

    public boolean customerExists(String email){
        return (this.map.get(email) != null);
    }

    public Customer getCustomer(long customerId){
        if((int)customerId < 0 || (int)customerId > this.customers.size()) return null;
        return customers.get((int)customerId);
    }

    public int getTicketAmountOf(Customer customer){
        return this.map.get(customer.getID()).size();
    }

    public int getTicketAmountOf(long customerId){
        return this.map.get(customerId).size();
    }

    public boolean customerExists(Customer customer){
        return this.map.containsKey(customer.getID());
    }

    public boolean customerExists(long customerId){
        return this.map.containsKey(customerId);
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
        this.map.put(customer.getID(), new LinkedList<Ticket>());
    }

    public void addTicket(Customer customer, Ticket ticket){
        this.map.get(customer.getID()).add(ticket);
    }

    public void addTicket(int customerId, Ticket ticket){
        this.map.get(customerId).add(ticket);
    }
}
