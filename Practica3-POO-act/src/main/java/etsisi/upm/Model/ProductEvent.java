package etsisi.upm.Model;

import etsisi.upm.XML.DateTimeAdapter;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({ProductFood.class, ProductService.class})
public abstract class ProductEvent extends Product {
    @XmlElement(name = "Expiration")
    @XmlJavaTypeAdapter(DateTimeAdapter.class) // ← CAMBIAR AQUÍ
    private LocalDateTime expiration;

    private int maxPeople;

    public ProductEvent() {
        super();
    }

    public ProductEvent(int id, String name, double price, String expiration, int maxPeople) {
        super(id, name, price);
        // Lógica común de parseo de fecha
        this.expiration = parseExpirationDate(expiration);
        this.maxPeople = maxPeople;
    }

    protected LocalDateTime parseExpirationDate(String expiration) {

        try {
            java.time.format.DateTimeFormatter formatter =
                    java.time.format.DateTimeFormatter.ofPattern("uuuu-MM-dd")
                            .withResolverStyle(java.time.format.ResolverStyle.STRICT);
            java.time.LocalDate datePart = java.time.LocalDate.parse(expiration, formatter);
            return datePart.atStartOfDay();
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Use yyyy-MM-dd");
        }
    }

    public LocalDateTime getExpiration() { return expiration; }
    public int getMaxPeople() { return maxPeople; }

    public abstract String getClassType();
}