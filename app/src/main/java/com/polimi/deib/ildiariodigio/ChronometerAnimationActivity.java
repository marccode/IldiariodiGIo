package com.polimi.deib.ildiariodigio;

import android.graphics.Color;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.polimi.deib.ildiariodigio.R;


public class ChronometerAnimationActivity extends Activity {

    private ImageButton btnPlay;
    private ImageButton btnBack;

    private TextView countdown_textview;
    private TextView min_textview;

    // Animation Views
    private WaveView waves;
    private ImageView pizza;
    //CircleRoadProgress circleProgress;
    private CircleProgressView circle;

    private CountDownTimer cdt;

    String animation_type;
    boolean running = false;
    int remaining_time;
    int initial_time;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer_animation);


        Intent i = getIntent(); // gets the previously created intent
        initial_time = i.getIntExtra("duration", 0);
        animation_type = i.getStringExtra("animation_type");
        /* TEMPORAL SOLUTION!!! */
        //initial_time = 35000;
        //animation_type = "circle";

        // Time
        countdown_textview = (TextView) findViewById(R.id.textView_countdown);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static-Bold.otf");
        countdown_textview.setTypeface(tf);
        countdown_textview.setTextColor(getResources().getColor(R.color.orange));
        countdown_textview.setText(millisecondsToString(initial_time));

        // MIN
        min_textview = (TextView) findViewById(R.id.textView_min);
        min_textview.setTextColor(getResources().getColor(R.color.light_grey));
        min_textview.setTypeface(tf);

        // TYPE OF ANIMATION:
        waves = (WaveView) findViewById(R.id.waveView);
        pizza = (ImageView) findViewById(R.id.pizzaView);
        //circleProgress = (CircleRoadProgress) findViewById(R.id.circleProgressView);
        circle = (CircleProgressView) findViewById(R.id.circleView);

        Log.e("TAG", Integer.toString(initial_time));
        switch (animation_type) {
            case "waves":
                waves.setShapeType(WaveView.ShapeType.CIRCLE);
                waves.setProgressValue(100);
                waves.setBorderWidth(10);
                waves.setAmplitudeRatio(60);
                waves.setWaveColor(Color.parseColor("#ADAAFA"));
                waves.setBorderColor(Color.parseColor("#C8C7C9"));

                // HIDE THE OTHER ANIMATIONS:
                pizza.setVisibility(View.GONE);
                circle.setVisibility(View.GONE);
                //circleProgress.setVisibility(View.GONE);
                break;

            case "pizza":
                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_0));
                //pizza.setBackground(getApplicationContext().getResources().getDrawable(R.drawable.pizza_0));

                // HIDE THE OTHER ANIMATIONS:
                waves.setVisibility(View.GONE);
                circle.setVisibility(View.GONE);
                //circleProgress.setVisibility(View.GONE);
                break;

            case "circle":
                waves.setVisibility(View.GONE);
                pizza.setVisibility(View.GONE);
                circle.setProgress(100);
                //circleProgress.changePercentage(100);
                break;
        }


        cdt = createCountDownTimer(initial_time, 500);
        cdt.start();
        running = true;

        // BUTTONS
        btnPlay = (ImageButton) findViewById(R.id.imageButton_play);
        btnPlay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (running) {
                    running = false;
                    cdt.cancel();
                    btnPlay.setImageResource(R.drawable.button_play);
                } else {
                    running = true;
                    cdt = createCountDownTimer(remaining_time, 500);
                    cdt.start();
                    btnPlay.setImageResource(R.drawable.button_pause);
                }

            }
        });

        btnBack = (ImageButton) findViewById(R.id.imageButton_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                cdt.cancel();
                Intent i = new Intent(ChronometerAnimationActivity.this, AnimationSetTypeActivity.class);
                i.putExtra("duration", initial_time);
                ChronometerAnimationActivity.this.startActivity(i);
                //finish();
            }
        });
    }

    private CountDownTimer createCountDownTimer(int countdown_time, int interval) {
        return new CountDownTimer(countdown_time, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                running = true;
                remaining_time = (int) millisUntilFinished;
                countdown_textview.setText(millisecondsToString(remaining_time));

                float aux;
                int progress;
                switch (animation_type) {
                    case "waves":
                        aux = (float) initial_time;
                        progress = (int) ((millisUntilFinished / aux) * 100);
                        waves.setProgressValue(progress);
                        break;

                    case "pizza":
                        aux = (float) initial_time;
                        progress = (int) (((millisUntilFinished / aux) * 8) + 0.5);
                        switch (progress) {
                            case 0:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_8));
                                break;
                            case 1:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_7));
                                break;
                            case 2:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_6));
                                break;
                            case 3:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_5));
                                break;
                            case 4:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_4));
                                break;
                            case 5:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_3));
                                break;
                            case 6:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_2));
                                break;
                            case 7:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_1));
                                break;
                            case 8:
                                pizza.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.pizza_0));
                                break;

                        }
                        break;

                    case "circle":
                        aux = (float) initial_time;
                        progress = (int) ((millisUntilFinished / aux) * 100);
                        //circleProgress.changePercentage(progress);
                        circle.setProgress(progress);
                }
            }

            @Override
            public void onFinish() {
                running = false;
                remaining_time = 0;
                Intent i = new Intent(ChronometerAnimationActivity.this, BravoActivity.class);
                i.putExtra("chronometer_type", "animation");
                i.putExtra("animation_type", animation_type);
                i.putExtra("duration", initial_time);
                ChronometerAnimationActivity.this.startActivity(i);
            }
        };
    }

    private String millisecondsToString(int milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }
}