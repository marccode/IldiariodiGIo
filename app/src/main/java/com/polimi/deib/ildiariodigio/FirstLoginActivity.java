package com.polimi.deib.ildiariodigio;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;


public class FirstLoginActivity extends AppCompatActivity {

    ImageButton next_button;
    DBAdapter db;
    EditText etNomeBambino;
    EditText etNomeGenitori;
    String nome_bambino;
    String nome_genitore;
    boolean genitore;

   private String APP_DIRECTORY = "myPictureApp/";
   private String MEDIA_DIRECTORY = APP_DIRECTORY + "media";

   private final String PARENT_PICTURE_NAME = "profile_picture_parent.jpg";
   private final String KID_PICTURE_NAME = "profile_picture_kid.jpg";

    private final int PHOTO_CODE=400;
    private final int SELECT_PICTURE=200;

    public static Uri uriParent;
    public static Uri uriKid;
    public static Bitmap bitmapParent;
    public static Bitmap bitmapKid;

    private ImageButton imageButtonParent;
    private ImageButton imageButtonKid;
    String path = Environment.getExternalStorageDirectory() + File.separator +
            MEDIA_DIRECTORY + File.separator;
    private File file;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_first_login);

        bitmapKid=null;
        bitmapParent=null;
        uriKid=null;
        uriParent=null;

        etNomeBambino = (EditText) findViewById(R.id.nome_bambino);
        etNomeGenitori = (EditText) findViewById(R.id.nome_genitori);

        imageButtonParent = (ImageButton) findViewById(R.id.imageButton_upload_parent);
        imageButtonKid = (ImageButton) findViewById(R.id.imageButton_upload_kid);



        db = new DBAdapter(getApplicationContext());
        db.open();

        imageButtonParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] options = {"Camara", "Galleria", "Annulare"};
                final AlertDialog.Builder builder = new AlertDialog.Builder(FirstLoginActivity.this);
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
                            intent.getExtras();
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
                final AlertDialog.Builder builder = new AlertDialog.Builder(FirstLoginActivity.this);
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

        next_button = (ImageButton) findViewById(R.id.button_modifica);
        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome_bambino = etNomeBambino.getText().toString();
                nome_genitore = etNomeGenitori.getText().toString();


                if (!nome_bambino.matches(""))
                    db.setChildrenName(nome_bambino);
                else
                    db.setChildrenName("Nome bambino");

                if (!nome_genitore.matches(""))
                    db.setParentName(nome_genitore);
                else
                    db.setParentName("Nome genitore");

                db.close();

                Toast.makeText(getApplicationContext(), "Entering the menu to choose", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(FirstLoginActivity.this, LoginActivity.class);
                FirstLoginActivity.this.startActivity(myIntent);
            }
        });

    }

    private void openCamera() {
        file= new File (Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        file.mkdirs();

        File newFile;
        newFile = new File(path + PARENT_PICTURE_NAME);
        if(!genitore)
        newFile = new File(path + KID_PICTURE_NAME);



        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(newFile));
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
                    if(genitore) {
                        bitmapParent=null;
                        uriParent=data.getData();
                        imageButtonParent.setImageURI(uriParent);
                        imageButtonParent.setImageBitmap(getCroppedBitmap(drawableToBitmap(imageButtonParent.getDrawable())));

                    }else {
                        bitmapKid=null;
                        uriKid=data.getData();
                        imageButtonKid.setImageURI(uriKid);
                        imageButtonKid.setImageBitmap(getCroppedBitmap(drawableToBitmap(imageButtonKid.getDrawable())));

                    }
                }
                break;
        }
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
            uriParent=null;
            bitmapParent = BitmapFactory.decodeFile(dir + PARENT_PICTURE_NAME);
            imageButtonParent.setImageBitmap(getCroppedBitmap(bitmapParent));
        }else {
            uriKid=null;
            bitmapKid = BitmapFactory.decodeFile(dir + KID_PICTURE_NAME);
            imageButtonKid.setImageBitmap(getCroppedBitmap(bitmapKid));
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