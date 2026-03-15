package etsisi.upm.Display;

import etsisi.upm.Logic.*;
import etsisi.upm.Model.*;
import java.util.List;

public class UserDisplay {
    private final UserLogic userLogic;
    private final UserCatalog userCatalog;
    private final TicketCatalog ticketCatalog;


    public UserDisplay(UserCatalog userCatalog, TicketCatalog ticketCatalog, UserLogic userLogic) {
        this.userCatalog = userCatalog;
        this.ticketCatalog = ticketCatalog;
        this.userLogic = userLogic;
    }


    public String displayClientAdd(String input) {
        try {
            Client client = userLogic.handleClientAdd(input);

            return client.toString() + "\nclient add: ok\n";
        } catch (Exception e) {
            return "Error adding client: " + e.getMessage() + "\nclient add: fail\n";
        }
    }

    public String displayClientRemove(String input) {
        try {
            if (userLogic.handleClientRemove(input)) {
                return "client remove: ok\n";
            } else {
                return "Client not found\nclient remove: fail\n";
            }
        } catch (Exception e) {
            return "Error removing client: " + e.getMessage() + "\nclient remove: fail\n";
        }
    }


    public String displayClientList() {
        try {
            List<String> clients = userLogic.handleClientList();
            StringBuilder result = new StringBuilder("Client:\n");
            for (String client : clients) {
                result.append("  ").append(client).append("\n");
            }
            return result.toString() + "client list: ok\n";
        } catch (Exception e) {
            return "Error listing clients: " + e.getMessage() + "\nclient list: fail\n";
        }
    }


    public String displayCashAdd(String input) {
        try {
            Cashier cashier = userLogic.handleCashAdd(input);
            return cashier.toString() + "\ncash add: ok\n";
        } catch (Exception e) {
            return "Error adding cashier: " + e.getMessage() + "\ncash add: fail\n";
        }
    }

    public String displayCashRemove(String input) {
        try {
            if (userLogic.handleCashRemove(input)) {
                return "cash remove: ok\n";
            } else {
                return "Cashier not found\n" + "cash remove: fail\n";
            }
        } catch (Exception e) {
            return "Error removing cashier: " + e.getMessage() + "\ncash remove: fail\n";
        }
    }


    public String displayCashList() {
        try {
            List<String> cashiers = userLogic.handleCashList();
            StringBuilder result = new StringBuilder("Cash:\n");
            for (String cashier : cashiers) {
                result.append("  ").append(cashier).append("\n");
            }
            return result.toString() + "cash list: ok\n";
        } catch (Exception e) {
            return "Error listing cashiers: " + e.getMessage() + "\ncash list: fail\n";
        }
    }

    public String displayCashTickets(String input) {
        try {
            List<String> tickets = userLogic.handleCashTicket(input);
            StringBuilder result = new StringBuilder("Tickets: \n");
            if (tickets.isEmpty()) {
                result.append("  No tickets found for this cashier\n");
            } else {
                for (String ticket : tickets) {
                    result.append("  ").append(ticket).append("\n");
                }
            }
            return result.toString() + "cash tickets: ok\n";
        } catch (Exception e) {
            return "Error listing tickets: " + e.getMessage() + "\ncash tickets: fail\n";
        }
    }
}