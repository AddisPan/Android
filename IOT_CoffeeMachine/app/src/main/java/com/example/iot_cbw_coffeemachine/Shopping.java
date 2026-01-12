package com.example.iot_cbw_coffeemachine;

import android.widget.ImageView;

public class Shopping {

    //    int img_product;
    int id;
    int img_product;
    String name;
    String temperature;
    String sweet;
    String price;
    String quantity;
    int iv_temp, iv_sweet;

    public Shopping(int id, int img_product, String name, int iv_temp, String temperature, int iv_sweet, String sweet, String price, String quantity) {

        this.id = id;
        this.img_product = img_product;
        this.name = name;
        this.temperature = temperature;
        this.sweet = sweet;
        this.price = price;
        this.quantity = quantity;
        this.iv_temp = iv_temp;
        this.iv_sweet = iv_sweet;
    }

    public int getIv_temp() {
        return iv_temp;
    }

    public void setIv_temp(int iv_temp) {
        this.iv_temp = iv_temp;
    }

    public int getIv_sweet() {
        return iv_sweet;
    }

    public void setIv_sweet(int iv_sweet) {
        this.iv_sweet = iv_sweet;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    //    public String getImg_product() {
//        return img_product;
//    }
//
//    public void setImg_product(String img_product) {
//        this.img_product = img_product;
//    }
    public int getImg_product() {
        return img_product;
    }

    public void setImg_product(int img_product) {
        this.img_product = img_product;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSweet() {
        return sweet;
    }

    public void setSweet(String sweet) {
        this.sweet = sweet;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
