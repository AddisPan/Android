package com.example.iot_cbw_coffeemachine;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Activity_record extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    private SharedPreferences record, cid;
    private SharedPreferences.Editor record_editor;

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        toolbar = findViewById(R.id.toolbar);
        listView = findViewById(R.id.listview_record);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ArrayList<Record> recordList = new ArrayList<>();
        RecordAdapter recordAdapter = new RecordAdapter(this, R.layout.list_record, recordList);
        listView.setAdapter(recordAdapter);

        record = getApplicationContext().getSharedPreferences("record", Context.MODE_PRIVATE);
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
                PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/searchOrderrecord.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas >= 8) {
                                String[] parts = result.split(",", 9);
                                String a = parts[0]; //o_id
                                String b = parts[1]; //c_id
                                String c = parts[2]; //date
                                String d = parts[3]; //time
                                String e = parts[4]; //totalcups
                                String f = parts[5]; //discont
                                String g = parts[6]; //totalprice
                                String h = parts[7]; //firstproduct
                                String i = parts[8];
                                result = i;

                                int cups = Integer.parseInt(e) - 1;
                                e = String.valueOf(cups);
                                recordList.add(new Record(c + " " + d, h, "...等" + e + "杯", f, g + "元", a));
                                recordAdapter.notifyDataSetChanged();

                            } else {
                                String[] parts = result.split(",", 8);
                                String a = parts[0]; //o_id
                                String b = parts[1]; //c_id
                                String c = parts[2]; //date
                                String d = parts[3]; //time
                                String e = parts[4]; //totalcups
                                String f = parts[5]; //discont
                                String g = parts[6]; //totalprice
                                String h = parts[7]; //firstproduct
                                String i = "";
                                result = i;

                                int cups = Integer.parseInt(e) - 1;
                                e = String.valueOf(cups);
                                recordList.add(new Record(c + " " + d, h, "...等" + e + "杯", f, g + "元", a));
                                recordAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                record_editor = record.edit();
                String o_id = recordList.get(position).getO_id();
                String discount = recordList.get(position).getDiscount();
                String total = recordList.get(position).getTotal();
                record_editor.putString("o_id", o_id);
                record_editor.putString("discount", discount);
                record_editor.putString("total", total);
                record_editor.commit();
                startActivity(new Intent(Activity_record.this, activity_recorddetail.class));
            }
        });
    }
}