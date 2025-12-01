package etsisi.upm.Model;

public class Client extends User {
    private String Dni;
    private Cashier registeredBy;

    public Client(String name, String dni, String email, Cashier registeredBy) {
        super(name, email);
        this.registeredBy = registeredBy;
        this.Dni = dni;
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

    public String getRegisteredBy() {
        return registeredBy.getId();
    }

    @Override
    public String toString() {
        return "Client{identifier='" + Dni + "', email='" + email + "', name='" + name + "', registeredBy='" + registeredBy.getId() + "'}\n";
    }
}