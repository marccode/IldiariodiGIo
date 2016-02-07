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

public class StartActivity extends AppCompatActivity {

    ImageButton enter_button;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

        enter_button = (ImageButton)findViewById(R.id.imageButton_enter);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean previouslyStarted = prefs.getBoolean("pref_previously_started", false);
                if(!previouslyStarted) {
                    SharedPreferences.Editor edit = prefs.edit();
                    edit.putBoolean("pref_previously_started", Boolean.TRUE);
                    edit.commit();
                    Toast.makeText(getApplicationContext(), "Entering the application", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(StartActivity.this, FirstLoginActivity.class);
                    StartActivity.this.startActivity(myIntent);

                }
                else {
                    Toast.makeText(getApplicationContext(), "Entering the application", Toast.LENGTH_LONG).show();
                    Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
                    StartActivity.this.startActivity(myIntent);
                }


            }
        });
    }
}
