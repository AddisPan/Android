package com.example.a108222040_golf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6, et7, et8, et9, et10, et11, et12, et13, et14, et15,
            et16, et17, et18;
    TextView tv;
    Button btnSum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        et7 = (EditText) findViewById(R.id.et7);
        et8 = (EditText) findViewById(R.id.et8);
        et9 = (EditText) findViewById(R.id.et9);
        et10 = (EditText) findViewById(R.id.et10);
        et11 = (EditText) findViewById(R.id.et11);
        et12 = (EditText) findViewById(R.id.et12);
        et13 = (EditText) findViewById(R.id.et13);
        et14 = (EditText) findViewById(R.id.et14);
        et15 = (EditText) findViewById(R.id.et15);
        et16 = (EditText) findViewById(R.id.et16);
        et17 = (EditText) findViewById(R.id.et17);
        et18 = (EditText) findViewById(R.id.et18);
        tv = (TextView) findViewById(R.id.TV1);
        btnSum = (Button) findViewById(R.id.bt1);
        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int sum = 0;
                try {
                    sum += Integer.parseInt(et1.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et2.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et3.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et4.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et5.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et6.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et7.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et8.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et9.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et10.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et11.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et12.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et13.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et14.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et15.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et16.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et17.getText().toString());
                } catch (Exception e) {
                }
                try {
                    sum += Integer.parseInt(et18.getText().toString());
                } catch (Exception e) {
                }
                tv.setText("總合為" + sum);
            }
        });
    }
}