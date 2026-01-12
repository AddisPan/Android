package com.example.a10822040_readwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class GSONActivity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> aryData = new ArrayList<>();
    ListView LvGson;
    String keyname = "gson";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsonactivity);
        LvGson = (ListView) findViewById(R.id.lvGSON);
//        InitialData();
//        fnWriteObjectToSP(keyname, aryData);
        aryData = fnReadObjectFromSP(keyname);
        SimpleAdapter ap = new SimpleAdapter(this, aryData, R.layout.customerlayout,
                new String[]{"Data", "Description","pic"},
                new int[]{R.id.cuslayout_name, R.id.cuslayout_price,
                        R.id.cuslayout_iv});
        LvGson.setAdapter(ap);
    }//end onCreate

    private ArrayList<HashMap<String,String>> fnReadObjectFromSP(String _keyname){
        ArrayList<HashMap<String,String>> al = null;
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        String text = sp.getString(_keyname,"");
        Gson gson = new Gson();
        //gson.fromJson(text, new TypeToken<>(){}.getType());
        al = gson.fromJson(text, new TypeToken<ArrayList<HashMap<String,String>>>(){}.getType());
        Toast.makeText(getApplicationContext(), "Read Success",
                Toast.LENGTH_SHORT).show();
        return al;
    }

    private void fnWriteObjectToSP(String _keyname, ArrayList<HashMap<String,String>> _aryData){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        Gson gson = new Gson();
        String json = gson.toJson(_aryData);
        Log.v("test", json);
        spEditor.putString(_keyname, json);
        spEditor.commit();
        Toast.makeText(getApplicationContext(), "Write Success",
                Toast.LENGTH_SHORT).show();
    }//end fnWriteObjectToSP

    private void InitialData() {
        String[] sData = {"元智", "明道", "世界", "高科", "台東", "成功"};
        String[] sDataDescription = {"50", "40", "20", "20", "20", "25"};
        int[] iPic = {R.drawable.logo1, R.drawable.logo2, R.drawable.logo3
                , R.drawable.logo4, R.drawable.logo5, R.drawable.logo6};

        for(int i = 0 ; i<sData.length ; i++){
            HashMap<String,String> hashMap= new HashMap<>();
            hashMap.put("Data", sData[i]);
            hashMap.put("Description", sDataDescription[i]);
            hashMap.put("pic", String.valueOf(iPic[i]));
            aryData.add(hashMap);
        }
    }//end InitialData
}//end class