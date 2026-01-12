package com.example.a108222040_listview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class CustomerActivityUpdate extends AppCompatActivity {
    ArrayList<HashMap<String, String>> aryList;
    LinearLayout ll;
    int iSelectIndex = 0;
    ImageView iSelectImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_update);
        aryList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("data");
        InitialUI();
    }//end onCreate

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
    }//end of InitialUI

    public void getSelected(int i)
    {
        Toast.makeText(getApplicationContext(), ImageDialogFragment.ipic[i], Toast.LENGTH_SHORT).show();
        aryList.get(iSelectIndex).put("pic", String.valueOf(ImageDialogFragment.ipic[i]));
        iSelectImageView.setImageResource(ImageDialogFragment.ipic[i]);
//        if (ll.getChildAt(i) instanceof LinearLayout)
//        {
//            LinearLayout ll2 = (LinearLayout)ll.getChildAt(i);
//            if (ll2.getChildAt(2) instanceof ImageView)
//            {
//                ImageView iv = (ImageView) ll2.getChildAt(2);
//                iv.setImageResource(Integer.parseInt(aryList.get(iSelectIndex).get("pic")));
//            }
//        }
    }//end getSelected

    @Override
    public void onBackPressed() {
        for (int i = 0 ; i < aryList.size() ; i++)
        {
            aryList.get(i).put("name",((EditText)((LinearLayout)ll.getChildAt(i)).getChildAt(0)).getText().toString());
            aryList.get(i).put("fans", ((EditText)((LinearLayout)ll.getChildAt(i)).getChildAt(1)).getText().toString());
        }
        getIntent().putExtra("Result", aryList);
        setResult(996,getIntent());
        super.onBackPressed();
    }//end oBP

    public void reloadUI()
    {
        ll.removeAllViews();
        for (int i = 0 ; i < aryList.size() ; i++)
        {
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            ll.addView(ll2, new ViewGroup.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    150));

            ll2.setWeightSum(10);

            EditText et = new EditText(this);
            et.setGravity(Gravity.CENTER);
            et.setText(aryList.get(i).get("name"));
            ll2.addView(et, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,2));

            et = new EditText(this);
            et.setGravity(Gravity.CENTER);
            et.setText(aryList.get(i).get("fans"));
            ll2.addView(et, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,2));

            ImageView iv = new ImageView(this);
            iv.setImageResource(Integer.parseInt(aryList.get(i).get("pic")));
            ll2.addView(iv, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,5));
            iv.setOnClickListener(new ClassImageViewChoice(i));

            Button btns = new Button(this);
            btns.setText("Del");
            ll2.addView(btns, new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,1));
            btns.setOnClickListener(new ClassButtonDel(i));
        }//end for



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
                       150));

                ll2.setWeightSum(10);

                HashMap <String, String> hashMap = new HashMap();
                hashMap.put("name", "");
                hashMap.put("fans", "");
                hashMap.put("pic", String.valueOf(R.drawable.ic_launcher_background));
                aryList.add(hashMap);

                EditText et = new EditText(getApplicationContext());
                et.setGravity(Gravity.CENTER);
                et.setText("");

                ll2.addView(et, new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,3));

                et = new EditText(getApplicationContext());
                et.setGravity(Gravity.CENTER);
                et.setText("");

                ll2.addView(et, new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,3));

                ImageView iv = new ImageView(getApplicationContext());
                iv.setImageResource(R.drawable.ic_launcher_background);
                ll2.addView(iv, new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.WRAP_CONTENT,3));
                iv.setOnClickListener(new ClassImageViewChoice(aryList.size() - 1));

                Button btns = new Button(getApplicationContext());
                btns.setText("Del");
                ll2.addView(btns, new LinearLayout.LayoutParams(
                        0,
                        LinearLayout.LayoutParams.MATCH_PARENT,1));
                btns.setOnClickListener(new ClassButtonDel(aryList.size() - 1));
            }
        });
    }//end reloadUI


    class ClassImageViewChoice implements View.OnClickListener{
        int position;
        ClassImageViewChoice(int _position)
        {
            position = _position;
        }
        @Override
        public void onClick(View v) {
            iSelectIndex = position;
            iSelectImageView = (ImageView) v;
            (new ImageDialogFragment()).show(getSupportFragmentManager(), "abc");
        }
    }//end class ClassImageViewChoice


    class ClassButtonDel implements View.OnClickListener
    {
        int position;
        ClassButtonDel(int _position)
        {
            position = _position;
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), aryList.get(position).get("name"), Toast.LENGTH_SHORT).show();
            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivityUpdate.this);
            builder.setMessage("確定刪除這筆資料嗎?").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    aryList.remove(position);
                    //reloadUI();
                    ll.removeViewAt(position);
                }
            }).setNegativeButton("取消", null).show();
        }//end onClick
    }//end class
}//end class