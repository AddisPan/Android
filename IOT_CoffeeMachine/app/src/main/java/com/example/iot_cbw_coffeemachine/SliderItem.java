package com.example.iot_cbw_coffeemachine;

public class SliderItem {

    private String title;
    private String product;
    private String temperature;
    private String sweet;
    private String price;
    int image;
//    private String imageUrl;

    public SliderItem(String title, String product, String temperature, String sweet, String price, int image) {
        this.title = title;
        this.product = product;
        this.temperature = temperature;
        this.sweet = sweet;
        this.price = price;
        this.image = image;
//        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

//    public String getImageUrl() {
//        return imageUrl;
//    }
//
//    public void setImageUrl(String imageUrl) {
//        this.imageUrl = imageUrl;
//    }
}
