package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Client.class, Cashier.class}) // VITAL PARA XML
public abstract class User {
    protected String name;
    protected String email;

    public User() {}
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public abstract String toString();
}



