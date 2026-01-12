package com.example.iot_cbw_coffeemachine;

public class ShopDetail {
    String name;
    String tempNsweet;
    String quantity;
    String total;

    public ShopDetail(String name, String tempNsweet, String quantity, String total) {
        this.name = name;
        this.tempNsweet = tempNsweet;
        this.quantity = quantity;
        this.total = total;
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
