package com.example.a108222040_listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

public class SimpleListActivityUpdate extends AppCompatActivity {

    String [] data;
    ArrayList<String> aryList;
    LinearLayout ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_list_update);
        data = getIntent().getStringArrayExtra("data");
        aryList = new ArrayList<String>(Arrays.asList(data));
        //Toast.makeText(getApplicationContext(), data[0], Toast.LENGTH_SHORT).show();

        InitialUI();
    }//end onCreate

    @Override
    public void onBackPressed() {
        for (int i = 0 ; i < aryList.size() ; i++)
        {
            aryList.set(i, ((EditText)((LinearLayout)ll.getChildAt(i)).getChildAt(0)).getText().toString());
            aryList.set(i, ((EditText)((LinearLayout)ll.getChildAt(i)).getChildAt(1)).getText().toString());
        }
        String [] aryString = aryList.toArray(new String[0]);
        Log.v("test", aryString[0]);
        getIntent().putExtra("Result", aryList);
        setResult(999,getIntent());
        super.onBackPressed();
        //View v = ll.getChildAt(0);
//        if (v instanceof LinearLayout)
//        {
//            View v2 = ((LinearLayout) v).getChildAt(0);
//            if (v2 instanceof EditText)
//            {
//                Toast.makeText(getApplicationContext(),((EditText) v2).getText().toString(),
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//        Toast.makeText(getApplicationContext(), "死了死了快死了", Toast.LENGTH_SHORT).show();
    }

    private void InitialUI()
    {
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
        reloadUI();

//        for (int i = 0 ; i < aryList.size() ; i++)
//        {
//            LinearLayout ll2 = new LinearLayout(this);
//            ll2.setOrientation(LinearLayout.HORIZONTAL);
//            ll.addView(ll2, new ViewGroup.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.MATCH_PARENT));
//
//            ll2.setWeightSum(6);
//
//            EditText et = new EditText(this);
//            et.setGravity(Gravity.CENTER);
//            et.setInputType(InputType.TYPE_CLASS_NUMBER);
//            et.setHint(aryList.get(i));
//            ll2.addView(et, new LinearLayout.LayoutParams(
//                    0,
//                    LinearLayout.LayoutParams.WRAP_CONTENT,5));
//
//
//
//            Button btns = new Button(this);
//            btns.setText("Del");
//            ll2.addView(btns, new LinearLayout.LayoutParams(
//                    0,
//                    LinearLayout.LayoutParams.MATCH_PARENT,1));
//            btns.setOnClickListener(new ClassButtonDel(i));
//        }

        //可輸入的第一洞
//        Button btnAdd = new Button(this);
//        btnAdd.setGravity(Gravity.CENTER);
//        btnAdd.setText("新增");
//        ll.addView(btnAdd, new ViewGroup.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT));
    }//end of InitialUI

    public void reloadUI()
    {
        for (int i = 0 ; i < aryList.size() ; i++)
        {
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            ll.addView(ll2, new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));

            ll2.setWeightSum(6);

            EditText et = new EditText(this);
            et.setGravity(Gravity.CENTER);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            et.setHint(aryList.get(i));
            ll2.addView(et, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,5));



            Button btns = new Button(this);
            btns.setText("Del");
            ll2.addView(btns, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,1));
            btns.setOnClickListener(new ClassButtonDel(i));
        }
        Button btnAdd = new Button(this);
        btnAdd.setGravity(Gravity.CENTER);
        btnAdd.setText("新增");
        ll.addView(btnAdd, new ViewGroup.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout ll2 = new LinearLayout(getApplicationContext());
                ll2.setOrientation(LinearLayout.HORIZONTAL);
                ll.addView(ll2, aryList.size(),new ViewGroup.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT));

                ll2.setWeightSum(6);

                EditText et = new EditText(getApplicationContext());
                et.setGravity(Gravity.CENTER);
                et.setInputType(InputType.TYPE_CLASS_NUMBER);
                et.setText("");
                aryList.add("");
                ll2.addView(et, new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,5));

                Button btns = new Button(getApplicationContext());
                btns.setText("Del");
                ll2.addView(btns, new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,1));
                btns.setOnClickListener(new ClassButtonDel(aryList.size() - 1));
            }
        });
    }//end reloadUI

    class ClassButtonDel implements View.OnClickListener
    {
        int position;
        ClassButtonDel(int _position)
        {
            position = _position;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), aryList.get(position), Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(SimpleListActivityUpdate.this);
            builder.setMessage("確定刪除這筆資料嗎?").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aryList.remove(position);
                    reloadUI();
                    ll.removeViewAt(position);
                }
            }).setNegativeButton("取消", null).show();
        }//end onClick
    }//end class
}
