package com.example.iot_cbw_coffeemachine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.net.URL;
import java.util.ArrayList;

public class Activity_combination extends AppCompatActivity implements CombineAdapter.OnCombinationListener {
    private Toolbar toolbar;
    private Button btnAdd;
    private int count = 0;
    private SharedPreferences cid;
 ImageView iv_temp, iv_sweet, iv_arrow;
    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination);

        toolbar = findViewById(R.id.toolbar);
        btnAdd = findViewById(R.id.add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_temp = findViewById(R.id.iv_temp);
        iv_sweet = findViewById(R.id.iv_sweet);
        iv_arrow = findViewById(R.id.iv_arrow);

        RecyclerView recyclerView = findViewById(R.id.rev_user);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Combination> arrayList = new ArrayList<>();
        CombineAdapter adapter = new CombineAdapter(Activity_combination.this, arrayList, this);
        recyclerView.setAdapter(adapter);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                startActivity(new Intent(Activity_combination.this,MainActivity.class));
            }
        });

        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "c_id";
                String[] data = new String[1];
                data[0] = c_id;
                // 192.168.1.153
                // 192.168.0.16
                PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/searchCustomer.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas >= 6) {
                                count += 1;
                                String[] parts = result.split(",", 7);
                                String a = parts[0]; //cp_id
                                String b = parts[1]; //c_id
                                String c = parts[2]; //p_id
                                String d = parts[3]; //cp_name
                                String e = parts[4]; //sweet
                                String f = parts[5]; //coldhot
                                String g = parts[6]; //remain

//                                f = searchTemperature(f);
//                                e = searchSweet(e);

                                arrayList.add(new Combination(count - 1, d, searchProduct(c), lib.changeTempImage_Code(f), searchTemperature(f), lib.changeSweetImage_Code(e), searchSweet(e),a));
                                adapter.notifyDataSetChanged();
                                result = g;
                            } else {
                                count += 1;
                                String[] parts = result.split(",", 6);
                                String a = parts[0];
                                String b = parts[1];
                                String c = parts[2];
                                String d = parts[3];
                                String e = parts[4];
                                String f = parts[5];
                                String g = "";

//                                f = searchTemperature(f);
//                                e = searchSweet(e);

                                arrayList.add(new Combination(count - 1, d, searchProduct(c), lib.changeTempImage_Code(f), searchTemperature(f), lib.changeSweetImage_Code(e), searchSweet(e),a));
                                adapter.notifyDataSetChanged();
                                result = g;
                            }
                        }
                    }
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), activity_combination_list.class));
            }
        });

    }

    private String searchTemperature(String temperature){
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
            default:
                break;
        }
        return temperature;
    }

    private String searchSweet(String sweet){
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
            default:
                break;
        }
        return sweet;
    }

    private String searchProduct(String pid){
        String product="";
        switch (pid){
            case "P010":
                product="黑咖啡";
                break;
            case "P110":
                product="拿鐵";
                break;
            default:
                break;
        }
        return product;
    }

    @Override
    public void onCombinationClick(int position) {
        Toast.makeText(this, "點下去修改", Toast.LENGTH_SHORT).show();
//        startActivity(new Intent(this,activity_combination_list.class));
    }
}