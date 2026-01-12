package com.example.recyler_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CustomerActivityupdate extends AppCompatActivity {

    RecyclerView rv;
    ArrayList<HashMap<String, String>> aryList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activityupdate);
        rv = (RecyclerView) findViewById(R.id.rv_update);
        aryList = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("data");
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
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout_edit, parent, false);
            return new MainItemViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ((MainItemViewHolder) holder).getImageView().setImageResource(Integer.parseInt(aryList.get(position).get("pic")));
            ((MainItemViewHolder) holder).getET1().setText(aryList.get(position).get("name"));
            ((MainItemViewHolder) holder).getET2().setText(aryList.get(position).get("fans"));
        }

        @Override
        public int getItemCount() {
            return aryList.size();
        }
    }//end class

    public class MainItemViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView iv;
        private EditText et1, et2;
        public MainItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.cus_imageView);
            et1 = (EditText) itemView.findViewById(R.id.customer_ed1);
            et2 = (EditText) itemView.findViewById(R.id.customer_ed2);
        }
        public ImageView getImageView() {return iv;}
        public EditText getET1() {return et1;}
        public EditText getET2() {return et2;}
    }//end class onBind
}