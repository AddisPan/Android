package com.example.a108222040_toolbars;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ToolbarActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("海賊王");
        toolbar.setSubtitle("尾田榮一郎");
        toolbar.setTitleTextColor(Color.BLUE);
        toolbar.setSubtitleTextColor(Color.RED);

        Drawable drawable = resize(getResources().getDrawable(R.drawable.onepiece));
        toolbar.setLogo(drawable);

        //讓ActionBar的功能可以用在 toolbar上面
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }//end onCreate

    private Drawable resize(Drawable image) {
        Bitmap b = ((BitmapDrawable) image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, 150, 50, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }//end resize


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }//end onCreateOptionsMenu

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int getId = item.getItemId();
        switch (getId){
            case android.R.id.home:
                finish();
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