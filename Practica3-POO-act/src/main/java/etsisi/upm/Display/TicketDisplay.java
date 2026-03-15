package etsisi.upm.Display;

import etsisi.upm.Logic.*;
import etsisi.upm.Model.*;
import java.util.List;
import java.util.Scanner;

public class TicketDisplay {
    private final TicketLogic ticketLogic;
    private final TicketCatalog ticketCatalog;
    private final UserCatalog userCatalog;


    public TicketDisplay(TicketCatalog ticketCatalog, UserCatalog userCatalog, TicketLogic ticketLogic) {
        this.ticketCatalog = ticketCatalog;
        this.userCatalog = userCatalog;
        this.ticketLogic = ticketLogic;
    }

    public String displayTicketNew(String input) {
        try {
            Ticket ticket = ticketLogic.handleTicketNew(input);
            StringBuilder result = new StringBuilder();
            result.append("Ticket : ").append(ticket.getId());
            result.append(" (").append(ticket.getType()).append(")");
            result.append("\n  Status: ").append(ticket.getState());
            Client client = userCatalog.getClient(ticket.getUserID());
            if (client != null) {
                result.append("\n  Client type: ").append(
                        client.getType() == ClientType.Company ? "Company" : "Individual"
                );
            }
            result.append("\nticket new: ok\n");
            return result.toString();
        } catch (Exception e) {
            return "Error creating ticket: " + e.getMessage() + "\nticket new: fail\n";
        }
    }

    public String displayTicketAdd(String input) {
        try {
            Scanner sc = new Scanner(input);
            String ticketId = sc.next();
            String cashId = sc.next();
            List<String> ticketContent = ticketLogic.handleTicketAdd(input);
            Ticket ticket = ticketCatalog.getTicket(ticketId, cashId);
            StringBuilder result = new StringBuilder();
            result.append("Ticket : ").append(ticketId);
            if (ticket != null && ticket.getState() == TicketState.CLOSED) {
                result.append("-").append(ticket.getTimeClose());
            }
            result.append("\n");
            for (String line : ticketContent) {
                if (!line.trim().isEmpty()) {
                    result.append("  ").append(line).append("\n");
                }
            }
            result.append("ticket add: ok\n");
            return result.toString();
        } catch (Exception e) {
            return "Error adding to ticket: " + e.getMessage() + "\nticket add: fail\n";
        }
    }


    public String displayTicketRemove(String input) {
        try {
            Scanner sc = new Scanner(input);
            String ticketId = sc.next();
            String cashId = sc.next();
            List<String> ticketContent = ticketLogic.handleTicketRemove(input);
            Ticket ticket = ticketCatalog.getTicket(ticketId, cashId);
            StringBuilder result = new StringBuilder();
            result.append("Ticket : ").append(ticketId);
            if (ticket != null && ticket.getState() == TicketState.CLOSED) {
                result.append("-").append(ticket.getTimeClose());
            }
            result.append("\n");
            for (String line : ticketContent) {
                if (!line.trim().isEmpty()) {
                    result.append("  ").append(line).append("\n");
                }
            }
            result.append("ticket remove: ok\n");
            return result.toString();
        } catch (Exception e) {
            return "Error removing from ticket: " + e.getMessage() + "\nticket remove: fail\n";
        }
    }


    public String displayTicketPrint(String input) {
        try {
            Scanner sc = new Scanner(input);
            String ticketId = sc.next();
            String cashId = sc.next();
            List<String> items = ticketLogic.handleTicketPrint(input);
            Ticket ticket = ticketCatalog.getTicket(ticketId, cashId);
            StringBuilder result = new StringBuilder("Ticket : ");
            if (ticket != null) {
                result.append(ticket.getId());
                if (ticket.getState() == TicketState.CLOSED && ticket.getTimeClose() != null) {
                    result.append("-").append(ticket.getTimeClose());
                }
                result.append("\n");
                for (String line : items) {
                    result.append("  ").append(line).append("\n");
                }
            }
            result.append("ticket print: ok\n");
            return result.toString();
        } catch (Exception e) {
            return "Error printing ticket: " + e.getMessage() + "\nticket print: fail\n";
        }
    }


    public String displayTicketList() {
        try {
            List<String> tickets = ticketLogic.handleTicketList();
            StringBuilder result = new StringBuilder("Ticket List:\n");
            for (String ticketInfo : tickets) {
                result.append("  ").append(ticketInfo).append("\n");
            }
            result.append("ticket list: ok\n");
            return result.toString();
        } catch (Exception e) {
            return "Error listing tickets: " + e.getMessage() + "\nticket list: fail\n";
        }
    }
}