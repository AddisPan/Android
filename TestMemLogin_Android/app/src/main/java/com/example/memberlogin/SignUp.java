package com.example.memberlogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {
    //全域變數，把使用者輸入的東西存進來
    TextInputEditText txtFullname, txtUsername, txtEmail, txtPassword;
    Button btnSignup;
    TextView textviewLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtFullname = findViewById(R.id.fullname);
        txtUsername = findViewById(R.id.username);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.password);
        btnSignup = findViewById(R.id.btnSignUp);
        textviewLogin = findViewById(R.id.loginText);
        progressBar = findViewById(R.id.progress);

        textviewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class); //打開login的介面
                startActivity(intent);
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullname, username, password, email;
                fullname = String.valueOf(txtFullname.getText());
                username = String.valueOf(txtUsername.getText());
                password = String.valueOf(txtPassword.getText());
                email = String.valueOf(txtEmail.getText());

                //檢查輸入的值裡面沒有是空值的
                //如果使用者輸入的值都沒有，會做if裡的動作
                if(!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")){
                    //set progressbar visible
                    progressBar.setVisibility(View.VISIBLE);
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //Starting Write and Read data with URL
                            //Creating array for parameters
                            String[] field = new String[4];
                            field[0] = "fullname";
                            field[1] = "username";
                            field[2] = "password";
                            field[3] = "email";

                            //存使用者在手機上輸入的資料
                            String[] data = new String[4];
                            data[0] = "fullname";
                            data[1] = "username";
                            data[2] = "password";
                            data[3] = "email";
                            //連到後端資料庫
                            PutData putData = new PutData("http://192.168.0.166/LoginRegister/signup.php", "POST", field, data);
                            if (putData.startPut()) {
                                if (putData.onComplete()) {
                                    progressBar.setVisibility(View.GONE); //看不見
                                    String result = putData.getResult();
                                    //如果註冊成功會開啟login畫面
                                    if(result.equals("Sign Up Success")){
                                        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
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
