package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageButton parent_button;
    ImageButton modifica_button; // dejo el enter por probar luego cuando este suelto habra que poner el lapiz
    EditText nome_bambino;
    EditText nome_genitore;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DBAdapter(getApplicationContext());
        db.open();
        nome_bambino = (EditText) findViewById(R.id.nome_bambino);
        Log.e("TAG", "OUTSIDE: " + db.getChildrenName());
        nome_bambino.setText(db.getChildrenName());

        nome_genitore=(EditText) findViewById(R.id.nome_genitori);
        nome_genitore.setText(db.getParentName());
        db.close();

        parent_button = (ImageButton)findViewById(R.id.imageButton_parent);
        modifica_button = (ImageButton)findViewById(R.id.button_modify);

        parent_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Entering the menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });
        modifica_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Entering the modify menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(LoginActivity.this, ModificaActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });


    }
}

