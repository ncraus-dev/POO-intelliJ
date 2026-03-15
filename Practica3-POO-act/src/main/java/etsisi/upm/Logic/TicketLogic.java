package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;
import java.util.Scanner;

public class TicketLogic {
    private TicketCatalog ticketCatalog;
    private UserCatalog userCatalog;
    private ProductCatalog productCatalog;
    private ServiceCatalog serviceCatalog;

    public TicketLogic(TicketCatalog tc, UserCatalog uc, ProductCatalog pc, ServiceCatalog sc) {
        this.ticketCatalog = tc;
        this.userCatalog = uc;
        this.productCatalog = pc;
        this.serviceCatalog = sc;
    }


    public Ticket handleTicketNew(String input) {
        Scanner sc = new Scanner(input);
        List<String> tokens = new ArrayList<>();
        while (sc.hasNext()) tokens.add(sc.next());

        TypeTicket typeTicket = TypeTicket.Products;
        if (!tokens.isEmpty()) {
            String last = tokens.get(tokens.size() - 1);
            if (last.equals("-c")) { typeTicket = TypeTicket.Combined; tokens.remove(tokens.size() - 1); }
            else if (last.equals("-s")) { typeTicket = TypeTicket.Services; tokens.remove(tokens.size() - 1); }
            else if (last.equals("-p")) { typeTicket = TypeTicket.Products; tokens.remove(tokens.size() - 1); }
        }

        if (tokens.size() < 2 || tokens.size() > 3) throw new IllegalArgumentException("Invalid ticket new command");

        String ticketId = (tokens.size() == 3) ? tokens.get(0) : null;
        String cashId = (tokens.size() == 3) ? tokens.get(1) : tokens.get(0);
        String userId = (tokens.size() == 3) ? tokens.get(2) : tokens.get(1);


        Client usuario = userCatalog.getClient(userId);
        if (usuario == null) throw new IllegalArgumentException("Client not found: " + userId);
        if (userCatalog.getCashier(cashId) == null) throw new IllegalArgumentException("Cashier not found: " + cashId);


        if (usuario.getType() == ClientType.General && typeTicket != TypeTicket.Products){
            throw new IllegalArgumentException("General clients cannot create Service/Combined tickets");
        }
        if (usuario.getType() == ClientType.Company && typeTicket == TypeTicket.Products){
            throw new IllegalArgumentException("Company clients cannot create Products-only ticket (Use Combined)");
        }

        ITicketPrinter<Sales> printerStrategy;

        if (usuario.getType() == ClientType.Company) {
            printerStrategy = new PrinterEmpresa();
        } else {
            printerStrategy = new PrinterParticular();
        }


        if (ticketId == null) {
            return ticketCatalog.ticketNew2(cashId, userId, typeTicket, printerStrategy);
        } else {
            return ticketCatalog.ticketNew1(ticketId, cashId, userId, typeTicket, printerStrategy);
        }
    }

    public List<String> handleTicketAdd(String input) {
        Scanner sc = new Scanner(input);
        String ticketId = sc.next();
        String cashId = sc.next();
        String itemIdStr = sc.next();
        int amount = sc.hasNextInt() ? sc.nextInt() : 1;

        Ticket<Sales> ticket = ticketCatalog.getTicket(ticketId, cashId);
        if (ticket == null) throw new IllegalArgumentException("Ticket not found: " + cashId + " " + ticketId);
        if (ticket.getState() == TicketState.CLOSED) throw new IllegalArgumentException("Ticket is CLOSED");

        try {
            int prodId = Integer.parseInt(itemIdStr);
            Product p = productCatalog.getProductById(prodId);

            if (p != null) {

                if (ticket.getType() == TypeTicket.Services)
                    throw new IllegalArgumentException("Cannot add Product to a Services-only ticket.");

                TicketItem<Sales> item = new TicketItem<>(p, amount);

                if (sc.hasNext()) {
                    String rest = sc.nextLine();
                    if (!rest.trim().isEmpty()) item.settextPersonalizable(rest);
                }

                ticket.addProduct(item);
                return buildResponse(ticket);
            }
        } catch (NumberFormatException e) {

            if (serviceCatalog != null) {
                ProductService s = serviceCatalog.getService(itemIdStr);

                if (s != null) {

                    if (ticket.getType() == TypeTicket.Products)
                        throw new IllegalArgumentException("Cannot add Service to a Products-only ticket.");
                    ticket.addProduct(new TicketItem<>(s, amount));
                    return buildResponse(ticket);
                }
            }
        }

        throw new IllegalArgumentException("Item not found (neither Product nor Service): " + itemIdStr);
    }

    public List<String> handleTicketRemove(String input) {
        Scanner sc = new Scanner(input);
        String ticketId = sc.next();
        String cashId = sc.next();
        String prodIdStr = sc.next();

        Ticket<Sales> ticket = ticketCatalog.getTicket(ticketId, cashId);
        if (ticket == null) throw new IllegalArgumentException("Ticket not found: " + cashId + " " + ticketId);
        if (ticket.getState() == TicketState.CLOSED) throw new IllegalArgumentException("Ticket is CLOSED");

        if (ticket.removeProduct(prodIdStr)) {
            return buildResponse(ticket);
        } else {
            throw new IllegalArgumentException("Error removing product from ticket (ID not found in items)");
        }
    }

    public List<String> handleTicketPrint(String input) {
        Scanner sc = new Scanner(input);
        String ticketId = sc.next();
        String cashId = sc.next();

        Ticket<Sales> t = ticketCatalog.getTicket(ticketId, cashId);
        if (t == null) throw new IllegalArgumentException("Ticket not found: " + cashId + " " + ticketId);
        if (t.getState() == TicketState.CLOSED) {

            return buildResponse(t);
        }
        t.setOpen(TicketState.CLOSED);

        return buildResponse(t);
    }

    public List<String> handleTicketList() {
        List<String> result = new ArrayList<>();
        for (Ticket t : ticketCatalog.getTickets()) {
            result.add(t.toString());
        }
        return result;
    }

    private List<String> buildResponse(Ticket<Sales> t) {
        Client c = userCatalog.getClient(t.getUserID());
        String fullTicket = t.getPrintableString(c);
        return Arrays.asList(fullTicket.split("\n"));
    }
}