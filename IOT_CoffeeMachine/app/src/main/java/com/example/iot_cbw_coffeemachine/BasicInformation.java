package com.example.iot_cbw_coffeemachine;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class BasicInformation extends AppCompatActivity {
    EditText et_nickname;
    TextView tv_bluetooth, tv_birthday;
    Button previousBtn, finishBtn;
    RadioGroup radioGroup;
    RadioButton radioButton;
    CheckBox cb_milk, cb_sugar;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    String allergen = "", gender = "";
    private SharedPreferences user;
    private SharedPreferences.Editor user_editor;
    private BluetoothAdapter mBtAdapter;

    // 192.168.50.204
    // 192.168.1.153
//    String ipAddress= "192.168.50.204";

    Universal_lib lib = new Universal_lib();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_information);

        et_nickname = findViewById(R.id.nickname);
        tv_birthday = findViewById(R.id.birthday);
        radioGroup = findViewById(R.id.genderGroup);
        cb_milk = findViewById(R.id.milk);
        cb_sugar = findViewById(R.id.sugar);
        previousBtn = findViewById(R.id.previous);
        finishBtn = findViewById(R.id.finish);
        tv_bluetooth = findViewById(R.id.bluetooth);

        //String androidID = Secure.getString(getApplicationContext().getContentResolver(),
        //       Secure.ANDROID_ID);
        //tv_bluetooth.setText(androidID);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BasicInformation.this, SignUp.class));
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(selectedId);
                String nickname, genderBtn, birthday, bluetooth;
                nickname = String.valueOf(et_nickname.getText());
                genderBtn = radioButton.getText().toString();
                birthday = tv_birthday.getText().toString();
