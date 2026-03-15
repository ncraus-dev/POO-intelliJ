package etsisi.upm.XML;

import etsisi.upm.Model.Cashier;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class CashierEntry {
    @XmlAttribute
    private String key;

    @XmlElement
    private Cashier value;

    public CashierEntry() {}
    public CashierEntry(String key, Cashier value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public Cashier getValue() { return value; }
}
