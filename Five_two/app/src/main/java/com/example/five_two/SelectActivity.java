package com.example.five_two;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class SelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        /*ImageButton Button1 = (ImageButton)this.findViewById(R.id.selectImageButton1);
        Button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(SelectActivity.this, UnderwayActivity.class);
                startActivity(intent);
            }
        });*/

        ImageButton on = (ImageButton) this.findViewById(R.id.selectImageButton1);
        on.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(SelectActivity.this, UnderwayActivity.class));
            }
        });

        ImageButton onr = (ImageButton) this.findViewById(R.id.selectImageButton2);
        onr.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(SelectActivity.this,R.style.AlertDialog);
                builder.setTitle("请选择");
                builder.setMessage("人机对战or双人对战");

                builder.setPositiveButton("人机",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent=new Intent(SelectActivity.this, OxhornaiActivity.class);
                        startActivity(intent);
                    }}
                );
                builder.setNegativeButton("双人",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent=new Intent(SelectActivity.this, OxhornActivity.class);
                        startActivity(intent);
                    }}
                );
                builder.create().show();
            }
        });



        /*ImageButton twoi = (ImageButton) this.findViewById(R.id.selectImageButton7);
        twoi.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                startActivity(new Intent(SelectActivity.this, WinnerActivity.class));
            }
        });*/


    }


}
