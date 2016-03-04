package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.TextView;

public class ChronometerMenuActivity extends AppCompatActivity {

    Button audio;
    Button animation;
    Button video;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer_menu);

        // Title
        TextView title = (TextView) findViewById(R.id.textView_title);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        title.setTypeface(tf);
        title.setTextColor(getResources().getColor(R.color.title_grey));


        video = (Button)findViewById(R.id.button_video);
        button_back = (ImageButton)findViewById(R.id.imageButton_back);

        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        audio = (Button)findViewById(R.id.button_audio);
        audio.setTypeface(tf);
        audio.setTextColor(getResources().getColor(R.color.white));

        animation = (Button)findViewById(R.id.button_animation);
        animation.setTypeface(tf);
        animation.setTextColor(getResources().getColor(R.color.white));

        video = (Button)findViewById(R.id.button_video);
        video.setTypeface(tf);
        video.setTextColor(getResources().getColor(R.color.white));

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChronometerMenuActivity.this, AudioGridActivity.class);
                ChronometerMenuActivity.this.startActivity(myIntent);
            }
        });

        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChronometerMenuActivity.this, AnimationSetTimeActivity.class);
                ChronometerMenuActivity.this.startActivity(myIntent);
            }
        });

        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Ricorda", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ChronometerMenuActivity.this, VideoGridActivity.class);
                ChronometerMenuActivity.this.startActivity(myIntent);
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(ChronometerMenuActivity.this, HomeActivity.class);
                ChronometerMenuActivity.this.startActivity(myIntent);
            }
        });
    }
}
