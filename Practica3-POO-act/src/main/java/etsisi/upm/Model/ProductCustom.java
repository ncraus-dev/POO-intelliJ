package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Locale;

@XmlRootElement(name="ProductCustom")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductCustom extends Product {
    private Category category;
    private int maxPers;
    private boolean MODIFIED;

    public ProductCustom() {
        super();
    }

    public ProductCustom(int id, String name, Category category, double price, int maxPers) {
        super(id, name, price);
        this.category = category;
        this.maxPers = maxPers;
        this.MODIFIED = true;
    }

    public ProductCustom(int id, String name, Category category, double price) {
        super(id, name, price);
        this.category = category;
        this.maxPers = 0;
        this.MODIFIED = false;
    }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
    public int getmaxPers() { return maxPers; }
    public boolean getModified() { return MODIFIED; }


    @Override
    public String toStringTicket() {
        if(!this.MODIFIED)
            return String.format(Locale.US, "{class:Product, id:%s, name:'%s', category:%s, price:%.1f}",
                    getId(), getName(), getCategory(), getPrice());
        else
            return String.format(Locale.US, "{class:ProductPersonalized, id:%s, name:'%s', category:%s, price:%.1f, maxPersonal:%d}",
                    getId(), getName(), getCategory(), getPrice(), getmaxPers());
    }

    @Override
    public String toString() {
        return toStringTicket();
    }
}