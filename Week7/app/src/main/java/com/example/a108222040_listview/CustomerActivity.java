package com.example.a108222040_listview;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerActivity extends AppCompatActivity {
    ListView lv3;
    String [] strName = {"LBJ", "Lillard", "AD", "KT", "SC", "Lavine"};
    int [] ifans = {89,56,89,32,54,65};
    int [] ipic = {R.drawable.b01, R.drawable.b02, R.drawable.b03,
                R.drawable.b04, R.drawable.b05, R.drawable.b06};
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Toast.makeText(getApplicationContext(), "ABC" + resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode == 3 && resultCode == 996)
        {
            arrayList = (ArrayList<HashMap<String, String>>) data.getSerializableExtra("Result");
            SimpleAdapter ap = new SimpleAdapter(this, arrayList, R.layout.customer_layout,
                    new String[]{"name","fans","pic"},
                    new int[]{R.id.customerTextView1, R.id.customerTextView2, R.id.cus_imageView});
            lv3.setAdapter(ap);
        }
    }//end AR

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        lv3 = (ListView) findViewById(R.id.ListView3);
        for (int i = 0 ; i < strName.length ; i++)
        {
            HashMap <String, String> hashMap = new HashMap();
            hashMap.put("name", strName[i]);
            hashMap.put("fans", String.valueOf(ifans[i]));
            hashMap.put("pic", String.valueOf(ipic[i]));
            arrayList.add(hashMap);
        }//end for
        SimpleAdapter ap = new SimpleAdapter(this, arrayList, R.layout.customer_layout,
                new String[]{"name","fans","pic"},
                new int[]{R.id.customerTextView1, R.id.customerTextView2, R.id.cus_imageView});
        lv3.setAdapter(ap);
    }//end of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customor, menu);
        return super.onCreateOptionsMenu(menu);
    }//end onCrateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int getId = item.getItemId();
        switch(getId)
        {
            case R.id.menu_customer_update:
                Intent intent = new Intent(getApplicationContext(), CustomerActivityUpdate.class);
                intent.putExtra("data", arrayList);
                startActivityForResult(intent, 3);
                break;
            case R.id.menu_customer_about_me:
                Toast.makeText(getApplicationContext(), "你好,我是A108222040", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected
}//end class