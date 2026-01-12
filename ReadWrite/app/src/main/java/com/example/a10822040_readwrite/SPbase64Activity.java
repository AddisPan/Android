package com.example.a10822040_readwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SPbase64Activity extends AppCompatActivity {
    ArrayList<HashMap<String,String>> aryData = new ArrayList<>();
    ListView lvSPBase64;
    String keyname = "spbase64";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spbase64);
        lvSPBase64 = (ListView) findViewById(R.id.lvSPBase64);
//        InitialData();
//        fnWriteObjectToSP(keyname, aryData);
        aryData = fnReadObjectFromSP(keyname);

        SimpleAdapter ap = new SimpleAdapter(this, aryData, R.layout.customerlayout,
                new String[]{"Data", "Description","pic"},
                new int[]{R.id.cuslayout_name, R.id.cuslayout_price,
                        R.id.cuslayout_iv});
        lvSPBase64.setAdapter(ap);
    }//end onCreate

    private ArrayList<HashMap<String,String>> fnReadObjectFromSP(String _keyname){
        ArrayList<HashMap<String,String>> al;
        al = (ArrayList<HashMap<String,String>>) getObject(_keyname, getApplicationContext());
        if (al != null){
            Toast.makeText(getApplicationContext(), "Read Success",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Read fail",
                    Toast.LENGTH_SHORT).show();
        }
        return al;
    }

    private void fnWriteObjectToSP(String _keyname, ArrayList<HashMap<String,String>> _aryData){
        if (saveObject(_keyname, _aryData, getApplicationContext())) {
            Toast.makeText(getApplicationContext(), "Write Success",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Write fail",
                    Toast.LENGTH_SHORT).show();
        };
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

    //通過base64儲存複雜物件到SharedPreferences
    public static boolean saveObject(String key, Object obj, Context context) {
        //如果物件為null，不儲存，直接返回儲存失敗
        if (obj == null) {
            return false;
        }
        //flag,用於判斷是否儲存物件成功
        boolean flag = false;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // 內部建立一個byte型別陣列的緩衝區,最初預設為32位，在需要的時候會自動增加大小,從流中讀位元組陣列
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        //主要的作用是用於寫入物件資訊與讀取物件資訊
        ObjectOutputStream objectOutputStream = null;
        try {
            // 建立物件輸出流，並封裝位元組流
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            // 將物件寫入位元組流
            objectOutputStream.writeObject(obj);
            // 將位元組流編碼成base64的字元竄
            String base64String = new String(Base64.encode(byteArrayOutputStream
                    .toByteArray(), Base64.DEFAULT));
            flag = preferences.edit().putString(key, base64String).commit();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //主要做關閉流操作
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (byteArrayOutputStream != null) {
                    byteArrayOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return flag;
        }
    }
    //讀取複雜物件
    public static Object getObject(String key, Context context) {
        Object obj = null;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String objString = preferences.getString(key, "");
        // 讀取位元組
        byte[] base64 = Base64.decode(objString.getBytes(), Base64.DEFAULT);
        // 位元組流
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(base64);
        //物件流
        ObjectInputStream objectInputStream = null;
        try {
            // 再次封裝
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            // 讀取物件
            obj = objectInputStream.readObject();
        } catch (Exception e) {
            // e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }

                if (byteArrayInputStream != null) {
                    byteArrayInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }//end getObject

}//end class