package com.example.five_two;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.ums.OperationCallback;
import com.mob.ums.User;
import com.example.five_two.UMSGUI;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import rx.Subscriber;

public class LoginActivity extends AppCompatActivity {

    //public static String TAG = "bmob";
    private EditText el_username = null;
    private EditText el_password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        Button eleven = (Button) this.findViewById(R.id.button_log);
        eleven.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                UMSGUI.setTheme(com.example.five_two.themes.defaultt.DefaultTheme.class);
                UMSGUI.showLogin(new OperationCallback<User>()
                {
                    public void onCancel(){


                    }
                });

            }
        });

        Button threenn = (Button) this.findViewById(R.id.button_exitall);
        threenn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                finish();
                System.exit(0);
            }
        });


















        }





}