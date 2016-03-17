package com.polimi.deib.ildiariodigio;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.Lock;

public class DiarioMenuActivity extends AppCompatActivity {

    Button camera;
    Button guarda_foto;
    ImageButton back;
    String ruta_fotos;
    File file;
    File mi_foto;
    String file_name;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_menu);

        camera = (Button) findViewById(R.id.access_camera);
        guarda_foto = (Button) findViewById(R.id.access_photos);
        back = (ImageButton) findViewById(R.id.imageButton_back);

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
                Log.e("TAG", getCode().toString());
                mi_foto = new File(file_name);

                try {
                    mi_foto.createNewFile();
                } catch (IOException ex) {
                    Log.e("ERROR ", "Error:" + ex);
                }


                Uri uri = Uri.fromFile(mi_foto);


                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                startActivityForResult(intent, 1);


            }

        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (file_name != null) {
            outState.putString("file_name", file_name);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("file_name")) {
            file_name = savedInstanceState.getString("file_name");
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {

            if (resultCode == RESULT_OK) {
                Intent i = new Intent(DiarioMenuActivity.this, ModificaFotoActivity.class);
                i.putExtra("from_camera", true);
                i.putExtra("path_name", file_name);
                DiarioMenuActivity.this.startActivity(i);
            } else {
                Intent intent_no_photo = new Intent(DiarioMenuActivity.this, DiarioMenuActivity.class);
                DiarioMenuActivity.this.startActivity(intent_no_photo);
            }

        }

    }

    @SuppressLint("SimpleDateFormat")
    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        String date = dateFormat.format(new Date());
        String photoCode = "pic_" + date;
        return photoCode;
    }



}