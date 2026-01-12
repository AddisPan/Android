package com.example.iot_cbw_coffeemachine;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.nfc.Tag;
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

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Fragment_order extends Fragment implements MyExpandableListAdapter.OnAddButtonClickListener, ShoppingAdapter.OnShoppingListener {
    private TextView tv_coffee, tv_tempResult, tv_sweetResult;
    private Spinner sp_quantity; //sp_temperature, sp_sweet
    private Button btn_add;
    private SharedPreferences order, cid, rec;
    private SharedPreferences.Editor order_editor, rec_editor;
    int count = 0;
    private Context mContext;
    ShoppingAdapter.OnShoppingListener shoppingListener;
    private Toolbar toolbar;

    //0822 expandable listview
    ArrayList<String> groupList = new ArrayList<>();
    ArrayList<Combination> childList;
    Map<String, ArrayList<Combination>> favoriteCollection = new HashMap<String, ArrayList<Combination>>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    ArrayList<Shopping> shoppingList;
    ShoppingAdapter shoppingAdapter;
    TextView tv_price;
    Button btnSend;
    Spinner spinner_discount;
    ScrollView scrollView;
    LinearLayout linearLayout;

    SeekBar seekbar_temp, seekbar_sweet;

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress = "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order, container, false);

        tv_coffee = view.findViewById(R.id.tv_coffee);
//        sp_temperature = view.findViewById(R.id.spinner_temp);
//        sp_sweet = view.findViewById(R.id.spinner_sweet);
        sp_quantity = view.findViewById(R.id.spinner_quantity);
        btn_add = view.findViewById(R.id.btn_add);
        seekbar_temp = view.findViewById(R.id.seekbar_temp);
        seekbar_sweet = view.findViewById(R.id.seekbar_sweet);
        tv_tempResult = view.findViewById(R.id.tv_tempResult);
        tv_sweetResult = view.findViewById(R.id.tv_sweetResult);

        toolbar = view.findViewById(R.id.mtoolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        //0827
        tv_price = view.findViewById(R.id.tv_price);
        btnSend = view.findViewById(R.id.btn_send);
        spinner_discount = view.findViewById(R.id.spinner_discount);

        scrollView = view.findViewById(R.id.scrollView);
        linearLayout = view.findViewById(R.id.linearLayout);

        //1023
        order = getContext().getSharedPreferences("order", Context.MODE_PRIVATE);
        order_editor = order.edit();
        seekbar_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        tv_tempResult.setText("熱");
                        break;
                    case 1:
                        tv_tempResult.setText("正常");
                        break;
                    case 2:
                        tv_tempResult.setText("少冰");
                        break;
                    case 3:
                        tv_tempResult.setText("微冰");
                        break;
                    case 4:
                        tv_tempResult.setText("去冰");
                        break;
                    default:
                        break;
                }
                order_editor.putInt("temperature", progress);
                order_editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar_sweet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        tv_sweetResult.setText("無糖");
                        break;
                    case 1:
                        tv_sweetResult.setText("一分糖");
                        break;
                    case 2:
                        tv_sweetResult.setText("二分糖");
                        break;
                    case 3:
                        tv_sweetResult.setText("三分糖");
                        break;
                    case 4:
                        tv_sweetResult.setText("四分糖");
                        break;
                    case 5:
                        tv_sweetResult.setText("五分糖");
                        break;
                    case 6:
                        tv_sweetResult.setText("六分糖");
                        break;
                    case 7:
                        tv_sweetResult.setText("七分糖");
                        break;
                    case 8:
                        tv_sweetResult.setText("八分糖");
                        break;
                    case 9:
                        tv_sweetResult.setText("九分糖");
                        break;
                    case 10:
                        tv_sweetResult.setText("全糖");
                        break;
                    default:
                        break;
                }
                order_editor.putInt("sweet", progress);
                order_editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        //0822 expandable listview
