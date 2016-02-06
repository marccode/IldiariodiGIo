package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class ModificaActivity extends AppCompatActivity {

    ImageButton next_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica);

        next_button = (ImageButton)findViewById(R.id.button_modifica);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Entering the menu to modify", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ModificaActivity.this, LoginActivity.class);
                ModificaActivity.this.startActivity(myIntent);
            }
        });
    }
}
