package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RecordAdapter extends ArrayAdapter<Record> {
    private Context mContext;
    private int mResource;

    public RecordAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Record> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mResource,parent,false);

        TextView txtTime=convertView.findViewById(R.id.time);
        TextView txtCoffee=convertView.findViewById(R.id.coffee);
        TextView txtCups=convertView.findViewById(R.id.cups);
        TextView txtDiscount=convertView.findViewById(R.id.discount);
        TextView txtTotal=convertView.findViewById(R.id.total);

        txtTime.setText(getItem(position).getTime());
        txtCoffee.setText(getItem(position).getCoffee());
        txtCups.setText(getItem(position).getCups());
        txtDiscount.setText(getItem(position).getDiscount());
        txtTotal.setText(getItem(position).getTotal());

        return convertView;
    }
}
