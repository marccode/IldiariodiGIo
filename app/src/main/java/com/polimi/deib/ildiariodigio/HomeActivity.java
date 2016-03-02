package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    ImageButton button_cronometro;
    ImageButton button_agenda;
    ImageButton button_ricorda;
    ImageButton button_attivita;
    ImageButton button_diario;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        button_cronometro = (ImageButton)findViewById(R.id.imageButton_cronometro);
        button_agenda = (ImageButton)findViewById(R.id.imageButton_agenda);
        button_ricorda = (ImageButton)findViewById(R.id.imageButton_ricorda);
        button_attivita = (ImageButton)findViewById(R.id.imageButton_attivita);
        button_diario = (ImageButton)findViewById(R.id.imageButton_diario);
        button_back = (ImageButton)findViewById(R.id.imageButton_back);

        button_cronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Cronometro", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, VideoGridActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Agenda", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_ricorda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Ricorda", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_attivita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Attivita", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Diario", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });
    }
}
