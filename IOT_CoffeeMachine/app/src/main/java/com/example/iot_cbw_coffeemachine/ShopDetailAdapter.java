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

public class ShopDetailAdapter extends ArrayAdapter<ShopDetail> {
    private Context mContext;
    private int mResource;

    public ShopDetailAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ShopDetail> objects) {
        super(context, resource, objects);

        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater= LayoutInflater.from(mContext);
        convertView=layoutInflater.inflate(mResource,parent,false);

        TextView txtName=convertView.findViewById(R.id.name);
        TextView txtTempNsweet=convertView.findViewById(R.id.tempNsweet);
        TextView txtQuantity=convertView.findViewById(R.id.quantity);
        TextView txtTotal=convertView.findViewById(R.id.total);

        txtName.setText(getItem(position).getName());
        txtTempNsweet.setText(getItem(position).getTempNsweet());
        txtQuantity.setText(getItem(position).getQuantity());
        txtTotal.setText(getItem(position).getTotal());

        return convertView;
    }
}
