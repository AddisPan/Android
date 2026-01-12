package com.example.iot_cbw_coffeemachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.FetchData;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    EditText et_email, et_password;
    Button loginBtn;
    TextView tv_login;
    private SharedPreferences cid, user;
    private SharedPreferences.Editor cid_editor, user_editor;

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        loginBtn = findViewById(R.id.loginButton);
        tv_login = findViewById(R.id.loginText);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email, password;
                email = String.valueOf(et_email.getText());
                password = String.valueOf(et_password.getText());

                if(!email.equals("") && !password.equals("")){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "email";
                            field[1] = "password";
                            String[] data = new String[2];
                            data[0] = email;
                            data[1] = password;
                            PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/login.php","POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    String result = putData.getResult();
                                    String[] parts = result.split(",");
                                    String tf = parts[0];
                                    if(tf.equals("true")){
                                        String c_id = parts[1]; //maybe need to count comma
                                        Toast.makeText(Login.this, c_id + " 登入成功", Toast.LENGTH_SHORT).show();
                                        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
                                        cid_editor = cid.edit();
                                        cid_editor.putString("c_id", c_id);
                                        cid_editor.putBoolean("islogin",true);
                                        user = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                        user_editor = user.edit();
                                        user_editor.putString("email", email);
                                        user_editor.putString("password", password);
                                        user_editor.commit();
                                        cid_editor.commit();
                                        startActivity(new Intent(Login.this, MainActivity.class));
                                    }
                                    else{
//                                        Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(Login.this, result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "信箱/密碼不得為空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });
    }
}
