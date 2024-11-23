package com.farmwiseai.tnauagricart;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class BrowserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);
        UpdateChecker updateChecker = new UpdateChecker(this);
        if (updateChecker.checkForUpdate()) {
            updateChecker.showForceUpdateDialog();
        } else {
            // Introduce a delay before starting MainActivity
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Start MainActivity
                    openUrl("https://www.tnauagricart.com/");

                }
            }, 2000); // 2000

        }
    }

    private void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
        finish();
    }
}


