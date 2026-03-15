package etsisi.upm.XML;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ExpirationAdapter extends XmlAdapter<String, LocalDate> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public LocalDate unmarshal(String dateStr) {
        return dateStr == null || dateStr.trim().isEmpty()
                ? null
                : LocalDate.parse(dateStr.trim(), formatter);
    }

    @Override
    public String marshal(LocalDate date) {
        return date == null ? null : date.format(formatter);
    }
}