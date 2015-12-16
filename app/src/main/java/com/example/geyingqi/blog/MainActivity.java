package com.example.geyingqi.blog;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        //延迟进入
        getWindow().getDecorView().post(new Runnable() {
            @Override
            public void run() {
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(MainActivity.this,MainTabActivity.class));
                        finish();
                    }
                });
            }
        });
    }
}
