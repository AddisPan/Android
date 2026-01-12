package com.example.a10822040_readwrite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class TextActivity extends AppCompatActivity {
    EditText exTxt;
    Button btnWrite, btnRead;
    TextView tv1;
    String file = "Test.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        exTxt = (EditText) findViewById(R.id.etText);
        tv1 = (TextView) findViewById(R.id.tvText);
        btnWrite = (Button) findViewById(R.id.btnSaveText);
        btnRead = (Button) findViewById(R.id.btnRoadText);
        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //write file
                writeToFile(exTxt.getText().toString(), getApplicationContext());
                Toast.makeText(getApplicationContext(),"Write success", Toast.LENGTH_SHORT).show();
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //read file
                tv1.setText(readFromFile(getApplicationContext()));
                Toast.makeText(getApplicationContext(), "Read success", Toast.LENGTH_SHORT).show();
            }
        });
    }//end onCreate

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    context.openFileOutput(file, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }//end write


    private String readFromFile(Context context) {
        String ret = "";
        try {
            InputStream inputStream = context.openFileInput(file);
            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();
                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append("\n").append(receiveString);
                }
                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret;
    }//end read
}//end class