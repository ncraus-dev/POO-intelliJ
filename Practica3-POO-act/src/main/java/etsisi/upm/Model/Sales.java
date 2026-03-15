package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Product.class, ProductService.class})
public abstract class Sales implements Cloneable {
    protected String id;

    public Sales(String id) {
        if(id != null && !id.isEmpty()) {
            this.id = id;
        } else {
            throw new IllegalArgumentException("Invalid ID in Sales");
        }
    }

    public Sales() {}

    public String getId() { return id; }
    public abstract double getPrice();
    public abstract String toStringTicket();

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Cloning not supported for Sales object: " + this.getClass().getName(), e);
        }
    }
}