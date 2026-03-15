package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;

public class TicketCatalog {
    private Map<String, Ticket> ticketList = new LinkedHashMap<>();

    public TicketCatalog() { }


    public Ticket ticketNew1(String id, String cashId, String userId, TypeTicket type, ITicketPrinter<Sales> printer) {
        String compositeId = cashId + " " + id;
        if (ticketList.containsKey(compositeId)) {
            throw new IllegalArgumentException("Ticket ID already exists for this cashier: " + compositeId);
        }
        Ticket<Sales> newTicket = new Ticket<>(id, cashId, userId, type, printer);
        ticketList.put(compositeId, newTicket);
        return newTicket;
    }


    public Ticket ticketNew2(String cashId, String userId, TypeTicket type, ITicketPrinter<Sales> printer) {
        int counter = 1;
        String newId;
        String compositeId;

        do {
            newId = String.valueOf(counter);
            compositeId = cashId + " " + newId;
            counter++;
        } while (ticketList.containsKey(compositeId));

        Ticket<Sales> newTicket = new Ticket<>(newId, cashId, userId, type, printer);
        ticketList.put(compositeId, newTicket);
        return newTicket;
    }


    public Ticket getTicket(String ticketId, String cashId) {
        if (ticketId == null || cashId == null) {
            return null;
        }
        String compositeKey = cashId.trim() + " " + ticketId.trim();
        return ticketList.get(compositeKey);
    }

    public boolean removeTicket(String ticketId, String cashId) {
        if (ticketId == null || cashId == null) {
            return false;
        }
        String compositeKey = cashId.trim() + " " + ticketId.trim();
        return ticketList.remove(compositeKey) != null;
    }


    public void removeTicketsByCashier(String cashId) {
        if (cashId == null) return;

        List<String> keysToRemove = new ArrayList<>();
        for (String key : ticketList.keySet()) {
            if (key.startsWith(cashId + " ")) {
                keysToRemove.add(key);
            }
        }
        for (String key : keysToRemove) {
            ticketList.remove(key);
        }
    }

    public Collection<Ticket> getTickets() {
        return ticketList.values();
    }

    public void setTicketsMap(Map<String, Ticket> ticketMap) {
        this.ticketList = ticketMap;
    }
}