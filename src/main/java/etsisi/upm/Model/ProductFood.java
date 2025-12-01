package etsisi.upm.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductFood extends Product {
    private LocalDate expiration;
    private int maxPeople;

    public ProductFood(int id, String name, double price, String expiration, int maxPeople){
        super(id,name,price);
        // Validar número de personas
        if(maxPeople < 2 || maxPeople > 100) {
            throw new IllegalArgumentException("The amount of people must be between 2 and 100");
        }
        // Parsear fecha de expiración
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.expiration = LocalDate.parse(expiration, formatter);

        // Validar que sea al menos 3 días después (contando horas)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minAllowed = now.plusDays(3);

        if (this.expiration.atStartOfDay().isBefore(minAllowed)) {
            throw new IllegalArgumentException("Expiration date must be at least 3 days in advance");
        }

        this.maxPeople = maxPeople;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    @Override
    public String toString() {
        return String.format("{class:Food, id:%d, name:'%s', price:0.0, date of Event:%s, max people allowed:%d}",
        // No mostramos las horas
                getId(), getName(), expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), maxPeople);
    }
}