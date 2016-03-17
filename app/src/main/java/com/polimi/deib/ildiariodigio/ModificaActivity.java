package com.polimi.deib.ildiariodigio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    private String APP_DIRECTORY = "IlDiarioDiGio/";
    private String MEDIA_DIRECTORY = APP_DIRECTORY + "Profiles";
    private String TEMPORAL_PICTURE_NAME="temporal.jpg";

    private final String PARENT_PICTURE_NAME = "parent.jpg";
    private final String KID_PICTURE_NAME = "child.jpg";

    private Uri parent_image_path_uri;// = "/sdcard/IlDiarioDiGio/Profiles/parent.jpg";
    private Uri child_image_path_uri; // = "/sdcard/IlDiarioDiGio/Profiles/child.jpg";

    private String default_parent_image_path = "/sdcard/parent_profile_image.jpg";
    private String default_child_image_path = "/sdcard/child_profile_image.jpg";

    private final int PHOTO_CODE=400;
    private final int SELECT_PICTURE=200;

    public static Uri uriParent;
    public static Uri uriKid;
    public static Bitmap bitmapMParent;
    public static Bitmap bitmapMKid;




    String path = Environment.getExternalStorageDirectory() + File.separator +
            MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
    private File file;
    private ImageButton imageButtonParent;
    private ImageButton imageButtonKid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modifica);

        imageButtonParent = (ImageButton) findViewById(R.id.imageButton_modifica_parent);
        imageButtonKid = (ImageButton) findViewById(R.id.imageButton_modifica_kid);

        db= new DBAdapter(getApplicationContext());
        db.open();
        //child_image_path_uri.fromFile(new File(db.getProfilePhotoChildren()));
        //parent_image_path_uri.fromFile(new File(db.getProfilePhotoParent()));

        String p = db.getProfilePhotoParent();
        String c = db.getProfilePhotoChildren();

        if (!p.equals("null")) {
            File parent_photo = new File(p);
            if(parent_photo.exists() && !parent_photo.isDirectory()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(parent_photo.getAbsolutePath());
                imageButtonParent.setImageBitmap(getCroppedBitmap(myBitmap));
            }
        }
        if (!c.equals("null")) {
            File child_photo = new File(c);
            if(child_photo.exists() && !child_photo.isDirectory()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(child_photo.getAbsolutePath());
                imageButtonKid.setImageBitmap(getCroppedBitmap(myBitmap));
            }
        }

        nome_bambino=(EditText) findViewById(R.id.EditxKid);
        nome_bambino.setHint(db.getChildrenName());

        nome_genitore=(EditText) findViewById((R.id.EditxParent));
        nome_genitore.setHint(db.getParentName());


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

                if (!nome_bambino.getText().toString().equals(""))
                    db.setChildrenName(nome_bambino.getText().toString());
                if (!nome_genitore.getText().toString().equals(""))
                    db.setParentName(nome_genitore.getText().toString());

                db.close();

                Toast.makeText(getApplicationContext(), "Entering the menu to modify", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ModificaActivity.this, LoginActivity.class);
                ModificaActivity.this.startActivity(myIntent);
            }
        });

    }



    private void openCamera() {
        file= new File (Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        file.mkdirs();

        File newFile;
        newFile = new File(default_parent_image_path);
        if(!genitore)
            newFile = new File(default_child_image_path);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(newFile));
        startActivityForResult(intent, PHOTO_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case PHOTO_CODE:
                if(resultCode == RESULT_OK) {
                    //decodeBitmap(path);
                    if (genitore) {
                        File parent_photo = new File(default_parent_image_path);
                        if(parent_photo.exists() && !parent_photo.isDirectory()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(parent_photo.getAbsolutePath());
                            imageButtonParent.setImageBitmap(getCroppedBitmap(myBitmap));
                        }
                        db.setProfilePhotoParent(default_parent_image_path);
                    }
                    else {
                        File child_photo = new File(default_child_image_path);
                        if(child_photo.exists() && !child_photo.isDirectory()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(child_photo.getAbsolutePath());
                            imageButtonKid.setImageBitmap(getCroppedBitmap(myBitmap));
                        }
                        db.setProfilePhotoChildren(default_child_image_path);
                    }
                }
                break;
            case SELECT_PICTURE:
                if(resultCode == RESULT_OK) {
                    if(genitore) {
                        uriParent=data.getData();
                        imageButtonParent.setImageURI(uriParent);
                        imageButtonParent.setImageBitmap(getCroppedBitmap(drawableToBitmap(imageButtonParent.getDrawable())));
                        Log.e("GGG", getRealPathFromURI(uriParent));
                        db.setProfilePhotoParent(getRealPathFromURI(uriParent));

                    }else {
                        uriKid=data.getData();
                        imageButtonKid.setImageURI(uriKid);
                        imageButtonKid.setImageBitmap(getCroppedBitmap(drawableToBitmap(imageButtonKid.getDrawable())));
                        db.setProfilePhotoChildren(getRealPathFromURI(uriKid));
                    }
                }
                break;
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
    private void decodeBitmap(String dir){

        if(genitore) {
            bitmapMParent = BitmapFactory.decodeFile(dir + PARENT_PICTURE_NAME);
            bitmapMParent=getCroppedBitmap(bitmapMParent);
            imageButtonParent.setImageBitmap(bitmapMParent);
        }else {
            bitmapMKid = BitmapFactory.decodeFile(dir + KID_PICTURE_NAME);
            bitmapMKid = getCroppedBitmap(bitmapMKid);
            imageButtonKid.setImageBitmap(bitmapMKid);
        }
    }

    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
}