package com.example.a108222040_intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    Button btn2to3;
    TextView tv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btn2to3 = (Button) findViewById(R.id.btn2to3);
        btn2to3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity3.class));
            }
        });

        tv2 = (TextView) findViewById(R.id.tv2);
        String name = getIntent().getStringExtra("name");
        String password = getIntent().getStringExtra("password");
        tv2.setText("帳號" + name + ",\n密碼" + password);
        if (name.equals("abc") && password.equals("123"))
        {
            getIntent().putExtra("Result","OK");
            setResult(1,getIntent());
            startActivity(new Intent(getApplicationContext(), MainActivity3.class));
        }
        else
        {
            getIntent().putExtra("Result","帳號或密碼錯誤");
            setResult(1,getIntent());
        }
        finish();
    }//end Create
}