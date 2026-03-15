package etsisi.upm.Display;

import etsisi.upm.Logic.*;
import etsisi.upm.Model.*;
import java.util.List;
import java.util.Scanner;

public class SalesDisplay {
    private final ProductCatalog productCatalog;
    private final TicketCatalog ticketCatalog;
    private final ProductLogic productLogic;
    private final ServiceCatalog serviceCatalog;
    private final ServiceLogic serviceLogic;


    public SalesDisplay(ProductCatalog productCatalog, TicketCatalog ticketCatalog,
                        ServiceCatalog serviceCatalog, ProductLogic productLogic,
                        ServiceLogic serviceLogic) {
        this.productCatalog = productCatalog;
        this.ticketCatalog = ticketCatalog;
        this.serviceCatalog = serviceCatalog;
        this.productLogic = productLogic;
        this.serviceLogic = serviceLogic;
    }


    public String displayAddProductCustom(String input) {
        try {
            ProductCustom prod = productLogic.handleProdAddCustom(input);
            return prod.toString() + "\nprod add: ok\n";
        } catch (Exception e) {
            return "Error adding product: " + e.getMessage() + "\nprod add: fail\n";
        }
    }


    public String displayAddService(String input) {
        try {
            ProductService prod = serviceLogic.handleProdAddService(input);
            return prod.toString() + "\nprod add: ok\n";
        } catch (Exception e) {
            return "Error adding service: " + e.getMessage() + "\nprod add: fail\n";
        }
    }


    public String displayAddProductFood(String input) {
        try {
            ProductFood prod = productLogic.handleProdAddFood(input);
            return prod.toString() + "\nprod addFood: ok\n";
        } catch (Exception e) {
            return "Error adding food product: " + e.getMessage() + "\nprod addFood: fail\n";
        }
    }


    public String displayAddProductMeeting(String input) {
        try {
            ProductMeeting prod = productLogic.handleProdAddMeeting(input);
            return prod.toString() + "\nprod addMeeting: ok\n";
        } catch (Exception e) {
            return "Error adding meeting product: " + e.getMessage() + "\nprod addMeeting: fail\n";
        }
    }


    public String displayProductRemove(String input) {
        Scanner sc = new Scanner(input);
        String idToRemove = sc.next();

        try {

            Product prod = productCatalog.getProduct(idToRemove);
            if (prod != null) {
                productCatalog.removeProduct(idToRemove);
                return prod.toString() + "\nprod remove: ok\n";
            } else {
                throw new IllegalArgumentException("Product not found: " + idToRemove);
            }
        } catch (Exception e) {
            try {
                ProductService removedService = serviceLogic.handleProdRemove(idToRemove);
                return removedService.toString() + "\nprod remove: ok\n";
            } catch (Exception ex) {
                return "Error removing item: " + ex.getMessage() + "\nprod remove: fail\n";
            }
        }
    }


    public String displayProductUpdate(String input) {
        try {
            Product updatedProduct = productLogic.handleProdUpdate(input);
            return updatedProduct.toString() + "\nprod update: ok\n";
        } catch (Exception e) {
            return "Error updating product: " + e.getMessage() + "\nprod update: fail\n";
        }
    }


    public String displayProductList() {
        try {
            StringBuilder result = new StringBuilder();

            result.append("--------------Productos--------------\n");
            List<String> products = productLogic.handleProdList();
            result.append("Catalog:\n");
            for (String p : products) {
                result.append("  ").append(p).append("\n");
            }
            result.append("--------------------------------------\n");
            result.append("--------------Servicios--------------\n");
            List<String> services = serviceLogic.handleProdList();
            for (String s : services) {
                result.append("  ").append(s).append("\n");
            }
            result.append("--------------------------------------\n");
            result.append("prod list: ok\n");
            return result.toString();
        } catch (Exception e) {
            return "Error listing products: " + e.getMessage() + "\nprod list: fail\n";
        }
    }

    public ServiceCatalog getServiceCatalog() {
        return serviceCatalog;
    }

    public void setServiceCount(int count) {
        serviceLogic.setCount(count);
    }
}