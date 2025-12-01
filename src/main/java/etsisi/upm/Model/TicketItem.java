package etsisi.upm.Model;

import java.util.Locale;

public class TicketItem {
 /* private ProductCustom productC;
    private ProductFood productF;
    private ProductMeeting productM;*/

    private Product product;
    private int quantity;
    private String[] textPersonalizable;
    private int contPers;

    public TicketItem(Product product,int quantity){
        this.product=product;
        this.quantity=quantity;
        this.contPers=0;
    }


    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getContPers() { return this.contPers; }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    private double getUnitPriceWithSurcharge() {
        double basePrice = product.getPrice();
        double surcharge = 0.0;
        if (this.contPers > 0) {
            surcharge = (basePrice * 0.10) * this.contPers;
        }
        return basePrice + surcharge;
    }
    public double getSubtotal() {
        return getUnitPriceWithSurcharge() * quantity;
    }

    public double getDiscount() {
        if (!(product instanceof ProductCustom))
            return 0.0;

        return getUnitPriceWithSurcharge() * ((ProductCustom) product).getCategory().getDiscount();
    }

    public void settextPersonalizable(String textPersonalizableInput) {
        if(product instanceof ProductCustom && ((ProductCustom)product).getModified()){
            this.textPersonalizable=textPersonalizableInput.trim().split("--p");
            int contadorValidos = 0;
            for (String s : this.textPersonalizable) {
                if (s != null && !s.isEmpty()) {
                    contadorValidos++;
                }
            }
            if(contadorValidos>((ProductCustom) product).getmaxPers()){
                throw new IllegalArgumentException
                        ("You ecceded the limit。The limit of personalizations of this product is " + ((ProductCustom) product).getmaxPers());
            }
            String[] aux = new String[contadorValidos];
            int indiceAux = 0;
            for (int i = 0; i < this.textPersonalizable.length; i++) {
                if (this.textPersonalizable[i] != null && !this.textPersonalizable[i].isEmpty()) {
                    aux[indiceAux] = this.textPersonalizable[i];
                    indiceAux++;
                }
            }
            this.textPersonalizable = aux;

            this.contPers = contadorValidos;//actualizo mi atributo para que no me de 0
        }
    }


    @Override
    public String toString() {
        if (!(this.product instanceof ProductCustom customProd) || this.contPers == 0 || this.textPersonalizable == null) {
            return product.toString();
        }

        // Calculamos el precio real unitario
        double unitPriceWithTax = getUnitPriceWithSurcharge();

        // Construimos la lista
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < textPersonalizable.length; i++) {
            if (textPersonalizable[i] != null) {
                sb.append((textPersonalizable[i]).trim());//hay que poner trim aqui
                if (i < textPersonalizable.length - 1) sb.append(", ");
            }
        }
        sb.append("]");

        return String.format(Locale.US,
                "{class:ProductPersonalized, id:%d, name:'%s', category:%s, price:%.1f, maxPersonal:%d, personalizationList:%s}",
                customProd.getId(),
                customProd.getName(),
                customProd.getCategory(),
                unitPriceWithTax,
                customProd.getmaxPers(),
                sb.toString()
                //lo vuelvo a pasar a string , aqui es una funcion de biblio, no el abstracto
        );
    }

}
