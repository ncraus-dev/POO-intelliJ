package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;

public class ServiceCatalog {
    private Map<String, ProductService> services;

    public ServiceCatalog() {
        this.services = new TreeMap<>();
    }

    public boolean addService(ProductService product) {
        if (services.get(product.getId()) != null) {
            throw new IllegalArgumentException("The service has the id");
        }
        if (product.getExpiration().isBefore(java.time.LocalDate.now())) {
            throw new IllegalArgumentException("Service expiration date must be in the future");
        }
        services.put(product.getId(), product);
        return true;
    }

    public boolean removeService(String id) {
        if (!services.containsKey(id)) {
            throw new IllegalArgumentException("No Service found with id " + id);
        }
        services.remove(id);
        return true;
    }

    public ProductService getService(String id) {
        return services.get(id);
    }

    public List<ProductService> getProducts() {
        return new ArrayList<>(services.values());
    }

    public void setProductsMap(Map<String, ProductService> productsMap) {
        this.services = productsMap;
    }

    public int last() {
        if (services.isEmpty()) {
            return 0;
        }

        int maxNum = 0;
        for (String id : services.keySet()) {

            if (id != null && id.matches("\\d+S")) {
                String numPart = id.substring(0, id.length() - 1);
                try {
                    int num = Integer.parseInt(numPart);
                    if (num > maxNum) {
                        maxNum = num;
                    }
                } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid number format: " + numPart);
                }
            }
        }
        return maxNum;
    }
}