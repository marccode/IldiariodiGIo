package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {


    Button button_cronometro;
    Button button_agenda;
    Button button_ricorda;
    Button button_attivita;
    Button button_diario;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Title
        TextView title = (TextView) findViewById(R.id.textView_title);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        title.setTypeface(tf);
        title.setTextColor(getResources().getColor(R.color.title_grey));

        button_cronometro = (Button)findViewById(R.id.button_cronometro);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        button_cronometro.setTypeface(tf);
        button_cronometro.setTextColor(getResources().getColor(R.color.white));
        button_cronometro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, ChronometerMenuActivity.class);
                HomeActivity.this.startActivity(i);
            }
        });

        button_agenda = (Button)findViewById(R.id.button_agenda);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        button_agenda.setTypeface(tf);
        button_agenda.setTextColor(getResources().getColor(R.color.white));
        button_agenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Agenda", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_ricorda = (Button)findViewById(R.id.button_ricorda);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        button_ricorda.setTypeface(tf);
        button_ricorda.setTextColor(getResources().getColor(R.color.white));
        button_ricorda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Ricorda", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_attivita = (Button)findViewById(R.id.button_attivita);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        button_attivita.setTypeface(tf);
        button_attivita.setTextColor(getResources().getColor(R.color.white));
        button_attivita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Attivita", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
                HomeActivity.this.startActivity(myIntent);
            }
        });

        button_diario = (Button)findViewById(R.id.button_diario);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        button_diario.setTypeface(tf);
        button_diario.setTextColor(getResources().getColor(R.color.white));
        button_diario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, DiarioMenuActivity.class);
                HomeActivity.this.startActivity(i);
            }
        });

        button_back = (ImageButton)findViewById(R.id.imageButton_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, LoginActivity.class);
                HomeActivity.this.startActivity(i);
            }
        });
    }
}