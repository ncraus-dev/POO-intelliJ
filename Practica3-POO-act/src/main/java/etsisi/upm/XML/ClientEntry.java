package etsisi.upm.XML;

import etsisi.upm.Model.Client;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class ClientEntry {
    @XmlAttribute
    private String key;

    @XmlElement
    private Client value;

    public ClientEntry() {}
    public ClientEntry(String key, Client value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public Client getValue() { return value; }
}
