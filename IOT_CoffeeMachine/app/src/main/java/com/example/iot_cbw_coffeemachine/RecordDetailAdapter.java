package com.example.iot_cbw_coffeemachine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;

import java.util.ArrayList;

public class RecordDetailAdapter extends RecyclerView.Adapter<RecordDetailAdapter.RecordDetailViewHolder> {

    private ArrayList<RecordDetail> recordDetailList;

    public RecordDetailAdapter(ArrayList<RecordDetail> recordDetailList) {
        this.recordDetailList = recordDetailList;
    }

    @NonNull
    @Override
    public RecordDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_recorddetail,parent,false);
        return new RecordDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordDetailViewHolder holder, int position) {
        RecordDetail recordDetail = recordDetailList.get(position);

        if(recordDetail==null){
            return;
        }

        holder.iv_coffee.setImageResource(recordDetail.getImage());
        holder.txtName.setText(recordDetail.getName());
        holder.txtTempNSweet.setText(recordDetail.getTempNsweet());
        holder.txtPrice.setText(recordDetail.getPrice());
        holder.txtQuantity.setText(recordDetail.getQuantity());
        holder.txtTotal.setText(recordDetail.getTotal());

    }

    @Override
    public int getItemCount() {
        if(recordDetailList != null){
            return recordDetailList.size();
        }
        return 0;
    }

    public class RecordDetailViewHolder extends RecyclerView.ViewHolder{

        ImageView iv_coffee;
        TextView  txtName, txtTempNSweet, txtPrice, txtQuantity, txtTotal;

        public RecordDetailViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_coffee=itemView.findViewById(R.id.iv_coffee);
            txtName=itemView.findViewById(R.id.tv_product);
            txtTempNSweet=itemView.findViewById(R.id.tv_tempNsweet);
            txtPrice=itemView.findViewById(R.id.tv_price);
            txtQuantity=itemView.findViewById(R.id.tv_quantity);
            txtTotal=itemView.findViewById(R.id.tv_total);
        }
    }

}

