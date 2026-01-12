package com.example.iot_cbw_coffeemachine;

public class Record {
    String time;
    String coffee;
    String cups;
    String discount;
    String total;
    String o_id;

    public Record(String _time, String _coffee, String _cups, String _discount, String _total, String _o_id) {
        time = _time;
        coffee = _coffee;
        cups = _cups;
        discount = _discount;
        total = _total;
        o_id =_o_id;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String _time) {
        time = _time;
    }

    public String getCoffee() {
        return coffee;
    }

    public void setCoffee(String _coffee) {
        coffee = _coffee;
    }

    public String getCups() {
        return cups;
    }

    public void setCups(String _cups) {
        cups = _cups;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String _discount) {
        discount = _discount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String _total) {
        total = _total;
    }
}
