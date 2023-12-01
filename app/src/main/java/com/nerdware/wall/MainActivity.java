package com.nerdware.wall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, initializationStatus -> {
        });

        int SPLASH_SCREEN = 5000;
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this,Discovery.class);
            startActivity(intent);
            finish();

        }, SPLASH_SCREEN);
    }
}