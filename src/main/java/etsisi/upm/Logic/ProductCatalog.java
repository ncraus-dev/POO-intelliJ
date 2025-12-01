package etsisi.upm.Logic;
import etsisi.upm.Model.*;
import javax.xml.catalog.Catalog;
import java.util.*;

public class ProductCatalog {
    private Map<Integer,Product> products;

    public ProductCatalog(){
        this.products = new TreeMap<>();
     }

    public ProductCatalog(Map<Integer,Product> listaDeProductosYaExistentes){
        this.products = listaDeProductosYaExistentes;
    }


    // ================= ADDS =================

    public boolean addProduct(Product product){
        if(products.get(product.getId())!=null){throw new IllegalArgumentException("The product has the id");}
        products.put(product.getId(),product);
            return true;
    }

    // ================= REMOVES =================

    public boolean removeProduct(int id) {
        if (!products.containsKey(id)) {
            throw new IllegalArgumentException("No product found with id " + id);
        }else{
            products.remove(id);
        }
        return true;
    }

    // ================= UPDATES =================

    public boolean updateProduct(int id, String name, double price, Category category) {
        boolean updated = false;
        for(Product product: products.values()){
            if(product instanceof  ProductCustom){
                ProductCustom productExist = (ProductCustom) product;
                if (productExist.getId() == id) {
                    productExist.setName(name);
                    productExist.setPrice(price);
                    productExist.setCategory(category);
                    updated = true;
                }
            }
        }
        if(updated){
            return updated;
        }else {
            throw new IllegalArgumentException("No product found with id " + id);
        }
    }


    public Product getProduct(int id) {
        return products.get(id);}

    public List<Product> getProducts() {
        return new ArrayList<>(products.values());
    }

}
