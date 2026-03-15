package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServiceLogic {
    private ServiceCatalog serviceCatalog;
    private int count;

    public ServiceLogic(ServiceCatalog serviceCatalog) {
        this.serviceCatalog = serviceCatalog;

        this.count = serviceCatalog.last() + 1;
    }

    public ProductService handleProdAddService(String input) {
        Scanner sc = new Scanner(input);

        if (!sc.hasNext()) {
            throw new IllegalArgumentException("Missing date for service");
        }

        String date = sc.findInLine("\\d{4}-\\d{2}-\\d{2}");
        if (date == null) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }

        if (!sc.hasNext()) {
            throw new IllegalArgumentException("Missing service type");
        }
        String category = sc.nextLine().trim();

        LocalDate datePart;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            datePart = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }

        if (datePart.isBefore(LocalDate.now().plusDays(1))) {
            throw new IllegalArgumentException("Service expiration must be at least 1 day in advance");
        }

        String id = String.valueOf(count) + 'S';
        count++;

        ProductService productService = new ProductService(id, datePart, category);
        serviceCatalog.addService(productService);
        return productService;
    }


    public List<String> handleProdList() {
        List<String> printedCatalog = new ArrayList<>();
        for (ProductService p : serviceCatalog.getProducts()) {
            printedCatalog.add(p.toString());
        }
        return printedCatalog;
    }

    public ProductService handleProdRemove(String input) {
        Scanner sc = new Scanner(input);
        String id = sc.next();
        ProductService prod = serviceCatalog.getService(id);
        if (prod == null) {
            throw new IllegalArgumentException("Service not found with id " + id);
        }

        boolean success = serviceCatalog.removeService(id);
        if (success) {
            return prod;
        } else {
            throw new IllegalArgumentException("The service could not be deleted " + id);
        }
    }

    public void setCount(int count) {
        this.count = count;
    }
}