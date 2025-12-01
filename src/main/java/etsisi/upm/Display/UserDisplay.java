package etsisi.upm.Display;

import etsisi.upm.Logic.TicketCatalog;
import etsisi.upm.Logic.UserCatalog;
import etsisi.upm.Logic.UserLogic;
import etsisi.upm.Model.Cashier;
import etsisi.upm.Model.Client;

import java.util.List;

public class UserDisplay {
    UserLogic userLogic;
    UserCatalog users;
    TicketCatalog tickets;

    public UserDisplay(UserCatalog list, TicketCatalog tickets) {
        this.users = list;
        this.tickets = tickets;
        this.userLogic = new UserLogic(users,tickets);
    }

    public String displayClientAdd(String input){
       Client client = userLogic.handleClientAdd(input);
            return client.toString() + "client add: ok\n";

    }

    public String displayClientRemove(String input){
        if(userLogic.handleClientRemove(input))
            return "client remove: ok\n";
        else return "";
    }

    public String displayClientList(){
        List<String> clients = userLogic.handleClientList();
        StringBuilder result = new StringBuilder("Clients:\n");
        for (String c : clients) {
            result.append(c);
        }
        return result.toString().trim() + "\nclient list: ok\n";
    }

    public String displayCashAdd(String input){
        Cashier cashier =  userLogic.handleCashAdd(input);

            return cashier.toString() + "cash add: ok\n";

    }

    public String displayCashRemove(String input){
        if(userLogic.handleCashRemove(input))
            return "cash remove: ok\n";
        else return "";
    }

    public String displayCashList(){
        List<String> cashiers = userLogic.handleCashList();
        StringBuilder result = new StringBuilder("Cashiers:\n");
        for (String c : cashiers) {
            result.append(c);
        }
        return result.toString().trim() + "\ncash list: ok\n";
    }

    public String displayCashTickets(String input){
        List<String> tickets_1 = userLogic.handleCashTicket(input);
        StringBuilder result = new StringBuilder("Tickets:\n");
        for(String s: tickets_1){
            result.append(s).append("\n");
        }
        return result.toString().trim() + "\ncash tickets: ok\n";
    }

}

