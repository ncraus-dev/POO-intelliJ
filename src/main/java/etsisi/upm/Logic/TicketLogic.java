package etsisi.upm.Logic;

import etsisi.upm.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class TicketLogic {
    private TicketCatalog ticketCatalog;
    private UserCatalog userCatalog;

    public TicketLogic(TicketCatalog ticket, UserCatalog catalog) {
        this.ticketCatalog = ticket;
        this.userCatalog= catalog;
    }

    public Ticket handleTicketnew(String input) {
        Scanner sc = new Scanner(input);
        String i1 = sc.next();
        String i2 = sc.next();

        if (sc.hasNextLine()) {
            String i3 = sc.next();
            if(userCatalog.getCashier(i2)==null){
                throw new IllegalArgumentException("The ticket don't have "+i2+" in the catalog"); }
            if(userCatalog.getClient(i3)==null){
                throw new IllegalArgumentException("The ticket don't have "+i3+" in the catalog"); }
            return ticketCatalog.ticketNew1(i1, i2, i3);

        } else {
            if(userCatalog.getCashier(i1)==null){
                throw new IllegalArgumentException("The ticket don't have a cashier in the catalog"); }
            if(userCatalog.getClient(i2)==null){
                throw new IllegalArgumentException("The ticket don't have a client in the catalog"); }
            return ticketCatalog.ticketNew2(i1, i2);

        }
    }

    public List<String> handleTicketAdd(String input) {
        Scanner sc = new Scanner(input);

        String ticketId = sc.next();
        String cashId = sc.next();
        int prodId = sc.nextInt();
        int amount = sc.nextInt();
        boolean hasPersonalization = sc.hasNext();
        // Verificamos si hay algo más en la línea (las personalizaciones)
        Ticket ticket = ticketCatalog.getTicket(ticketId);
        if (ticket == null) throw new IllegalArgumentException("Ticket not found.");
        if(ticket.getState()=="CLOSED"){ throw new IllegalArgumentException("The ticket is closed."); }
        boolean added;
        if (hasPersonalization) {
            //String pers = sc.next().trim(); // "--pred", esto solo cogia la primera palabra
            String pers = sc.nextLine().trim();//para coger toda la linea
            added = ticketCatalog.ticketAdd(ticketId, cashId, prodId, amount, pers);
        } else {
            added = ticketCatalog.ticketAdd(ticketId, cashId, prodId, amount);
        }
        if (!added)
            throw new IllegalArgumentException("Error adding product to ticket.");
        return buildTicketStrings(ticket);
    }

    public List<String> handleTicketPrint(String input) {
        Scanner sc = new Scanner(input);
        String ticketId = sc.next().trim();
        String cashId = sc.next().trim();
        //String aux = ticketId + cashId;
        //List<String> printedTicket = new ArrayList<>();
        Ticket ticket = ticketCatalog.getTicket(ticketId);
        ticketCatalog.getTicket(ticketId).setOpen(2);
        return buildTicketStrings(ticket);
    }

    public List<String> handleTicketRemove(String input) {
        Scanner sc = new Scanner(input);
        String ticketId = sc.next().trim();
        String cashId = sc.next().trim();
        int prodId = sc.nextInt();
        Ticket ticket = ticketCatalog.getTicket(ticketId);
        if(ticket == null) throw new IllegalArgumentException("Ticket not found.");
        if(ticket.getState()=="CLOSED"){ throw new IllegalArgumentException("The ticket is closed."); }
        if (ticketCatalog.ticket_remove(ticketId, cashId, prodId)) {
            return buildTicketStrings(ticket);
        } else throw new IllegalArgumentException("Error removing product from ticket.");
    }


    public List<String> handleTicketList() {
        List<String> printedList = new ArrayList<>();
        for (Ticket t : ticketCatalog.getTickets()) {
            printedList.add(t.getId() + "- " + t.getState());
        }

        return printedList;
    }


    private List<String> buildTicketStrings(Ticket ticket) {
        List<String> lines = new ArrayList<>();
        if (ticket.getState() == "CLOSED") lines.add("" + ticket.getId() + "-" +  ticket.getTimeClose());
        else{lines.add(" " + ticket.getId());}

        for (TicketItem item : ticket.getItems()) {
            double discount = item.getDiscount();
            String discountStr = discount > 0 ? " **discount -" + (float) discount : " ";

            if (item.getProduct() instanceof ProductCustom) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    lines.add(item + discountStr);
                }
            } else if (item.getProduct() instanceof ProductMeeting meeting) {
                String meetingLine = String.format(
                        "{class:Meeting, id:%d, name:'%s', price:%.2f, date of Event:%s, max people allowed:%d, actual people in event:%d}",
                        meeting.getId(), meeting.getName(), meeting.getPrice() * item.getQuantity(),
                        meeting.getExpiration(), meeting.getMaxPeople(), item.getQuantity());
                lines.add(meetingLine);
            } else if (item.getProduct() instanceof ProductFood food) {
                String foodLine = String.format(
                        "{class:Food, id:%d, name:'%s', price:%.2f, date of Event:%s, max people allowed:%d, actual people in event:%d}",
                        food.getId(), food.getName(), food.getPrice() * item.getQuantity(), food.getExpiration(), food.getMaxPeople(), item.getQuantity());
                lines.add(foodLine);
            } else {
                lines.add(item + discountStr);
            }
        }

        lines.add("\sTotal price : " + String.format(Locale.US, "%.2f", ticket.calculateTotal()));
        lines.add("\sTotal discount : " + String.format(Locale.US, "%.2f", ticket.calculateDiscount()));
        lines.add("\sFinal price : " + String.format(Locale.US, "%.2f", ticket.calculateFinal()));

        return lines;
    }

}