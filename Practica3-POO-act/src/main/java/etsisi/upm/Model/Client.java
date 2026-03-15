package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Client extends User {
    private String Dni;

    @XmlElement(name = "Cajero_de_registro")
    private Cashier registeredBy;
    private ClientType type;

    public Client(){
        super();
    }
    public Client(String name, String dni, String email, Cashier registeredBy,ClientType type) {
        super(name, email);
        this.registeredBy = registeredBy;
        this.Dni = dni;
        this.type = type;
    }


    public String getDni() {
        return Dni;
    }

    @Override
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public ClientType getType() {
        return type;
    }

    public Cashier getRegisteredBy() {
        return registeredBy;
    }

    @Override
    public String toString() {
        String typeString = (type == ClientType.Company) ? "COMPANY" : "USER";
        return typeString + "{identifier='" + Dni + "', email='" + email +
                "', name='" + name + "', registeredBy='" + registeredBy.getId() + "'}";
    }
}