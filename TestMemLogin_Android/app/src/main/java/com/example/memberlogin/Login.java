package com.example.memberlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class Login extends AppCompatActivity {
    TextInputEditText txtUsername, txtPassword;
    Button btnLogin;
    TextView textviewSignup;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.usernameLogin);
        txtPassword = findViewById(R.id.passwordLogin);
        btnLogin = findViewById(R.id.btnLogin);
        textviewSignup = findViewById(R.id.txtSignUp);
        progressBar = findViewById(R.id.progressLogin);

        textviewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class); //打開login的介面
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;

                username = String.valueOf(txtUsername.getText());
                password = String.valueOf(txtPassword.getText());


                //檢查輸入的值裡面沒有是空值的
                //如果使用者輸入的值都沒有，會做if裡的動作
                if(!username.equals("") && !password.equals("")){
                    //set progressbar visible
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[2];
                            field[0] = "username";
                            field[1] = "password";

                            //存使用者在手機上輸入的資料
                            String[] data = new String[2];
                            data[0] = "username";
                            data[1] = "password";
                            //連到後端資料庫
                            PutData putData = new PutData("http://192.168.0.166/LoginRegister/login.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE); //看不見
                                    String result = putData.getResult();
                                    //如果註冊成功會開啟login畫面
                                    if(result.equals("Login Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        //如果註冊失敗會出現訊息
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }else{
                    //如果使用者還沒有輸入，會跳出這個訊息
                    Toast.makeText(getApplicationContext(), "All fields are required.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }// end onCreate
}// end class
