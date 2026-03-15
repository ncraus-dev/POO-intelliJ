package etsisi.upm;

import etsisi.upm.Display.*;
import etsisi.upm.Logic.*;
import etsisi.upm.Model.*;
import etsisi.upm.XML.*;
import javax.xml.bind.*;
import java.io.File;
import java.util.*;
import java.util.Scanner;

public class CLI {
    private static final String PROD = "prod";
    private static final String CLIENT = "client";
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
    private static final Scanner INPUT = new Scanner(System.in);
    private static final String INITIAL = "Welcome to the ticket module App.\nTicket module. Type 'help' to see commands.";
    private static final String GOODBYE = "Closing application.\nGoodbye!\n";
    private static final String ERROR = "Command does not exist.";

    private ProductCatalog catalog = new ProductCatalog();
    private ServiceCatalog serviceCatalog = new ServiceCatalog();
    private TicketCatalog tickets = new TicketCatalog();
    private UserCatalog userCatalog = new UserCatalog();

    private final TicketLogic ticketLogic;
    private final ProductLogic productLogic;
    private final ServiceLogic serviceLogic;
    private final UserLogic userLogic;

    private final GeneralDisplay generalDisplay;
    private final SalesDisplay salesDisplay;
    private final UserDisplay userDisplay;
    private final TicketDisplay ticketDisplay;

    private File archivo = new File("Catalogos.xml"); // Archivo de persistencia

    public CLI() {

        this.ticketLogic = new TicketLogic(tickets, userCatalog, catalog, serviceCatalog);
        this.productLogic = new ProductLogic(catalog);
        this.serviceLogic = new ServiceLogic(serviceCatalog);
        this.userLogic = new UserLogic(userCatalog, tickets);

        // Inicializar displays con sus dependencias
        this.generalDisplay = new GeneralDisplay();
        this.salesDisplay = new SalesDisplay(catalog, tickets, serviceCatalog, productLogic, serviceLogic);
        this.userDisplay = new UserDisplay(userCatalog, tickets, userLogic);
        this.ticketDisplay = new TicketDisplay(tickets, userCatalog, ticketLogic);
    }

