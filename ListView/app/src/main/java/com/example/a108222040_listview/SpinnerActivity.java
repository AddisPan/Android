package com.example.a108222040_listview;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class SpinnerActivity extends AppCompatActivity {
    Spinner spStringArray, spSimpleList, spSimpleList2, spCustomer;
    String[] strData = {"基隆市", "台北市", "新北市", "桃園市", "新竹市",
            "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣",
            "嘉義縣", "台南市", "高雄市", "屏東縣", "台東縣",
            "花蓮縣", "宜蘭縣", "澎湖縣", "金門縣", "馬祖縣"};
    int[] Person = {23,33,2234,232,223,12,978,
            6545,213,456,789,123,6548,
            134,68,78,321,648,21,46};

    String[] strName = {"LBJ", "DL", "AD", "KT", "SC", "Lavine"};
    int[] iPrice = {50,45,15,15,15,25};
    int[] ipic = {R.drawable.b01, R.drawable.b02,R.drawable.b03,R.drawable.b04,R.drawable.b05,R.drawable.b06};
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> arrayList2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner);
        spStringArray = (Spinner) findViewById(R.id.spstringarray);
        spSimpleList = (Spinner) findViewById(R.id.spSimpleList);
        spSimpleList2 = (Spinner) findViewById(R.id.spSimpleList2);
        spCustomer = (Spinner) findViewById(R.id.spCustomer);


        spStringArray.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] strAry = getResources().getStringArray(R.array.s1);
                Toast.makeText(getApplicationContext(), strAry[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> ap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strData);
        spSimpleList.setAdapter(ap);

        spSimpleList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), strData[position], Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        for(int i=0; i<strData.length; i++){
            HashMap<String, String> hashMap = new HashMap();
            hashMap.put("city", strData[i]);
            hashMap.put("person",String.valueOf(Person[i]));
            arrayList.add(hashMap);
        }
        SimpleAdapter ap2 = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"city", "person"}, new int[]{android.R.id.text1, android.R.id.text2});
        spSimpleList2.setAdapter(ap2);


        spSimpleList2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hashMap = arrayList.get(position);
                String s = hashMap.get("city") + hashMap.get("person");
                Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        for(int i=0; i<strName.length; i++){
            HashMap<String, String> hashMap = new HashMap();
            hashMap.put("name", strName[i]);
            hashMap.put("price",String.valueOf(iPrice[i]));
            hashMap.put("pic",String.valueOf(ipic[i]));
            arrayList2.add(hashMap);
        }
        SimpleAdapter ap3 = new SimpleAdapter(this, arrayList2, R.layout.customer_layout,
                new String[]{"name", "price", "pic"}, new int[]{R.id.customerTextView1, R.id.customerTextView2, R.id.cus_imageView});
        spCustomer.setAdapter(ap3);

        spCustomer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                HashMap<String, String> hashMap = arrayList2.get(position);
                String s = hashMap.get("name") + hashMap.get("price");
                Toast.makeText(getApplicationContext(), s , Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