//        groupList.add("我的最愛");
//        cid = getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
//        String c_id = cid.getString("c_id", "");
//
//        childList = new ArrayList<>();
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                int combineCount = 0;
//                String[] field = new String[1];
//                field[0] = "c_id";
//                String[] data = new String[1];
//                data[0] = c_id;
//                //192.168.50.204
//                PutData putData = new PutData("http://192.168.1.153/coffee/searchCustomer.php", "POST", field, data);
//                if (putData.startPut()) {
//                    if (putData.onComplete()) {
//                        String result = putData.getResult();
//                        while (!result.isEmpty()) {
//                            int commas = 0;
//                            for (int i = 0; i < result.length(); i++) {
//                                if (result.charAt(i) == ',') commas++;
//                            }
//                            if (commas >= 6) {
//                                String[] parts = result.split(",", 7);
//                                String a = parts[0]; //cp_id
//                                String b = parts[1]; //c_id
//                                String c = parts[2]; //p_id
//                                String d = parts[3]; //cp_name
//                                String e = parts[4]; //sweet
//                                String f = parts[5]; //coldhot
//                                String g = parts[6]; //remain
//
//                                f = searchTemp(f);
//                                e = searchSweet(e);
//
//                                childList.add(new Combination(combineCount, d, searchProduct(c), f, e, a));
//                                result = g;
//                                combineCount += 1;
//                            } else {
//                                String[] parts = result.split(",", 6);
//                                String a = parts[0];
//                                String b = parts[1];
//                                String c = parts[2];
//                                String d = parts[3];
//                                String e = parts[4];
//                                String f = parts[5];
//                                String g = "";
//
//                                f = searchTemp(f);
//                                e = searchSweet(e);
//
//                                childList.add(new Combination(combineCount, d, searchProduct(c), f, e, a));
//                                result = g;
//                                combineCount += 1;
//                            }
//                        }
//                    }
//                }
//            }
//        });
//
////        childList.add(new Combination(0, "測試一下", "xx咖啡", "正常", "無糖"));
//        favoriteCollection.put(groupList.get(0), childList);
//        expandableListView = view.findViewById(R.id.favoriteListView);
//        expandableListAdapter = new MyExpandableListAdapter(getActivity(), favoriteCollection, groupList, this);
//        expandableListView.setAdapter(expandableListAdapter);

//        order = getContext().getSharedPreferences("order", Context.MODE_PRIVATE);
//        order_editor = order.edit();
//        order_editor.clear().commit();


        shoppingList = new ArrayList<>();
        Gson gson = new Gson();
        String getJson = order.getString("shopObj", "");
        if (!getJson.isEmpty()) {
            Type type = new TypeToken<ArrayList<Shopping>>() {
            }.getType();
            shoppingList = gson.fromJson(getJson, type);
        }


        RecyclerView rev_shoppingcart = view.findViewById(R.id.rev_shoppingCart);
        LinearLayoutManager linearLayoutManager_shoppingcart = new LinearLayoutManager(getActivity());
        rev_shoppingcart.setLayoutManager(linearLayoutManager_shoppingcart);
        RecyclerView.ItemDecoration itemDecoration_shoppingcart = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rev_shoppingcart.addItemDecoration(itemDecoration_shoppingcart);

        shoppingAdapter = new ShoppingAdapter(getActivity(), shoppingList, this);
        rev_shoppingcart.setAdapter(shoppingAdapter);

//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                setListViewHeight(parent, groupPosition);
//                return false;
//            }
//        });

