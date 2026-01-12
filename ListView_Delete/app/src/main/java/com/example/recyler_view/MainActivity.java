package com.example.recyler_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    String [] strName = {"LBJ", "Lillard", "AD", "KT", "SC", "Lavine"};
    int [] ifans = {89,56,89,32,54,65};
    int [] ipic = {R.drawable.b01, R.drawable.b02, R.drawable.b03,
            R.drawable.b04, R.drawable.b05, R.drawable.b06};
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0 ; i < strName.length ; i++)
        {
            HashMap <String, String> hashMap = new HashMap();
            hashMap.put("name", strName[i]);
            hashMap.put("fans", String.valueOf(ifans[i]));
            hashMap.put("pic", String.valueOf(ipic[i]));
            arrayList.add(hashMap);
        }//end for
        rv = (RecyclerView) findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager);
        MainAdapter ap = new MainAdapter();
        rv.setAdapter(ap);
    }//end onCreate

    public class MainAdapter extends RecyclerView.Adapter
    {
        View v;
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout,
                    parent, false);
            return new MainItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MainItemViewHolder) holder).getImageView().setImageResource(Integer.parseInt(arrayList.get(position).get("pic")));
            ((MainItemViewHolder) holder).getTv1().setText(arrayList.get(position).get("name"));
            ((MainItemViewHolder) holder).getTv2().setText(arrayList.get(position).get("fans"));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }//end class

    public class MainItemViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;
        private TextView tv1, tv2;
        public MainItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.cus_imageView);
            tv1 = (TextView) itemView.findViewById(R.id.customerTextView1);
            tv2 = (TextView) itemView.findViewById(R.id.customerTextView2);
        }
        public ImageView getImageView() {return iv;}
        public TextView getTv1() {return tv1;}
        public TextView getTv2() {return tv2;}
    }//end class onBind

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_customer, menu);
        return super.onCreateOptionsMenu(menu);
    }//end onCrateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int getId = item.getItemId();
        switch(getId)
        {
            case R.id.menu_customer_update:
                Intent intent = new Intent(getApplicationContext(), CustomerActivityupdate.class);
                intent.putExtra("data", arrayList);
                startActivityForResult(intent, 3);
                break;
            case R.id.menu_customer_about_me:
                Toast.makeText(getApplicationContext(), "你好,我是A108222040", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected
}