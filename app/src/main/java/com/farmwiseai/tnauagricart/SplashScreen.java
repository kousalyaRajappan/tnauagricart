package com.farmwiseai.tnauagricart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        // Introduce a delay before starting MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashScreen.this, BrowserActivity.class);
                startActivity(intent);
                // Finish SplashScreen activity so it can't be returned to
                finish();
            }
        }, 2000); // 2000 milliseconds delay
    }
}

