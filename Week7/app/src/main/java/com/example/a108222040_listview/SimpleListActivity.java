package com.example.a108222040_listview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SimpleListActivity extends AppCompatActivity {

    ListView lv1;
    String [] strData = {"基隆市","台北市","新北市","桃園市","新竹市","苗栗縣","台中市","彰化縣","南投縣"
            ,"雲林縣","嘉義縣","台南市","高雄市","屏東縣","宜蘭縣","花蓮縣","台東縣"
            ,"澎湖縣","金門縣","馬祖縣"};
    ArrayAdapter<String>ap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "ABC" + resultCode, Toast.LENGTH_SHORT).show();
        if (requestCode == 1 && resultCode == 999)
        {
            strData = data.getStringArrayExtra("Result");
            Log.v("test", strData[0]);
            //ap.notifyDataSetChanged();
            ArrayAdapter<String> ap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,strData);
            lv1.setAdapter(ap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list);
        lv1 = (ListView) findViewById(R.id.ListView1);
        ArrayAdapter<String> ap = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1 ,strData);
        lv1.setAdapter(ap);
    }//end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_simplelist, menu);
        return super.onCreateOptionsMenu(menu);
    }//end onCrateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int getId = item.getItemId();
        switch(getId)
        {
            case R.id.menu_update:
                Intent intent = new Intent(getApplicationContext(), SimpleListActivityUpdate.class);
                intent.putExtra("data", strData);
                startActivityForResult(intent, 1);
                break;
            case R.id.menu_about_me:
                Toast.makeText(getApplicationContext(), "你好,我是A108222040", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}