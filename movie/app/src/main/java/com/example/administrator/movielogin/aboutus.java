package com.example.administrator.movielogin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class aboutus extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        final TextView logView=(TextView)findViewById(R.id.textView10);
        logView.setMovementMethod(ScrollingMovementMethod.getInstance());
    }
}
