package com.example.iot_cbw_coffeemachine;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.ArrayList;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {

    private ArrayList<Shopping> shoppingList;

    private LayoutInflater inflater;
    private Context mContext;
    private OnShoppingListener shoppingListener;
    private SharedPreferences cart, order;
    private SharedPreferences.Editor cart_editor, order_editor;

    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public ShoppingAdapter(Context context, ArrayList<Shopping> shoppingList, OnShoppingListener shoppingListener) {
        mContext = context;
        this.shoppingList = shoppingList;
        this.shoppingListener = shoppingListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_shoppingcart, parent, false);
        return new ShoppingAdapter.ViewHolder(view, shoppingListener);
//        return new ShoppingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Shopping shopping = shoppingList.get(position);

        if (shopping == null) {
            return;
        }

//        File imgFile = new  File(shopping.getImg_product());
//        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//        holder.img_product.setImageBitmap(myBitmap);

        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(shopping.getId()));
        holder.img_product.setImageResource(shopping.getImg_product());
        holder.name.setText(shopping.getName());
        holder.temperature.setText(shopping.getTemperature());
        holder.sweet.setText(shopping.getSweet());
        holder.price.setText(shopping.getPrice());
        holder.iv_temp.setImageResource(shopping.getIv_temp());
        holder.iv_sweet.setImageResource(shopping.getIv_sweet());

//        holder.quantity.setSelection(getIndex(holder.quantity,shopping.getQuantity()));
//        holder.quantity.setSelection(((ArrayAdapter)holder.quantity.getAdapter()).getPosition(shopping.getQuantity()));
//        int value = Integer.parseInt(shopping.getQuantity());
//        holder.quantity.setSelection(value - 1);

//        price = shopping.getPrice().replaceAll("[^0-9]", "");
//        price_int = Integer.parseInt(price) * spinner_value;

        ArrayList<String> quantityList = new ArrayList<>();
        quantityList.add("1");
        quantityList.add("2");
        quantityList.add("3");
        quantityList.add("4");
        quantityList.add("5");
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, quantityList);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        holder.quantity.setSelection(quantityAdapter.getPosition("4"));
        holder.quantity.setAdapter(quantityAdapter);
        int value = Integer.parseInt(shopping.getQuantity().replaceAll("[^0-9]", ""));
        holder.quantity.setSelection(value - 1);

        holder.quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();

//                shopping.setQuantity(result);
                shopping.setQuantity(result);
                int quantity = Integer.parseInt(result);
//                int price = Integer.parseInt(shopping.getPrice());
//               int price = Integer.parseInt(shopping.getPrice().replaceAll("[^0-9]", ""));

                String total = "";
                Fragment_information information = new Fragment_information();
                for (Coffee coffee : information.getCoffeeData()) {
                    if (coffee.getName().equals(shopping.getName())) {
                        String eachPrice = coffee.getDollars();
                        int price = Integer.parseInt(eachPrice.replaceAll("[^0-9]", ""));
                        total = calculatePrice(price, quantity);
                        shopping.setPrice(total);
//                        shopping.setQuantity(result);
                        holder.price.setText(total);
//                        holder.quantity.setSelection(quantity-1);
                        countTotal();
                    }
                }

                order = mContext.getSharedPreferences("order", Context.MODE_PRIVATE);
                order_editor = order.edit();
                Gson gson = new Gson();
                String cart_json = gson.toJson(shoppingList);
                order_editor.putString("shopObj", cart_json);
                order_editor.commit();

//                cart = mContext.getSharedPreferences("cart", Context.MODE_PRIVATE);
//                cart_editor = cart.edit();
//                Gson gson = new Gson();
//                String cart_json = gson.toJson(shoppingList);
//                cart_editor.putString("cartObj", cart_json);
//                cart_editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView parent) {

            }
        });

//        holder.img_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = shoppingList.get(holder.getAdapterPosition()).getName();
//
//                shoppingList.remove(holder.getAdapterPosition());
////                shoppingListener.refreshShopCart(shoppingList);
//                notifyItemRemoved(holder.getAdapterPosition());
//
//                cart = mContext.getSharedPreferences("cart", Context.MODE_PRIVATE);
//                cart_editor = cart.edit();
//                order = mContext.getSharedPreferences("order", Context.MODE_PRIVATE);
//                order_editor = order.edit();
//
//                Gson gson = new Gson();
//                String cart_json = gson.toJson(shoppingList);
////                String order_json = gson.toJson(shoppingList);
//
//                countTotal();
//
//                cart_editor.putString("cartObj", cart_json);
//                cart_editor.commit();
////                order_editor.putString("orderObj", order_json);
////                order_editor.commit();
//                order_editor.putString("count", String.valueOf(getItemCount()));
//                order_editor.commit();
//
//            }
//        });

