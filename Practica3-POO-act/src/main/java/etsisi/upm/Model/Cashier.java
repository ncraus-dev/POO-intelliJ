package etsisi.upm.Model;

import etsisi.upm.Logic.Ticket;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Cashier extends User {
    private String id;

    public Cashier(){
        super();

    }
    public Cashier(String id, String name, String email) {
        super(name, email);
        this.id = id;

    }

    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String toString() {
        return "Cash{identifier='" + id + "', name='" + name + "', email='" + email + "'}\n";
    }
}
