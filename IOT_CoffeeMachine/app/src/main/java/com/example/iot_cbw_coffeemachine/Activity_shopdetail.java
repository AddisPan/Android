package com.example.iot_cbw_coffeemachine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;

public class Activity_shopdetail extends AppCompatActivity {
    Toolbar toolbar;
    ListView listView;
    TextView tv_discount, tv_total;
    Button btn_confirm;
    private SharedPreferences cart, record, cid, order;
    private SharedPreferences.Editor cart_editor, record_editor, order_editor;
    ArrayList<Shopping> shoppingList = new ArrayList<>();
    int cup = 0, sum = 0, count = 1;
    String discount = "";

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);

        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listview);
        tv_discount = findViewById(R.id.tv_discount);
        tv_total = findViewById(R.id.tv_total);
        btn_confirm = findViewById(R.id.btn_confirm);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        ArrayList<Shopping> shoppingList = new ArrayList<>(); //command out 22/8/8

        cart = getApplicationContext().getSharedPreferences("cart", Context.MODE_PRIVATE);
        record = getApplicationContext().getSharedPreferences("record", Context.MODE_PRIVATE);
        order = getApplicationContext().getSharedPreferences("order", Context.MODE_PRIVATE);

        cart_editor = cart.edit();
        record_editor = record.edit();
        order_editor = order.edit();

        Gson gson = new Gson();
//        String getJson = cart.getString("cartObj", ""); //order會蓋掉
//        if (!getJson.isEmpty()) {
//            Type type1 = new TypeToken<ArrayList<Shopping>>() {
//            }.getType();
//            shoppingList = gson.fromJson(getJson, type1);
//        }

        String getJson = order.getString("shopObj", ""); //order會蓋掉
        if (!getJson.isEmpty()) {
            Type type1 = new TypeToken<ArrayList<Shopping>>() {
            }.getType();
            shoppingList = gson.fromJson(getJson, type1);
        }

//        for (int i = 0; i < shoppingList.size(); i++) {
//            int cups = 0;
//            cups += Integer.parseInt(shoppingList.get(i).getQuantity().replaceAll("[^0-9]", ""));
//            String coffeecups = String.valueOf(cups);
//            order_editor.putString("coffeecups", coffeecups);
//            order_editor.commit();
//        }

//        String coffeecups = order.getString("coffeecups", "");
//        if(!coffeecups.isEmpty()){
//            int cups = Integer.parseInt(coffeecups);
//            if(cups>1) {
////                while (cups > count) {
////                    count += 1;
//                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                    builder.setTitle("領取咖啡及放置杯子");  //設置標題
//                    builder.setIcon(R.mipmap.ic_launcher_round); //標題前面那個小圖示
//                    builder.setMessage("第1杯是否製作完成以及放好第2杯杯子? "); //提示訊息
//                while (cups > count) {
//                    count += 1;
//                    builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            count += 1;
//                            builder.setMessage("杯是否製作完成以及放好第杯杯子?");
//                            Toast.makeText(getApplicationContext(), ""+count,Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                    builder.create().show();
//                }
////                builder.create().show();
//            }
//        }

        ArrayList<ShopDetail> arrayList = new ArrayList<>();

        for (int i = 0; i < shoppingList.size(); i++) {
            Shopping shopping = shoppingList.get(i);
            String name = shopping.getName();
            String tempNsweet = "(" + shopping.getTemperature() + "/" + shopping.getSweet() + ")";

            int quantityInt = Integer.parseInt(shopping.getQuantity());
//            String total = shopping.getPrice();

            String totalPrice = "";
            Fragment_list list = new Fragment_list();
            for (Coffee coffee : list.getCoffeeData()) {
                if (coffee.getName().equals(shopping.getName())) {
                    String eachPrice = coffee.getDollars();
                    int price = Integer.parseInt(eachPrice.replaceAll("[^0-9]", ""));
                    totalPrice = calculatePrice(price, quantityInt);
                    shopping.setPrice(totalPrice);
                }
            }
            String quantity = "*" + shopping.getQuantity();
            String total = shopping.getPrice() + "元";
            arrayList.add(new ShopDetail(name, tempNsweet, quantity, total));
        }

        ShopDetailAdapter shopDetailAdapter = new ShopDetailAdapter(getApplicationContext(), R.layout.list_shopdetail, arrayList);
        listView.setAdapter(shopDetailAdapter);

        for (int i = 0; i < arrayList.size(); i++) {
            cup += Integer.parseInt(arrayList.get(i).getQuantity().replaceAll("[^0-9]", ""));
//            sum += Integer.parseInt(arrayList.get(i).getTotal().replaceAll("[^0-9]", ""));
        }

        String discount_value = order.getString("discount","");
//        String discount = "";
        if (discount_value.equals("0.8")) {
            discount="80%折價券";
            for (int i = 0; i < shoppingList.size(); i++) {
                sum += Integer.parseInt(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));
            }
            BigDecimal discount_scale = new BigDecimal("0.8");
            sum = BigDecimal.valueOf(sum).multiply(discount_scale).intValueExact();
        } else {
            order_editor.putString("discount", "");
            order_editor.commit();
            for (int i = 0; i < shoppingList.size(); i++) {
                sum += Double.parseDouble(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));
            }
        }
        sum = Math.round(sum);
        tv_discount.setText(discount);
        tv_total.setText(sum + "元");

        String totalcups = String.valueOf(cup);
