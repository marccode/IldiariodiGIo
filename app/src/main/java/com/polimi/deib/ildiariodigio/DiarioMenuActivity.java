package com.polimi.deib.ildiariodigio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiarioMenuActivity extends AppCompatActivity {

    Button camera;
    Button guarda_foto;
    ImageButton back;
    String ruta_fotos;
    File file;
    File mi_foto;
    String file_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_menu);

        camera = (Button)findViewById(R.id.access_camera);
        guarda_foto = (Button)findViewById(R.id.access_photos);
        back = (ImageButton)findViewById(R.id.imageButton_back);

        ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/fotos_diario/";
        file = new File(ruta_fotos);
        file.mkdirs();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioMenuActivity.this, HomeActivity.class);
                DiarioMenuActivity.this.startActivity(intent);
            }
        });

        guarda_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioMenuActivity.this, DiarioGridActivity.class);
                DiarioMenuActivity.this.startActivity(intent);
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                file_name = ruta_fotos + getCode() + ".jpg";
                mi_foto = new File( file_name );

                try {
                    mi_foto.createNewFile();
                }catch (IOException ex) {
                     Log.e("ERROR ", "Error:" + ex);
                    }


                Uri uri = Uri.fromFile(mi_foto);


                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, 0);
            }
        });

    }

    @SuppressLint("SimpleDateFormat")
    private String getCode()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date() );
        String photoCode = "pic_" + date;
        return photoCode;
    }


}
