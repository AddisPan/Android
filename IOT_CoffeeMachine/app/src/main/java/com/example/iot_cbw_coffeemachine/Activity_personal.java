package com.example.iot_cbw_coffeemachine;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
//import com.canhub.cropper
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Activity_personal extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView img_person;
    private TextView tv_date;
    private EditText et_name, et_nickname;
    private RadioGroup radioGroup;
    RadioButton radioButton;
    private RadioButton rb_male, rb_female;
    private CheckBox cb_milk, cb_sugar;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private SharedPreferences cid, user;
    private SharedPreferences.Editor user_editor;
    String allergen = "", gender = "";

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        img_person = findViewById(R.id.iv_personal);
        toolbar = findViewById(R.id.toolbar);
        tv_date = findViewById(R.id.tv_date);
        et_name = findViewById(R.id.et_name);
        et_nickname = findViewById(R.id.et_nickname);
        radioGroup = findViewById(R.id.rg_gender);
        rb_male = findViewById(R.id.rb_male);
        rb_female = findViewById(R.id.rb_female);
        cb_milk = findViewById(R.id.cb_milk);
        cb_sugar = findViewById(R.id.cb_sugar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[1];
                field[0] = "c_id";
                String[] data = new String[1];
                data[0] = c_id;
                PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/searchPersonal.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        while (!result.isEmpty()) {
                            int commas = 0;
                            for (int i = 0; i < result.length(); i++) {
                                if (result.charAt(i) == ',') commas++;
                            }
                            if (commas == 9) {
                                String[] parts = result.split(",", 10);
                                String a = parts[0]; //c_id
                                String b = parts[1]; //name
                                String c = parts[2]; //phone
                                String d = parts[3]; //email
                                String e = parts[4]; //bluetooth
                                String f = parts[5]; //password maybe
                                String g = parts[6]; //nickname
                                String h = parts[7]; //gender
                                String i = parts[8]; //birth
                                String j = parts[9]; //allergen
                                String k = "";
                                result = k;

                                et_name.setText(b);
                                et_nickname.setText(g);
                                tv_date.setText(i);

                                if (h.equals("0")) {
                                    rb_male.setChecked(true);
                                } else {
                                    rb_female.setChecked(true);
                                }

                                if (j.equals("0")) {
                                    cb_milk.setChecked(true);
                                } else if (j.equals("1")) {
                                    cb_sugar.setChecked(true);
                                } else if (j.equals("01")) {
                                    cb_milk.setChecked(true);
                                    cb_sugar.setChecked(true);
                                }

//                                user = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//                                user_editor = user.edit();
//                                user_editor.putString("cid",a);
//                                user_editor.putString("phone",c);
//                                user_editor.putString("email",d);
//                                user_editor.putString("password",f);
//                                user_editor.putString("bluetooth",e);
//                                user_editor.commit();
                            }
                        }
                    }
                }
            }
        });

        android.app.ActionBar actionBar = getActionBar();
        toolbar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        actionBar.show();
                }
                return false;
            }
        });

        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Activity_personal.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = year + "/" + month + "/" + day;
                tv_date.setText(date);
            }
        };

//        img_person.setOnClickListener(new View.OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.M)
//            @Override
//            public void onClick(View view) {
//                boolean pick = true;
//                if (pick == true) {
//                    if (!checkCameraPermission()) {
//                        requestCameraPermission();
//                    } else PickImage();
//                } else {
//                    if (!checkStoragePermission()) {
//                        requestStoragePermission();
//                    } else PickImage();
//                }
//            }
//        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);
        String name, nickname, genderBtn, birthday;
        name = String.valueOf(et_name.getText());
        nickname = String.valueOf(et_nickname.getText());
        genderBtn = radioButton.getText().toString();
        birthday = tv_date.getText().toString();

        if(genderBtn.equals("男")){
            gender = "0";
        }else{
            gender = "1";
        }

        if (cb_milk.isChecked()) {
            allergen = allergen + 0;
        }
        if (cb_sugar.isChecked()) {
            allergen = allergen + 1;
        }

        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
        String c_id = cid.getString("c_id", "");
        user = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        String c_id = user.getString("cid","");
        String phone = user.getString("phone","");
        String email = user.getString("email","");
        String password = user.getString("password","");
        String bluetooth = user.getString("bluetooth","");
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                String[] field = new String[10];
                field[0] = "c_id";
                field[1] = "name";
                field[2] = "phone";
                field[3] = "email";
                field[4] = "password";
                field[5] = "bluetooth";
                field[6] = "nickname";
                field[7] = "gender";
                field[8] = "birthday";
                field[9] = "allergen";
                String[] data = new String[10];
                data[0] = c_id;
                data[1] = name;
                data[2] = phone;
                data[3] = email;
                data[4] = password;
                data[5] = bluetooth;
                data[6] = nickname;
                data[7] = gender;
                data[8] = birthday;
                data[9] = allergen;

                PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/editPersonal.php", "POST", field, data);
                if (putData.startPut()) {
                    if (putData.onComplete()) {
                        String result = putData.getResult();
                        if(result.equals("true")){
                            Toast.makeText(Activity_personal.this,"成功修改囉777",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(Activity_personal.this,result,Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        int selectedId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(selectedId);
//        String name, nickname, genderBtn, birthday;
//        name = String.valueOf(et_name.getText());
//        nickname = String.valueOf(et_nickname.getText());
//        genderBtn = radioButton.getText().toString();
//        birthday = tv_date.getText().toString();
//
//        if(genderBtn.equals("男")){
//            gender = "0";
//        }else{
//            gender = "1";
//        }
//
//        if (cb_milk.isChecked()) {
//            allergen = allergen + 0;
//        }
//        if (cb_sugar.isChecked()) {
//            allergen = allergen + 1;
//        }
//
//        user = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
//        String c_id = user.getString("cid","");
//        String phone = user.getString("phone","");
//        String email = user.getString("email","");
//        String password = user.getString("password","");
//        String bluetooth = user.getString("bluetooth","");
//        Handler handler = new Handler();
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                String[] field = new String[10];
//                field[0] = "c_id";
//                field[1] = "name";
//                field[2] = "phone";
//                field[3] = "email";
//                field[4] = "password";
//                field[5] = "bluetooth";
//                field[6] = "nickname";
//                field[7] = "gender";
//                field[8] = "birth";
//                field[9] = "allergen";
//                String[] data = new String[10];
//                data[0] = c_id;
//                data[1] = name;
//                data[2] = phone;
//                data[3] = email;
//                data[4] = password;
//                data[5] = bluetooth;
//                data[6] = nickname;
//                data[7] = gender;
//                data[8] = birthday;
//                data[9] = allergen;
//
//                PutData putData = new PutData("http://192.168.1.153/coffee/editPersonal.php", "POST", field, data);
//                if (putData.startPut()) {
//                    if (putData.onComplete()) {
//                        String result = putData.getResult();
//                        if(result.equals("true")){
//                            Toast.makeText(Activity_personal.this,"成功修改囉777",Toast.LENGTH_SHORT).show();
//                        }else{
//                            Toast.makeText(Activity_personal.this,"失敗!!",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
//        });
    }

//    public void PickImage() {
//        CropImage.activity().start(this);
//    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestStoragePermission() {
        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestCameraPermission() {
        requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    public boolean checkStoragePermission() {
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res2;
    }

    public boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Context applicationContext = MainActivity.getContextOfApplication();
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                Uri resultUri = result.getUri();
//                Picasso.get().load(resultUri).into(img_person);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
//    }
}
