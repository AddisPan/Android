package com.example.a108222040_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class SimpleListActivity2 extends AppCompatActivity {

    ListView lv2;
    String [] strData = {"基隆市","台北市","新北市","桃園市","新竹市","苗栗縣","台中市","彰化縣","南投縣"
            ,"雲林縣","嘉義縣","台南市","高雄市","屏東縣","宜蘭縣","花蓮縣","台東縣"
            ,"澎湖縣","金門縣","馬祖縣"};
    int [] Person = {23, 33, 3324, 56, 89, 36, 78, 63, 67, 36
                   , 87, 65, 3256, 98, 56, 78, 63, 88, 32, 67};
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list2);
        lv2 = (ListView) findViewById(R.id.ListView2);
        for (int i = 0 ; i < strData.length ; i++)
        {
            HashMap <String, String> hashMap = new HashMap();
            hashMap.put("city", strData[i]);
            hashMap.put("person", String.valueOf(Person[i]));
            arrayList.add(hashMap);
        }//end for
        SimpleAdapter ap = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
                new String[]{"city","person"},
                new int[]{android.R.id.text1, android.R.id.text2});
        lv2.setAdapter(ap);
    }//end onCreate
}