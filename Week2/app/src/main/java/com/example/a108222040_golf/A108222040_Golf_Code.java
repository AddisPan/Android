package com.example.a108222040_golf;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class A108222040_Golf_Code extends AppCompatActivity {
    Button btnSum;
    TextView tvResult;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a108222040__golf__code);

        fnInitialUI();
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
    }//end

    void fnInitialUI()
    {
        ScrollView sv = new ScrollView(this);
        this.addContentView(sv, new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT,
                ScrollView.LayoutParams.MATCH_PARENT));

        ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        sv.addView(ll,new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));

        for (int i = 0 ; i < 18 ; i++){
            EditText et = new EditText(this);
            et.setGravity(Gravity.CENTER);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setHint("第" + (i+1) + "洞");
            ll.addView(et, new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
        }

        btnSum = new Button(this);
        ll.addView(btnSum, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        btnSum.setText("Sum");

        tvResult = new TextView(this);
        tvResult.setGravity(Gravity.CENTER);
        tvResult.setTextSize(28);
        ll.addView(tvResult, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

    }//end fnInitialUI()

}