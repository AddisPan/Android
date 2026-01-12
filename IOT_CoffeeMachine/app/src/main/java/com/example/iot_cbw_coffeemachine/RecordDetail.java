package com.example.iot_cbw_coffeemachine;

public class RecordDetail {
    int image;
    String name;
    String tempNsweet;
    String price;
    String quantity;
    String total;

    public RecordDetail(int image, String name, String tempNsweet, String price, String quantity, String total) {
        this.image = image;
        this.name = name;
        this.tempNsweet = tempNsweet;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTempNsweet() {
        return tempNsweet;
    }

    public void setTempNsweet(String tempNsweet) {
        this.tempNsweet = tempNsweet;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
