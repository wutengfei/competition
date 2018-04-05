package com.example.five_two;

import android.app.Activity;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IntroductionActivity extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

                TextView tv =(TextView)findViewById(R.id.textView);
                TextView t =(TextView)findViewById(R.id.textView2);
                Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lishu.ttf");
                tv.setTypeface(typeFace);
                t.setTypeface(typeFace);




    }



}
