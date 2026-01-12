package com.example.a108222040_intentwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity2 extends AppCompatActivity {

    WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        wv = (WebView) findViewById(R.id.WebV);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.loadUrl("https://www.shu.edu.tw/");
        wv.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                wv.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }//end onCreate

    @Override
    public void onBackPressed(){
        if (wv.canGoBack())
        {
            wv.goBack();
            return;
        }
        super.onBackPressed();
    }
}