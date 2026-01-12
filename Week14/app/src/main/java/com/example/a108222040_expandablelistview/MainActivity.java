package com.example.a108222040_expandablelistview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String [] straryEdu = {"國小", "國中", "高中", "大學", "碩士", "博士"};
    String [] straryEduDesc = {"基礎教育", "基本教育", "普通高中及高職", "普通大學及科技大學", "", ""};
    String [][] strarySch = {{"仁愛國小", "信義國小"}, {"文青中學", "忠孝國中"},
            {"建國中學", "北一女高", "中正高中", "內湖高工"},
            {"世新大學", "台灣大學", "台科大"}, {"台灣大學", "清華大學", "交通大學"}, {"土博士", "洋博士"}};
    int [][] strarySchlogo = {{R.drawable.logo1, R.drawable.logo2}, {R.drawable.logo3, R.drawable.logo4},
            {R.drawable.logo5, R.drawable.logo6, R.drawable.logo1, R.drawable.logo2},
            {R.drawable.logo3, R.drawable.logo4, R.drawable.logo5}, {R.drawable.logo6, R.drawable.logo1,
            R.drawable.logo2}, {R.drawable.logo3, R.drawable.logo4}};
    ExpandableListView eplv;
    ExpandableListAdapter exap;

    ArrayList<Map<String, String>> arrayList = new ArrayList<>();
    ArrayList<ArrayList<Map<String, String>>> arrayListSchools = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        eplv = (ExpandableListView) findViewById(R.id.eplv);
        for(int i = 0; i < straryEdu.length ; i++){
            Map<String, String> map = new HashMap<>();
            map.put("name", straryEdu[i]);
            map.put("desc", straryEduDesc[i]);
            arrayList.add(map);

            ArrayList<Map<String, String>> arylistSch = new ArrayList<>();
            for(int j = 0 ; j < strarySch[i].length ; j++){
                Map<String, String> mapsch = new HashMap<>();
                mapsch.put("school", strarySch[i][j]);
                mapsch.put("schlogo", String.valueOf(strarySchlogo[i][j]));
                arylistSch.add(mapsch);
            }
            arrayListSchools.add(arylistSch);
        }

        exap = new SimpleExpandableListAdapter2(this,
                arrayList,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"name", "desc"},
                new int[]{android.R.id.text1, android.R.id.text2},
                arrayListSchools,
                R.layout.customerlayout,
                new String[]{"school", "schlogo"},
                new int[]{R.id.textView, R.id.imageView});

        eplv.setAdapter(exap);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tootlbar);

//        toolbar.setNavigationIcon(R.drawable.logo1);
        toolbar.setLogo(R.drawable.logo2);
        toolbar.setTitle("I am Title");
        toolbar.setSubtitle("SubTitle");
        toolbar.setTitleTextColor(Color.BLUE);
        toolbar.inflateMenu(R.menu.menu_example);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }
}