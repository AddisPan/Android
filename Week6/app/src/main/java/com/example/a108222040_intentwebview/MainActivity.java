package com.example.a108222040_intentwebview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnWebSite, btnPhone, btnGoogleMap, btnEmail, btnImages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWebSite = (Button) findViewById(R.id.btnWebsite);
        btnPhone = (Button) findViewById(R.id.btnPhone);
        btnGoogleMap = (Button) findViewById(R.id.btnGoogleMap);
        btnEmail = (Button) findViewById(R.id.btnMail);
        btnImages = (Button) findViewById(R.id.btnImages);
        btnWebSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://www.shu.edu.tw/"));
                startActivity(intent);
            }
        });

        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("tel:+0911236123"));
                startActivity(intent);
            }
        });

        btnGoogleMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("google.navigation:q=世新大學"));
                startActivity(intent);
            }
        });

        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("mailto:shu@mail.shu.edu.tw"));
                startActivity(intent);
            }
        });

        btnImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("content://media/external/images/media/"));
                startActivity(intent);
            }
        });
    }//end onCreate
}