//        ArrayAdapter<CharSequence> sweetAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.sweet_value, R.layout.spinner_text);
//        sp_sweet.setAdapter(sweetAdapter);
//
//        ArrayAdapter<CharSequence> tempAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.temp4order_value, R.layout.spinner_text);
//        sp_temperature.setAdapter(tempAdapter);

        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.quantity_value, R.layout.spinner_text);
        sp_quantity.setAdapter(quantityAdapter);

        setPreference(); // 10/27

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String coffeeText = bundle.getString("coffee");
            if (coffeeText != null) {
                tv_coffee.setText(coffeeText);
                order_editor.putString("product", tv_coffee.getText().toString());
                order_editor.commit();
            }

            int image = bundle.getInt("image");
            String product = bundle.getString("product");
            String temperature = bundle.getString("temperature");
            String sweet = bundle.getString("sweet");
            String price = bundle.getString("price");

            if (image != 0 && product != null && temperature != null && sweet != null && price != null) {
                count = order.getInt("count", 0);
                shoppingList.add(new Shopping(count, image, product, lib.changeTempImage(temperature), temperature, lib.changeSweetImage(sweet), sweet, price, "*1"));
                shoppingAdapter.notifyDataSetChanged();

                count += 1;
                order_editor.putInt("count", count);
                order_editor.commit();
                Handler handler_scroll = new Handler();
                handler_scroll.postDelayed(runnable, 200);
            }

            String coffee_fav = bundle.getString("coffee_fav");
            String temperature_fav = bundle.getString("temperature_fav");
            String sweet_fav = bundle.getString("sweet_fav");

            if (coffee_fav != null && temperature_fav != null && sweet_fav != null) {
                Fragment_information information = new Fragment_information();
                for (Coffee coffeeObj : information.getCoffeeData()) {
                    if (coffeeObj.getName().equals(coffee_fav)) { //coffee.getName() == product (same)
                        int image_fav = coffeeObj.getImage(); //original int
                        String dollars_fav = coffeeObj.getDollars();
                        count = order.getInt("count", 0);
                        shoppingList.add(new Shopping(count, image_fav, coffee_fav, lib.changeTempImage(temperature_fav), temperature_fav, lib.changeSweetImage(sweet_fav), sweet_fav, dollars_fav, "*1"));
                        shoppingAdapter.notifyDataSetChanged();

                        count += 1;
                        order_editor.putInt("count", count);
                        order_editor.commit();
                        Handler handler_scroll = new Handler();
                        handler_scroll.postDelayed(runnable, 200);
                    }
                }
            }

//            scrollView.scrollTo(0, linearLayout.getMeasuredHeight() - scrollView.getHeight());
        }

//        sp_temperature.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView parent, View view, int position, long id) {
//                String result = parent.getItemAtPosition(position).toString();
//                order_editor.putString("temperature", result);
//                order_editor.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView parent) {
//
//            }
//        });
//
//        sp_sweet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView parent, View view, int position, long id) {
//                String result = parent.getItemAtPosition(position).toString();
//                order_editor.putString("sweet", result);
//                order_editor.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView parent) {
//
//            }
//        });

        sp_quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();
                order_editor.putString("quantity", result);
                order_editor.commit();
            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });

        tv_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment_infor = new Fragment_information();
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
                frgTransaction.replace(R.id.fragment_container, fragment_infor).commit();
            }
        });

        Fragment_information fragment_information = new Fragment_information();
//        cart = getContext().getSharedPreferences("cart", Context.MODE_PRIVATE);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                cart_editor = cart.edit();

                String product = tv_coffee.getText().toString();
//                String temperature = sp_temperature.getSelectedItem().toString();
//                String sweet = sp_sweet.getSelectedItem().toString();
                String temperature = tv_tempResult.getText().toString();
                String sweet = tv_sweetResult.getText().toString();
                String quantity = sp_quantity.getSelectedItem().toString();
                String tempNsweet = temperature + "/" + sweet;

