package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    ImageButton parent_button;
    ImageButton kid_button;
    ImageButton modifica_button; // dejo el enter por probar luego cuando este suelto habra que poner el lapiz
    TextView nome_bambino;
    TextView nome_genitore;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db= new DBAdapter(getApplicationContext());

        db.open();

        nome_bambino =(TextView) findViewById(R.id.tvGio);
        nome_bambino.setText(db.getChildrenName());

        nome_genitore =(TextView) findViewById(R.id.tvParent);
        nome_genitore.setText(db.getParentName());

        db.close();
        parent_button = (ImageButton)findViewById(R.id.imageButton_parent);
        kid_button = (ImageButton) findViewById(R.id.imageButton_kid);

        modifica_button = (ImageButton)findViewById(R.id.button_modify);

        if(FirstLoginActivity.bitmapParent!=null)
        parent_button.setImageBitmap(FirstLoginActivity.bitmapParent);
        if(FirstLoginActivity.bitmapKid!=null)
        kid_button.setImageBitmap(FirstLoginActivity.bitmapKid);

        if(FirstLoginActivity.uriParent!=null)
        parent_button.setImageURI(FirstLoginActivity.uriParent);
        if(FirstLoginActivity.uriKid!=null)
        kid_button.setImageURI(FirstLoginActivity.uriKid);

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

