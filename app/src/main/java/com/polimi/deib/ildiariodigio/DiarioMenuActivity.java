package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class DiarioMenuActivity extends AppCompatActivity {

    Button camera;
    Button guarda_foto;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_menu);

        camera = (Button)findViewById(R.id.access_camera);
        guarda_foto = (Button)findViewById(R.id.access_photos);
        back = (ImageButton)findViewById(R.id.imageButton_back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioMenuActivity.this, HomeActivity.class);
                DiarioMenuActivity.this.startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


        guarda_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioMenuActivity.this, DiarioMenuActivity.class);
                DiarioMenuActivity.this.startActivity(intent);
            }
        });

    }
}