//                for (Coffee coffee : list.getCoffeeData()) {
//                    if (coffee.getName().equals(product)) { //coffee.getName() == product (same)
//                        int image = coffee.getImage(); //original int
//                        String dollars = coffee.getDollars();
//
//                        Gson gson = new Gson();
//                        String getJson = cart.getString("cartObj", "");
//                        if (!getJson.isEmpty()) {
//                            Type type = new TypeToken<ArrayList<Shopping>>() {
//                            }.getType();
//                            shoppingList = gson.fromJson(getJson, type);
//                            shoppingList.add(new Shopping(image, product, temperature, sweet, dollars, quantity));
//                        } else {
//                            shoppingList.add(new Shopping(image, product, temperature, sweet, dollars, quantity));
//                        }
//                        String putJson = gson.toJson(shoppingList);
//                        cart_editor.putString("cartObj", putJson);
//                        cart_editor.commit();
//                    }
//                }
//
//                Toast.makeText(getActivity(), "加入成功!", Toast.LENGTH_SHORT).show();
//                //有implements shoppinglitsener 稍後處理
//                ShoppingAdapter shopping = new ShoppingAdapter(mContext, shoppingList, shoppingListener);
//                count = shopping.getItemCount();
//                tv_quantityInCart.setText("目前已有" + count + "項在購物車內!");
//                order_editor.putString("count", String.valueOf(count));
//                order_editor.commit();
////                Gson gson = new Gson();
////                String json = gson.toJson(shoppingList);
////                order_editor.putString("orderObj", json);
////                order_editor.commit();

                count = order.getInt("count", 0);
                Gson gson = new Gson();

//                //0825
//                Gson gson = new Gson();
//                String getJson = order.getString("editObj", "");
//                if (!getJson.isEmpty()) {
//                    Type type = new TypeToken<ArrayList<Edit>>() {
//                    }.getType();
//                    editList = gson.fromJson(getJson, type);
//                    editList.add(new Edit(count, product, tempNsweet, "*"+quantity));
//                    editAdapter.notifyDataSetChanged();
//                } else {
//                    editList.add(new Edit(count, product, tempNsweet, "*"+quantity));
//                    editAdapter.notifyDataSetChanged();
//                }

                // 0827 commend
//                editList.add(new Edit(count, product, tempNsweet, "*" + quantity));
//                editAdapter.notifyDataSetChanged();

                for (Coffee coffee : fragment_information.getCoffeeData()) {
                    if (coffee.getName().equals(product)) { //coffee.getName() == product (same)
                        int image = coffee.getImage(); //original int
                        String dollars = coffee.getDollars();
                        shoppingList.add(new Shopping(count, image, product, lib.changeTempImage(temperature), temperature, lib.changeSweetImage(sweet), sweet, dollars, quantity));
                        shoppingAdapter.notifyDataSetChanged();
                    }
                }

                count += 1;
                order_editor.putInt("count", count);
                order_editor.commit();

                String putJson = gson.toJson(shoppingList);
                order_editor.putString("shopObj", putJson);
                order_editor.commit();
            }
        });

        spinner_discount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();

                int sum = 0;
                if (result.equals("80%折價券")) {
                    order_editor.putString("discount", "0.8");
                    order_editor.commit();
                    for (int i = 0; i < shoppingList.size(); i++) {
                        sum += Integer.parseInt(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));
                    }
                    BigDecimal discount = new BigDecimal("0.8");
                    sum = BigDecimal.valueOf(sum).multiply(discount).intValueExact();
                } else {
                    order_editor.putString("discount", "");
                    order_editor.commit();
                    for (int i = 0; i < shoppingList.size(); i++) {
                        sum += Double.parseDouble(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));
                    }
                }
                sum = Math.round(sum);
                changeQuantity(sum);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                new AlertDialog.Builder(getActivity())
