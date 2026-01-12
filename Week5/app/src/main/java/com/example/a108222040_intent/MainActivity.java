package com.example.a108222040_intent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn2,btn3;
    EditText etName, etPassword;
    Button btnLogin;
    TextView tv;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == 1)
        {
            String strResult =  data.getStringExtra("Result");
            tv.setText(strResult);
            if (strResult.equals("OK")) {finish();}
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), MainActivity2.class);
                startActivity(intent);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity3.class));
            }
        });

        etName = (EditText) findViewById(R.id.etName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        tv = (TextView) findViewById(R.id.tv);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                intent.putExtra("name",etName.getText().toString());
                intent.putExtra("password",etPassword.getText().toString());
                //會有一個等待回傳值
                startActivityForResult(intent,2);
            }
        });
    }//end onCreate

    @Override
    protected void onStart() {
        super.onStart();
        Log.v("test","MainActivity Start");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("test","MainActivity Stop");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.v("test","MainActivity Pause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v("test","MainActivity Resume");
    }
}//end class