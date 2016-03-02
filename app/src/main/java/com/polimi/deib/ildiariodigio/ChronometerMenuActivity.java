package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Button;

public class ChronometerMenuActivity extends AppCompatActivity {

    Button audio;
    Button animation;
    Button video;
    ImageButton button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer_menu);

        audio = (Button)findViewById(R.id.button_audio);
        animation = (Button)findViewById(R.id.button_animation);
        video = (Button)findViewById(R.id.button_video);
        button_back = (ImageButton)findViewById(R.id.imageButton_back);

        audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Cronometro", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ChronometerMenuActivity.this, AudioGridActivity.class);
                ChronometerMenuActivity.this.startActivity(myIntent);
            }
        });

        animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Agenda", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ChronometerMenuActivity.this, ChronometerAnimationActivity.class);
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
                //Toast.makeText(getApplicationContext(), "Back", Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(ChronometerMenuActivity.this, HomeActivity.class);
                ChronometerMenuActivity.this.startActivity(myIntent);
            }
        });
    }
}
