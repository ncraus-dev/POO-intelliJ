package etsisi.upm.Logic;

import etsisi.upm.Model.Category;
import etsisi.upm.Model.ProductCustom;
import etsisi.upm.Model.TicketItem;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Ticket {
    private List<TicketItem> items;
    private int open; // 0 empty, 1 open, 2 closed
    private String cashID, userID;
    private String id;
    private String TimeClose;

    public Ticket(String id, String cashID, String userID) {
        this.items = new LinkedList<>();
        this.open = 0;
        this.cashID = cashID;
        this.id = id;
        this.userID = userID;
    }

    public String getId() {
        return id;
    }

    public String getCashID() {
        return cashID;
    }


    // En Ticket.java

    public boolean addProduct(TicketItem newTicketItem) {
        boolean found = false;
        String newProductName = newTicketItem.getProduct().getName();
        if(newProductName== null)
            throw new IllegalArgumentException("Product is null");
        // Usamos Iterator o indice manual para controlar el bucle con un booleano
        int i = 0;
        while (i < items.size() && !found) {//lo cambio a while mejor
            TicketItem currentItem = items.get(i);
            // 1. Tienen el mismo nombre (o ID)
            // 2. El item NUEVO no tiene personalizaciones (es estándar)
            // 3. El item YA EXISTENTE no tiene personalizaciones (es estándar)
            if (currentItem.getProduct().getName().equals(newProductName) &&
                    newTicketItem.getContPers() == 0 &&
                    currentItem.getContPers() == 0) {
                // Si cumple lo anterior
                currentItem.setQuantity(currentItem.getQuantity() + newTicketItem.getQuantity());
                found = true;
            }

            i++;
        }
        if (!found) {
            items.add(newTicketItem);
            sortItemsByName();
        }

        this.open = 1;
        return true;
    }

    public boolean removeProduct(int id) {
        // He cambiado el parámetro a String para ser consistente con addProduct (que usa nombre).
        // Si tu producto usa ID numérico, cambia esto a int y compara con getId().
        Iterator<TicketItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            TicketItem item = iterator.next();
            if (item.getProduct().getId() == (id)) {
                iterator.remove();
                // Si la lista queda vacía, actualizamos el estado
                if (items.isEmpty()) {
                    open = 0;
                }
                return true;
            }
        }
        return false;
    }

    public void sortItemsByName() {
        int n = items.size();
        // Bucle anidado para comparar pares adyacentes
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {

                TicketItem item1 = items.get(j);
                TicketItem item2 = items.get(j + 1);

                String name1 = item1.getProduct().getName();
                String name2 = item2.getProduct().getName();
                if (name1.compareToIgnoreCase(name2) > 0) {
                    items.set(j, item2);
                    items.set(j + 1, item1);
                }
            }
        }
    }
    public Map<Category, Integer> getCategoryCounts() {// cuenta productos diferentes de cada categoria
        Map<Category, Integer> counts = new HashMap<>();//recuerda en el enunciado practica 1 especificaban si mas de 1 productos de la misma categoria aplico descuento
        for (TicketItem item : items) {
            if (item.getProduct() instanceof ProductCustom customProd) {
                Category cat = customProd.getCategory();
                // Sumamos la cantidad de este ítem al total de esa categoría
                counts.put(cat, counts.getOrDefault(cat, 0) + item.getQuantity());
            }
        }
        return counts;
    }
    public double calculateDiscount() {
        double totalDiscount = 0.0;
        //Obtenemos el mapa con los conteos totales
        Map<Category, Integer> counts = getCategoryCounts();

        for (TicketItem item : items) {
            if (item.getProduct() instanceof ProductCustom customProd) {
                //sumamos el descuento si la categoría tiene 2 o más unidades en total
                if (counts.getOrDefault(customProd.getCategory(), 0) >= 2) {
                    totalDiscount += item.getDiscount() * item.getQuantity();
                }
            }
        }
        return totalDiscount;
    }

    public double calculateTotal() {
        double totalPrice = 0.0;
        for (TicketItem item : items) {
            // Usamos getSubtotal() que ya devuelve (Precio + Recargo) * Cantidad
            totalPrice += item.getSubtotal();
        }
        return totalPrice;
    }

    public double calculateFinal() {
        return calculateTotal() - calculateDiscount();
    }

    public String getState() {
        String[] state = {"EMPTY", "OPEN", "CLOSED"};
        if (open < 0 || open >= state.length) return "UNKNOWN";
        return state[open];
    }

    public void setOpen(int open) {
        this.open = open;
        if(open==2){
            LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            this.TimeClose = date.toString();
            this.TimeClose=this.TimeClose.replace("T","-");
        }
    }


    @Override
    public String toString() {
        return this.getId() + " - " + this.getState();
    }

    public List<TicketItem> getItems() {
        return items;
    }

    public String getTimeClose() {
        return TimeClose;
    }
}