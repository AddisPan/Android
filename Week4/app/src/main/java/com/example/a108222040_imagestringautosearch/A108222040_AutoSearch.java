package com.example.a108222040_imagestringautosearch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class A108222040_AutoSearch extends AppCompatActivity {

    AutoCompleteTextView autov;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a108222040__auto_search);
        autov = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        String [] autoString = getResources().getStringArray(R.array.PlaceArray);
        ArrayAdapter<String> ap = new ArrayAdapter<>(this,R.layout.listitem,autoString);
        //Data 做綁定
        autov.setAdapter(ap);
    }
}