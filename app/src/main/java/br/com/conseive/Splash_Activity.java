package br.com.conseive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

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

                Intent intent = new Intent();
                intent.setClass(Splash_Activity.this, Login_Activity.class);
                startActivity(intent);
            }
        }, 4000);
    }

}
