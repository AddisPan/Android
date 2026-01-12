package com.example.iot_cbw_coffeemachine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.format.Time;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
//import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
//import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
//import com.smarteist.autoimageslider.SliderAnimations;
//import com.smarteist.autoimageslider.SliderView;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Fragment_information extends Fragment implements CoffeeAdapter.OnCoffeeListener {

    private Toolbar toolbar;
    ImageView iv_recommend, iv_new, iv_recommendTempIcon, iv_recommendSweetIcon, iv_newTempIcon, iv_newSweetIcon;
    TextView tv_recommendPro, tv_recommendTemp, tv_recommendSweet, tv_recommendPrice, tv_newPro, tv_newTemp, tv_newSweet, tv_newPrice;
    private SharedPreferences cid, rec, order;
    private SharedPreferences.Editor cid_editor, rec_editor, order_editor;

    //list
    RecyclerView rev_list;
    private ArrayAdapter<Coffee> adapter;
    private ArrayList<Coffee> coffeeData = new ArrayList<>();
    private ArrayList<Coffee> coffeeData_db = new ArrayList<>();
    private ArrayList<Coffee> coffeeFilter = new ArrayList<>();
    private int count = 0;

    //dialog
    Dialog dialog;
    String favorite = "";

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress = "192.168.50.204";
    ImageView gif_good;

    Universal_lib lib = new Universal_lib();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information, container, false);
        toolbar = view.findViewById(R.id.mtoolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        iv_recommend = view.findViewById(R.id.iv_recommend);
        iv_new = view.findViewById(R.id.iv_new);

        tv_recommendPro = view.findViewById(R.id.recommendProduct);
        tv_recommendTemp = view.findViewById(R.id.recommendTemp);
        tv_recommendSweet = view.findViewById(R.id.recommendSweet);
        tv_recommendPrice = view.findViewById(R.id.recommendPrice);
        iv_recommendTempIcon = view.findViewById(R.id.recommendTempIcon);
        iv_recommendSweetIcon = view.findViewById(R.id.recommendSweetIcon);
        iv_newTempIcon = view.findViewById(R.id.newTempIcon);
        iv_newSweetIcon = view.findViewById(R.id.newSweetIcon);

        tv_newPro = view.findViewById(R.id.newProduct);
        tv_newTemp = view.findViewById(R.id.newTemp);
        tv_newSweet = view.findViewById(R.id.newSweet);
        tv_newPrice = view.findViewById(R.id.newPrice);

        cid = getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        String preferTime = searchPreferTime();
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[2];
                field[0] = "c_id";
                field[1] = "time";
                String[] data = new String[2];
                data[0] = c_id;
                data[1] = preferTime;
                PutData putData = new PutData("http://" + lib.ipAddress + "/coffee/algorithm.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
//                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

                        //c006,1,4,11,2
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas == 4) {
                                count += 1;
                                String[] parts = result.split(",", 5);
                                String a = parts[0]; //cid
                                String b = parts[1]; //favorite
                                String c = parts[2]; //sweet
                                String d = parts[3]; //preferred_time
                                String e = parts[4]; //coldhot
                                String f = "";
                                result = f;

                                if (b.equals("0")) {
                                    favorite = "黑咖啡";
                                    String sweet = searchSweet(c);
                                    String temprature = searchTemperature(e);
                                    for (Coffee coffee : getCoffeeData()) {
                                        if (coffee.getName().equals(favorite)) { //coffee.getName() == product (same)
                                            int image = coffee.getImage(); //original int
                                            String price = coffee.getDollars();
                                            iv_recommend.setImageResource(image);
                                            iv_recommend.setTag(image);
                                            tv_recommendPro.setText(favorite);
                                            tv_recommendTemp.setText(temprature);
                                            tv_recommendSweet.setText(sweet);
                                            tv_recommendPrice.setText(price);
                                            iv_recommendTempIcon.setImageResource(lib.changeTempImage(tv_recommendTemp.getText().toString()));
                                            iv_recommendSweetIcon.setImageResource(lib.changeSweetImage(tv_recommendSweet.getText().toString()));
                                        }
                                    }
                                } else {
                                    favorite = "拿鐵";
                                    String sweet = searchSweet(c);
                                    String temprature = searchTemperature(e);
                                    for (Coffee coffee : getCoffeeData()) {
                                        if (coffee.getName().equals(favorite)) { //coffee.getName() == product (same)
                                            int image = coffee.getImage(); //original int
                                            String price = coffee.getDollars();
                                            iv_recommend.setImageResource(image);
                                            iv_recommend.setTag(image);
                                            tv_recommendPro.setText(favorite);
                                            tv_recommendTemp.setText(temprature);
                                            tv_recommendSweet.setText(sweet);
                                            tv_recommendPrice.setText(price);
                                            iv_recommendTempIcon.setImageResource(lib.changeTempImage(tv_recommendTemp.getText().toString()));
                                            iv_recommendSweetIcon.setImageResource(lib.changeSweetImage(tv_recommendSweet.getText().toString()));
                                        }
                                    }
                                }
                            } else {
                                Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            }
        });

        tv_newPro.setText("黑咖啡");
        tv_newTemp.setText("微冰");
        tv_newSweet.setText("三分糖");
        tv_newPrice.setText("50元");
        iv_new.setImageResource(R.drawable.coffee5);
        iv_new.setTag(R.drawable.coffee5);

        iv_newTempIcon.setImageResource(lib.changeTempImage(tv_newTemp.getText().toString()));
        iv_newSweetIcon.setImageResource(lib.changeSweetImage(tv_newSweet.getText().toString()));

        dialog = new Dialog(getActivity());
        rec = getActivity().getSharedPreferences("rec", Context.MODE_PRIVATE);
        rec_editor = rec.edit();
