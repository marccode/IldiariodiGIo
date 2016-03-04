package com.polimi.deib.ildiariodigio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
public class BravoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bravo);

        ImageButton back_button = (ImageButton) findViewById(R.id.imageButton_back);
        ImageButton home_button = (ImageButton) findViewById(R.id.imageButton_home);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = getIntent();
                switch(i.getStringExtra("type")) {
                    case "animation":
                        String animation = i.getStringExtra("animation");
                        int duration= i.getIntExtra("duration", 0);
                        Intent intentAnimation = new Intent(BravoActivity.this, ChronometerAnimationActivity.class);
                        intentAnimation.putExtra("animation", animation);
                        intentAnimation.putExtra("duration", duration);
                        BravoActivity.this.startActivity(intentAnimation);
                        break;

                    case "song":
                        String song_title = i.getStringExtra("title");
                        String song_duration= i.getStringExtra("duration");
                        String song_path = i.getStringExtra("path");
                        Intent intentSong = new Intent(BravoActivity.this, ChronometerAudioActivity.class);
                        intentSong.putExtra("title", song_title);
                        intentSong.putExtra("duration", song_duration);
                        intentSong.putExtra("path", song_path);
                        BravoActivity.this.startActivity(intentSong);
                        break;

                    case "video":
                        String video_title = i.getStringExtra("title");
                        String video_duration = i.getStringExtra("duration");
                        String video_path = i.getStringExtra("path");
                        Intent intentVideo = new Intent(BravoActivity.this, ChronometerVideoActivity.class);
                        intentVideo.putExtra("title", video_title);
                        intentVideo.putExtra("duration", video_duration);
                        intentVideo.putExtra("path", video_path);
                        BravoActivity.this.startActivity(intentVideo);
                        break;
                }
            }
        });

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(BravoActivity.this, HomeActivity.class);
                BravoActivity.this.startActivity(myIntent);
            }
        });

    }
}
