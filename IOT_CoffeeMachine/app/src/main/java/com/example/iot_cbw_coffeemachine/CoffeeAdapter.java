package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.content.Intent;
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

public class CoffeeAdapter extends RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder> {

    private ArrayList<Coffee> coffeeList;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private SharedPreferences cid;
    Context context;
    private OnCoffeeListener onCoffeeListener;

    public CoffeeAdapter(Context context, ArrayList<Coffee> coffeeList, OnCoffeeListener onCoffeeListener) {
        this.context = context;
        this.coffeeList = coffeeList;
        this.onCoffeeListener = onCoffeeListener;
    }

    @NonNull
    @Override
    public CoffeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_coffee, parent, false);
        return new CoffeeViewHolder(view, onCoffeeListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CoffeeViewHolder holder, int position) {
        Coffee coffee = coffeeList.get(position);

        if (coffee == null) {
            return;
        }

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(coffee.getId()));
        holder.iv_coffee.setImageResource(coffee.getImage());
        holder.txtName.setText(coffee.getName());
        holder.txtDollars.setText(coffee.getDollars());
        holder.txtCoffeeBeans.setText(coffee.getCoffeeBeans());
        holder.txtCalorie.setText(coffee.getCalorie());
        holder.txtCaffeine.setText(coffee.getCaffeine());

        holder.layoutIntroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, activity_introduction.class);
                intent.putExtra("image", coffee.getImage());
                intent.putExtra("coffee", coffee.getName());
                context.startActivity(intent);
            }
        });

        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCoffeeListener.OnCoffeeClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (coffeeList != null) {
            return coffeeList.size();
        }
        return 0;
    }

    public class CoffeeViewHolder extends RecyclerView.ViewHolder {

        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layoutIntroduction;
        RelativeLayout mainlayout;
        ImageView iv_coffee;
        TextView txtName, txtDollars, txtCoffeeBeans, txtCalorie, txtCaffeine;
        OnCoffeeListener onCoffeeListener;

        public CoffeeViewHolder(@NonNull View itemView, OnCoffeeListener onCoffeeListener) {
            super(itemView);

            this.onCoffeeListener = onCoffeeListener;
            swipeRevealLayout = itemView.findViewById(R.id.swipeRevealLayout);
            layoutIntroduction = itemView.findViewById(R.id.layout_introduction);
            mainlayout = itemView.findViewById(R.id.mainlayout);
            iv_coffee = itemView.findViewById(R.id.iv_coffee);
            txtName = itemView.findViewById(R.id.name);
            txtDollars = itemView.findViewById(R.id.dollars);
            txtCoffeeBeans = itemView.findViewById(R.id.coffeeBeans);
            txtCalorie = itemView.findViewById(R.id.calorie);
            txtCaffeine = itemView.findViewById(R.id.caffeine);
        }
    }

    public interface OnCoffeeListener {
        void OnCoffeeClick(int position);
    }
}

