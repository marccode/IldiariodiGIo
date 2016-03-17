package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModificaFotoActivity extends AppCompatActivity {

    ImageButton back;
    ImageButton button_enter;
    boolean from_camera;
    private ImageView image;
    EditText et_title_photo;
    EditText et_description_photo;
    String title_photo;
    String description_photo;
    String date;
    String path;
    int id_from_foto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifica_foto);

        et_title_photo = (EditText) findViewById(R.id.title_photo);
        et_description_photo = (EditText) findViewById(R.id.description_photo);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        et_title_photo.setTypeface(tf);
        et_description_photo.setTypeface(tf);

        back = (ImageButton)findViewById(R.id.imageButton_back);

        Intent intent = getIntent();
        from_camera = intent.getBooleanExtra("from_camera",true);


        if(from_camera) {
            path = intent.getStringExtra("path_name");
        }
        else {
            DBAdapter db = new DBAdapter(getApplicationContext());
            db.open();

            Intent myIntent = getIntent();

            id_from_foto = myIntent.getIntExtra("id", 0);
            Cursor cursor = db.getPhoto(id_from_foto);

            cursor.moveToFirst();
            title_photo = cursor.getString(0);
            path = cursor.getString(1);
            description_photo = cursor.getString(2);
            date = cursor.getString(3);

            cursor.close();

        }


        File imgFile = new  File(path);


        if(imgFile.exists())
        {

            image = (ImageView)findViewById(R.id.foto_modify);
            Picasso.with(getApplicationContext()).load(imgFile).into(image);
            //image.setImageURI(Uri.fromFile(imgFile));


            et_title_photo.setText(title_photo);


            et_description_photo.setText(description_photo);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ModificaFotoActivity.this, DiarioMenuActivity.class);
                Intent i = new Intent(ModificaFotoActivity.this, FotoDiarioActivity.class);

                if(from_camera == true) {
                    ModificaFotoActivity.this.startActivity(intent);

                }
                else {
                    i.putExtra("id",id_from_foto);
                    i.putExtra("from_modify", true);
                    ModificaFotoActivity.this.startActivity(i);
                }

            }
        });

        et_title_photo = (EditText) findViewById(R.id.title_photo);

        et_description_photo = (EditText) findViewById(R.id.description_photo);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(new Date() );

        button_enter = (ImageButton) findViewById(R.id.imageButton_enter);

        button_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = 0;
                title_photo = et_title_photo.getText().toString();
                description_photo = et_description_photo.getText().toString();

                DBAdapter db = new DBAdapter(getApplicationContext());
                db.open();

                Intent myIntent = new Intent(ModificaFotoActivity.this, FotoDiarioActivity.class);
                myIntent.putExtra("from_modify",true);

                if(from_camera == true) {
                    id = db.addPhoto(path, title_photo, description_photo, date);
                    myIntent.putExtra("id", id);
                }
                else {

                    db.updatePhoto(id_from_foto, path, title_photo, description_photo, date);
                    myIntent.putExtra("id", id_from_foto);
                }


                db.close();

                ModificaFotoActivity.this.startActivity(myIntent);
            }
        });
    }
}