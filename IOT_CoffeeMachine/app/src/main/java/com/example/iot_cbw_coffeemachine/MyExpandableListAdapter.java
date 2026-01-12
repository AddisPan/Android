package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

    Context context;
    Map<String, ArrayList<Combination>> favoriteCollection;
    ArrayList<String> groupList;
    private ArrayList<Combination> listCombination;
    private SharedPreferences order;
    private SharedPreferences.Editor order_editor;
    int count = 0;
    OnAddButtonClickListener buttonClickListener;

    public MyExpandableListAdapter(Context context, Map<String, ArrayList<Combination>> favoriteCollection, ArrayList<String> groupList,OnAddButtonClickListener buttonClickListener) {
        this.context = context;
        this.favoriteCollection = favoriteCollection;
        this.groupList = groupList;
//        this.buttonClickListener = buttonClickListener; //1031 command
    }

    @Override
    public int getGroupCount() {
        return favoriteCollection.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return favoriteCollection.get(groupList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return favoriteCollection.get(groupList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String favoriteName = groupList.get(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.group_item, null);
        }
        TextView item;
        item = convertView.findViewById(R.id.favorite);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(favoriteName);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Combination favorite = (Combination) getChild(groupPosition, childPosition);
//        Combination combination = listCombination.get(childPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.child_item, null);
        }
        TextView name, coffee, temperature, sweet;
        name = convertView.findViewById(R.id.name);
        coffee = convertView.findViewById(R.id.coffee);
        temperature = convertView.findViewById(R.id.temperature);
        sweet = convertView.findViewById(R.id.sweet);
        name.setText(favorite.getName());
        coffee.setText(favorite.getCoffee());
        temperature.setText(favorite.getTemperature());
        sweet.setText(favorite.getSweet());

        order = context.getSharedPreferences("order", Context.MODE_PRIVATE);
        order_editor = order.edit();

        Button addbtn;
        addbtn = convertView.findViewById(R.id.addButton);

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClickListener.addButtonClick(childPosition);
//                String coffee = favorite.getCoffee();
//                String temperature = favorite.getTemperature();
//                String sweet = favorite.getSweet();
//                String tempNsweet = "(" + temperature + "/" + sweet + ")";
//                count += 1;
//
//                Gson gson = new Gson();
//                String getJson = order.getString("editObj", "");
//                if (!getJson.isEmpty()) {
//                    Type type = new TypeToken<ArrayList<Shopping>>() {
//                    }.getType();
//                    editList = gson.fromJson(getJson, type);
//                    editList.add(new Edit(count - 1, coffee, tempNsweet, "*1"));
//
//                } else {
//                    editList.add(new Edit(count - 1, coffee, tempNsweet, "*1"));
//                }
//
////                editList.add(new Edit(count - 1, "test","temppp","*1"));
//                String putJson = gson.toJson(editList);
//                order_editor.putString("editObj", putJson);
//                order_editor.commit();
            }
        });
//        name.setText(combination.getName());
//        coffee.setText(combination.getCoffee());
//        temperature.setText(combination.getTemperature());
//        sweet.setText(combination.getSweet());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface OnAddButtonClickListener {
        void addButtonClick(int position);
    }
}
