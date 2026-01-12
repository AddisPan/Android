package com.example.iot_cbw_coffeemachine;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNav;

    // a static variable to get a reference of our application context
    public static Context contextOfApplication;
    public static Context getContextOfApplication()
    {
        return contextOfApplication;
    }
    private SharedPreferences cid, rec;
    private SharedPreferences.Editor cid_editor, rec_editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        if (getIntent().getBooleanExtra("LOGOUT", false))
//        {
//            finish();
//        }

        Uri uri = getIntent().getData();

//        //取得URL所帶進來的Intent物件
//        Intent tIntent = this.getIntent();
//        //取得Schema
//        String tSchema = tIntent.getScheme();
//
//        //取得URL
//        Uri myURI = tIntent.getData();
        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
        rec = getApplicationContext().getSharedPreferences("rec", Context.MODE_PRIVATE);
        rec_editor = rec.edit();
        String recommend = rec.getString("recommend","");
        Dialog dialog = new Dialog(this);
        ArrayAdapter<CharSequence> quantityAdapter = ArrayAdapter.createFromResource(MainActivity.this, R.array.quantity_value, R.layout.spinner_text);
//        Fragment_information information = new Fragment_information();
//        information.openRecommendDialog(quantityAdapter, dialog, cid, MainActivity.this);
        if (uri != null) {

//            rec_editor.putString("uri", "true");
//
////            Toast.makeText(MainActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
////            Log.d("TAG", "uri?????????????? " + uri);
//            rec_editor.putString("recommend", "true");
//            rec_editor.commit();
////            Fragment_information information = new Fragment_information();
////            information.openRecommendDialog(dialog, cid);
        } else if (recommend.equals("true")){
//            Fragment_information information = new Fragment_information();
//            information.openRecommendDialog(dialog, cid);
        }else{
//            new AlertDialog.Builder(MainActivity.this)
//                    .setTitle("掃描QRCode")
//                    .setMessage("請至現場掃描QRCode登入")
//                    .setPositiveButton("確認", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//                            android.os.Process.killProcess(android.os.Process.myPid());
//                            System.exit(0);
//                        }
//                    })
//                    .show();
        }

//        cid = getApplicationContext().getSharedPreferences("cid", Context.MODE_PRIVATE);
        boolean islogin = cid.getBoolean("islogin",false);
        if(islogin){
            //statusbar color
            Window window = MainActivity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.bg_title));

            bottomNav = findViewById(R.id.bottom_navigation);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new Fragment_information()).commit();
            bottomNav.setSelectedItemId(R.id.nav_information); // may not need this
            bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.nav_information:
                            selectedFragment = new Fragment_information();
                            break;

                        case R.id.nav_list:
                            selectedFragment = new Fragment_list();
                            break;

                        case R.id.nav_order:
                            selectedFragment = new Fragment_order();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    return true;
                }
            });
            contextOfApplication = getApplicationContext(); //for crop image
        }else{
            startActivity(new Intent(MainActivity.this, Login.class));
        }


//        if (savedInstanceState != null) {
//            //Restore the fragment's instance
//            fragment_order = (Fragment_order) getSupportFragmentManager().getFragment(savedInstanceState, "myFragment");
//        }

//        Intent intent = getIntent();
//        String message = intent.getStringExtra("coffee");
//
//        Bundle bundle = new Bundle();
//        bundle.putString("coffee", message);
//
//        Fragment_order orderObj = new Fragment_order();
//        orderObj.setArguments(bundle);
    }

//    @Override
//    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
//        super.onSaveInstanceState(outState, outPersistentState);
//        getSupportFragmentManager().putFragment(outState,"myFragment",fragment_order);
//    }

}