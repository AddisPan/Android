package com.example.iot_cbw_coffeemachine;

public class Coffee {
    int id;
    String name;
    String dollars;
    int image;
    int categoryID;
    String coffeeBeans;
    String calorie;
    String caffeine;

    public Coffee(int id, String name, String dollars, int image, int categoryID, String coffeeBeans, String calorie, String caffeine) {
        this.id = id;
        this.name = name;
        this.dollars = dollars;
        this.image = image;
        this.categoryID = categoryID;
        this.coffeeBeans = coffeeBeans;
        this.calorie = calorie;
        this.caffeine = caffeine;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getCaffeine() {
        return caffeine;
    }

    public void setCaffeine(String caffeine) {
        this.caffeine = caffeine;
    }

    public String getCoffeeBeans() {
        return coffeeBeans;
    }

    public void setCoffeeBeans(String coffeeBeans) {
        this.coffeeBeans = coffeeBeans;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDollars() {
        return dollars;
    }

    public void setDollars(String dollars) {
        this.dollars = dollars;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }
}
