package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;
import java.util.Locale;

public class PrinterEmpresa implements ITicketPrinter<Sales> {

 public String print(List<TicketItem<Sales>> items, Client client) {
        StringBuilder sb = new StringBuilder();

        double totalProductsBase = 0;
        int serviceCount = 0;
        List<TicketItem<Sales>> productItems = new ArrayList<>();
        List<TicketItem<Sales>> serviceItems = new ArrayList<>();

        for (TicketItem<Sales> item : items) {
            if (item.getElement() instanceof ProductService) {
                serviceItems.add(item);
                serviceCount += item.getQuantity();
            } else {
                productItems.add(item);
                totalProductsBase += item.getSubtotal();
            }
        }


        if (productItems.isEmpty() && !serviceItems.isEmpty()) {
            sb.append("Services Included: \n");
            for (TicketItem<Sales> item : serviceItems) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    sb.append("  ").append(item.getElement().toStringTicket()).append("\n");
                }
            }
            return sb.toString().trim();
        }


        if (!serviceItems.isEmpty()) {
            sb.append("Services Included: \n");
            for (TicketItem<Sales> item : serviceItems) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    sb.append("  ").append(item.getElement().toStringTicket()).append("\n");
                }
            }
        }


        sb.append("Products Included: \n");

        for (TicketItem<Sales> item : productItems) {
            if(item.getElement() instanceof ProductEvent){
                sb.append("  ").append(item.toString()).append("\n");

            }
            else{
            for (int i = 0; i < item.getQuantity(); i++) {
                sb.append("  ").append(item.getElement().toStringTicket()).append("\n");
            }
        }}


        double currentTotal = totalProductsBase;
        float totalDes= (float) (15 * serviceCount) /100;
        if(serviceCount > 6) {
            currentTotal = currentTotal * (0.1);
        }else {
            currentTotal = currentTotal * (1 - totalDes);
        }


     double totalDiscount = totalProductsBase - currentTotal;

        sb.append(String.format(Locale.US, "  Total price: %.1f\n", totalProductsBase));
        sb.append(String.format(Locale.US,
                "  Extra Discount from services:%.1f **discount -%.1f\n",
                totalDiscount, totalDiscount));
        sb.append(String.format(Locale.US, "  Total discount: %.1f\n", totalDiscount));
        sb.append(String.format(Locale.US, "  Final Price: %.1f", currentTotal));

        return sb.toString();
    }

}