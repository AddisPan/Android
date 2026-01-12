package com.example.a10822040_readwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnText, btnObject, btnSP, btnGson, btnSPBase64;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnText = (Button) findViewById(R.id.btnText);
        btnObject = (Button) findViewById(R.id.btnObject);
        btnSP = (Button) findViewById(R.id.btnSP);
        btnGson = (Button) findViewById(R.id.btnGSON);
        btnSPBase64 = (Button) findViewById(R.id.btnSPBase64);

        btnText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TextActivity.class));
            }
        });

        btnObject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ObjectActivity.class));
            }
        });

        btnSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SPActivity.class));
            }
        });

        btnGson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), GSONActivity.class));
            }
        });

        btnSPBase64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SPbase64Activity.class));
            }
        });
    }//end onCreate
}//end class