//        String discount = "";
        String totalsum = String.valueOf(sum);

        if(!arrayList.isEmpty()) {
            String firstproduct = arrayList.get(0).getName();


            cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
            String c_id = cid.getString("c_id", "");

            Handler handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[5];
                    field[0] = "c_id";
                    field[1] = "totalcups";
                    field[2] = "discount";
                    field[3] = "totalprice";
                    field[4] = "firstproduct";
                    String[] data = new String[5];
                    data[0] = c_id;
                    data[1] = totalcups;
                    data[2] = discount;
                    data[3] = totalsum;
                    data[4] = firstproduct;

                    PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/addOrderrecord.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();

                            String[] parts = result.split(",");
                            String tf = parts[0];
                            String o_id = parts[1];
                            if (tf.equals("true")) {
                                Toast.makeText(Activity_shopdetail.this, o_id, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Activity_shopdetail.this, result, Toast.LENGTH_SHORT).show();
                            }


                            for (int i = 0; i < shoppingList.size(); i++) {
                                Shopping shopping = shoppingList.get(i);
                                String cups = shopping.getQuantity();

                                int quantityInt = Integer.parseInt(shopping.getQuantity());
                                String totalPrice = "";
                                Fragment_list list = new Fragment_list();
                                for (Coffee coffee : list.getCoffeeData()) {
                                    if (coffee.getName().equals(shopping.getName())) {
                                        String eachPrice = coffee.getDollars();
                                        int price = Integer.parseInt(eachPrice.replaceAll("[^0-9]", ""));
                                        totalPrice = calculatePrice(price, quantityInt);
                                        shopping.setPrice(totalPrice);
                                    }
                                }
                                String price = shopping.getPrice();
                                String product = shopping.getName();
                                String temperature = shopping.getTemperature();
                                String sweet = shopping.getSweet();

                                String[] field_detail = new String[4];
                                field_detail[0] = "o_id";
                                field_detail[1] = "p_id";
                                field_detail[2] = "cups";
                                field_detail[3] = "price";
                                String[] data_detail = new String[4];
                                data_detail[0] = o_id;
                                data_detail[1] = searchCPid(product, temperature, sweet); //pid 不能重複，pid = { p101, p001} searchPid(product)
                                data_detail[2] = cups;
                                data_detail[3] = price;

                                PutData putData_detail = new PutData("http://"+lib.ipAddress+"/coffee/addSaledetail.php", "POST", field_detail, data_detail);
                                if (putData_detail.startPut()) {
                                    if (putData_detail.onComplete()) {
                                        String result_detail = putData_detail.getResult();

//                                    String[] parts = result.split(",");
//                                    String tf = parts[0];
//                                    String o_id = parts[1];

                                        if (result_detail.equals("true")) {
                                            Toast.makeText(Activity_shopdetail.this, "777777", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(Activity_shopdetail.this, result_detail, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }

                        }
                    }

                    //o_id shoppingList (可能需要temp/sweet欄位) (單價圖片抓的到)
                    //addSaledetailP (o_id, p_id, cups, price)
                }
            });
        }
        String putJson = gson.toJson(arrayList);
        record_editor.putString("recordObj", putJson);
        record_editor.commit();

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cart_editor.clear().commit();
//                order_editor.putString("count", "0");
//                order_editor.commit();
                order_editor.clear().commit();
                order_editor.putInt("count", 0);
                order_editor.commit();
                startActivity(new Intent(Activity_shopdetail.this, MainActivity.class));
            }
        });
    }
    private String searchCPid(String productName, String temperature, String sweet){
        String cpid="CP";
        switch (productName){
            case "黑咖啡":
                cpid=cpid+0;
                break;
            case "拿鐵":
                cpid=cpid+1;
                break;
            default:
                break;
        }

        switch (temperature) {
            case "熱":
                cpid = cpid + 0;
                break;
            case "正常":
                cpid = cpid + 1;
                break;
            case "少冰":
                cpid = cpid + 2;
                break;
            case "微冰":
                cpid = cpid + 3;
                break;
            case "去冰":
                cpid = cpid + 4;
                break;
            default:
                break;
        }

        switch (sweet) {
            case "無糖":
                cpid = cpid + 0;
                break;
            case "一分糖":
                cpid = cpid + 1;
                break;
            case "二分糖":
                cpid = cpid + 2;
                break;
            case "三分糖":
                cpid = cpid + 3;
                break;
            case "四分糖":
                cpid = cpid + 4;
                break;
            case "五分糖":
                cpid = cpid + 5;
                break;
            case "六分糖":
                cpid = cpid + 6;
                break;
            case "七分糖":
                cpid = cpid + 7;
                break;
            case "八分糖":
                cpid = cpid + 8;
                break;
            case "九分糖":
                cpid = cpid + 9;
                break;
            case "全糖":
                cpid = cpid + "A";
                break;
            default:
                break;
        }
        return cpid;
    }

    private String searchPid(String productName){
        String pid="";
        switch (productName){
            case "欸黑咖啡":
                pid="P010";
                break;
            case "耶黑咖啡":
                pid="P110";
                break;
            case "想要拿鐵":
                pid="P210";
                break;
            case "不要拿鐵":
                pid="P310";
                break;
            case "給我拿鐵":
                pid="P410";
                break;
            default:
                break;
        }
        return pid;
    }

    private static String calculatePrice(int price, int quantity) {
        String result = "";
        result = Integer.toString(price * quantity);
        return result;
    }
}