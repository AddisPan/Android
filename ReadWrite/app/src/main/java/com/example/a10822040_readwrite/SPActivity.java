package com.example.a10822040_readwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SPActivity extends AppCompatActivity {
    EditText et;
    Button btnSaveSP, btnReadSP;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spactivity);
        et =(EditText) findViewById(R.id.etSP);
        btnSaveSP = (Button) findViewById(R.id.btnSaveSP);
        btnReadSP = (Button) findViewById(R.id.btnRoadSP);
        tv = (TextView) findViewById(R.id.tvSP);
        btnSaveSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();
                spEditor.putString("text", et.getText().toString());
                spEditor.commit();
                Toast.makeText(getApplicationContext(), "Write Success",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnReadSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getPreferences(MODE_PRIVATE);
                String text = sp.getString("text","");
                tv.setText(text);
                Toast.makeText(getApplicationContext(), "Read Success",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }//end OnCreate
}//end class