package etsisi.upm.XML;

import etsisi.upm.Logic.Ticket;
import etsisi.upm.Model.*;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.*;

@XmlRootElement(name = "Catalogos")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlCatalogs {


    @XmlElementWrapper(name = "CatalogoProductos")
    @XmlElement(name = "ProductoEntry")
    private List<ProductEntry> productos = new ArrayList<>();

    @XmlElementWrapper(name = "CatalogoClientes")
    @XmlElement(name = "ClientEntry")
    private List<ClientEntry> clientes = new ArrayList<>();

    @XmlElementWrapper(name = "CatalogoCajeros")
    @XmlElement(name = "CashierEntry")
    private List<CashierEntry> cajeros = new ArrayList<>();

    @XmlElementWrapper(name = "CatalogoTickets")
    @XmlElement(name = "TicketEntry")
    private List<TicketEntry> tickets = new ArrayList<>();

    @XmlElementWrapper(name = "CatalogoServicios")
    @XmlElement(name = "ServiceEntry")
    private List<ServiceEntry> service = new ArrayList<>();

    public XmlCatalogs() {}


    public List<ProductEntry> getProductos() { return productos; }
    public void setProductos(List<ProductEntry> productos) { this.productos = productos; }

    public List<ClientEntry> getClientes() { return clientes; }
    public void setClientes(List<ClientEntry> clientes) { this.clientes = clientes; }

    public List<CashierEntry> getCajeros() { return cajeros; }
    public void setCajeros(List<CashierEntry> cajeros) { this.cajeros = cajeros; }

    public List<TicketEntry> getTickets() { return tickets; }
    public void setTickets(List<TicketEntry> tickets) { this.tickets = tickets; }

    public List<ServiceEntry> getService() { return service; }
    public void setService(List<ServiceEntry> service) { this.service = service; }

}
