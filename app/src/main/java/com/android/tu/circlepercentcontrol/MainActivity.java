package com.android.tu.circlepercentcontrol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import com.android.tu.circlelibrary.CirclePercentBar;

public class MainActivity extends AppCompatActivity {

    CirclePercentBar circlePercentBar;

    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circlePercentBar= (CirclePercentBar) findViewById(R.id.circle_bar);
        circlePercentBar.setPercentData(55.2f,new DecelerateInterpolator());

        startBtn= (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                circlePercentBar.setPercentData((float) (100*Math.random()),new DecelerateInterpolator());
            }
        });
    }
}
