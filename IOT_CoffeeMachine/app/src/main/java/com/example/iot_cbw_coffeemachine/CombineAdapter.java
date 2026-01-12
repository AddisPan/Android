package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class CombineAdapter extends RecyclerView.Adapter<CombineAdapter.CombineViewHolder> {

    private ArrayList<Combination> listCombination;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private OnCombinationListener onCombinationListener;
    private SharedPreferences cid;
    Context context;

    Universal_lib lib = new Universal_lib();

    public CombineAdapter(Context context, ArrayList<Combination> listCombination, OnCombinationListener onCombinationListener) {
        this.context = context;
        this.listCombination = listCombination;
        this.onCombinationListener = onCombinationListener;
    }

    @NonNull
    @Override
    public CombineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_combination, parent, false);
        return new CombineViewHolder(view, onCombinationListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CombineViewHolder holder, int position) {
        Combination combination = listCombination.get(position);

        if (combination == null) {
            return;
        }

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(combination.getId()));
        holder.txtName.setText(combination.getName());
        holder.txtCoffee.setText(combination.getCoffee());
        holder.txtTemperature.setText(combination.getTemperature());
        holder.txtSweet.setText(combination.getSweet());
        holder.iv_temp.setImageResource(combination.getIv_temp());
        holder.iv_sweet.setImageResource(combination.getIv_sweet());

        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cid = context.getSharedPreferences("cid", Context.MODE_PRIVATE);
                String c_id = cid.getString("c_id", "");
                String cp_id = listCombination.get(holder.getAdapterPosition()).getCpid();

                Handler handler = new Handler();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        String[] field = new String[2];
                        field[0] = "c_id";
                        field[1] = "cp_id";
                        String[] data = new String[2];
                        data[0] = c_id;
                        data[1] = cp_id;

                        PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/deleteCustomerProduct.php", "POST", field, data);
                        if (putData.startPut()) {
                            if (putData.onComplete()) {
                                String result = putData.getResult();
                                if (result.equals("true")) {
                                    listCombination.remove(holder.getAdapterPosition());
                                    notifyItemRemoved(holder.getAdapterPosition());
                                    Toast.makeText(context, "刪除88888", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                });

            }
        });

        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCombinationListener.onCombinationClick(holder.getAdapterPosition());
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

        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layoutDelete;
        RelativeLayout mainlayout;
        TextView txtName, txtCoffee, txtTemperature, txtSweet;
        OnCombinationListener onCombinationListener;
        ImageView iv_temp, iv_sweet;

        public CombineViewHolder(@NonNull View itemView, OnCombinationListener onCombinationListener) {
            super(itemView);

            this.onCombinationListener = onCombinationListener;
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            layoutDelete = itemView.findViewById(R.id.layout_delete);
            mainlayout = itemView.findViewById(R.id.mainlayout);
            txtName = itemView.findViewById(R.id.name);
            txtCoffee = itemView.findViewById(R.id.coffee);
            txtTemperature = itemView.findViewById(R.id.temperature);
            txtSweet = itemView.findViewById(R.id.sweet);
            iv_temp = itemView.findViewById(R.id.iv_temp);
            iv_sweet = itemView.findViewById(R.id.iv_sweet);
        }
    }

    public interface OnCombinationListener {
        void onCombinationClick(int position);
    }
}

