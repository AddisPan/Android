package com.example.iot_cbw_coffeemachine;

public class Combination {
    int id;
    String name;
    String coffee;
    String temperature;
    String sweet;
    String cpid;
    int iv_temp, iv_sweet;

    public Combination(int _id, String _name, String _coffee, int _iv_temp, String _temperature, int _iv_sweet, String _sweet, String _cpid) {
        id = _id;
        name = _name;
        coffee = _coffee;
        iv_temp = _iv_temp;
        temperature = _temperature;
        iv_sweet = _iv_sweet;
        sweet = _sweet;
        cpid = _cpid;
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

    public String getCpid() {
        return cpid;
    }

    public void setCpid(String cpid) {
        this.cpid = cpid;
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

    public void setName(String _name) {
        name = _name;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String _coffee) {
        coffee = _coffee;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String _temperature) {
        temperature = _temperature;
    }

    public String getSweet() {
        return sweet;
    }

    public void setSweet(String _sweet) {
        sweet = _sweet;
    }
}
