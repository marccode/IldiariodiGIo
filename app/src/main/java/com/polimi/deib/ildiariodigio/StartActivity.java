package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    ImageButton enter_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        enter_button = (ImageButton)findViewById(R.id.imageButton_enter);
        enter_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Entering the application", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(StartActivity.this, LoginActivity.class);
                StartActivity.this.startActivity(myIntent);
            }
        });
    }
}
