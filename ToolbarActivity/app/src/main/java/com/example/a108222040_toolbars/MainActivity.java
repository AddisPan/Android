package com.example.a108222040_toolbars;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnActionBar, btnToolBar, btnAppBar, btnCollapsingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnActionBar = (Button) findViewById(R.id.btnActionbar);
        btnToolBar = (Button) findViewById(R.id.btnToolbar);
        btnAppBar = (Button) findViewById(R.id.btnAppbar);
        btnCollapsingBar = (Button) findViewById(R.id.btnCollapsingbar);

        btnActionBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActionBarActivity.class);
                startActivity(intent);
            }
        });

        btnToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ToolbarActivity.class);
                startActivity(intent);
            }
        });

        btnAppBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AppBarActivity.class);
                startActivity(intent);
            }
        });

        btnCollapsingBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollapsingActivity.class);
                startActivity(intent);
            }
        });
    }//end onCreate
}//end class