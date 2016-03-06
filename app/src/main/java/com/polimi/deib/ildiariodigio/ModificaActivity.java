package com.polimi.deib.ildiariodigio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class ModificaActivity extends AppCompatActivity {

    ImageButton next_button;
    EditText nome_bambino;
    EditText nome_genitore;
    DBAdapter db;

    boolean genitore=true;

    private String APP_DIRECTORY = "myPictureApp/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private String TEMPORAL_PICTURE_NAME="temporal.jpg";
    private final int PHOTO_CODE=100;
    private final int SELECT_PICTURE=200;
    String path = Environment.getExternalStorageDirectory() + File.separator +
            MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
    private File file;
    private ImageButton imageButtonParent;
    private ImageButton imageButtonKid;

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

        imageButtonParent = (ImageButton) findViewById(R.id.imageButton_upload_parent);
        imageButtonKid = (ImageButton) findViewById(R.id.imageButton_upload_kid);

        imageButtonParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Camara", "Galleria", "Annulare"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(ModificaActivity.this);
                builder.setTitle("Selezionare un'opzione");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int seleccion) {
                        genitore=true;
                        if (options[seleccion] == "Camara") {
                            openCamera();
                        } else if (options[seleccion] == "Galleria") {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Selezionare una foto"), SELECT_PICTURE);
                        } else if (options[seleccion] == "Anullare") {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

        imageButtonKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Camara", "Galleria", "Annulare"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(ModificaActivity.this);
                builder.setTitle("Selezionare un'opzione");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int seleccion) {
                        genitore=false;
                        if (options[seleccion] == "Camara") {
                            openCamera();
                        } else if (options[seleccion] == "Galleria") {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(intent.createChooser(intent, "Selezionare una foto"), SELECT_PICTURE);
                        } else if (options[seleccion] == "Anullare") {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });

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


    private void openCamera() {
        file= new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        file.mkdirs();


        File newFile = new File(path);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case PHOTO_CODE:
                if(resultCode == RESULT_OK)
                    decodeBitmap(path);
                break;
            case SELECT_PICTURE:
                if(resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    if(genitore)
                        imageButtonParent.setImageURI(uri);
                    else
                        imageButtonKid.setImageURI(uri);
                }
                break;
        }
    }

    private void decodeBitmap(String dir){
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imageButtonParent.setImageBitmap(bitmap);
    }

}