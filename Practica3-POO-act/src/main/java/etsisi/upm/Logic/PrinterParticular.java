package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;
import java.util.Locale;

public class PrinterParticular implements ITicketPrinter<Sales> {
    @Override
    public String print(List<TicketItem<Sales>> items, Client client) {
        StringBuilder sb = new StringBuilder();

        Map<Category, Integer> counts = new HashMap<>();
        for (TicketItem<Sales> item : items) {
            if (item.getElement() instanceof ProductCustom pc) {
                counts.put(pc.getCategory(),
                        counts.getOrDefault(pc.getCategory(), 0) + item.getQuantity());
            }
        }

        double total = 0;
        double totalDiscount = 0;

        for (TicketItem<Sales> item : items) {
            Sales s = item.getElement();
            int quantity = item.getQuantity();

            if (item.getContPers() > 0) {

                sb.append(item.toString());

                if (s instanceof ProductCustom pc) {
                    Category cat = pc.getCategory();
                    if (counts.getOrDefault(cat, 0) >= 2) {
                        // Descuento sobre precio CON recargo
                        double priceWithSurcharge = item.getSubtotal() / quantity;
                        double discount = priceWithSurcharge * cat.getDiscount();
                        totalDiscount += discount;
                        sb.append(String.format(Locale.US, " **discount -%.1f", discount));
                    }
                }
                sb.append("\n");
                total += item.getSubtotal();
            }

            else {

                if (s instanceof ProductCustom pc) {
                for (int i = 0; i < quantity; i++) {
                    sb.append(s.toStringTicket());
                        Category cat = pc.getCategory();
                        if (counts.getOrDefault(cat, 0) >= 2) {
                            double discount = pc.getPrice() * cat.getDiscount();
                            totalDiscount += discount;
                            sb.append(String.format(Locale.US, " **discount -%.1f", discount));
                        }
                    sb.append("\n");
                    total += s.getPrice();
                    }


                }
                else{
                    sb.append(item.toString());

                    sb.append("\n");

                    total += (s.getPrice()*quantity);
                }
            }
        }

        sb.append(String.format(Locale.US, "Total price: %.1f\n", total));
        sb.append(String.format(Locale.US, "Total discount: %.1f\n", totalDiscount));
        sb.append(String.format(Locale.US, "Final Price: %.1f", total - totalDiscount));

        return sb.toString();
    }
}
