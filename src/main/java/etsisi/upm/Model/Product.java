package etsisi.upm.Model;

import java.util.ArrayList;

public abstract class Product {

    protected int id;
    protected String name;
    protected double price;



    public Product(int id, String name, double price) {
        if ((id > 0 ) && !name.isEmpty() && name.length() <= 100 && price > 0) {
            this.id = id;
            this.name = name;
            this.price = price;
        }
        else{ throw new  IllegalArgumentException("Unable to create the product");}
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.length() > 100) {
            System.out.println("The name is too long");
        }
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price <= 0) {
            System.out.println("The price must be positive");
        }
        this.price = price;
    }

}
