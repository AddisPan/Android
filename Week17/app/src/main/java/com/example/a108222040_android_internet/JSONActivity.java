package com.example.a108222040_android_internet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

public class JSONActivity extends AppCompatActivity {
    ListView lv;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonactivity);
        lv = (ListView) findViewById(R.id.lvJSON);
        handler = new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                String getString = (String) msg.obj;
//                Toast.makeText(getApplicationContext(), getString, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(getString);
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        JSONArray ja = jsonArray.getJSONArray(i);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("date", ja.get(0).toString());
                        hashMap.put("price", ja.get(6).toString());
                        arrayList.add(hashMap);
                    }
                    SimpleAdapter ap = new SimpleAdapter(getApplicationContext(), arrayList, android.R.layout.simple_list_item_2,
                    new String[]{"date", "price"}, new int[]{android.R.id.text1, android.R.id.text2});
                    lv.setAdapter(ap);
                } catch (Exception e){

                }
            }
        };
        String strurl = "https://www.twse.com.tw/exchangeReport/STOCK_DAY?response=json&date=20180901&stockNo=2330";
        getJson(strurl);
        //        SimpleAdapter ap = new SimpleAdapter(this, arrayList, android.R.layout.simple_list_item_2,
//                new String[]{"date", "price"}, new int[]{android.R.id.text1, android.R.id.text2});
//        lv.setAdapter(ap);
    }//end onCreate

    public void getJson(String strurl){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //你的URL
                    URL url = new URL(strurl);
                    HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                    conn.setReadTimeout(3000);
                    conn.setConnectTimeout(3000);//設置超時
                    conn.setRequestMethod("GET");
                    conn.setUseCaches(false);//數據不多不用緩存
                    //開始連接
                    conn.connect();
                    //取得數據
                    InputStream inputStream = conn.getInputStream();
                    InputStreamReader input = new InputStreamReader(inputStream);
                    BufferedReader buffer = new BufferedReader(input);
                    if(conn.getResponseCode() == 200){//200表示"OK"
                        String inputLine;
                        StringBuffer resultData  = new StringBuffer();//StringBuffer字符串拼接很快
                        while((inputLine = buffer.readLine())!= null){
                            resultData.append(inputLine);
                        }
                        String text = resultData.toString();
                        String message = text;
                        Message msg = Message.obtain(); // Creates an new Message instance
                        msg.obj = message; // Put the string into Message, into "obj" field.
                        msg.setTarget(handler); // Set the Handler
                        msg.sendToTarget(); //Send the message
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}//end class