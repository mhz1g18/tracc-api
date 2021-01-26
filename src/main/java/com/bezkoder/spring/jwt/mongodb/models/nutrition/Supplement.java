package com.bezkoder.spring.jwt.mongodb.models.nutrition;

public class Supplement extends Nutrition {

    private float quantity;

    public Supplement() {
        super("SUPPLEMENT");
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }
}
