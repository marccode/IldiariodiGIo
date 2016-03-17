package com.polimi.deib.ildiariodigio;

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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class LoginActivity extends AppCompatActivity {

    ImageButton imageButtonParent;
    ImageButton imageButtonKid;
    ImageButton modifica_button; // dejo el enter por probar luego cuando este suelto habra que poner el lapiz
    TextView nome_bambino;
    TextView nome_genitore;
    DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db= new DBAdapter(getApplicationContext());

        db.open();

        nome_bambino =(TextView) findViewById(R.id.tvGio);
        nome_bambino.setText(db.getChildrenName());

        nome_genitore =(TextView) findViewById(R.id.tvParent);
        nome_genitore.setText(db.getParentName());

        String p = db.getProfilePhotoParent();
        String c = db.getProfilePhotoChildren();

        imageButtonParent = (ImageButton)findViewById(R.id.imageButton_parent);
        imageButtonKid = (ImageButton) findViewById(R.id.imageButton_kid);

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

        db.close();


        modifica_button = (ImageButton)findViewById(R.id.button_modify);

        imageButtonParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Entering the menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                //myIntent.putExtra("chi", "parent");
                LoginActivity.this.startActivity(myIntent);
            }
        });

        imageButtonKid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Entering the menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                //myIntent.putExtra("chi", "child");
                LoginActivity.this.startActivity(myIntent);
            }
        });

        modifica_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Entering the modify menu", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(LoginActivity.this, ModificaActivity.class);
                LoginActivity.this.startActivity(myIntent);
            }
        });


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

