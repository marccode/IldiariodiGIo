package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;

public class ModificaFotoActivity extends AppCompatActivity {

    ImageButton back;
    boolean from_camera;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_foto);

        back = (ImageButton)findViewById(R.id.imageButton_back);

        Intent intent = getIntent();
        from_camera = intent.getBooleanExtra("from_camera",true);
        String file_name = intent.getStringExtra("path_name");

        File imgFile = new  File(file_name);
        if(imgFile.exists())
        {
            image = (ImageView)findViewById(R.id.foto_modify);
            image.setImageURI(Uri.fromFile(imgFile));

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificaFotoActivity.this, DiarioMenuActivity.class);
                Intent i = new Intent(ModificaFotoActivity.this, DiarioGridActivity.class);

                if(from_camera == true) {
                    ModificaFotoActivity.this.startActivity(intent);
                }
                else {
                    ModificaFotoActivity.this.startActivity(i);
                }

            }
        });
    }
}
