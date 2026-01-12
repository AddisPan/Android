package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment_list extends Fragment implements CombineAdapter_add.OnAddButtonClickListener {
    private Spinner sp_product;
    private ListView listView;
    private Button btn_check;
    private ArrayAdapter<Coffee> adapter;
    private ArrayList<Coffee> coffeeData = new ArrayList<>();
    private ArrayList<Coffee> coffeeFilter = new ArrayList<>();
    private Toolbar toolbar;

    //expandable listview
    ArrayList<String> groupList = new ArrayList<>();
    ArrayList<Combination> childList;
    Map<String, ArrayList<Combination>> favoriteCollection = new HashMap<String, ArrayList<Combination>>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    int count = 0;
    private SharedPreferences order, cid;
    private SharedPreferences.Editor order_editor;
    ArrayList<Combination> arrayList;

    Universal_lib lib = new Universal_lib();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        toolbar = view.findViewById(R.id.mtoolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        RecyclerView rev_favorite = view.findViewById(R.id.rev_favorite);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rev_favorite.setLayoutManager(linearLayoutManager);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        rev_favorite.addItemDecoration(itemDecoration);

        arrayList = new ArrayList<>();
        CombineAdapter_add adapter = new CombineAdapter_add(getActivity(), arrayList, this);
        rev_favorite.setAdapter(adapter);

        cid = getActivity().getSharedPreferences("cid", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int combineCount = 0;
                String[] field = new String[1];
                field[0] = "c_id";
                String[] data = new String[1];
                data[0] = c_id;
                //192.168.50.204
                PutData putData = new PutData("http://" + lib.ipAddress + "/coffee/searchCustomer.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas >= 6) {
                                String[] parts = result.split(",", 7);
                                String a = parts[0]; //cp_id
                                String b = parts[1]; //c_id
                                String c = parts[2]; //p_id
                                String d = parts[3]; //cp_name
                                String e = parts[4]; //sweet
                                String f = parts[5]; //coldhot
                                String g = parts[6]; //remain

//                                f = searchTemp(f);
//                                e = searchSweet(e);

                                arrayList.add(new Combination(combineCount, d, searchProduct(c), lib.changeTempImage_Code(f), searchTemp(f), lib.changeSweetImage_Code(e), searchSweet(e), a));
                                adapter.notifyDataSetChanged();
                                result = g;
                                combineCount += 1;
                            } else {
                                String[] parts = result.split(",", 6);
                                String a = parts[0];
                                String b = parts[1];
                                String c = parts[2];
                                String d = parts[3];
                                String e = parts[4];
                                String f = parts[5];
                                String g = "";

//                                f = searchTemp(f);
//                                e = searchSweet(e);

                                arrayList.add(new Combination(combineCount, d, searchProduct(c), lib.changeTempImage_Code(f), searchTemp(f), lib.changeSweetImage_Code(e), searchSweet(e), a));
                                adapter.notifyDataSetChanged();
                                result = g;
                                combineCount += 1;
                            }
                        }
                    }
                }
            }
        });


//        //expandable listview
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
//                PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/searchCustomer.php", "POST", field, data);
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
////                                f = searchTemp(f);
////                                e = searchSweet(e);
//
//                                childList.add(new Combination(combineCount, d, searchProduct(c), lib.changeTempImage_Code(f), searchTemp(f), lib.changeSweetImage_Code(e), searchSweet(e), a));
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
////                                f = searchTemp(f);
////                                e = searchSweet(e);
//
//                                childList.add(new Combination(combineCount, d, searchProduct(c), lib.changeTempImage_Code(f), searchTemp(f), lib.changeSweetImage_Code(e), searchSweet(e), a));
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
//
//        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                setListViewHeight(parent, groupPosition);
//                return false;
//            }
//        });


