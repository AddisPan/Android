package com.example.iot_cbw_coffeemachine;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class activity_combination_list extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText et_name;
    private Spinner sp_product; // sp_temp, sp_sweet
    private Button btn_save;
    private SharedPreferences combine, cid, order;
    private SharedPreferences.Editor combine_editor, order_editor;

    SeekBar seekbar_temp, seekbar_sweet;
    TextView tv_tempResult, tv_sweetResult;
    String temperature = "", sweet = "";
ImageView iv_arrow;
    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress = "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combination_list);

        toolbar = findViewById(R.id.toolbar);
        et_name = findViewById(R.id.et_name);
        sp_product = findViewById(R.id.spinner_product);
        seekbar_temp = findViewById(R.id.seekbar_temp);
        seekbar_sweet = findViewById(R.id.seekbar_sweet);
        tv_tempResult = findViewById(R.id.tv_tempResult);
        tv_sweetResult = findViewById(R.id.tv_sweetResult);
        btn_save = findViewById(R.id.btn_save);
        iv_arrow = findViewById(R.id.iv_arrow);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        iv_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                finish();
                startActivity(new Intent(activity_combination_list.this,MainActivity.class));
            }
        });

        combine = getApplicationContext().getSharedPreferences("combine", Context.MODE_PRIVATE);
        combine_editor = combine.edit();

        ArrayAdapter productAdapter = ArrayAdapter.createFromResource(this, R.array.product4combine_value, R.layout.spinner_text);
        sp_product.setAdapter(productAdapter);

        sp_product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String result = parent.getItemAtPosition(position).toString();
                combine_editor.putString("product", result);
                combine_editor.commit();
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        seekbar_temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        tv_tempResult.setText("熱");
                        break;
                    case 1:
                        tv_tempResult.setText("正常");
                        break;
                    case 2:
                        tv_tempResult.setText("少冰");
                        break;
                    case 3:
                        tv_tempResult.setText("微冰");
                        break;
                    case 4:
                        tv_tempResult.setText("去冰");
                        break;
                    default:
                        break;
                }
                combine_editor.putString("temperature", String.valueOf(progress));
                combine_editor.commit();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekbar_sweet.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                switch (progress) {
                    case 0:
                        tv_sweetResult.setText("無糖");
                        break;
                    case 1:
                        tv_sweetResult.setText("一分糖");
                        break;
                    case 2:
                        tv_sweetResult.setText("二分糖");
                        break;
                    case 3:
                        tv_sweetResult.setText("三分糖");
                        break;
                    case 4:
                        tv_sweetResult.setText("四分糖");
                        break;
                    case 5:
                        tv_sweetResult.setText("五分糖");
                        break;
                    case 6:
                        tv_sweetResult.setText("六分糖");
                        break;
                    case 7:
                        tv_sweetResult.setText("七分糖");
                        break;
                    case 8:
                        tv_sweetResult.setText("八分糖");
                        break;
                    case 9:
                        tv_sweetResult.setText("九分糖");
                        break;
                    case 10:
                        tv_sweetResult.setText("全糖");
                        break;
                    default:
                        break;
                }
                if (progress == 10) {
                    combine_editor.putString("sweet", "A");
                    combine_editor.commit();
                } else {
                    combine_editor.putString("sweet", String.valueOf(progress));
                    combine_editor.commit();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        ArrayAdapter tempAdapter = ArrayAdapter.createFromResource(this, R.array.temp4order_value, R.layout.spinner_text);
//        sp_temp.setAdapter(tempAdapter);
//        sp_temp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String result = parent.getItemAtPosition(position).toString();
////                combine_editor.putString("temperature", result);
////                combine_editor.commit();
//                switch (result) {
//                    case "熱":
//                        combine_editor.putString("temperature", "0");
//                        combine_editor.commit();
//                        break;
//
//                    case "正常":
//                        combine_editor.putString("temperature", "1");
//                        combine_editor.commit();
//                        break;
//
//                    case "少冰":
//                        combine_editor.putString("temperature", "2");
//                        combine_editor.commit();
//                        break;
//
//                    case "微冰":
//                        combine_editor.putString("temperature", "3");
//                        combine_editor.commit();
//                        break;
//
//                    case "去冰":
//                        combine_editor.putString("temperature", "4");
//                        combine_editor.commit();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        ArrayAdapter sweetAdapter = ArrayAdapter.createFromResource(this, R.array.sweet_value, R.layout.spinner_text);
//        sp_sweet.setAdapter(sweetAdapter);
//        sp_sweet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String result = parent.getItemAtPosition(position).toString();
////                combine_editor.putString("sweet", result);
////                combine_editor.commit();
//
//                switch (result) {
//                    case "全糖":
//                        combine_editor.putString("sweet", "0");
//                        combine_editor.commit();
//                        break;
//
//                    case "少糖":
//                        combine_editor.putString("sweet", "1");
//                        combine_editor.commit();
//                        break;
//
//                    case "半糖":
//                        combine_editor.putString("sweet", "2");
//                        combine_editor.commit();
//                        break;
//
//                    case "微糖":
//                        combine_editor.putString("sweet", "3");
//                        combine_editor.commit();
//                        break;
//
//                    case "無糖":
//                        combine_editor.putString("sweet", "4");
//                        combine_editor.commit();
//                        break;
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(activity_combination_list.this)
                        .setTitle("儲存確認")
                        .setMessage("確定要儲存嗎?")
                        .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
                                String c_id = cid.getString("c_id", "");

                                String product = combine.getString("product", "");
                                String combine_name = String.valueOf(et_name.getText());

                                temperature = combine.getString("temperature", "");
                                sweet = combine.getString("sweet", "");
                                if (temperature.isEmpty()) {
                                    temperature = "0";
                                }
                                if (sweet.isEmpty()) {
                                    sweet = "5";
                                }
//                                String temperature = tv_tempResult.getText().toString();
//                                String sweet = tv_sweetResult.getText().toString();

                                Handler handler = new Handler();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        String[] field = new String[6];
                                        field[0] = "cp_id";
                                        field[1] = "c_id";
                                        field[2] = "p_id";
                                        field[3] = "cp_name";
                                        field[4] = "sweet";
                                        field[5] = "coldhot";
                                        String[] data = new String[6];
                                        data[0] = searchCPid(product, temperature, sweet); //5
                                        data[1] = c_id;
                                        data[2] = searchPid(product); //5
                                        data[3] = combine_name;
                                        data[4] = sweet;
                                        data[5] = temperature;

                                        PutData putData = new PutData("http://" + lib.ipAddress + "/coffee/addCustomerProduct.php", "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                if (result.equals("true")) {
                                                    Toast.makeText(activity_combination_list.this, "儲存成功", Toast.LENGTH_SHORT).show();
                                                    combine_editor.clear().commit();
                                                    startActivity(new Intent(activity_combination_list.this, Activity_combination.class));
                                                } else {
                                                    Toast.makeText(activity_combination_list.this, "儲存失敗", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    }
                                });
                                startActivity(new Intent(getApplicationContext(), Activity_combination.class));
                            }

                        })
                        .setNegativeButton("取消", null)
                        .show();
            }
        });
    }

    private String searchCPid(String productName, String temperature, String sweet) {
        String cpid = "CP";
        switch (productName) {
            case "黑咖啡":
                cpid = cpid + 0;
                break;
            case "拿鐵":
                cpid = cpid + 1;
                break;
            default:
                break;
        }
        switch (temperature) {
            case "熱":
                cpid = cpid + 0;
                break;
            case "正常":
                cpid = cpid + 1;
                break;
            case "少冰":
                cpid = cpid + 2;
                break;
            case "微冰":
                cpid = cpid + 3;
                break;
            case "去冰":
                cpid = cpid + 4;
                break;
            default:
                break;
        }

        switch (sweet) {
            case "無糖":
                cpid = cpid + 0;
                break;
            case "一分糖":
                cpid = cpid + 1;
                break;
            case "二分糖":
                cpid = cpid + 2;
                break;
            case "三分糖":
                cpid = cpid + 3;
                break;
            case "四分糖":
                cpid = cpid + 4;
                break;
            case "五分糖":
                cpid = cpid + 5;
                break;
            case "六分糖":
                cpid = cpid + 6;
                break;
            case "七分糖":
                cpid = cpid + 7;
                break;
            case "八分糖":
                cpid = cpid + 8;
                break;
            case "九分糖":
                cpid = cpid + 9;
                break;
            case "全糖":
                cpid = cpid + "A";
                break;
            default:
                break;
        }
        cpid = cpid + temperature + sweet;
        return cpid;
    }

    private String searchPid(String productName) {
        String pid = "";
        switch (productName) {
            case "黑咖啡":
                pid = "P010";
                break;
            case "拿鐵":
                pid = "P110";
                break;
            default:
                break;
        }
        return pid;
    }
}