//                        .setTitle("訂單確認")
//                        .setMessage("確定要送出訂單嗎?")
//                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//
//                                Toast.makeText(getActivity(), "送出成功!", Toast.LENGTH_SHORT).show();
//
////                                cart_editor.clear().commit();
//                                startActivity(new Intent(getActivity(), Activity_shopdetail.class));
//                            }
//                        })
//                        .setNegativeButton("取消", null)
//                        .show();
                rec = getContext().getSharedPreferences("rec", Context.MODE_PRIVATE);
                rec_editor = rec.edit();
                String qrcode = rec.getString("qrcode", "");
                if (qrcode.equals("true")) {
                    rec_editor.clear().commit();
                    Toast.makeText(getActivity(), "送出成功!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), Activity_shopdetail.class));
                } else {
                    Toast.makeText(getActivity(), "請先掃描QRCode", Toast.LENGTH_SHORT).show();
                }

//                for (int i = 0; i < shoppingList.size(); i++) {
//                    int cups = 0;
//                    cups += Integer.parseInt(shoppingList.get(i).getQuantity().replaceAll("[^0-9]", ""));
//                    String coffeecups = String.valueOf(cups);
//                    order_editor.putString("coffeecups", "");
//                    order_editor.commit();
//                }
            }
        });

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_order, container, false);
        return view;
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();
                }
                //Add Divider Height
                totalHeight += listView.getDividerHeight() * (listAdapter.getChildrenCount(i) - 1);
            }
        }
        //Add Divider Height
        totalHeight += listView.getDividerHeight() * (listAdapter.getGroupCount() - 1);

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setPreference() {

        String product = order.getString("product", "尚未選擇飲品");
//        String temperature = order.getString("temperature", "熱");
//        String sweet = order.getString("sweet", "全糖");
        int temperature = order.getInt("temperature", 0);
        int sweet = order.getInt("sweet", 5);
        String quantity = order.getString("quantity", "1");
        tv_coffee.setText(product);
//        sp_temperature.setSelection(((ArrayAdapter) sp_temperature.getAdapter()).getPosition(temperature));
//        sp_sweet.setSelection(((ArrayAdapter) sp_sweet.getAdapter()).getPosition(sweet));
        seekbar_temp.setProgress(temperature);
        seekbar_sweet.setProgress(sweet);
        sp_quantity.setSelection(((ArrayAdapter) sp_quantity.getAdapter()).getPosition(quantity));
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
//            scrollView.scrollTo(0, 300);// 改變滾動條的位置
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
        }
    };

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("product", tv_coffee.getText().toString());
//        outState.putString("temperature", sp_temperature.getSelectedItem().toString());
//        outState.putString("sweet", sp_sweet.getSelectedItem().toString());
        outState.putString("quantity", sp_quantity.getSelectedItem().toString());
    }

    @Override
    public void addButtonClick(int position) {
//        childList = new ArrayList<>();
//        editList = new ArrayList<>();

//        Toast.makeText(getActivity(),"position: "+position, Toast.LENGTH_SHORT).show();
        Combination combination = childList.get(position);
        String coffee = combination.getCoffee();
        String temperature = combination.getTemperature();
        String sweet = combination.getSweet();
        String tempNsweet = temperature + "/" + sweet;
        count = order.getInt("count", 0);

        Gson gson = new Gson();
//        String getJson = order.getString("editObj", "");
//        if (!getJson.isEmpty()) {
//            Type type = new TypeToken<ArrayList<Edit>>() {
//            }.getType();
//            editList = gson.fromJson(getJson, type);
//            editList.add(new Edit(count, coffee, tempNsweet, "*1"));
//            editAdapter.notifyDataSetChanged();
//        } else {
//            editList.add(new Edit(count, coffee, tempNsweet, "*1"));
//            editAdapter.notifyDataSetChanged();
//        }

        //0827 commend
//        editList.add(new Edit(count, coffee, tempNsweet, "*1"));
//        editAdapter.notifyDataSetChanged();

        Fragment_information infor = new Fragment_information();
        for (Coffee coffeeObj : infor.getCoffeeData()) {
            if (coffeeObj.getName().equals(coffee)) { //coffee.getName() == product (same)
                int image = coffeeObj.getImage(); //original int
                String dollars = coffeeObj.getDollars();
                shoppingList.add(new Shopping(count, image, coffee, lib.changeTempImage(temperature), temperature, lib.changeSweetImage(sweet), sweet, dollars, "*1"));
                shoppingAdapter.notifyDataSetChanged();
            }
        }

        count += 1;
        order_editor.putInt("count", count);
        order_editor.commit();

        String putJson = gson.toJson(shoppingList);
        order_editor.putString("shopObj", putJson);
        order_editor.commit();
    }

    @Override
    public void changeQuantity(int total) {
        tv_price.setText(total + "元");
    }

}