//        sp_product = view.findViewById(R.id.spinner_product);
////        btn_check = view.findViewById(R.id.btn_check);
//        listView = view.findViewById(R.id.listview);
//        CoffeeAdapter_original coffeeAdapterOriginal = new CoffeeAdapter_original(view.getContext(), R.layout.recycle_coffee, coffeeData); //getCoffeeData()
//        listView.setAdapter(coffeeAdapterOriginal);
//
//        String[] pid = {"P010", "P110", "P210", "P310", "P410"};
//        for (String p_id : pid) {
//            Handler handler = new Handler();
//            handler.post(new Runnable() {
//                @Override
//                public void run() {
//                    String[] field = new String[1];
//                    field[0] = "p_id";
//                    String[] data = new String[1];
//                    data[0] = p_id;
//                    PutData putData = new PutData("http://192.168.1.153/coffee/searchProduct.php", "POST", field, data);
//                    if (putData.startPut()) {
//                        if (putData.onComplete()) {
//                            String result = putData.getResult();
//                            while (!result.isEmpty()) {
//                                int commas = 0;
//                                for (int i = 0; i < result.length(); i++) {
//                                    if (result.charAt(i) == ',') commas++;
//                                }
//                                if (commas == 3) {
//                                    String[] parts = result.split(",", 4);
//                                    String a = parts[0]; //pid
//                                    String b = parts[1]; //product
//                                    String c = parts[2]; //pic
//                                    String d = parts[3]; //price
//                                    String e = "";
//                                    result = e;
//
////                                    switch (a){
////                                        case "P010":
////                                            coffeeData.add(new Coffee(b, d, R.drawable.coffee4, 1));
////                                            break;
////                                        case "P110":
////                                            coffeeData.add(new Coffee(b, d, R.drawable.coffee5, 1));
////                                            break;
////                                        case "P210":
////                                            coffeeData.add(new Coffee(b, d, R.drawable.coffee1, 2));
////                                            break;
////                                        case "P310":
////                                            coffeeData.add(new Coffee(b, d, R.drawable.coffee2, 2));
////                                            break;
////                                        case "P410":
////                                            coffeeData.add(new Coffee(b, d, R.drawable.coffee3, 2));
////                                            break;
////                                        default:
////                                            break;
////                                    }
//
//                                    coffeeAdapterOriginal.notifyDataSetChanged();
//                                } else {
//                                    Toast.makeText(getContext(), result, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        }
//                    }
//                }
//            });
//        }
//
//        ArrayAdapter<CharSequence> productAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.product_value, R.layout.spinner_text);
//        sp_product.setAdapter(productAdapter);
//        sp_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView parent, View view, int position, long id) {
//                if (position >= 0 && position < 3) {
//                    getSelectedCategoryData(position);
//                } else {
//                    Toast.makeText(getContext(), "沒有東東", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView parent) {
//
//            }
//        });
//
////        btn_check.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                startActivity(new Intent(getActivity(), Activity_shopping_cart.class));
////            }
////        });
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Bundle bundle = new Bundle();
//                String text = sp_product.getSelectedItem().toString();
//                if (text.equals("全部商品")) {
//                    Coffee coffee = coffeeData.get(i);
//                    bundle.putString("coffee", coffee.getName());
//                } else if (text.equals("咖啡") || text.equals("拿鐵")) {
//                    Coffee coffee = coffeeFilter.get(i);
//                    bundle.putString("coffee", coffee.getName());
//                }
//
//                Fragment_order fragment_order = new Fragment_order();
//                fragment_order.setArguments(bundle);
//                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
//                FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
//                frgTransaction.replace(R.id.fragment_container, fragment_order).commit();
//            }
//        });

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    public ArrayList<Coffee> getCoffeeData() {
//        ArrayList<Coffee> test = new ArrayList<>();

//        coffeeData.clear();
//        coffeeData.add(new Coffee("欸黑咖啡", "50元", R.drawable.coffee4, 1));
//        coffeeData.add(new Coffee("耶黑咖啡", "65元", R.drawable.coffee5, 1));
//        coffeeData.add(new Coffee("想要拿鐵", "70元", R.drawable.coffee1, 2));
//        coffeeData.add(new Coffee("不要拿鐵", "80元", R.drawable.coffee2, 2));
//        coffeeData.add(new Coffee("給我拿鐵", "70元", R.drawable.coffee3, 2));

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

//    private void getSelectedCategoryData(int categoryID) {
////        ArrayList<Coffee>coffeeData=new ArrayList<>();
//
//        if (categoryID == 0) {
////            coffeeData.clear();
//            adapter = new CoffeeAdapter_original(getContext(), R.layout.recycle_coffee, getCoffeeData());
//        } else {
//            coffeeFilter.clear();
////            coffeeData.clear();
//            for (Coffee coffee : getCoffeeData()) {
//                if (coffee.getCategoryID() == categoryID) {
//                    coffeeFilter.add(coffee);
//                }
//            }
//            adapter = new CoffeeAdapter_original(getContext(), R.layout.recycle_coffee, coffeeFilter);
//        }
//        listView.setAdapter(adapter);
//    }

    @Override
    public void addButtonClick(int position) {
        //        childList = new ArrayList<>();
//        editList = new ArrayList<>();

//        Toast.makeText(getActivity(),"position: "+position, Toast.LENGTH_SHORT).show();

        Combination combination = arrayList.get(position);
        String coffee = combination.getCoffee();
        String temperature = combination.getTemperature();
        String sweet = combination.getSweet();
        String tempNsweet = temperature + "/" + sweet;
//        count = order.getInt("count", 0);

        Gson gson = new Gson();

        Bundle bundle = new Bundle();
        bundle.putString("coffee_fav", coffee);
        bundle.putString("temperature_fav", temperature);
        bundle.putString("sweet_fav", sweet);

        Fragment_order fragment_order = new Fragment_order();
        fragment_order.setArguments(bundle);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction frgTransaction = fragmentManager.beginTransaction();
        frgTransaction.replace(R.id.fragment_container, fragment_order).commit();

//        count += 1;
//        order_editor.putInt("count", count);
//        order_editor.commit();
//
//        String putJson = gson.toJson(shoppingList);
//        order_editor.putString("shopObj", putJson);
//        order_editor.commit();
    }

    private String searchTemp(String temperature) {
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

    private String searchProduct(String pid) {
        String product = "";
        switch (pid) {
            case "P010":
                product = "欸黑咖啡";
                break;
            case "P110":
                product = "耶黑咖啡";
                break;
            case "P210":
                product = "想要拿鐵";
                break;
            case "P310":
                product = "不要拿鐵";
                break;
            case "P410":
                product = "給我拿鐵";
                break;
            default:
                break;
        }
        return product;
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
}