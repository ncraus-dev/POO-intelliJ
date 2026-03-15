package etsisi.upm.Model;

import etsisi.upm.XML.ExpirationAdapter;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.util.Locale;

@XmlRootElement(name = "ProductService")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProductService extends Sales {
    @XmlElement(name = "Expiration")
    @XmlJavaTypeAdapter(ExpirationAdapter.class)
    private LocalDate expiration;

    private String category;
    public ProductService(String id, LocalDate expiration, String category) {
        super(id);
        this.expiration = expiration;
        this.category = category;
    }

    public ProductService() {}

    @Override
    public double getPrice() { return 0.0; }

    @Override
    public String toStringTicket() {

        return String.format(Locale.US,
                "{class:ProductService, id:%s, category:%s, expiration:%s}",
                getId(), category, expiration.toString());
    }

    public LocalDate getExpiration() { return expiration; }
    public String getCategory() { return category; }

    @Override
    public String toString() { return toStringTicket(); }
}