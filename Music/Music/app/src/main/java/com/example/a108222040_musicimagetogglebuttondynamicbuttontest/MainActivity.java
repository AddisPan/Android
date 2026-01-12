package com.example.a108222040_musicimagetogglebuttondynamicbuttontest;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    MediaPlayer mp;
    ToggleButton tgButton;
    EditText et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(this, R.raw.i);
        iv = (ImageView) findViewById(R.id.imageView);
        iv.setTag(R.drawable.close);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (iv.getDrawable() == getResources().getDrawable(R.drawable.close)) {
//                    iv.setImageDrawable(getResources().getDrawable(R.drawable.open));
//                }else {
//                    iv.setImageDrawable(getResources().getDrawable(R.drawable.close));
//                }
                if ((int)iv.getTag() == R.drawable.close)
                {
                    iv.setTag(R.drawable.open);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.open));
                    mp.start();
                } else {
                    iv.setTag(R.drawable.close);
                    iv.setImageDrawable(getResources().getDrawable(R.drawable.close));
                    mp.pause();
                }
            }
        });

        tgButton = (ToggleButton) findViewById(R.id.toggleButton);
        tgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tgButton.isChecked())
                {
                    mp.start();
                } else {
                    mp.pause();
                }
            }//end onClick
        });

        et = (EditText) findViewById(R.id.et);
        et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String strName = et.getText().toString();
                if (event.getAction() == KeyEvent.ACTION_UP)
                {
                    if (strName.length() > 5){
                        Toast.makeText(MainActivity.this, "長度不可超過5個字",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });
    }//end onCreate()
}