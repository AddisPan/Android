package com.example.iot_cbw_coffeemachine;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class activity_for_test extends AppCompatActivity {

    ImageView imageView;
    Button button;

    ArrayList<String> groupList = new ArrayList<>();
    ArrayList<Combination> childList = new ArrayList<>();
    Map<String, ArrayList<Combination>> favoriteCollection = new HashMap<String, ArrayList<Combination>>();
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_for_test);

////        imageView = findViewById(R.id.imageView);
////        button = findViewById(R.id.button);
//
//        groupList.add("我的最愛");
////        String[] combination = {"測試一下", "xx咖啡", "正常", "無糖"};
//        childList.add(new Combination(0, "測試一下", "xx咖啡", "正常", "無糖"));
//        favoriteCollection.put(groupList.get(0), childList);
//
//        expandableListView = findViewById(R.id.favoriteListView);
//        expandableListAdapter = new MyExpandableListAdapter(this, favoriteCollection, groupList);
//        expandableListView.setAdapter(expandableListAdapter);
//        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            int lastPosition = -1;
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                if (lastPosition != -1 && groupPosition != lastPosition) {
//                    expandableListView.collapseGroup(lastPosition);
//                }
//                lastPosition = groupPosition;
//            }
//        });
//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//                String result = expandableListAdapter.getChild(groupPosition, childPosition).toString();
//                Toast.makeText(activity_for_test.this, result, Toast.LENGTH_SHORT).show();
//                return true;
//            }
//        });

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Uri imgUri=Uri.parse("android.resource://com.example.iot_cbw_coffeemachine/"+R.drawable.coffee1);
//                Uri imgUri=Uri.parse("android.resource://com.example.iot_cbw_coffeemachine/res/photo/coffee1.png");
//                imageView.setImageURI(null);
//                imageView.setImageURI(imgUri);
//
////                String s = Environment.getExternalStorageDirectory().getAbsolutePath() + "/photo/coffee1.png";
////                Bitmap myBitmap = BitmapFactory.decodeFile(s);
////                imageView.setImageBitmap(myBitmap);
//
////                File imgFile = new File("../../../../res/photo/coffee1.png");
////
////                if(imgFile.exists()){
////                    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
////                    imageView.setImageBitmap(myBitmap);
////                }else{
////                    Toast.makeText(activity_for_test.this,"something wrong",Toast.LENGTH_SHORT).show();
////                }
//            }
//        });
    }
}