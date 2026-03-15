package etsisi.upm.XML;

import etsisi.upm.Model.Product;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductEntry {
    @XmlAttribute
    private String key;

    @XmlElement
    private Product value;

    public ProductEntry() {}

    public ProductEntry(String key, Product value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key; }
    public Product getValue() {
        return value; }
}
