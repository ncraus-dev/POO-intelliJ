package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProductLogic {
    private ProductCatalog productCatalog;

    public ProductLogic(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

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

        productCatalog.addProduct(p);
        return p;
    }

    public ProductFood handleProdAddFood(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();

        String name = sc.findInLine("\"([^\"]*)\"");
        if (name == null) throw new IllegalArgumentException("Name must be between quotes.");
        name = name.replace("\"", "");

        double price = sc.nextDouble();
        String dateStr = sc.findInLine("\\d{4}-\\d{2}-\\d{2}");
        int maxPeople = sc.nextInt();
        validateMaxPeople(maxPeople);

        ProductFood p = new ProductFood(id, name, price, dateStr, maxPeople);

        if (p.getExpiration().isBefore(LocalDateTime.now().plusDays(3))) {
            throw new IllegalArgumentException("Food expiration must be at least 3 days in advance");
        }

        productCatalog.addProduct(p);
        return p;
    }

    public ProductMeeting handleProdAddMeeting(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();

        String name = sc.findInLine("\"([^\"]*)\"");
        if (name == null) throw new IllegalArgumentException("Name must be between quotes.");
        name = name.replace("\"", "");

        double price = sc.nextDouble();
        String dateStr = sc.findInLine("\\d{4}-\\d{2}-\\d{2}");
        int maxPeople = sc.nextInt();
        validateMaxPeople(maxPeople);

        ProductMeeting p = new ProductMeeting(id, name, price, dateStr, maxPeople);

        if (p.getExpiration().isBefore(LocalDateTime.now().plusHours(12))) {
            throw new IllegalArgumentException("Meeting must be scheduled at least 12 hours in advance");
        }

        productCatalog.addProduct(p);
        return p;
    }

    public List<String> handleProdList() {
        List<String> printedCatalog = new ArrayList<>();
        for (Product p : productCatalog.getProducts()) {
            printedCatalog.add(p.toString());
        }
        return printedCatalog;
    }

    public Product handleProdUpdate(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();
        String field = sc.next().toLowerCase();
        String value = sc.nextLine().trim();

        Product product = productCatalog.getProductById(id);
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + id);
        }

        switch (field) {
            case "name":
                product.setName(value.replace("\"", ""));
                break;
            case "price":
                double price = Double.parseDouble(value);
                if (price <= 0) throw new IllegalArgumentException("Price must be positive");
                product.setPrice(price);
                break;
            case "category":
                try {
                    Category newCategory = Category.valueOf(value.toUpperCase());
                    if (product instanceof ProductCustom custom) {
                        custom.setCategory(newCategory);
                    } else {
                        throw new IllegalArgumentException("Only customizable products have categories");
                    }
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Invalid category: " + value + ". Valid categories: " +
                            Arrays.toString(Category.values()));
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid field: " + field);
        }

        return product;
    }

    public Product handleProdRemove(String input) {
        Scanner sc = new Scanner(input);
        int id = sc.nextInt();

        Product prod = productCatalog.getProductById(id);
        if (prod == null) {
            throw new IllegalArgumentException("Product not found: " + id);
        }

        productCatalog.removeProduct(String.valueOf(id));
        return prod;
    }

    private void validateMaxPeople(int maxPeople) {
        if (maxPeople < 2 || maxPeople > 100) {
            throw new IllegalArgumentException("The amount of people must be between 2 and 100");
        }
    }
}