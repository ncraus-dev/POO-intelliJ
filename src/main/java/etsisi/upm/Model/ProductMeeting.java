package etsisi.upm.Model;
import java.time.LocalDate; // Import necesario
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProductMeeting extends Product {
    private LocalDateTime expiration;
    private int maxPeople;

    public ProductMeeting(int id, String name, double price, String expiration, int maxPeople){
        super(id,name,price);
        if(maxPeople < 2 || maxPeople > 100) {
            throw new IllegalArgumentException("The amount of people must be between 2 and 100");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate datePart = LocalDate.parse(expiration, formatter);
        this.expiration = datePart.atStartOfDay();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minAllowed = now.plusHours(12);

        if (this.expiration.isBefore(minAllowed)) {
            throw new IllegalArgumentException("Meeting must be scheduled at least 12 hours in advance");
        }

        this.maxPeople = maxPeople;
    }

    public LocalDate getExpiration() {
      return expiration.toLocalDate();
    }

    public int getMaxPeople() {
        return maxPeople;
    }

    @Override
    public String toString() {
        return String.format("{class:Meeting, id:%d, name:'%s', price:0.0, date of Event:%s, max people allowed:%d}",
                getId(), getName(),
                expiration.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), maxPeople);
    }
}