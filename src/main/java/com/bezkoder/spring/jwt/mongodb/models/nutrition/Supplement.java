package com.bezkoder.spring.jwt.mongodb.models.nutrition;

public class Supplement extends Nutrition {

    private EUnit unit;

    public Supplement() {
        super("SUPPLEMENT");
    }

    public EUnit getUnit() {
        return unit;
    }

    public void setUnit(EUnit unit) {
        this.unit = unit;
    }


}