    public void start() {
       ChargeXML();

        System.out.println(INITIAL);
        boolean exit = false;
        String chosen = "";

        while (!exit) {
            System.out.printf("%s ", USER);
            String commandLineInput = INPUT.nextLine().trim();
            String[] processedLine = separate(commandLineInput);

            String command = processedLine[0];
            String args = null;
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
                        chosen = ERROR;
                }
            } catch (Exception e) {
               chosen = "Error processing ->" + e.getMessage() + "\n";
            }
            System.out.println(commandLineInput + "\n" + chosen);
        }
        WriteXML(); // Guarda datos en XML al finalizar
    }
    private void ChargeXML() {
        try {
            JAXBContext context = JAXBContext.newInstance(
                    XmlCatalogs.class,
                    Product.class, ProductEvent.class, ProductCustom.class,
                    Ticket.class, TicketItem.class,
                    User.class, Client.class, Cashier.class,
                    ProductEntry.class, ClientEntry.class, CashierEntry.class, TicketEntry.class,
                    ProductService.class, ServiceEntry.class, ProductMeeting.class,ProductFood.class
            );

            Unmarshaller unmarshaller = context.createUnmarshaller();
            XmlCatalogs xmlCatalogs;
            if (this.archivo.exists()) {
                xmlCatalogs = (XmlCatalogs) unmarshaller.unmarshal(archivo);

                if (xmlCatalogs.getProductos() != null) {
                    Map<String, Product> productMap = new TreeMap<>();
                    for (ProductEntry e : xmlCatalogs.getProductos()) {
                        productMap.put(e.getKey(), e.getValue());
                    }
                    catalog.setProductsMap(productMap);
                }

                if (xmlCatalogs.getClientes() != null) {
                    Map<String, Client> clientMap = new HashMap<>();
                    for (ClientEntry e : xmlCatalogs.getClientes()) {
                        clientMap.put(e.getKey(), e.getValue());
                    }
                    userCatalog.setClientsMap(clientMap);
                }

                if (xmlCatalogs.getCajeros() != null) {
                    Map<String, Cashier> cashierMap = new HashMap<>();
                    for (CashierEntry e : xmlCatalogs.getCajeros()) {
                        cashierMap.put(e.getKey(), e.getValue());
                    }
                    userCatalog.setCashiersMap(cashierMap);
                }

                if (xmlCatalogs.getTickets() != null) {
                    Map<String, Ticket> ticketMap = new TreeMap<>();
                    for (TicketEntry e : xmlCatalogs.getTickets()) {
                        Ticket t = e.getValue();

                        if (t.getType() == TypeTicket.Products) {
                            t.setPrinterStrategy(new PrinterParticular());
                        } else {
                            t.setPrinterStrategy(new PrinterEmpresa());
                        }

                        ticketMap.put(e.getKey(), t);
                    }
                    tickets.setTicketsMap(ticketMap);
                }
                if (xmlCatalogs.getService() != null) {
                    Map<String, ProductService> serviceMap = new TreeMap<>();
                    for (ServiceEntry e : xmlCatalogs.getService()) {
                        serviceMap.put(e.getKey(), e.getValue());
                    }
                    serviceCatalog.setProductsMap(serviceMap);
                    serviceLogic.setCount(serviceCatalog.last() + 1);
                }
            } else {
                xmlCatalogs = new XmlCatalogs();
            }
        } catch (Exception e) {
            System.out.println("Error cargando datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void WriteXML() {
        try {
            XmlCatalogs xmlCatalogs = new XmlCatalogs();
            List<ProductEntry> productList = new ArrayList<>();
            for (Product p : catalog.getProducts()) {
                productList.add(new ProductEntry(p.getId(), p));
            }
            xmlCatalogs.setProductos(productList);

            List<ClientEntry> clientList = new ArrayList<>();
            for (Client c : userCatalog.listClients()) {
                clientList.add(new ClientEntry(c.getDni(), c));
            }
            xmlCatalogs.setClientes(clientList);

            List<CashierEntry> cashierList = new ArrayList<>();
            for (Cashier c : userCatalog.listCashiers()) {
                cashierList.add(new CashierEntry(c.getId(), c));
            }
            xmlCatalogs.setCajeros(cashierList);

            List<TicketEntry> ticketList = new ArrayList<>();
            for (Ticket t : tickets.getTickets()) {
                String key = t.getCashID() + " " + t.getId(); // Clave compuesta para tickets
                ticketList.add(new TicketEntry(key, t));
            }
            xmlCatalogs.setTickets(ticketList);

            List<ServiceEntry> serviceList = new ArrayList<>();
            for (ProductService p : serviceCatalog.getProducts()) {
                serviceList.add(new ServiceEntry(p.getId(), p));
            }
            xmlCatalogs.setService(serviceList);
            JAXBContext context = JAXBContext.newInstance(
                    XmlCatalogs.class,
                    Product.class, ProductEvent.class, ProductCustom.class,
                    Ticket.class, TicketItem.class,
                    User.class, Client.class, Cashier.class,
                    ProductEntry.class, ClientEntry.class, CashierEntry.class, TicketEntry.class,
                    ProductService.class, ServiceEntry.class, ProductMeeting.class,ProductFood.class
            );

            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(xmlCatalogs, archivo);
        } catch (Exception e) {
            System.out.println("Error guardando datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String startProd(String args, String atributes) {
        String chosen = "";
        switch (args) {
            case ADD:
                if (!empiezaConFecha(atributes))
                    chosen = salesDisplay.displayAddProductCustom(atributes);
                else
                    chosen = salesDisplay.displayAddService(atributes); // ¡NUEVO! Añadir servicio
                break;
            case ADDFOOD:
                chosen = salesDisplay.displayAddProductFood(atributes);
                break;
            case ADDMEETING:
                chosen = salesDisplay.displayAddProductMeeting(atributes);
                break;
            case LIST:
                chosen = salesDisplay.displayProductList();
                break;
            case PROD_UPDATE:
                chosen = salesDisplay.displayProductUpdate(atributes);
                break;
            case REMOVE:
                chosen = salesDisplay.displayProductRemove(atributes);
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
    private String startTicket(String args, String atributes) {
        String chosen = "";
        switch (args) {
            case TICKET_NEW:
                chosen = ticketDisplay.displayTicketNew(atributes);
                break;
            case ADD:
                chosen = ticketDisplay.displayTicketAdd(atributes);
                break;
            case REMOVE:
                chosen = ticketDisplay.displayTicketRemove(atributes);
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
    private String startCashier(String args, String atributes) {
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
    private String startClient(String args, String atributes) {
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
        return input.trim().split("\\s+", 3);
    }
    public static boolean empiezaConFecha(String texto) {
        return texto != null && texto.matches("^\\d{4}-\\d{2}-\\d{2}.*");
    }
}