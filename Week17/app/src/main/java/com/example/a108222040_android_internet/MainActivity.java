package com.example.a108222040_android_internet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnHTML, btnWebImage, btnLVWebImage, btnJSON;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnHTML = (Button) findViewById(R.id.btnHTML);
        btnWebImage = (Button) findViewById(R.id.btnWebImage);
        btnLVWebImage = (Button) findViewById(R.id.btnLVWebImage);
        btnJSON = (Button) findViewById(R.id.btJSON);

        btnHTML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HTMLActivity.class));
            }
        });

        btnWebImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ImageWebActivity.class));
            }
        });

        btnLVWebImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListViewImageWebActivity.class));
            }
        });

        btnJSON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JSONActivity.class));
            }
        });
    }//end onCreate
}//end class