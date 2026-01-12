package com.example.iot_cbw_coffeemachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

public class SignUp extends AppCompatActivity {
    EditText et_name, et_email, et_password, et_phone;
    Button signupBtn;
    TextView tv_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_name = findViewById(R.id.name);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        et_phone = findViewById(R.id.phone);

        signupBtn = findViewById(R.id.signUpBtn);
        tv_signup = findViewById(R.id.signUpTv);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, password, phone;
                name = String.valueOf(et_name.getText());
                email = String.valueOf(et_email.getText());
                password = String.valueOf(et_password.getText());
                phone = String.valueOf(et_phone.getText());

                if (!name.equals("") && !email.equals("") && !password.equals("") && !phone.equals("")) {
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[4];
                            field[0] = "name";
                            field[1] = "email";
                            field[2] = "password";
                            field[3] = "phone";
                            String[] data = new String[4];
                            data[0] = name;
                            data[1] = email;
                            data[2] = password;
                            data[3] = phone;

                            Intent intent = new Intent(SignUp.this, BasicInformation.class);
                            intent.putExtra(field[0], data[0]);
                            intent.putExtra(field[1], data[1]);
                            intent.putExtra(field[2], data[2]);
                            intent.putExtra(field[3], data[3]);
                            startActivity(intent);
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "姓名/信箱/密碼/電話不得為空", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }
}