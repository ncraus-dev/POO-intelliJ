package etsisi.upm.Display;

import etsisi.upm.Logic.Ticket;
import etsisi.upm.Logic.TicketCatalog;
import etsisi.upm.Logic.TicketLogic;
import etsisi.upm.Logic.UserCatalog;

import java.util.Locale;
import java.util.List;

public class TicketDisplay {
    TicketLogic ticketLogic;
    TicketCatalog ticket;
    UserCatalog userCatalog;

    public TicketDisplay(TicketCatalog list,UserCatalog userCatalog) {
        this.ticket = list;
        this.userCatalog = userCatalog;
        this.ticketLogic = new TicketLogic(ticket,userCatalog);
    }
    public String displayTicketNew(String input){
      Ticket ticket= ticketLogic.handleTicketnew(input);
        return "Ticket: " + ticket.getId() + "\n\tTotal price: " + ticket.calculateTotal() +
        "\n\tTotal discount: " + ticket.calculateDiscount() + "\n\tFinal price: " + ticket.calculateFinal() + "\nticket new: ok\n";
    }

    public String displayTicketAdd(String input){
        List<String> ticket= ticketLogic.handleTicketAdd(input);
        StringBuilder result = new StringBuilder("Ticket:");
        for (String p : ticket) {
            result.append("\s" + p).append("\n");
        }
        return result.toString().trim()  + "\nticket add: ok\n";
    }
    public String displayTicketRemove(String input){
        List<String> ticket= ticketLogic.handleTicketRemove(input);
        StringBuilder result = new StringBuilder("Ticket:");
        for (String p : ticket) {
            result.append("\s" +p).append("\n");
        }
        return result.toString().trim()  + "\nticket remove: ok\n";
    }
    public String displayTicketPrint(String input){
        List<String> items = ticketLogic.handleTicketPrint(input);
        StringBuilder result = new StringBuilder("Ticket:");
        for (String p : items) {
            result.append("\s" +p).append("\n");
        }
        return result.toString().trim()  + "\nticket print: ok\n";
    }
    public String displayTicketList(){
        List<String> tickets = ticketLogic.handleTicketList();
        StringBuilder result = new StringBuilder("Ticket List:\n");
        for (String p : tickets) {
            result.append(p).append("\n");
        }
        return result.toString().trim() + "\nticket list: ok\n";

    }

}
