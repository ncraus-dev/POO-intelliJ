package etsisi.upm.Logic;

import etsisi.upm.Model.*;
import java.util.*;

public class ProductCatalog {
    private Map<String, Product> products;

    public ProductCatalog() {
        this.products = new TreeMap<>(); // TreeMap mantiene orden alfabético por ID
    }

    // TODO: CORREGIDO - addProduct con validación duplicada
    // Razón: En pooo.txt no validaba correctamente IDs duplicados para productos.
    // Para E3 necesitamos asegurar unicidad de IDs incluso al cargar desde XML.
    public boolean addProduct(Product product) {
        String id = product.getId(); // ID ya es String en Sales
        if (products.containsKey(id)) {
            throw new IllegalArgumentException("Product with ID " + id + " already exists");
        }
        products.put(id, product);
        return true;
    }


    public boolean removeProduct(String id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("No product found with id " + id);
        }
        products.remove(id);
        return true;
    }

    public Product getProduct(String id) {
        return products.get(id);
    }

    public Product getProductById(int id) {
        return products.get(String.valueOf(id));
    }

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

    public void setProductsMap(Map<String, Product> productsMap) {
        this.products = productsMap;
    }
}