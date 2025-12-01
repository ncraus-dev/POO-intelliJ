package etsisi.upm.Model;

import etsisi.upm.Logic.Ticket;

import java.util.*;

public class Cashier extends User {
    private String id;
    private List<Ticket> tickets = new LinkedList<>();

    public Cashier(String id, String name, String email) {
        super(name, email);
        this.id = id;
    }


    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    public boolean addTicket(Ticket ticket) {
        return (tickets.add(ticket)) ? true : false;
    }

    public boolean removeTicket(String id) {
        Iterator<Ticket> iterator = tickets.iterator();
        boolean removed = false;
        while (iterator.hasNext() && !removed) {
            Ticket ticket = iterator.next();
            if (Objects.equals(ticket.getId(), id)) {
                tickets.remove(ticket);
                removed = true;
            }
        }
        return removed;
    }

    public List<Ticket> listTickets() {
        return new ArrayList<>(tickets);
    }


    @Override
    public String toString() {
        return "Cash{identifier='" + id + "', name='" + name + "', email='" + email + "'}\n";
    }
}
