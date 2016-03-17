package com.polimi.deib.ildiariodigio;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FotoDiarioActivity extends AppCompatActivity {

    ImageButton back;
    ImageButton modifica_button;
    ImageButton cancellare;
    int id;
    String title_photo;
    String description_photo;
    String date;
    String path;
    ImageView image;
    TextView photo_modified;
    TextView description_modified;
    TextView date_modified;
    boolean from_modify = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto_diario);

        back = (ImageButton)findViewById(R.id.imageButton_back);
        modifica_button = (ImageButton)findViewById(R.id.image_modify);

        DBAdapter db = new DBAdapter(getApplicationContext());
        db.open();

        Intent myIntent = getIntent();
        id = myIntent.getIntExtra("id", 0);
        from_modify = myIntent.getBooleanExtra("from_modify",false);

        Cursor cursor = db.getPhoto(id);

        cursor.moveToFirst();
        title_photo = cursor.getString(0);
        path = cursor.getString(1);
        description_photo = cursor.getString(2);
        date = cursor.getString(3);

        cursor.close();

        File imgFile = new  File(path);

        if(imgFile.exists())
        {

            image = (ImageView)findViewById(R.id.foto_modify);
            Picasso.with(getApplicationContext()).load(imgFile).into(image);

            //image.setImageURI(Uri.fromFile(imgFile));

        }

        TextView tv = (TextView) findViewById(R.id.textview_activity_title);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        tv.setTypeface(tf);
        tv.setTextColor(getResources().getColor(R.color.white));

        photo_modified = (TextView) findViewById(R.id.title_photo);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        photo_modified.setText(title_photo);

        description_modified = (TextView) findViewById(R.id.description_photo);
        description_modified.setTypeface(tf);
        description_modified.setText(description_photo);

        date_modified = (TextView) findViewById(R.id.textview_activity_title);
        date_modified.setTypeface(tf);
        date_modified.setText(getDateString(date));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FotoDiarioActivity.this, DiarioGridActivity.class);
                FotoDiarioActivity.this.startActivity(intent);
            }
        });

        modifica_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(FotoDiarioActivity.this, ModificaFotoActivity.class);
                intent.putExtra("from_camera", false);
                intent.putExtra("id", id);
                FotoDiarioActivity.this.startActivity(intent);
            }
        });

        cancellare = (ImageButton) findViewById(R.id.cancellare);

        cancellare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(FotoDiarioActivity.this);
                alert.setTitle("Cancellare foto");
                alert.setMessage("Vuoi cancellare questa foto?");
                alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBAdapter db = new DBAdapter(getApplicationContext());
                        db.open();
                        db.deletePhoto(id);
                        db.close();

                        if (from_modify) {
                            Intent myIntent = new Intent(FotoDiarioActivity.this, DiarioMenuActivity.class);
                            FotoDiarioActivity.this.startActivity(myIntent);
                        } else {
                            Intent intent = new Intent(FotoDiarioActivity.this, DiarioGridActivity.class);
                            FotoDiarioActivity.this.startActivity(intent);
                        }


                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });

    }

    private String getDateString(String date) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        String day_string = date.substring(8, 10);

        String month_string;
        switch (month) {
            case 1:
                month_string = "gennaio";
                break;

            case 2:
                month_string = "febbraio";
                break;

            case 3:
                month_string = "marzo";
                break;

            case 4:
                month_string = "aprile";
                break;

            case 5:
                month_string = "maggio";
                break;

            case 6:
                month_string = "giugno";
                break;

            case 7:
                month_string = "luglio";
                break;

            case 8:
                month_string = "agosto";
                break;

            case 9:
                month_string = "settembre";
                break;

            case 10:
                month_string = "ottobre";
                break;

            case 11:
                month_string = "novembre";
                break;

            case 12:
                month_string = "dicembre";
                break;

            default:
                month_string = "unknown";
        }

        String year_string = "";
        Date now = new Date();
        if (now.getYear() != year) {
            year_string = " " + Integer.toString(year);
        }

        Date d = new Date();
        try {
            d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
            Log.e("fecha",d.toString());
        } catch(java.text.ParseException e) {
            e.printStackTrace();
        }
        String weekday_string = "Oggi";
        now.setHours(0);
        now.setMinutes(0);
        now.setSeconds(0);

        Log.e("fecha_actual", now.toString());

        if (!now.toString().equals(d.toString())) {
            Calendar c = Calendar.getInstance();
            c.setTime(d);
            int weekday = c.get(Calendar.DAY_OF_WEEK);

            switch (weekday) {
                case 1:
                    weekday_string = "domenica";
                    break;

                case 2:
                    weekday_string = "lunedì";
                    break;

                case 3:
                    weekday_string = "martedì";
                    break;

                case 4:
                    weekday_string = "mercoledì";
                    break;

                case 5:
                    weekday_string = "giovedì";
                    break;

                case 6:
                    weekday_string = "venerdì";
                    break;

                case 7:
                    weekday_string = "sabato";
                    break;
            }
        }
        return weekday_string + ", " + day_string + " " + month_string + year_string;
    }
}