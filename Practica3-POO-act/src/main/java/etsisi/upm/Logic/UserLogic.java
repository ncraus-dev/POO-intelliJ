package etsisi.upm.Logic;

import etsisi.upm.Model.Cashier;
import etsisi.upm.Model.Client;
import etsisi.upm.Model.ClientType;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserLogic {
    private final UserCatalog userCatalog;
    private final TicketCatalog ticketCatalog;

    public UserLogic(UserCatalog userCatalog, TicketCatalog ticketCatalog) {
        this.userCatalog = userCatalog;
        this.ticketCatalog = ticketCatalog;
    }

    public Client handleClientAdd(String input) {
        Scanner sc = new Scanner(input);
        String name = sc.findInLine("\"([^\"]*)\"");
        if (name == null) throw new IllegalArgumentException("Name must be between quotes.");
        name = name.replace("\"", "");

        String identifier = sc.next();
        String email = sc.next();
        String cashId = sc.next();

        Cashier cashier = userCatalog.getCashier(cashId);
        if (cashier == null) throw new IllegalArgumentException("Cashier not found.");

        Client client;
        if (isNifNumValid(identifier)) {
            client = new Client(name, identifier, email, cashier, ClientType.Company);
        } else if(isDniNumValid(identifier)) {
            client = new Client(name, identifier, email, cashier, ClientType.General);
        }
        else{
            throw new IllegalArgumentException("Client could not be added");
        }
        if (userCatalog.addClient(client))
            return client;
        else throw new IllegalArgumentException("Client could not be added");
    }

    public boolean handleClientRemove(String input) {
        String DNI = input.trim();
        if (userCatalog.removeClient(DNI)) {
            return true;
        } else throw new RuntimeException("No client removed");
    }

    public List<String> handleClientList() {
        List<String> clientslist = new ArrayList<>();
        for (Client c : userCatalog.listClients()) {
            clientslist.add(c.toString());
        }
        return clientslist;
    }

    public Cashier handleCashAdd(String input) {
        Scanner sc = new Scanner(input);
        String id;
        StringBuilder name;
        String email;

        String firstToken = sc.next();

        if (firstToken.startsWith("\"")) {
            id = generateCashierId();
            name = new StringBuilder(firstToken);

            while (!name.toString().endsWith("\"") && sc.hasNext()) {
                name.append(" ").append(sc.next());
            }
            name = new StringBuilder(name.toString().replace("\"", ""));

            if (!sc.hasNext()) throw new IllegalArgumentException("Email is missing.");
            email = sc.next();

        } else {
            if (!firstToken.matches("UW\\d{7}")) {
                throw new IllegalArgumentException("Invalid Cashier ID format. Must be 'UW' followed by 7 digits.");
            }
            id = firstToken;

            if (!sc.hasNext()) throw new IllegalArgumentException("Name is missing.");
            String nameToken = sc.next();
            if (!nameToken.startsWith("\"")) {
                throw new IllegalArgumentException("Name must be in quotes.");
            }
            name = new StringBuilder(nameToken);
            while (!name.toString().endsWith("\"") && sc.hasNext()) {
                name.append(" ").append(sc.next());
            }
            name = new StringBuilder(name.toString().replace("\"", ""));

            if (!sc.hasNext()) throw new IllegalArgumentException("Email is missing.");
            email = sc.next();
        }

        if (userCatalog.getCashier(id) != null) {
            throw new IllegalArgumentException("Cashier ID already exists.");
        }

        Cashier cashier = new Cashier(id, name.toString(), email);
        if(userCatalog.addCashier(cashier))
            return cashier;
        else throw new IllegalArgumentException("Cashier could not be added");
    }

    public boolean handleCashRemove(String input) {
        String id = input.trim();
        if (userCatalog.removeCashier(id)) {

            List<Ticket> ticketsToRemove = new ArrayList<>();
            for(Ticket ticket : ticketCatalog.getTickets()) {
                if(ticket.getCashID().equals(id)) {
                    ticketsToRemove.add(ticket);
                }
            }


            for(Ticket ticket : ticketsToRemove) {
                ticketCatalog.removeTicket(ticket.getId(),ticket.getCashID());
            }
            return true;
        } else throw new RuntimeException("No cashier removed");
    }

    public List<String> handleCashList() {
        List<String> cashiersList = new ArrayList<>();
        for (Cashier c : userCatalog.listCashiers()) {
            cashiersList.add(c.toString());
        }
        return cashiersList;
    }

    private String generateCashierId() {
        java.util.Random random = new java.util.Random();
        String id;
        do {
            id = "UW" + String.format("%07d", random.nextInt(10000000));
        } while (userCatalog.getCashier(id) != null);
        return id;
    }

    public List<String> handleCashTicket(String input){
        String id = input.trim();
        Cashier cashier = userCatalog.getCashier(id);
        if (cashier == null) throw new IllegalArgumentException("Cashier not found.");

        List<String> result = new ArrayList<>();
        for(Ticket ticket: ticketCatalog.getTickets()){
            if(ticket.getCashID().equals(id))  result.add(ticket.toString());
        }
        return result;
    }

    private static boolean isNifNumValid(String nif) {
        if (nif == null || nif.length() != 9) return false;
        char firstChar = nif.charAt(0);
        if (!Character.isLetter(firstChar)) return false; // Primera letra obligatoria para NIF empresa
        String digits = nif.substring(1);
        return digits.matches("\\d{8}"); // 8 dígitos después de la letra
    }

    private static boolean isDniNumValid(String dni) {
        if (dni == null || dni.length() != 9) return false;
        String secuenciaLetras = "TRWAGMYFPDXBNJZSQVHLCKE";
        String numero = dni.substring(0, 8);
        char letraControl = Character.toUpperCase(dni.charAt(8));
        numero = numero.replace("X", "0")
                .replace("Y", "1")
                .replace("Z", "2");
        if (!numero.matches("\\d+")) return false;
        int i = Integer.parseInt(numero) % 23;
        return letraControl == secuenciaLetras.charAt(i);
    }
}