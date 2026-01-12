package com.example.iot_cbw_coffeemachine;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

public class activity_introduction extends AppCompatActivity implements CoffeeAdapter.OnCoffeeListener{
    Toolbar toolbar;
    ImageView iv_coffee;
    TextView tv_title, tv_introduction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        toolbar = findViewById(R.id.toolbar);
        iv_coffee = findViewById(R.id.iv_coffee);
        tv_title = findViewById(R.id.tv_title);
        tv_introduction = findViewById(R.id.tv_introduction);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String title = intent.getStringExtra("coffee");
        int image = intent.getIntExtra("image", 0);
        tv_title.setText(title);
        iv_coffee.setImageResource(image);
        switch (title){
            case "黑咖啡":
                tv_introduction.setText("此咖啡具有濃厚的口感，像酒一般的醇度，剛喝下有一股蜂蜜香，而後被濃厚的辛辣和黑巧克力口味所取代。具有相當濃厚實的香醇風味，且帶有較明顯的苦味與碳燒味，風韻獨具。");
            case "拿鐵":
                tv_introduction.setText("此咖啡具有濃厚的口感，像酒一般的醇度，剛喝下有一股蜂蜜香，而後被濃厚的辛辣和黑巧克力口味所取代。具有相當濃厚實的香醇風味，且帶有較明顯的苦味與碳燒味，風韻獨具。");
        }
    }

    @Override
    public void OnCoffeeClick(int position) {

    }

}