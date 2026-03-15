package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlRootElement;
import java.time.LocalDateTime;
import java.util.Locale;

@XmlRootElement(name = "ProductFood")
public class ProductFood extends ProductEvent {

    public ProductFood(int id, String name, double price, String expiration, int maxPeople) {
        super(id, name, price, expiration, maxPeople);
        // Validar 3 días desde HOY (no desde cuando se creó el objeto)
        LocalDateTime now = LocalDateTime.now();
        if (getExpiration().isBefore(now.plusDays(3))) {
            throw new IllegalArgumentException(
                    "Food expiration must be at least 3 days in advance");
        }
    }

    public ProductFood() {
        super();
    }

    @Override
    public String getClassType() {
        return "Food"; // Esto aparecerá en "{class:Food...}"
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