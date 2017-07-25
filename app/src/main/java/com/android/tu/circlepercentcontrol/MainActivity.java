package com.android.tu.circlepercentcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.tu.circlelibrary.CirclePercentBar;

public class MainActivity extends AppCompatActivity {

    CirclePercentBar circlePercentBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circlePercentBar= (CirclePercentBar) findViewById(R.id.circle_bar);
        circlePercentBar.setPercentData(55.2f);
    }
}
