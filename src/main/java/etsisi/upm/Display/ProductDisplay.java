package etsisi.upm.Display;

import etsisi.upm.Logic.ProductCatalog;
import etsisi.upm.Logic.ProductLogic;
import etsisi.upm.Logic.TicketCatalog;
import etsisi.upm.Model.ProductCustom;
import etsisi.upm.Model.ProductFood;
import etsisi.upm.Model.ProductMeeting;

import java.util.List;

public class ProductDisplay {
    ProductCatalog productCatalog;
    TicketCatalog ticketCatalog;
    ProductLogic productLogic;


    public ProductDisplay(ProductCatalog products, TicketCatalog tickets) {
        this.productCatalog = products;
        this.ticketCatalog = tickets;
        this.productLogic = new ProductLogic(productCatalog, ticketCatalog);
    }

    public String displayAddProductCustom(String input) {
        ProductCustom prod = productLogic.handleProdAddCustom(input);
        return prod.toString() + "\nprod add: ok\n";
    }

    public String displayAddProductFood(String input) {
        ProductFood prod = productLogic.handleProdAddFood(input);
        return prod.toString() + "\nprod addFood: ok\n";
    }

    public String displayAddProductMeeting(String input) {
        ProductMeeting prod = productLogic.handleProdAddMeeting(input);
        return prod.toString() + "\nprod addMeeting: ok\n";
    }

    public String displayProdRemove(String input) {
        String product = "";
        if (productLogic.handleProdRemove(input) instanceof ProductCustom productCustom)
            product = productCustom.toString();

        else if (productLogic.handleProdRemove(input) instanceof ProductFood productFood)
            product = productFood.toString();

        else if (productLogic.handleProdRemove(input) instanceof ProductMeeting productMeeting)
            product = productMeeting.toString();
        return product + "\nprod remove: ok\n";

    }

    public String displayProdUpdate(String input) {
        ProductCustom productCustom = productLogic.handleProdUpdate(input);
        return productCustom.toString() + "\nprod update: ok\n";
    }

    public String displayProductList() {
        List<String> products = productLogic.handleProdList();
        StringBuilder result = new StringBuilder("Catalog:\n");
        for (String p : products) {
            result.append("\s" + p).append("\n");
        }
        return result.toString().trim() + "\nprod list: ok\n";
    }
}
