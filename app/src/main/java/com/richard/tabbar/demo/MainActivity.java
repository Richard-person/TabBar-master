package com.richard.tabbar.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.richard.tabbar.TabBarView;

public class MainActivity extends AppCompatActivity {

    private TabBarView tbv_one;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tbv_one = findViewById(R.id.tbv_one);

        tbv_one.setOnTabBarCheckedChangeListener(new TabBarView.OnTabBarCheckedChangeListener() {
            @Override
            public void checked(String itemText, int position) {
                Toast.makeText(getApplicationContext(),"已选择了 " + itemText + "   位置 : " + position,Toast.LENGTH_LONG).show();
            }
        });
    }
}
