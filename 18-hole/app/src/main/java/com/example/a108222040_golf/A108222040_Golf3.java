package com.example.a108222040_golf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class A108222040_Golf3 extends AppCompatActivity {
    Button btnSum, btnAdd;
    TextView tvResult;
    LinearLayout ll;
    int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a108222040__golf3);
        fnInitialUI();
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EditText et = new EditText(activity_a108222040__golf3.this);
                EditText et = new EditText(getApplicationContext());
                et.setGravity(Gravity.CENTER);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                et.setHint("第" + (count + 1) + "洞");
                ll.addView(et, count,new ViewGroup.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                count++;
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int iSum = 0;
                for (int i = 0 ; i < ll.getChildCount() ; i++){
                    if (ll.getChildAt(i) instanceof EditText) {
                        EditText et = (EditText) ll.getChildAt(i);
                        try {
                            iSum += Integer.parseInt(et.getText().toString());
                        }catch (Exception e) {
                        }
                    }
                }
                tvResult.setText("合計:" + String.valueOf(iSum));
            }
        });
    }

    private void fnInitialUI() {
        //可拉動ScrollView
        ScrollView sv = new ScrollView(this);
        this.addContentView(sv, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT));

        //水平LinearLayout
        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        //可輸入的第一洞
        EditText et = new EditText(this);
        et.setGravity(Gravity.CENTER);
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setHint("第1洞");
        ll.addView(et, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //重直LinearLayout
        LinearLayout ll2 = new LinearLayout(this);
        ll2.setOrientation(LinearLayout.HORIZONTAL);
        ll.addView(ll2,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,   //跟父親一樣大
                LinearLayout.LayoutParams.WRAP_CONTENT));

        //加號
        btnAdd = new Button(this);
        ll2.addView(btnAdd, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT, 4));
        btnAdd.setText("+");

        //總和.....
        btnSum = new Button(this);
        ll2.addView(btnSum, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,1));
        btnSum.setText("Sum");

        tvResult = new TextView(this);
        tvResult.setGravity(Gravity.CENTER);
        tvResult.setTextSize(28);
        ll.addView(tvResult, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
    }//end fnInitialUI()
}