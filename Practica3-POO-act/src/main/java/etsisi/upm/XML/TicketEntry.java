package etsisi.upm.XML;

import etsisi.upm.Logic.Ticket;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class TicketEntry {
    @XmlAttribute
    private String key;

    @XmlElement
    private Ticket value;

    public TicketEntry() {}
    public TicketEntry(String key, Ticket value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public Ticket getValue() { return value; }
}
