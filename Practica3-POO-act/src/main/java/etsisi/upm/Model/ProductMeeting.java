package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Locale;

@XmlRootElement(name = "ProductMeeting")
public class ProductMeeting extends ProductEvent {

    public ProductMeeting(int id, String name, double price, String expiration, int maxPeople) {
        super(id, name, price, expiration, maxPeople);

        LocalDateTime now = LocalDateTime.now();
        if (getExpiration().isBefore(now.plusHours(12))) {
            throw new IllegalArgumentException(
                    "Meeting must be scheduled at least 12 hours in advance");
        }
    }

    public ProductMeeting() {
        super();
    }

    @Override
    public String getClassType() {
        return "Meeting"; // Esto aparecerá en "{class:Meeting...}"
    }

    @Override
    public String toStringTicket() {
        return String.format(Locale.US,
                "{class:%s, id:%s, name:'%s', price:%.1f, date of Event:%s, max people allowed:%d}",
                getClassType(), getId(), getName(), getPrice(),
                getExpiration().format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                getMaxPeople()
        );
    }

    @Override
    public String toString() {
        return toStringTicket();
    }
}