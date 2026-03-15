package etsisi.upm.Model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.Locale;


@XmlAccessorType(XmlAccessType.FIELD)
public class TicketItem<T extends Sales> {
    @XmlElement(name = "ItemDetalle")
    private T item;

    private int quantity;
    private String[] textPersonalizable;
    private int contPers;

    public TicketItem(T item, int quantity) {

        this.item = (T) item.clone();
        this.quantity = quantity;
        this.contPers = 0;
    }

    public TicketItem() {

    }

    public T getElement() { return item; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public int getContPers() { return this.contPers; }

    private double getUnitPriceWithSurcharge() {
        double basePrice = item.getPrice();
        double surcharge = 0.0;

        if (item instanceof ProductCustom && this.contPers > 0) {
            surcharge = (item.getPrice() * 0.10) * this.contPers;
        }
        return basePrice + surcharge;
    }

    public double getSubtotal() {
        return getUnitPriceWithSurcharge() * quantity;
    }

    public void settextPersonalizable(String textPersonalizableInput) {
        if(item instanceof ProductCustom && ((ProductCustom) item).getModified()) {
            this.textPersonalizable = textPersonalizableInput.trim().split("--p");
            int contadorValidos = 0;
            for (String s : this.textPersonalizable) {
                if (s != null && !s.isEmpty()) contadorValidos++;
            }
            if(contadorValidos > ((ProductCustom) item).getmaxPers()) {
                throw new IllegalArgumentException("Limit exceeded. Max: " + ((ProductCustom) item).getmaxPers());
            }

            String[] aux = new String[contadorValidos];
            int indiceAux = 0;
            for (String s : this.textPersonalizable) {
                if (s != null && !s.isEmpty()) {
                    aux[indiceAux++] = s.trim();
                }
            }
            this.textPersonalizable = aux;
            this.contPers = contadorValidos;
        } else {
            throw new IllegalArgumentException("Error-->This product can't be personalizable");
        }
    }

    @Override
    public String toString() {

        if (!(this.item instanceof ProductCustom customProd) || this.contPers == 0 || this.textPersonalizable == null) {
            String res= item.toStringTicket();
            res= res.replace("}",", actual people in event: ");
            res+= String.valueOf(quantity);
            res+="}";
            return res;
        }

        double unitPriceWithTax = getUnitPriceWithSurcharge();
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < textPersonalizable.length; i++) {
            if (textPersonalizable[i] != null) {
                sb.append(textPersonalizable[i].trim());
                if (i < textPersonalizable.length - 1) sb.append(", ");
            }
        }
        sb.append("]");

        if(item instanceof ProductEvent ){
            return String.format(Locale.US,
                    "{class:ProductPersonalized, id:%s, name:'%s', category:%s, price:%.1f, " +
                            "maxPersonal:%d, personalizationList:%s}",
                    customProd.getId(),
                    customProd.getName(),
                    customProd.getCategory(),
                    unitPriceWithTax,
                    customProd.getmaxPers(),
                    sb.toString()
            );
        }

        return String.format(Locale.US,
                "{class:ProductPersonalized, id:%s, name:'%s', category:%s, price:%.1f, " +
                        "maxPersonal:%d, personalizationList:%s}",
                customProd.getId(),
                customProd.getName(),
                customProd.getCategory(),
                unitPriceWithTax,
                customProd.getmaxPers(),
                sb.toString()
        );
    }
}