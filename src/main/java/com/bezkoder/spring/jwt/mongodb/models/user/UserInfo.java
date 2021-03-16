package com.bezkoder.spring.jwt.mongodb.models.user;

import java.time.LocalDate;

public class UserInfo {

    private String name;
    private LocalDate dateOfBirth;
    private float weight;
    private int height;

    public UserInfo() {}

    public UserInfo(String name, LocalDate dateOfBirth, float weight, int height) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
