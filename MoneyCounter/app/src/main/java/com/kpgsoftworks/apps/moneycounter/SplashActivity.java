package com.kpgsoftworks.apps.moneycounter;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    // Splash screen timer
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Get application version
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            TextView textView = (TextView) findViewById(R.id.splash_version_text);
            if (textView != null) {
                textView.setText(versionName);
            }
        } catch (Exception e) {
            // Do nothing
        }

        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer.
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over

                // Start your app main activity
                startActivity(new Intent(SplashActivity.this, MainActivity.class));

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}
