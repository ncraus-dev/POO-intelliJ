package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ProductEvent.class, ProductCustom.class})
public abstract class Product extends Sales implements Cloneable {
    private String name;
    private double price;

    public Product() {
        super();
    }


    public Product(int id, String name, double price) {
        super(String.valueOf(id));
        if (!name.isEmpty() && name.length() <= 100 && price > 0) {
            this.name = name;
            this.price = price;
        } else {
            throw new IllegalArgumentException("Unable to create the product");
        }
    }


    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.length() > 100) {
            System.out.println("The name is too long");
        }
        this.name = name;
    }
    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) {
            System.out.println("The price must be positive");
        }
        this.price = price;
    }
}