//        String uri_yes = rec.getString("uri", "");
        String recommend = rec.getString("recommend", "");
        Uri uri = getActivity().getIntent().getData();


        if (uri != null && recommend.isEmpty()) {
//            Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
//            Log.d("TAG", "uri?????????????? " + uri);
//            rec_editor.putString("uri", "true");
            rec_editor.putString("qrcode", "true");
            rec_editor.commit();
            openRecommendDialog();
            Toast.makeText(getActivity(), uri.toString(), Toast.LENGTH_SHORT).show();
        } else if (recommend.equals("true")) {
//            openRecommendDialog_test();
        } else {
            new AlertDialog.Builder(getActivity())
                    .setTitle("掃描QRCode")
                    .setMessage("請至現場掃描QRCode登入")
                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

//                            android.os.Process.killProcess(android.os.Process.myPid());
//                            System.exit(0);

//                                System.exit(0);
//                                Intent intent = new Intent(getActivity(), MainActivity.class);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.putExtra("LOGOUT", true);
//                                startActivity(intent);

                            getActivity().finish();
                        }
                    })
                    .show();
        }
//        rec_editor.putString("recommend", "true");
//        rec_editor.commit();


        iv_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Integer image = (Integer) iv_recommend.getTag();
                bundle.putInt("image", image);
                bundle.putString("product", tv_recommendPro.getText().toString());
                bundle.putString("temperature", tv_recommendTemp.getText().toString());
                bundle.putString("sweet", tv_recommendSweet.getText().toString());
                bundle.putString("price", tv_recommendPrice.getText().toString());

                Fragment_order fragment_order = new Fragment_order();
                fragment_order.setArguments(bundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
                frgTransaction.replace(R.id.fragment_container, fragment_order).commit();
            }
        });

        iv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Integer image = (Integer) iv_new.getTag();
                bundle.putInt("image", image);
                bundle.putString("product", tv_newPro.getText().toString());
                bundle.putString("temperature", tv_newTemp.getText().toString());
                bundle.putString("sweet", tv_newSweet.getText().toString());
                bundle.putString("price", tv_newPrice.getText().toString());

                Fragment_order fragment_order = new Fragment_order();
                fragment_order.setArguments(bundle);
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
                frgTransaction.replace(R.id.fragment_container, fragment_order).commit();
            }
        });

        //list
        rev_list = view.findViewById(R.id.rev_list);

        CoffeeAdapter coffeeAdapter = new CoffeeAdapter(view.getContext(), coffeeData_db, this); //getCoffeeData()
        rev_list.setAdapter(coffeeAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext());
        rev_list.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL);
        rev_list.addItemDecoration(itemDecoration);

        String[] pid = {"P010", "P110"};
        for (String p_id : pid) {
            handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[1];
                    field[0] = "p_id";
                    String[] data = new String[1];
                    data[0] = p_id;
                    PutData putData = new PutData("http://" + lib.ipAddress + "/coffee/searchProduct.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            while (!result.isEmpty()) {
                                int commas = 0;
                                for (int i = 0; i < result.length(); i++) {
                                    if (result.charAt(i) == ',') commas++;
                                }
                                if (commas == 3) {
                                    count += 1;
                                    String[] parts = result.split(",", 4);
                                    String a = parts[0]; //pid
                                    String b = parts[1]; //product
                                    String c = parts[2]; //pic
                                    String d = parts[3]; //price
                                    String e = "";
                                    result = e;

                                    switch (a) {
                                        case "P010":
                                            coffeeData_db.add(new Coffee(count - 1, b, d+"元", R.drawable.coffee5, 1, "哥倫比亞","100大卡","100毫克"));
                                            break;
                                        case "P110":
                                            coffeeData_db.add(new Coffee(count - 1, b, d+"元", R.drawable.coffee2, 2, "哥倫比亞","100大卡","100毫克"));
                                            break;
//                                        case "P210":
//                                            coffeeData.add(new Coffee(count - 1, b, d, R.drawable.coffee1, 2, "哥倫比亞","100大卡","100毫克"));
//                                            break;
//                                        case "P310":
//                                            coffeeData.add(new Coffee(count - 1, b, d, R.drawable.coffee2, 2, "哥倫比亞","100大卡","100毫克"));
//                                            break;
//                                        case "P410":
//                                            coffeeData.add(new Coffee(count - 1, b, d, R.drawable.coffee3, 2, "哥倫比亞","100大卡","100毫克"));
//                                            break;
                                        default:
                                            break;
                                    }
                                    coffeeAdapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            });
        }

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_information, container, false);
        return view;
    }

    private String searchTemperature(String temperature) {
        switch (temperature) {
            case "0":
                temperature = "熱";
                break;
            case "1":
                temperature = "正常";
                break;
            case "2":
                temperature = "少冰";
                break;
            case "3":
                temperature = "微冰";
                break;
            case "4":
                temperature = "去冰";
                break;
        }
        return temperature;
    }

    private String searchSweet(String sweet) {
        switch (sweet) {
            case "0":
                sweet = "無糖";
                break;
            case "1":
                sweet = "一分糖";
                break;
            case "2":
                sweet = "二分糖";
                break;
            case "3":
                sweet = "三分糖";
                break;
            case "4":
                sweet = "四分糖";
                break;
            case "5":
                sweet = "五分糖";
                break;
            case "6":
                sweet = "六分糖";
                break;
            case "7":
                sweet = "七分糖";
                break;
            case "8":
                sweet = "八分糖";
                break;
            case "9":
                sweet = "九分糖";
                break;
            case "A":
                sweet = "全糖";
                break;
        }
        return sweet;
    }

    private String searchPreferTime() {
        String dateformat = "MM,HH"; //日期的格式(第二個)
        Calendar mCal = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateformat);
        String today = df.format(mCal.getTime());
        String[] time = today.split(",", 2);
        String mm = time[0];
        String hh = time[1];
        int month = Integer.parseInt(mm);
        int hour = Integer.parseInt(hh);

        String preferTime = "0";
        if (month <= 3) {
            if (9 <= hour && hour < 12) {
                preferTime = "0";
            } else if (12 <= hour && hour < 18) {
                preferTime = "1";
            } else if (18 <= hour && hour < 22) {
                preferTime = "2";
            }
        } else if (3 < month && month <= 6) {
            if (9 <= hour && hour < 12) {
                preferTime = "3";
            } else if (12 <= hour && hour < 18) {
                preferTime = "4";
            } else if (18 <= hour && hour < 22) {
                preferTime = "5";
            }
        } else if (6 < month && month <= 9) {
            if (9 <= hour && hour < 12) {
                preferTime = "6";
            } else if (12 <= hour && hour < 18) {
                preferTime = "7";
            } else if (18 <= hour && hour < 22) {
                preferTime = "8";
            }
        } else if (9 < month && month <= 12) {
            if (9 <= hour && hour < 12) {
                preferTime = "9";
            } else if (12 <= hour && hour < 18) {
                preferTime = "10";
            } else if (18 <= hour && hour < 22) {
                preferTime = "11";
            }
        }

        return preferTime;
    }

    public void openRecommendDialog() {
        dialog.setContentView(R.layout.recommend_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        ImageView iv_coffee = dialog.findViewById(R.id.iv_coffee);
        TextView tv_greeting = dialog.findViewById(R.id.greeting);
        TextView tv_dialogPro = dialog.findViewById(R.id.dialogProduct);
        TextView tv_dialogTemp = dialog.findViewById(R.id.dialogTemp);
        TextView tv_dialogSweet = dialog.findViewById(R.id.dialogSweet);
        TextView tv_dialogPrice = dialog.findViewById(R.id.dialogPrice);
        Spinner sp_dialogQuantity = dialog.findViewById(R.id.dialogQuantity);
        Button btn_send = dialog.findViewById(R.id.btn_send);

        rec = getActivity().getSharedPreferences("rec", Context.MODE_PRIVATE);
        rec_editor = rec.edit();
        cid = getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "c_id";
                String[] data = new String[1];
                data[0] = c_id;
                PutData putData = new PutData("http://" + lib.ipAddress + "/coffee/searchPersonal.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas == 9) {
                                String[] parts = result.split(",", 10);
                                String a = parts[0]; //c_id
                                String b = parts[1]; //name
                                String c = parts[2]; //phone
                                String d = parts[3]; //email
                                String e = parts[4]; //bluetooth
                                String f = parts[5]; //password maybe
                                String g = parts[6]; //nickname
                                String h = parts[7]; //gender
                                String i = parts[8]; //birth
                                String j = parts[9]; //allergen
                                String k = "";
                                result = k;

                                if (h.equals("0")) {
                                    tv_greeting.setText(b + "先生您好，\n來一杯為您推薦的專屬咖啡?");
                                } else {
                                    tv_greeting.setText(b + "小姐您好，\n來一杯為您推薦的專屬咖啡?");
                                }
                            }
                        }
                    }
                }
            }
        });

        String preferTime = searchPreferTime();
        int time = Integer.parseInt(preferTime);
        if (9 <= time && time < 22) {
            handler = new Handler();
            handler.post(new Runnable() {
                @Override
                public void run() {
                    String[] field = new String[2];
                    field[0] = "c_id";
                    field[1] = "time";
                    String[] data = new String[2];
                    data[0] = c_id;
                    data[1] = preferTime;
                    PutData putData = new PutData("http://" + lib.ipAddress + "/coffee/algorithm.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
//                            Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();

                            //c006,1,4,11,2
                            while (!result.isEmpty()) {
                                int commas = 0;
                                for (int i = 0; i < result.length(); i++) {
                                    if (result.charAt(i) == ',') commas++;
                                }
                                if (commas == 4) {
                                    count += 1;
                                    String[] parts = result.split(",", 5);
                                    String a = parts[0]; //cid
                                    String b = parts[1]; //favorite
                                    String c = parts[2]; //sweet
                                    String d = parts[3]; //preferred_time
                                    String e = parts[4]; //coldhot
                                    String f = "";
                                    result = f;

                                    if (b.equals("0")) {
                                        favorite = "黑咖啡";
                                        String sweet = searchSweet(c);
                                        String temprature = searchTemperature(e);
                                        for (Coffee coffee : getCoffeeData()) {
                                            if (coffee.getName().equals(favorite)) { //coffee.getName() == product (same)
                                                int image = coffee.getImage(); //original int
                                                String price = coffee.getDollars();
                                                iv_coffee.setImageResource(image);
                                                tv_dialogPro.setText(favorite);
                                                tv_dialogTemp.setText(temprature);
                                                tv_dialogSweet.setText(sweet);
                                                tv_dialogPrice.setText(price);
//                                                gif_good.setImageResource(R.drawable.mark_good);
//                                                Glide.with(getActivity()).load(R.drawable.mark_good).into(gif_good);
//                                                rec_editor.putString("favorite", favorite);
//                                                rec_editor.commit();
                                            }
                                        }
                                    } else {
                                        favorite = "拿鐵";
                                        String sweet = searchSweet(c);
                                        String temprature = searchTemperature(e);
                                        for (Coffee coffee : getCoffeeData()) {
                                            if (coffee.getName().equals(favorite)) { //coffee.getName() == product (same)
                                                int image = coffee.getImage(); //original int
                                                String price = coffee.getDollars();
                                                iv_coffee.setImageResource(image);
                                                tv_dialogPro.setText(favorite);
                                                tv_dialogTemp.setText(temprature);
                                                tv_dialogSweet.setText(sweet);
                                                tv_dialogPrice.setText(price);
//                                                gif_good.setImageResource(R.drawable.mark_good);
//                                                Glide.with(getActivity()).load(R.drawable.mark_good).into(gif_good);
//                                                rec_editor.putString("favorite", favorite);
//                                                rec_editor.commit();
                                            }
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            });
        }
//        tv_dialogPro.setText("想要拿鐵");
//        tv_dialogTemp.setText("正常");
//        tv_dialogSweet.setText("半糖");
//        tv_dialogPrice.setText("70元");
//
//        for (Coffee coffee : getCoffeeData()) {
//            if (coffee.getName().equals("想要拿鐵")) { //coffee.getName() == product (same)
//                int image = coffee.getImage(); //original int
//                iv_coffee.setImageResource(image);
//            }
//        }

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                rec_editor.putString("recommend", "true");
                rec_editor.commit();
//                Toast.makeText(getActivity(),"close",Toast.LENGTH_SHORT).show();
            }
        });

//        ArrayList<String> quantityList = new ArrayList<>();
//        quantityList.add("1");
//        quantityList.add("2");
//        quantityList.add("3");
//        quantityList.add("4");
//        quantityList.add("5");
//        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, quantityList);
//        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        holder.quantity.setSelection(quantityAdapter.getPosition("4"));
//        sp_dialogQuantity.setAdapter(quantityAdapter);

        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getContext(), R.array.quantity_value, R.layout.spinner_text);
        sp_dialogQuantity.setAdapter(quantityAdapter);

        sp_dialogQuantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();
                int quantity = Integer.parseInt(result);
//                String price = tv_dialogPrice.getText().toString();
//                int eachprice = Integer.parseInt(price.replaceAll("[^0-9]", ""));
//                String total = String.valueOf(eachprice * quantity);
//                tv_dialogPrice.setText(total+"元");

                for (Coffee coffee : getCoffeeData()) {
                    if (coffee.getName().equals(favorite)) {
                        String eachPrice = coffee.getDollars();
                        int price = Integer.parseInt(eachPrice.replaceAll("[^0-9]", ""));
                        String total = String.valueOf(price * quantity);
                        tv_dialogPrice.setText(total);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String product = tv_dialogPro.getText().toString();
                String temperature = tv_dialogTemp.getText().toString();
                String sweet = tv_dialogSweet.getText().toString();
                String quantity = sp_dialogQuantity.getSelectedItem().toString();
                String price = tv_dialogPrice.getText().toString();

                ArrayList<Shopping> shoppingList = new ArrayList<>();
                order = getActivity().getSharedPreferences("order", Context.MODE_PRIVATE);
                order_editor = order.edit();

                for (Coffee coffee : getCoffeeData()) {
                    if (coffee.getName().equals(favorite)) { //coffee.getName() == product (same)
                        int image = coffee.getImage(); //original int
                        shoppingList.add(new Shopping(0, image, product, 0, temperature, 0, sweet, price, quantity));
                    }
                }

                Gson gson = new Gson();
                String putJson = gson.toJson(shoppingList);
                order_editor.putString("shopObj", putJson);
                order_editor.commit();

                rec = getActivity().getSharedPreferences("rec", Context.MODE_PRIVATE);
                rec_editor = rec.edit();
                rec_editor.clear().commit();

                startActivity(new Intent(getActivity(), Activity_shopdetail.class)); //not attach

                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
        setIconsVisible(menu, true);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        Fragment_personal frg_personal=new Fragment_personal();
//        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//        FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.personal:
//                frgTransaction.replace( R.id.frg_information, frg_personal).addToBackStack(null).commit();
                startActivity(new Intent(getActivity(), Activity_personal.class));
                break;
            case R.id.record:
                startActivity(new Intent(getActivity(), Activity_record.class));
                break;
            case R.id.favorite:
                startActivity(new Intent(getActivity(), Activity_combination.class));
                break;
            case R.id.exit:
                new AlertDialog.Builder(getActivity())
                        .setTitle("登出")
                        .setMessage("確定要登出嗎?")
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cid = getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
                                cid_editor = cid.edit();
                                cid_editor.clear().commit();
                                rec = getActivity().getSharedPreferences("rec", Context.MODE_PRIVATE);
                                rec_editor = rec.edit();
                                rec_editor.clear().commit();
                                startActivity(new Intent(getActivity(), Login.class));
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void setIconsVisible(Menu menu, boolean flag) {
        //判斷menu是否為空
        if (menu != null) {
            try {
                //如果不為空,就反射拿到menu的setOptionalIconsVisible方法
                Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                //暴力訪問該方法
                method.setAccessible(true);
                //呼叫該方法顯示icon
                method.invoke(menu, flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Coffee> getCoffeeData() {
//        ArrayList<Coffee> test = new ArrayList<>();

        coffeeData.clear();
        coffeeData.add(new Coffee(0, "黑咖啡", "70元", R.drawable.coffee5, 1, "哥倫比亞", "100大卡", "100毫克"));
        coffeeData.add(new Coffee(0, "拿鐵", "90元", R.drawable.coffee2, 2, "哥倫比亞", "100大卡", "100毫克"));
//        coffeeData.add(new Coffee(0, "想要拿鐵", "70元", R.drawable.coffee1, 2, "哥倫比亞","100大卡","100毫克"));
//        coffeeData.add(new Coffee(0, "不要拿鐵", "80元", R.drawable.coffee2, 2, "哥倫比亞","100大卡","100毫克"));
//        coffeeData.add(new Coffee(0, "給我拿鐵", "70元", R.drawable.coffee3, 2, "哥倫比亞","100大卡","100毫克"));

//        coffeeData.add(new Coffee(0, "欸黑咖啡", "50元", R.drawable.coffee4, 1, "哥倫比亞","100大卡","100毫克"));
//        coffeeData.add(new Coffee(0, "耶黑咖啡", "65元", R.drawable.coffee5, 1, "哥倫比亞","100大卡","100毫克"));
//        coffeeData.add(new Coffee(0, "想要拿鐵", "70元", R.drawable.coffee1, 2, "哥倫比亞","100大卡","100毫克"));
//        coffeeData.add(new Coffee(0, "不要拿鐵", "80元", R.drawable.coffee2, 2, "哥倫比亞","100大卡","100毫克"));
//        coffeeData.add(new Coffee(0, "給我拿鐵", "70元", R.drawable.coffee3, 2, "哥倫比亞","100大卡","100毫克"));

//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                String[] field = new String[1];
//                field[0] = "p_id";
//                String[] data = new String[1];
//                data[0] = "P010";
//                PutData putData = new PutData("http://192.168.1.153/coffee/searchProduct.php", "POST", field, data);
//                if (putData.startPut()) {
//                    if (putData.onComplete()) {
//                        String result = putData.getResult();
////                        Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
////                        while (!result.isEmpty()) {
//                            int commas = 0;
//                            for (int i = 0; i < result.length(); i++) {
//                                if (result.charAt(i) == ',') commas++;
//                            }
//                            if (commas == 3) {
//                                String[] parts = result.split(",", 4);
//                                String a = parts[0]; //pid
//                                String b = parts[1]; //product
//                                String c = parts[2]; //pic
//                                String d = parts[3]; //price
//                                String e = "";
//                                result = e;
//                                coffeeData.add(new Coffee(b, d, R.drawable.coffee4, 1));
////                                coffeeAdapter.notifyDataSetChanged();
//                            }else{
//                                coffeeData.add(new Coffee("你要拿鐵","85元",R.drawable.coffeelist_3,2));
//                            }
////                        }
//                    }
//                }
//            }
//        });

        return coffeeData;
    }

    @Override
    public void OnCoffeeClick(int position) {
        Bundle bundle = new Bundle();
        Coffee coffee = coffeeData.get(position);
        bundle.putString("coffee", coffee.getName());

        Fragment_order fragment_order = new Fragment_order();
        fragment_order.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
        frgTransaction.replace(R.id.fragment_container, fragment_order).commit();
    }

}