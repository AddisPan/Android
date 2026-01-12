package com.example.a108222040_imagestringautosearch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView iv;
    TextView tv;
    Button btnPre,btnNext;
    SeekBar seekBar;
    int iNowImage = 0;
    int [] intImages = {R.drawable.flow01b, R.drawable.flow02b, R.drawable.flow03b, R.drawable.flow04b
                        , R.drawable.flow05b, R.drawable.flow06b};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitialUI();
        iv.setImageResource(R.drawable.flow02b);
    }

    private void InitialUI() {
        iv = (ImageView) findViewById(R.id.imageView);
        tv = (TextView) findViewById(R.id.textView);
        tv.setText("總共" + intImages.length + "筆, 目前在" + (iNowImage + 1) + "筆" );
        btnPre = (Button) findViewById(R.id.btnPre);
        btnNext = (Button) findViewById(R.id.btnNext);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        btnPre.setOnClickListener(btnPreNext);
        btnNext.setOnClickListener(btnPreNext);
        seekBar.setOnSeekBarChangeListener(sbListener);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, A108222040_AutoSearch.class);
//                intent.setClass(getApplicationContext(), A108222040_AutoSearch.class);
                startActivity(intent);
            }
        });
    }

    SeekBar.OnSeekBarChangeListener sbListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            iv.setImageAlpha(progress * 255 / 100);
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };

    View.OnClickListener btnPreNext = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button btn = (Button) v;
            if (btn.getText().equals("前一筆"))
            {
                 if (iNowImage > 0) iNowImage--;
            }else if(btn.getText().equals("後一筆"))
            {
                 if (iNowImage < intImages.length-1) iNowImage++;
            }
            iv.setImageResource(intImages[iNowImage]);
            tv.setText("總共" + intImages.length + "筆, 目前在" + (iNowImage + 1) + "筆" );
        }
    };
}