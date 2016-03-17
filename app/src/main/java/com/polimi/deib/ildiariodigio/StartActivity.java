package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Handler;

public class StartActivity extends AppCompatActivity {


    SharedPreferences prefs;
    private static int SPLASH_TIME_OUT = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                boolean previouslyStarted = prefs.getBoolean("pref_previously_started", false);

                if(!previouslyStarted) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("pref_previously_started", Boolean.TRUE);
                    edit.commit();
                    Toast.makeText(getApplicationContext(), "Entering the application", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(StartActivity.this, FirstLoginActivity.class);
                    startActivity(myIntent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Entering the application", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);



    }

}