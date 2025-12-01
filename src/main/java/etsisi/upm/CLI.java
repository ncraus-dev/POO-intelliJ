package etsisi.upm;

import etsisi.upm.Display.GeneralDisplay;
import etsisi.upm.Display.ProductDisplay;
import etsisi.upm.Display.TicketDisplay;
import etsisi.upm.Display.UserDisplay;
import etsisi.upm.Logic.*;

import java.util.Scanner;

public class CLI {

    private static final String PROD = "prod";
    private final static String CLIENT = "client";
    private static final String CASHIER = "cash";
    private static final String TICKET = "ticket";
    private static final String ADD = "add";
    private static final String ADDFOOD = "addFood";
    private static final String ADDMEETING = "addMeeting";
    private static final String REMOVE = "remove";
    private static final String LIST = "list";
    private static final String PROD_UPDATE = "update";
    private static final String TICKET_NEW = "new";
    private static final String TICKET_PRINT = "print";
    private static final String TICKETS = "tickets";
    private static final String HELP = "help";
    private static final String ECHO = "echo";
    private static final String EXIT = "exit";
    private static final String USER = "tUpm>";
    private final static Scanner INPUT = new Scanner(System.in);
    private final static String INITIAL = "Welcome to the ticket module App.\nTicket module. Type 'help' to see commands.";
    private final static String GOODBYE = "Closing application.\nGoodbye!\n";
    private final static String ERROR = "Command does not exist.";


    private ProductCatalog catalog = new ProductCatalog();
    private TicketCatalog tickets = new TicketCatalog(catalog);
    private UserCatalog userCatalog = new UserCatalog();
    private final GeneralDisplay generalDisplay = new GeneralDisplay();
    private final ProductDisplay productDisplay = new ProductDisplay(catalog, tickets);
    private final UserDisplay userDisplay = new UserDisplay(userCatalog, tickets);
    private final TicketDisplay ticketDisplay = new TicketDisplay(tickets,userCatalog);

    public void start() {

        System.out.println(INITIAL);
        boolean exit = false;
        String chosen = "";

        while (!exit) {
            System.out.printf("%s ", USER);
            String commandLineInput = INPUT.nextLine().trim();
            String[] processedLine = separate(commandLineInput);

            String command = processedLine[0];
            String args = null;//puede estar vacio como en el help
            String atributes = null;
            if (processedLine.length >= 2) {
                args = processedLine[1].trim();
                if (processedLine.length == 3) {
                    atributes = processedLine[2].trim();
                }
            }

            try {
                switch (command) {
                    case PROD:
                        chosen = startProd(args, atributes);
                        break;
                    case TICKET:
                        chosen = startTicket(args, atributes);
                        break;
                    case CASHIER:
                        chosen = startCashier(args, atributes);
                        break;
                    case CLIENT:
                        chosen = startClient(args, atributes);
                        break;

                    case HELP:
                        chosen = generalDisplay.displayHelp();
                        break;

                    case ECHO:
                        chosen = generalDisplay.displayEcho(commandLineInput);
                        break;

                    case EXIT:
                        exit = true;
                        chosen = GOODBYE;
                        break;
                    default:
                        // ya tienes validación previa, pero por seguridad:
                        chosen = ERROR;
                }
            } catch (Exception e) {
                chosen = "Error processing ->" + e.getMessage() + "\n";
            }
            System.out.println(commandLineInput +"\n" + chosen);
        }
    }

    String startProd(String args, String atributes) {
        String chosen = "";
        switch (args) {
            case ADD:
                chosen = productDisplay.displayAddProductCustom(atributes);
                break;
            case ADDFOOD:
                chosen = productDisplay.displayAddProductFood(atributes);
                break;
            case ADDMEETING:
                chosen = productDisplay.displayAddProductMeeting(atributes);
                break;

            case LIST:
                chosen = productDisplay.displayProductList();
                break;
            case PROD_UPDATE:
                chosen = productDisplay.displayProdUpdate(atributes);
                break;
            case REMOVE:
                chosen = productDisplay.displayProdRemove(atributes);
                break;
            case null:
                chosen = ERROR;
                break;
            default:
                chosen = ERROR;
                break;

        }

        return chosen;
    }

    String startTicket(String args, String atributes) {
        String chosen = "";
        switch (args) {
            case TICKET_NEW:
                chosen = ticketDisplay.displayTicketNew(atributes);
                break;
            case ADD:
               chosen =  ticketDisplay.displayTicketAdd(atributes);
                break;
            case REMOVE:
               chosen =  ticketDisplay.displayTicketRemove(atributes);
                break;
            case TICKET_PRINT:
                chosen = ticketDisplay.displayTicketPrint(atributes);
                break;
            case LIST:
                chosen = ticketDisplay.displayTicketList();
                break;
            case null:
                chosen = ERROR;
                break;
            default:
                chosen = ERROR;
                break;
        }
        return chosen;
    }

    String startCashier(String args, String atributes) {
        String chosen = "";
        switch (args) {
            case ADD:
                chosen = userDisplay.displayCashAdd(atributes);
                break;
            case REMOVE:
                chosen = userDisplay.displayCashRemove(atributes);
                break;
            case LIST:
                chosen = userDisplay.displayCashList();
                break;
            case TICKETS:
                chosen = userDisplay.displayCashTickets(atributes);
                break;
            case null:
                chosen = ERROR;
                break;
            default:
                chosen = ERROR;
                break;
        }
        return chosen;
    }

    String startClient(String args, String atributes) {
        String chosen = "";
        switch (args) {
            case ADD:
                chosen = userDisplay.displayClientAdd(atributes);
                break;
            case REMOVE:
                chosen = userDisplay.displayClientRemove(atributes);
                break;
            case LIST:
                chosen = userDisplay.displayClientList();
                break;
            case null:
                chosen = ERROR;
                break;
            default:
                chosen = ERROR;
                break;
        }
        return chosen;
    }

    private String[] separate(String input) {
        return input.trim().split("\\s+", 3); // el \\s significa mas de un espacio
    }
}
