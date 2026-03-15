package etsisi.upm.XML;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    @Override
    public LocalDateTime unmarshal(String dateStr) {
        return dateStr == null || dateStr.trim().isEmpty()
                ? null
                : LocalDateTime.parse(dateStr.trim(), formatter);
    }

    @Override
    public String marshal(LocalDateTime dateTime) {
        return dateTime == null ? null : dateTime.format(formatter);
    }
}