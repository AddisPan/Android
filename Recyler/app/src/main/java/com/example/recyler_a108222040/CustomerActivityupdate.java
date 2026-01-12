package com.example.recyler_a108222040;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class CustomerActivityupdate extends AppCompatActivity {
    RecyclerView rv;
    ArrayList<HashMap<String, String>> arylist;
    MainAdapter ap;
    Button btnAdd, btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_activityupdate);
        rv = (RecyclerView) findViewById(R.id.rv_update);
        arylist = (ArrayList<HashMap<String, String>>) getIntent().getSerializableExtra("data");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(layoutManager); // 必須設置 LayoutManager
        ap = new MainAdapter();
        rv.setAdapter(ap);
        ItemTouchHelperCallback itcall = new ItemTouchHelperCallback(ap);
        ItemTouchHelper it = new ItemTouchHelper(itcall);
        it.attachToRecyclerView(rv);
        btnAdd = (Button) findViewById(R.id.btnADd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ap.addItem();
            }
        });
        btnSave = (Button) findViewById(R.id.btnsave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntent().putExtra("Result", arylist);
                setResult(996,getIntent());
                finish();
            }
        });
    }

    public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

        private MainAdapter mAdapter;
        public ItemTouchHelperCallback(MainAdapter adapter) {
            mAdapter = adapter;
        }

        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            Log.v("test", ""+direction);
            if (direction==4){
                mAdapter.showMenu(viewHolder.getAdapterPosition());
            } else if(direction==8){
                mAdapter.NotshowMenu();
            }
        }
    }

    interface ItemTouchHelperAdpter{
        void onItemMove(int fromPosition, int toPosition);
        void onItemDismiss(int position);
    }

    public class MainAdapter extends RecyclerView.Adapter implements ItemTouchHelperAdpter{
        private ArrayList<Boolean> arymenu = new ArrayList<>();

        public MainAdapter() {
            for(int i = 0; i < arylist.size(); i++){
                arymenu.add(Boolean.FALSE);
            }
        }

        public void showMenu(int position) {
            for(int i=0; i<arymenu.size(); i++){
                arymenu.set(i, Boolean.FALSE);
            }
            arymenu.set(position, Boolean.TRUE);
            notifyDataSetChanged();
        }

        public void NotshowMenu() {
            for(int i=0; i<arymenu.size(); i++){
                arymenu.set(i, Boolean.FALSE);
            }
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v;
            if (viewType == 0){
                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout_edit, parent, false);
                return new MainItemViewHolder(v);
            }else {
                v= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_layout_delete, parent, false);
                return new MainItemViewHolderDel(v);
            }
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof MainItemViewHolder){
                ((MainItemViewHolder)holder).getImageView().setImageResource(Integer.parseInt(arylist.get(position).get("pic")));
                ((MainItemViewHolder)holder).getEt1().setText(arylist.get(position).get("name"));
                ((MainItemViewHolder)holder).getEt2().setText(arylist.get(position).get("fans"));
            }else{
                ((MainItemViewHolderDel)holder).getImageView().setImageResource(Integer.parseInt(arylist.get(position).get("pic")));
                ((MainItemViewHolderDel)holder).getTv1().setText(arylist.get(position).get("name"));
                ((MainItemViewHolderDel)holder).getTv2().setText(arylist.get(position).get("fans"));
            }
        }

        @Override
        public int getItemViewType(int position) {
            if(arymenu.get(position)){
                return 1; //True
            }else{
                return 0; //False
            }
        }

        @Override
        public int getItemCount() {
            return arylist.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            Collections.swap(arylist, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onItemDismiss(int position) {
            arylist.remove(position);
            notifyItemRemoved(position);
        }

        public void changePic(int position, int pic){
            arylist.get(position).replace("pic", String.valueOf(pic));
            notifyDataSetChanged();
        }

        public void addItem(){
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name", "");
            hashMap.put("fans","");
            hashMap.put("pic",String.valueOf(R.drawable.ic_launcher_background));
            arylist.add(hashMap);
            arymenu.add(Boolean.FALSE);
            notifyItemInserted(arylist.size()-1);
        }
    }

    public class MainItemViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private EditText et1, et2;
        public MainItemViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.cus_imageView_edit);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    (new ImageDialogFragment(getAdapterPosition())).show(getSupportFragmentManager(), "abc");
                }
            });
            et1 = (EditText) itemView.findViewById(R.id.customer_ed1);
            et2 = (EditText) itemView.findViewById(R.id.customer_ed2);
            TextWatcher tw1 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    arylist.get(getAdapterPosition()).replace("name", et1.getText().toString());
                }
            };
            et1.addTextChangedListener(tw1);
            TextWatcher tw2 = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    arylist.get(getAdapterPosition()).replace("fans", et2.getText().toString());
                }
            };
            et2.addTextChangedListener(tw2);
        }
        public ImageView getImageView(){return iv;}
        public EditText getEt1(){return et1;}
        public EditText getEt2(){return et2;}
    }

    public class MainItemViewHolderDel extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv1, tv2;
        private Button btnDel;
        public MainItemViewHolderDel(@NonNull View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.cus_imageView_del);
            tv1 = (TextView) itemView.findViewById(R.id.customerTextView1_del);
            tv2 = (TextView) itemView.findViewById(R.id.customerTextView2_del);
            btnDel = (Button) itemView.findViewById(R.id.button_del);
            btnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), arylist.get(getAdapterPosition()).get("name"), Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CustomerActivityupdate.this);
                    builder.setMessage("確定刪除這筆資料 ?").setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ap.onItemDismiss(getAdapterPosition());
                            ap.NotshowMenu();
                        }
                    }).setNegativeButton("取消", null).show();
                }
            });
        }
        public ImageView getImageView(){return iv;}
        public TextView getTv1(){return tv1;}
        public TextView getTv2(){return tv2;}
        public Button getBtnDel(){return btnDel;}
    }




}