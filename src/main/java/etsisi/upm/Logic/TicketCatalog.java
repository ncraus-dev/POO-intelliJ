package etsisi.upm.Logic;
import etsisi.upm.Model.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;


public class TicketCatalog {
    private Map<String, Ticket> ticketList;
    private ProductCatalog products;

    public TicketCatalog(ProductCatalog products) {
        this.ticketList = new TreeMap<>();
        this.products = products;
    }

    public Ticket ticketNew1(String id, String cashId, String userId) {
        Ticket ticket;
        String aux = cashId + " " + id;
        if (ticketList.containsKey(aux )) throw new IllegalArgumentException("Ticket already exists!");
        else {
            ticket = new Ticket(id,cashId, userId);

            ticketList.put(aux, ticket);
            return ticket;
        }
    }
   public Ticket ticketNew2(String cashId, String userId) {
        Random rand = new Random();
        int randNum = rand.nextInt(99999);
        String aux = String.format("%05d", randNum);
        while (ticketList.containsKey(cashId + " " + aux)) {
            randNum = rand.nextInt(99999);
            aux = String.format("%05d", randNum);
        }
        LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        String id = date.toString();
       id= id.replace("T","-");
        id +=" "+aux;
        Ticket ticket = new Ticket(id,cashId, userId);
        cashId += aux;
        ticketList.put(cashId, ticket);
        return ticket;
    }


    public boolean ticket_remove(String ticketId, String cashId, int prodId) {
        String aux = cashId +" "+ ticketId;
        if (ticketList.get(aux).removeProduct(prodId)) return true;
        else return false;
    }


    public boolean ticketAdd(String ticketId, String cashId, int productId, int amount, String pers) {
        String aux = cashId +" "+ ticketId;
        TicketItem item = new TicketItem(products.getProduct(productId), amount);
        item.settextPersonalizable(pers);
        if (ticketList.get(aux) != null) {
            ticketList.get(aux).addProduct(item);
            return true;
        } else {
            return false;
        }
    }

    public boolean ticketAdd(String ticketId, String cashId, int productId, int amount) {
        String aux = cashId +" "+ ticketId;
        TicketItem item = new TicketItem(products.getProduct(productId), amount);
        if(ticketList.get(aux)!=null){
        ticketList.get(aux).addProduct(item);
        return true;}
        else return false;
    }



    public Ticket getTicket(String ticketId) {
        for(Ticket ticket : ticketList.values()){
            if(ticket.getId().equals(ticketId)) return ticket;
        }
        return null;
    }

    public List<Ticket> getTickets() {
        return new ArrayList<>(ticketList.values());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}

