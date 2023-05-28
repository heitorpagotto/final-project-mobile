package com.unifaj.projeto_final.models.entities;

import java.text.NumberFormat;
import java.util.Locale;

public class Products {
    private String name;
    private int quantity;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String toString() {
        Locale localeBR = new Locale("pt", "BR");
        NumberFormat moneyBRL = NumberFormat.getCurrencyInstance(localeBR);
        return getName() + "  Qtd: " + getQuantity() + "  " + moneyBRL.format(getPrice());
    }
}
