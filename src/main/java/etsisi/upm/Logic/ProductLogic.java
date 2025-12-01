package etsisi.upm.Logic;

import etsisi.upm.Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductLogic {
    private ProductCatalog productCatalog;
    private TicketCatalog ticketCatalog;

    public ProductLogic(ProductCatalog productCatalog, TicketCatalog ticketCatalog) {
        this.productCatalog = productCatalog;
        this.ticketCatalog = ticketCatalog;
    }

    // ================= PRODUCTOS =================

    public ProductCustom handleProdAddCustom(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();
        String name = sc.findInLine("\"([^\"]*)\"");
        if (name == null) {
            throw new IllegalArgumentException("Name must be between quotes.");
        }
        String categoryString = sc.next();
        Category category = Category.valueOf(categoryString.toUpperCase());
        double price = sc.nextDouble();
        name = name.replace("\"", "");
        ProductCustom p;
        if (sc.hasNextInt()) {
            int maxPers = sc.nextInt();
            p = new ProductCustom(id, name, category, price, maxPers);

        } else {
            p = new ProductCustom(id, name, category, price);
        }
        if (handleProdAdd(p))
            return p;
        else
            throw new IllegalArgumentException("Error processing ->prod addCustom ->Error adding product");
    }

    public ProductFood handleProdAddFood(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();
        String name = sc.findInLine("\"([^\"]*)\"");
        if (name == null) {
            throw new IllegalArgumentException("Name must be between quotes.");
        }
        name = name.replace("\"", "");
        double price = sc.nextDouble();
        String Date = sc.findInLine("\\d{4}-\\d{2}-\\d{2}");
        int maxPeople = sc.nextInt();
        ProductFood p = new ProductFood(id, name, price, Date, maxPeople);
        if (handleProdAdd(p))
            return p;
        else
            throw new IllegalArgumentException("Error processing ->prod addFood ->Error adding product");
    }

    public ProductMeeting handleProdAddMeeting(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();
        String name = sc.findInLine("\"([^\"]*)\"");
        if (name == null) {
            throw new IllegalArgumentException("Name must be between quotes.");
        }
        name = name.replace("\"", "");
        double price = sc.nextDouble();
        String Date = sc.findInLine("\\d{4}-\\d{2}-\\d{2}");
        int maxPeople = sc.nextInt();
        ProductMeeting p = new ProductMeeting(id, name, price, Date, maxPeople);
        if (handleProdAdd(p))
            return p;
        else
            throw new IllegalArgumentException("Error processing ->prod addFood ->Error adding product");
    }


    public boolean handleProdAdd(Product product) {
        if (productCatalog.addProduct(product))
            return true;
        else
            return false;
    }


    public List<String> handleProdList() {
        List<String> printedCatalog = new ArrayList<>();
        for (Product p : productCatalog.getProducts()) {
            printedCatalog.add(p.toString());
        }
        return printedCatalog;
    }

    public ProductCustom handleProdUpdate(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();
        String field = sc.next().toLowerCase();
        String value = sc.nextLine().trim();
        ProductCustom product = (ProductCustom) productCatalog.getProduct(id);
        boolean updated;
        if (product != null) {
            switch (field) {
                case "name":
                    value = value.replace("\"", "");
                    updated = productCatalog.updateProduct(product.getId(), value, product.getPrice(), product.getCategory());

                    break;
                case "price":
                    updated = productCatalog.updateProduct(product.getId(), product.getName(), Double.parseDouble(value), product.getCategory());

                    break;
                case "category":
                    updated = productCatalog.updateProduct(product.getId(), product.getName(), product.getPrice(), Category.valueOf(value.toUpperCase()));

                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
            if (updated)
                return product;
            else
                throw new IllegalArgumentException("Product could not be updated.");
        } else
            throw new IllegalArgumentException("This product type does not have a category.");
    }

    public Product handleProdRemove(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();
        Product prod = productCatalog.getProduct(id);
        boolean success = productCatalog.removeProduct(id);
        if (success) {
            return prod;
        } else {
            throw new IllegalArgumentException("The product could not be deleted " + id);
        }
    }
}
