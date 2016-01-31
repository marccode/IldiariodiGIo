package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageButton parent_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        parent_button = (ImageButton)findViewById(R.id.imageButton_parent);
        parent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Entering the menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });


    }
}
