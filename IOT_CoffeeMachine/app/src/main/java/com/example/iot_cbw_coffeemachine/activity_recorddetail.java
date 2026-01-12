package com.example.iot_cbw_coffeemachine;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class activity_recorddetail extends AppCompatActivity {
    Toolbar toolbar;
    TextView tv_oid, tv_discount, tv_total;
    private SharedPreferences cid, record;

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorddetail);

        toolbar = findViewById(R.id.toolbar);
        tv_oid = findViewById(R.id.tv_oid);
        tv_discount = findViewById(R.id.tv_discount);
        tv_total = findViewById(R.id.tv_total);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView rev_recordDetail = findViewById(R.id.rev_recorddetail);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rev_recordDetail.setLayoutManager(linearLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rev_recordDetail.addItemDecoration(itemDecoration);

        ArrayList<RecordDetail> recordDetailList = new ArrayList<>();
        RecordDetailAdapter detailAdapter = new RecordDetailAdapter(recordDetailList);
        rev_recordDetail.setAdapter(detailAdapter);

        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
        record = getApplicationContext().getSharedPreferences("record", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        String o_id = record.getString("o_id", "");
        String discount = record.getString("discount", "");
        String total = record.getString("total", "");

        tv_oid.setText(o_id);
        if(discount.equals("")){
            tv_discount.setText("尚未使用");
        }else{
            tv_discount.setText(discount);
        }
        tv_total.setText(total);

        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[2];
                field[0] = "c_id";
                field[1] = "o_id";
                String[] data = new String[2];
                data[0] = c_id;
                data[1] = o_id;
                PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/searchSaleDetail.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas >= 7) {
                                String[] parts = result.split(",", 8);
                                String a = parts[0]; //pid
                                String b = parts[1]; //cups
                                String c = parts[2]; //sweet
                                String d = parts[3]; //coldhot
                                String e = parts[4]; //total
                                String f = parts[5]; //eachprice
                                String g = parts[6]; //pic
                                String h = parts[7]; //remain
                                result = h;

                                char coffeeName = a.charAt(3);

                                switch (coffeeName) {
                                    case '0':
                                        a = "欸黑咖啡";
                                        break;
                                    case '1':
                                        a = "耶黑咖啡";
                                        break;
                                    case '2':
                                        a = "想要拿鐵";
                                        break;
                                    case '3':
                                        a = "不要拿鐵";
                                        break;
                                    case '4':
                                        a = "給我拿鐵";
                                        break;
                                }

                                d = searchTemperature(d);
                                c = searchSweet(c);

                                String tempNsweet = d + "/" + c;

                                switch (g) {
                                    case "coffee4":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee4, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee5":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee5, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee1":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee1, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee2":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee2, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee3":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee3, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    default:
                                        break;
                                }
                                detailAdapter.notifyDataSetChanged();
                            } else {
                                String[] parts = result.split(",", 7);
                                String a = parts[0]; //pid
                                String b = parts[1]; //cups
                                String c = parts[2]; //sweet
                                String d = parts[3]; //coldhot
                                String e = parts[4]; //total
                                String f = parts[5]; //eachprice
                                String g = parts[6]; //pic
                                String h = ""; //remain
                                result = h;

                                char coffeeName = a.charAt(3);

                                switch (coffeeName) {
                                    case '0':
                                        a = "欸黑咖啡";
                                        break;
                                    case '1':
                                        a = "耶黑咖啡";
                                        break;
                                    case '2':
                                        a = "想要拿鐵";
                                        break;
                                    case '3':
                                        a = "不要拿鐵";
                                        break;
                                    case '4':
                                        a = "給我拿鐵";
                                        break;
                                }

                                d = searchTemperature(d);
                                c = searchSweet(c);

                                String tempNsweet = d + "/" + c;
                                switch (g) {
                                    case "coffee4":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee4, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee5":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee5, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee1":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee1, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee2":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee2, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    case "coffee3":
                                        recordDetailList.add(new RecordDetail(R.drawable.coffee3, a, tempNsweet, "$"+f, "*"+b, e+"元"));
                                        break;
                                    default:
                                        break;
                                }
                                detailAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
            }
        });
    }
    private String searchTemperature(String temperature){
        switch (temperature) {
            case "0":
                temperature = "熱";
                break;
            case "1":
                temperature = "正常";
                break;
            case "2":
                temperature = "少冰";
                break;
            case "3":
                temperature = "微冰";
                break;
            case "4":
                temperature = "去冰";
                break;
        }
        return temperature;
    }

    private String searchSweet(String sweet){
        switch (sweet) {
            case "0":
                sweet = "全糖";
                break;
            case "1":
                sweet = "少糖";
                break;
            case "2":
                sweet = "半糖";
                break;
            case "3":
                sweet = "微糖";
                break;
            case "4":
                sweet = "無糖";
                break;
        }
        return sweet;
    }
}