package com.example.five_two;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class OxhornaiActivity extends AppCompatActivity {

    MediaPlayer player;
    //private FloatingActionButton fab1;
    private OxhornPanelai oxhornPanelai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxhornai);
        final ToggleButton tB = (ToggleButton) findViewById(R.id.button_son1);
        tB.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View arg0) {

                if (tB.isChecked()) {
                    tB.setBackgroundResource(R.drawable.aoff);
                    player = MediaPlayer.create(OxhornaiActivity.this, R.raw.uu);
                    player.start();
                    player.setLooping(true);

                } else {
                    player.pause();
                    tB.setBackgroundResource(R.drawable.aon);

                }
            }
        });


        Button Button5 = (Button) this.findViewById(R.id.button_rules1);
        Button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OxhornaiActivity.this, Rules1Activity.class);
                startActivity(intent);
            }
        });


        oxhornPanelai = (OxhornPanelai) findViewById(R.id.id_oxhornai);
        ImageButton more2 = (ImageButton) findViewById(R.id.more3);
        more2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                oxhornPanelai.start();
            }
        });
    }
}