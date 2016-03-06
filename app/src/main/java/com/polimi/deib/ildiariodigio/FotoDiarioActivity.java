package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class FotoDiarioActivity extends AppCompatActivity {

    ImageButton back;
    ImageButton modifica_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_diario);

        back = (ImageButton)findViewById(R.id.imageButton_back);
        modifica_button = (ImageButton)findViewById(R.id.image_modify);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FotoDiarioActivity.this, DiarioGridActivity.class);
                FotoDiarioActivity.this.startActivity(intent);
            }
        });

        modifica_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FotoDiarioActivity.this, ModificaFotoActivity.class);
                intent.putExtra("from_camera",false);
                FotoDiarioActivity.this.startActivity(intent);
            }
        });

        //comparar fecha con la de hoy para cambiar el texto del titulo
        //mirar en la base de datos el resto de cosas


    }
}
