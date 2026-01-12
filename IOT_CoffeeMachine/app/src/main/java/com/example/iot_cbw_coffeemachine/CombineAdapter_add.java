package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class CombineAdapter_add extends RecyclerView.Adapter<CombineAdapter_add.CombineViewHolder> {

    private ArrayList<Combination> listCombination;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private OnAddButtonClickListener onAddButtonClickListener;
    private SharedPreferences cid;
    Context context;

    Universal_lib lib = new Universal_lib();

    public CombineAdapter_add(Context context, ArrayList<Combination> listCombination, OnAddButtonClickListener onAddButtonClickListener) {
        this.context = context;
        this.listCombination = listCombination;
        this.onAddButtonClickListener = onAddButtonClickListener;
    }

    @NonNull
    @Override
    public CombineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_combination_add, parent, false);
        return new CombineViewHolder(view, onAddButtonClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CombineViewHolder holder, int position) {
        Combination combination = listCombination.get(position);

        if (combination == null) {
            return;
        }

        holder.txtName.setText(combination.getName());
        holder.txtCoffee.setText(combination.getCoffee());
        holder.txtTemperature.setText(combination.getTemperature());
        holder.txtSweet.setText(combination.getSweet());
        holder.iv_temp.setImageResource(combination.getIv_temp());
        holder.iv_sweet.setImageResource(combination.getIv_sweet());

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddButtonClickListener.addButtonClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listCombination != null) {
            return listCombination.size();
        }
        return 0;
    }

    public class CombineViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtCoffee, txtTemperature, txtSweet;
        OnAddButtonClickListener onAddButtonClickListener;
        ImageView iv_temp, iv_sweet;
        Button btn_add;

        public CombineViewHolder(@NonNull View itemView, OnAddButtonClickListener onAddButtonClickListener) {
            super(itemView);

            this.onAddButtonClickListener = onAddButtonClickListener;
            txtName = itemView.findViewById(R.id.name);
            txtCoffee = itemView.findViewById(R.id.coffee);
            txtTemperature = itemView.findViewById(R.id.temperature);
            txtSweet = itemView.findViewById(R.id.sweet);
            iv_temp = itemView.findViewById(R.id.iv_temp);
            iv_sweet = itemView.findViewById(R.id.iv_sweet);
            btn_add = itemView.findViewById(R.id.addButton);
        }
    }

    public interface OnAddButtonClickListener {
        void addButtonClick(int position);
    }
}

