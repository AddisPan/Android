package com.example.a108222040_toolbars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_bar);
    }//end onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int getId = item.getItemId();
        switch (getId){
            case R.id.call:
                Toast.makeText(this,"Call",Toast.LENGTH_SHORT).show();
                break;
            case R.id.star:
                Toast.makeText(this,"Star",Toast.LENGTH_SHORT).show();
                break;
            case R.id.speak:
                Toast.makeText(this,"Speak",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }//end onCreateOptionsMenu


}//end class