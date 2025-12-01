package etsisi.upm.Model;

import java.util.ArrayList;
import java.util.Locale;

public class ProductCustom extends Product {
    
    private Category category;
    private int maxPers;
    private final boolean modified;


    public ProductCustom(int id, String name, Category category, double price, int maxPers) {
        super(id, name, price);
        this.category = category;
        this.maxPers = maxPers;
        this.modified = true;
        }

    public ProductCustom(int id, String name, Category category, double price) {
        super(id, name, price);
        this.category = category;
        this.maxPers = 0;
        this.modified = false;
    }

   // public int get
    public Category getCategory() {
        return category;
    }


    public void setCategory(Category category) {
        this.category = category;
    }

    public int getmaxPers() {
        return maxPers;
    }

    public boolean getModified() {
        return modified;
    }


    @Override
    public String toString() {
        if(!this.modified)
            return String.format(Locale.US,"{class:Product, id:%d, name:'%s', category:%s, price:%.2f}"
                    , getId(), getName(), getCategory(), getPrice());
        else
            return String.format(Locale.US,"{class:ProductPersonalized, id:%d, name:'%s', category:%s, price:%.2f, maxPersonal:%d}"
                    , getId(), getName(), getCategory(), getPrice(),getmaxPers());
    }

}
