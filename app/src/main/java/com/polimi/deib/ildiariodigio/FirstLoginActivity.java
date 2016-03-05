package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class FirstLoginActivity extends AppCompatActivity {

    ImageButton next_button;
    DBAdapter db;
    EditText etNomeBambino;
    EditText etNomeGenitori;
    String nome_bambino;
    String nome_genitore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_first_login);

        etNomeBambino = (EditText) findViewById(R.id.nome_bambino);
        etNomeGenitori = (EditText) findViewById(R.id.nome_genitori);


        db = new DBAdapter(getApplicationContext());
        db.open();
        next_button = (ImageButton)findViewById(R.id.button_modifica);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome_bambino = etNomeBambino.getText().toString();
                nome_genitore = etNomeGenitori.getText().toString();



                if(!nome_bambino.matches(""))
                    db.setChildrenName(nome_bambino);
                else
                    db.setChildrenName("Nome bambino");

                if(!nome_genitore.matches(""))
                    db.setParentName(nome_genitore);
                else
                    db.setParentName("Nome genitore");

                db.close();

                Toast.makeText(getApplicationContext(), "Entering the menu to choose", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(FirstLoginActivity.this, LoginActivity.class);
                FirstLoginActivity.this.startActivity(myIntent);
            }
        });

    }
}