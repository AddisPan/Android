 package com.example.a108222040_listview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

 public class MainActivity extends AppCompatActivity {

    Button btnStringArray, btnSimpleList, btnSimpleList2, btnCustomer, btnSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStringArray = (Button) findViewById(R.id.btnStringArray);
        btnSimpleList = (Button) findViewById(R.id.btnSimpleList);
        btnSimpleList2 = (Button) findViewById(R.id.btnSimpleList2);
        btnCustomer = (Button) findViewById(R.id.btnCustomer);
        btnSpinner = (Button) findViewById(R.id.btnSpinner);
        btnStringArray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StrinArrayActivity.class));
            }
        });

        btnSimpleList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SimpleListActivity.class));
            }
        });

        btnSimpleList2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SimpleListActivity2.class));
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CustomerActivity.class));
            }
        });

        btnSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SpinnerActivity.class));
            }
        });
    }//end onCreate
}