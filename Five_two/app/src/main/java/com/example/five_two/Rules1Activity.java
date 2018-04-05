package com.example.five_two;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Rules1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules1);

        TextView tv =(TextView)findViewById(R.id.textView);
        TextView t =(TextView)findViewById(R.id.textView2);
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/lishu.ttf");
        tv.setTypeface(typeFace);
        t.setTypeface(typeFace);

    }
}
