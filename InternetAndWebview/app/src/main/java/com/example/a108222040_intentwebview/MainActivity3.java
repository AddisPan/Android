package com.example.a108222040_intentwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity3 extends AppCompatActivity {

    EditText etFirst, etSecond;
    Button btnGo;
    WebView wv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        etFirst = (EditText) findViewById(R.id.etFirst);
        etSecond = (EditText) findViewById(R.id.etSecond);
        btnGo = (Button) findViewById(R.id.btn);
        wv2 = (WebView) findViewById(R.id.WebView2);
        wv2.getSettings().setJavaScriptEnabled(true);
        //https://www.google.com.tw/maps/dir/116%E5%8F%B0%E5%8C%97%E5%B8%82%E6%96%87%E5%B1%B1%E5%8D%80%E6%9C%A8%E6%9F%B5%E8%B7%AF%E4%B8%80%E6%AE%B517%E5%B7%B71%E8%99%9F%E4%B8%96%E6%96%B0%E5%A4%A7%E5%AD%B8/%E8%87%BA%E5%8C%97%E8%BB%8A%E7%AB%99+%E5%8F%B0%E5%8C%97%E5%B8%82%E4%B8%AD%E6%AD%A3%E5%8D%80%E5%8C%97%E5%B9%B3%E8%A5%BF%E8%B7%AF3%E8%99%9F100%E8%87%BA%E7%81%A3/@25.018225,121.4942495,13z/data=!3m1!4b1!4m14!4m13!1m5!1m1!1s0x3442aa077249f59b:0xfb5860fd7348f354!2m2!1d121.543984!2d24.988664!1m5!1m1!1s0x3442a9727e339109:0xc34a31ce3a4abecb!2m2!1d121.5169537!2d25.0477942!3e2
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wv2.loadUrl("https://www.google.com.tw/maps/dir/" + etFirst.getText().toString()
                + "/" + etSecond.getText().toString() + "/花蓮市/");
            }
        });
    }
}