//                bluetooth = tv_bluetooth.getText().toString();
                bluetooth = "9433236";

                if (genderBtn.equals("男")) {
                    gender = "0";
                } else {
                    gender = "1";
                }

                if (cb_milk.isChecked()) {
                    allergen = allergen + 0;
                }
                if (cb_sugar.isChecked()) {
                    allergen = allergen + 1;
                }

                if (!nickname.equals("") && !gender.equals("") && !birthday.equals("") && !bluetooth.equals("")) {
                    //Start ProgressBar first (Set visibility VISIBLE)
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = getIntent();
                            String name = intent.getStringExtra("name");
                            String email = intent.getStringExtra("email");
                            String password = intent.getStringExtra("password");
                            String phone = intent.getStringExtra("phone");

                            String[] field_check = new String[3];
                            field_check[0] = "phone";
                            field_check[1] = "email";
                            field_check[2] = "bluetooth";
                            String[] data_check = new String[3];
                            data_check[0] = phone;
                            data_check[1] = email;
                            data_check[2] = bluetooth;
                            // 192.168.1.153
                            // 192.168.50.204
                            PutData putData_check = new PutData("http://"+lib.ipAddress+"/coffee/checkSignup.php", "POST", field_check, data_check);
                            if (putData_check.startPut()) {
                                if (putData_check.onComplete()) {
                                    String result_check = putData_check.getResult();

                                    if (result_check.equals("YYY")) {
                                        String[] field = new String[9];
                                        field[0] = "name";
                                        field[1] = "email";
                                        field[2] = "password";
                                        field[3] = "phone";
                                        field[4] = "nickname";
                                        field[5] = "gender";
                                        field[6] = "birthday";
                                        field[7] = "allergen";
                                        field[8] = "bluetooth";

                                        String[] data = new String[9];
                                        data[0] = name;
                                        data[1] = email;
                                        data[2] = password;
                                        data[3] = phone;
                                        data[4] = nickname;
                                        data[5] = gender;
                                        data[6] = birthday;
                                        data[7] = allergen;
                                        data[8] = bluetooth;

                                        PutData putData = new PutData("http://"+lib.ipAddress+"/coffee/addSignup.php", "POST", field, data);
                                        if (putData.startPut()) {
                                            if (putData.onComplete()) {
                                                String result = putData.getResult();
                                                if (result.equals("true")) {
                                                    Toast.makeText(BasicInformation.this, "註冊成功", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BasicInformation.this, Login.class));
                                                } else {
                                                    Toast.makeText(BasicInformation.this, "註冊失敗", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        user = getApplicationContext().getSharedPreferences("user", Context.MODE_PRIVATE);
                                        user_editor = user.edit();
                                        user_editor.putString("phone", phone);
                                        user_editor.putString("bluetooth", bluetooth);
                                        user_editor.commit();
                                    } else {
                                        Toast.makeText(BasicInformation.this, result_check, Toast.LENGTH_SHORT).show();
//                                        Toast.makeText(BasicInformation.this,"信箱/電話/藍芽已被使用",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                } else {
                    Toast.makeText(getApplicationContext(), "All fields should be required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tv_bluetooth.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
//                try {
//                    List<NetworkInterface> networkInterfaceList = Collections.list(NetworkInterface.getNetworkInterfaces());
//                    String stringMac = "";
//                    for (NetworkInterface networkInterface : networkInterfaceList) {
//                        if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
//                            for (int i = 0; i < networkInterface.getHardwareAddress().length; i++) {
//                                String stringMacByte = Integer.toHexString(networkInterface.getHardwareAddress()[i] & 0xFF);
//
//                                if (stringMacByte.length() == 1) {
//                                    stringMacByte = "0" + stringMacByte;
//                                }
//
//                                stringMac = stringMac + stringMacByte.toUpperCase() + ":";
//                            }
//                            break;
//                        }
//                    }
//                    tv_bluetooth.setText(stringMac.substring(0, stringMac.length() - 1));
//                } catch (SocketException e) {
//                    e.printStackTrace();
//                }

//                Intent intent = new Intent(getApplicationContext(), activity_blue.class);
//                startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);

//                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//                if (bluetoothAdapter == null) {
//                    // Alert user that Bluetooth is not available
//                    Toast.makeText(BasicInformation.this
//                            , "Sorry, your device does not support Bluetooth."
//                            , Toast.LENGTH_LONG).show();
//                }
//
//                if (!bluetoothAdapter.isEnabled()) {
//                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                    activityResultLauncher.launch(intent);
//                }
//
//                BluetoothDevice device = bluetoothAdapter.getRemoteDevice("");
//                BluetoothSocket tmp = null;
//                BluetoothSocket mmSocket = null;
//
//// Get a BluetoothSocket for a connection with the
//// given BluetoothDevice
//                try {
//                    if (ActivityCompat.checkSelfPermission(BasicInformation.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    tmp = device.createRfcommSocketToServiceRecord("");
//                    Method m = device.getClass().getMethod("createRfcommSocket", new Class[] {int.class});
//                    tmp = (BluetoothSocket) m.invoke(device, 1);
//                } catch (IOException | NoSuchMethodException e) {
//                    Log.e(TAG, "create() failed", e);
//                } catch (InvocationTargetException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                mmSocket = tmp;

//                //取得已配對裝置
//                Set<BluetoothDevice> srcDevices = bluetoothAdapter.getBondedDevices();
//
//                //如果知道MAC Address，也可以這麼取得Device
//                BluetoothDevice device = bluetoothAdapter.getRemoteDevice("");
//                device.getUuids();
//
//                //與裝置連線
//                BluetoothSocket socket = device.createRfcommSocketToServiceRecord(uuid);
//                socket.connect();

                //android_id
//                String androidId = Secure.getString(getApplicationContext().getContentResolver(),
//                        Secure.ANDROID_ID);

                //secure uuid
//                String androidId = Settings.Secure.getString(getApplicationContext().getContentResolver(),
//                        Settings.Secure.ANDROID_ID);
//
//                UUID androidId_UUID = UUID
//                        .nameUUIDFromBytes(androidId.getBytes(StandardCharsets.UTF_8));
//
//                String unique_id = androidId_UUID.toString();

//                tv_bluetooth.setText("");

                /*
                if (!checkWIFIPermission()) {
                    requestWIFIPermission();
                } else {
                    TelephonyManager tManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    String uuid = tManager.getDeviceId();
                }*/

                /*
                mBtAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBtAdapter == null)
                    tv_bluetooth.setText("您的裝置沒有支援藍芽");

                if (!mBtAdapter.isEnabled()) {
                    Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    activityResultLauncher.launch(enableIntent);
                } else {
                    if (!checkBTPermission()) {
                        requestBTPermission();
                    } else {
//                        String deviceMacAddress = mBtAdapter.getAddress();
                        String macAddress = android.provider.Settings.Secure.getString(getApplicationContext().getContentResolver(), "bluetooth_address");
                        tv_bluetooth.setText(macAddress);
                    }
                }*/
            }
        });

        tv_birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        BasicInformation.this,
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
                tv_birthday.setText(date);
            }
        };
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestBTPermission() {
        requestPermissions(new String[]{Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN}, 100);
    }

    public boolean checkBTPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestWIFIPermission() {
        requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, 100);
    }

    public boolean checkWIFIPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
        return res1;
    }

    private String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(Integer.toHexString(b & 0xFF) + ":");
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return "MAC-address: " + res1.toString();
            }
        } catch (Exception ex) {
            return "getting MAC-address error";
        }
        return "getting MAC-address error";
    }
}