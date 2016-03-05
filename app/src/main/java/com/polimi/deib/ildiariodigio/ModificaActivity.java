package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class ModificaActivity extends AppCompatActivity {

    ImageButton next_button;
    EditText nome_bambino;
    EditText nome_genitore;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modifica);

        db= new DBAdapter(getApplicationContext());
        db.open();

        nome_bambino=(EditText) findViewById(R.id.EditxKid);
        nome_bambino.setHint(db.getChildrenName());

        nome_genitore=(EditText) findViewById((R.id.EditxParent));
        nome_genitore.setHint(db.getParentName());

        next_button = (ImageButton)findViewById(R.id.button_modifica);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!nome_bambino.getText().toString().equals(""))
                    db.setChildrenName(nome_bambino.getText().toString());
                if(!nome_genitore.getText().toString().equals(""))
                    db.setParentName(nome_genitore.getText().toString());

                db.close();

                Toast.makeText(getApplicationContext(), "Entering the menu to modify", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ModificaActivity.this, LoginActivity.class);
                ModificaActivity.this.startActivity(myIntent);
            }
        });

    }
}