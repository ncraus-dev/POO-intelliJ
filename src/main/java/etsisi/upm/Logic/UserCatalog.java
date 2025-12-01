package etsisi.upm.Logic;

import etsisi.upm.Model.Cashier;
import etsisi.upm.Model.Client;

import java.util.*;

public class UserCatalog {
    private Map<String, Client> clients = new HashMap<>();
    private Map<String, Cashier> cashiers = new HashMap<>();

    public boolean addClient(Client client) {
        if (clients.containsKey(client.getDni())) return false;
        clients.put(client.getDni(), client);
        return true;
    }

    public boolean addCashier(Cashier cashier) {
        if (cashiers.containsKey(cashier.getId())) return false;
        cashiers.put(cashier.getId(), cashier);
        return true;
    }

    public Client getClient(String dni) {
        return clients.get(dni);
    }

    public Cashier getCashier(String id) {
        return cashiers.get(id);
    }

    public boolean removeClient(String dni) {
        if (!clients.containsKey(dni)) {
            return false;
        }
        clients.remove(dni);
        return true;
    }

    public boolean removeCashier(String id) {
        if (!cashiers.containsKey(id)) {
            return false;
        }
        cashiers.remove(id);
        return true;
    }

    public List<Client> listClients() {
        return new ArrayList<>(clients.values());
    }

    public List<Cashier> listCashiers() {
        return new ArrayList<>(cashiers.values());
    }

}
