package br.com.conceive.activitys;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

import br.com.conceive.R;

/**
 * Created by Denis Viana on 01/12/2016.
 */

public class Splash_Activity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_logo);


        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                SharedPreferences sharedPreferences = getSharedPreferences("APP", MODE_PRIVATE);
                boolean isLoggedIn = sharedPreferences.getBoolean("isLogin", false);
                if(isLoggedIn){
                    Intent intent = new Intent();
                    intent.setClass(Splash_Activity.this, Dashboard_Activity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.setClass(Splash_Activity.this, Login_Activity.class);
                    startActivity(intent);
                }
            }
        }, 4000);
    }

}
