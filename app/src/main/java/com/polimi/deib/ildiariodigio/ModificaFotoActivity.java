package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModificaFotoActivity extends AppCompatActivity {

    ImageButton back;
    ImageButton button_enter;
    boolean from_camera;
    private ImageView image;
    DBAdapter db;
    EditText et_title_photo;
    EditText et_description_photo;
    String title_photo;
    String description_photo;
    String date;
    String file_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_foto);

        back = (ImageButton)findViewById(R.id.imageButton_back);

        Intent intent = getIntent();
        from_camera = intent.getBooleanExtra("from_camera",true);
        file_name = intent.getStringExtra("path_name");

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

        et_title_photo = (EditText) findViewById(R.id.title_photo);
        et_description_photo = (EditText) findViewById(R.id.description_photo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        date = dateFormat.format(new Date() );


        db = new DBAdapter(getApplicationContext());
        db.open();

        button_enter = (ImageButton)findViewById(R.id.imageButton_enter);

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title_photo = et_title_photo.getText().toString();
                description_photo = et_description_photo.getText().toString();

                db.addPhoto(file_name, title_photo, description_photo, date);


                db.close();

                Toast.makeText(getApplicationContext(), "Entering menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ModificaFotoActivity.this, FotoDiarioActivity.class);
                ModificaFotoActivity.this.startActivity(myIntent);
            }
        });

    }
}