//        spinner_value = Integer.parseInt(holder.quantity.getSelectedItem().toString());
        holder.layoutDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                order = mContext.getSharedPreferences("order", Context.MODE_PRIVATE);
                order_editor = order.edit();
                shoppingList.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());

                countTotal();

                Gson gson = new Gson();
                String putJson = gson.toJson(shoppingList);
                order_editor.putString("shopObj", putJson);
                order_editor.commit();
            }
        });

        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "這樣可以點了嗎", Toast.LENGTH_SHORT).show();
//                onCombinationListener.onCombinationClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoppingList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_delete, img_product;
        TextView name, temperature, sweet, price;
        Spinner quantity;
        private OnShoppingListener shoppingListener;

        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layoutDelete;
        RelativeLayout mainlayout;
        ImageView iv_temp, iv_sweet;

        public ViewHolder(@NonNull View itemView, OnShoppingListener shoppingListener) {
            super(itemView);

            this.shoppingListener = shoppingListener;
//            img_delete = itemView.findViewById(R.id.img_delete);
            img_product = itemView.findViewById(R.id.img_product);
            name = itemView.findViewById(R.id.tv_name);
            temperature = itemView.findViewById(R.id.tv_temp);
            sweet = itemView.findViewById(R.id.tv_sweet);
            quantity = itemView.findViewById(R.id.spinner_quantity);
            price = itemView.findViewById(R.id.tv_price);

            swipeRevealLayout=itemView.findViewById(R.id.swipeRevealLayout);
            layoutDelete=itemView.findViewById(R.id.layout_delete);
            mainlayout=itemView.findViewById(R.id.mainlayout);
            iv_temp = itemView.findViewById(R.id.iv_temp);
            iv_sweet = itemView.findViewById(R.id.iv_sweet);

//            initializeSpinner(quantity);
//            ArrayList<String> quantityList = new ArrayList<>();
//            quantityList.add("1");
//            quantityList.add("2");
//            quantityList.add("3");
//            quantityList.add("4");
//            quantityList.add("5");
//            ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, quantityList);
//            quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            quantity.setAdapter(quantityAdapter);

//            quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    int quantity = i + 1;
//                    shoppingList.get(i).setName("hello");
//                    quantityAdapter.notifyDataSetChanged();
////                    shoppingList.get(i).setPrice(Integer.toString(quantity));
////                    int money = 0;
////                    String result = "";
////                    money = Integer.parseInt(shoppingList.get(i).getPrice());
////                    result = Integer.toString(money * quantity);
////                    shoppingList.get(i).setPrice(result);
//
////                    if(quantity == getItemId(getAdapterPosition()))
////                   shoppingListener.changeQuantity(shoppingList.get(getAdapterPosition()), quantity);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
        }
    }

    public ArrayList<Shopping> refreshShopCart(ArrayList<Shopping> shoppings) {
        if (shoppingList.size() != shoppings.size()) {
            shoppingList = shoppings;
        }
        return shoppingList;
    }

    private int getIndex(Spinner spinner, String s) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)) {
                return i;
            }
        }
        return 0;
    }

    private void initializeSpinner(Spinner spinner) {
        ArrayList<String> quantityList = new ArrayList<>();
        quantityList.add("1");
        quantityList.add("2");
        quantityList.add("3");
        quantityList.add("4");
        quantityList.add("5");
        ArrayAdapter<String> quantityAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, quantityList);
        quantityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(quantityAdapter);
    }

    private static String calculatePrice(int price, int quantity) {
        String result = "";
        result = Integer.toString(price * quantity);
        return result;
    }

    public void countTotal() {
//        int sum = 0;
//        for (int i = 0; i < shoppingList.size(); i++) {
////            sum += Integer.parseInt(shoppingList.get(i).getPrice());
//            sum += Integer.parseInt(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));
//        }
        int sum = 0;
        order = mContext.getSharedPreferences("order", Context.MODE_PRIVATE);
        String discount = order.getString("discount","");
        if(discount.equals("0.8")){
            for (int i = 0; i < shoppingList.size(); i++) {
                sum += Integer.parseInt(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));

            }
            BigDecimal discount_value = new BigDecimal("0.8");
            sum = BigDecimal.valueOf(sum).multiply(discount_value).intValueExact();
        }else{
            for (int i = 0; i < shoppingList.size(); i++) {
                sum += Double.parseDouble(shoppingList.get(i).getPrice().replaceAll("[^0-9]", ""));
            }
        }
        sum = Math.round(sum);
        shoppingListener.changeQuantity(sum);
    }

    public interface OnShoppingListener {
        void changeQuantity(int total);
//        void refreshShopCart(ArrayList<Shopping> shoppings);
    }
}

