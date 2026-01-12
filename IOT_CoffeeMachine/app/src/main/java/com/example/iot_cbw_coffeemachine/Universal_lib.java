package com.example.iot_cbw_coffeemachine;

public class Universal_lib {

    // 192.168.50.204
    // 192.168.1.153
    public String ipAddress= "192.168.43.201";

    public int changeTempImage(String temperature){
        int image=0;
        switch (temperature) {
            case "熱":
                image = R.drawable.ic_temp_hot;
                break;
            case "正常":
                image = R.drawable.ic_temp_10;
                break;
            case "少冰":
                image = R.drawable.ic_temp_7;
                break;
            case "微冰":
                image = R.drawable.ic_temp_5;
                break;
            case "去冰":
                image = R.drawable.ic_temp_0;
                break;
            default:
                break;
        }
        return image;
    }

    public int changeSweetImage(String sweet){
        int image=0;
        switch (sweet) {
            case "無糖":
                image = R.drawable.ic_sweet_0;
                break;
            case "一分糖":
                image = R.drawable.ic_sweet_0;
                break;
            case "二分糖":
                image = R.drawable.ic_sweet_1;
                break;
            case "三分糖":
                image = R.drawable.ic_sweet_1;
                break;
            case "四分糖":
                image = R.drawable.ic_sweet_1;
                break;
            case "五分糖":
                image = R.drawable.ic_sweet_5;
                break;
            case "六分糖":
                image = R.drawable.ic_sweet_5;
                break;
            case "七分糖":
                image = R.drawable.ic_sweet_5;
                break;
            case "八分糖":
                image = R.drawable.ic_sweet_5;
                break;
            case "九分糖":
                image = R.drawable.ic_sweet_10;
                break;
            case "全糖":
                image = R.drawable.ic_sweet_10;
                break;
            default:
                break;
        }
        return image;
    }

    public int changeTempImage_Code(String temperature){
        int image=0;
        switch (temperature) {
            case "0":
                image = R.drawable.ic_temp_hot;
                break;
            case "1":
                image = R.drawable.ic_temp_10;
                break;
            case "2":
                image = R.drawable.ic_temp_7;
                break;
            case "3":
                image = R.drawable.ic_temp_5;
                break;
            case "4":
                image = R.drawable.ic_temp_0;
                break;
            default:
                break;
        }
        return image;
    }

    public int changeSweetImage_Code(String sweet){
        int image=0;
        switch (sweet) {
            case "0":
                image = R.drawable.ic_sweet_0;
                break;
            case "1":
                image = R.drawable.ic_sweet_0;
                break;
            case "2":
                image = R.drawable.ic_sweet_1;
                break;
            case "3":
                image = R.drawable.ic_sweet_1;
                break;
            case "4":
                image = R.drawable.ic_sweet_1;
                break;
            case "5":
                image = R.drawable.ic_sweet_5;
                break;
            case "6":
                image = R.drawable.ic_sweet_5;
                break;
            case "7":
                image = R.drawable.ic_sweet_5;
                break;
            case "8":
                image = R.drawable.ic_sweet_5;
                break;
            case "9":
                image = R.drawable.ic_sweet_10;
                break;
            case "A":
                image = R.drawable.ic_sweet_10;
                break;
            default:
                break;
        }
        return image;
    }
}
