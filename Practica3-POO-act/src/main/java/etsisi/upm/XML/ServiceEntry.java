package etsisi.upm.XML;

import etsisi.upm.Model.ProductService;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceEntry {
    @XmlAttribute
    private String key;

    @XmlElement
    private ProductService value;

    public ServiceEntry() {}
    public ServiceEntry(String key, ProductService value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() { return key; }
    public ProductService getValue() { return value; }
}
