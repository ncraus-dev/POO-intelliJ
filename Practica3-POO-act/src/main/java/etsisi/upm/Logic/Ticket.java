package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import javax.xml.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@XmlRootElement(name = "Ticket")
@XmlAccessorType(XmlAccessType.FIELD)
public class Ticket<T extends Sales> {
    @XmlElementWrapper(name = "ListaDeProductos")
    @XmlElement(name = "TicketItem")
    private List<TicketItem<T>> items;

    @XmlTransient // IMPORTANTE: No guardar la estrategia en el XML
    private ITicketPrinter<T> printerStrategy;

    private TicketState ticketState;
    private String cashID;
    private String userID;
    private String id;
    private String TimeClose;
    private TypeTicket type; // Products, Services o Combined

    public Ticket(String id, String cashID, String userID, TypeTicket type, ITicketPrinter<T> printerStrategy) {
        this.items = new ArrayList<>();
        this.ticketState = TicketState.EMPTY; // Nace vacío
        this.cashID = cashID;
        this.id = id;
        this.userID = userID;
        this.type = type;
        this.printerStrategy = printerStrategy;
    }

    public Ticket() {
        this.items = new ArrayList<>();
        this.ticketState = TicketState.EMPTY;
    }

    public boolean addProduct(TicketItem<T> newTicketItem) {

        if (this.ticketState == TicketState.CLOSED) {
            throw new IllegalStateException("Ticket is CLOSED and cannot be modified");
        }


        if (!isValidTime(newTicketItem.getElement())) {
            throw new IllegalArgumentException("Product/Service expiration date has passed");
        }

        boolean found = false;
        String newId = newTicketItem.getElement().getId();
        int i = 0;


        while (i < items.size() && !found) {
            TicketItem<T> current = items.get(i);
            if (current.getElement().getId().equals(newId) &&
                    current.getContPers() == 0 && newTicketItem.getContPers() == 0) {
                current.setQuantity(current.getQuantity() + newTicketItem.getQuantity());
                found = true;
            }
            i++;
        }

        if (!found) items.add(newTicketItem);


        this.ticketState = TicketState.OPEN;
        return true;
    }


    public boolean removeProduct(String idInput) {

        if (this.ticketState == TicketState.CLOSED) {
            throw new IllegalStateException("Ticket is CLOSED and cannot be modified");
        }

        Iterator<TicketItem<T>> iterator = items.iterator();
        while (iterator.hasNext()) {
            TicketItem<T> item = iterator.next();
            if (item.getElement().getId().equals(idInput)) {
                iterator.remove();


                if (items.isEmpty()) {
                    this.ticketState = TicketState.EMPTY;
                }
                return true;
            }
        }
        return false;
    }

    public void setOpen(TicketState state) {
        if (state == TicketState.CLOSED) {

            if (items.isEmpty() || this.ticketState == TicketState.EMPTY) {
                throw new IllegalStateException("Error: Cannot close/print an EMPTY ticket.");
            }

            if (!validateEventsOnClose()) {
                throw new IllegalStateException("Error: Cannot close ticket with expired events.");
            }

            boolean hasProduct = false;
            boolean hasService = false;

            for (TicketItem<T> item : items) {
                if (item.getElement() instanceof Product) hasProduct = true;
                if (item.getElement() instanceof ProductService) hasService = true;
            }


            switch (this.type) {
                case Products: // Usuario Normal (Particular)
                    if (!hasProduct) throw new IllegalStateException("Error: Product Ticket empty.");
                    if (hasService) throw new IllegalStateException("Error: General Client cannot buy Services.");
                    break;
                case Services: // Empresa - Solo Servicios
                    if (!hasService) throw new IllegalStateException("Error: Service Ticket empty.");
                    if (hasProduct) throw new IllegalStateException("Error: Service Ticket cannot contain Products.");
                    break;
                case Combined: // Empresa - Combinado
                    if (!hasProduct || !hasService) {
                        throw new IllegalStateException("Error: Combined Ticket must have at least 1 Product AND 1 Service.");
                    }
                    break;
            }

            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            this.TimeClose = date.toString().replace("T", "-");
        }
        this.ticketState = state;
    }


    private boolean isValidTime(Sales product) {
        LocalDateTime now = LocalDateTime.now();

        if (product instanceof ProductService) {

            return !((ProductService) product).getExpiration().isBefore(LocalDate.now());
        } else if (product instanceof ProductEvent) {
            return ((ProductEvent) product).getExpiration().isAfter(now);
        }
        return true;
    }

    private boolean validateEventsOnClose() {
        LocalDateTime now = LocalDateTime.now();
        for (TicketItem<T> item : items) {
            if (item.getElement() instanceof ProductEvent) {
                LocalDateTime eventDateTime = ((ProductEvent) item.getElement()).getExpiration();
                if (eventDateTime.isBefore(now)) {
                    return false; // Evento ya pasó
                }
            }
        }
        return true;
    }

    public String getPrintableString(Client client) {
        if (printerStrategy != null) {
            return printerStrategy.print(items, client);
        }
        return "Error: No strategy defined for this ticket.";
    }

    public String getId() { return id; }
    public String getCashID() { return cashID; }
    public TicketState getState() { return ticketState; }
    public String getTimeClose() { return TimeClose; }
    public String getUserID() { return userID; }
    public TypeTicket getType() { return type; }
    public List<TicketItem<T>> getItems() { return items; }

    public void setPrinterStrategy(ITicketPrinter<T> strategy) {
        this.printerStrategy = strategy;
    }

    @Override
    public String toString() {
        return String.format("%s %s - %s", cashID, id, ticketState);
    }
}