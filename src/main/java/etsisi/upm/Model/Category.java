package etsisi.upm.Model;

public enum Category {
    MERCH(0.00),
    STATIONERY(0.05),
    CLOTHES(0.07),
    BOOK(0.1),
    ELECTRONIC(0.03);

    double discount;
    Category(double discount) {
        this.discount= discount;
    }

    public double getDiscount() {
        return discount;
    }
}