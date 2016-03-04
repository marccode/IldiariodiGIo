
package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.TextView;

public class AnimationSetTimeActivity extends AppCompatActivity {

    private ImageButton btnNext;
    private ImageButton btnBack;

    private CountDownInputView cdiv;

    private int milliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set_time);

        milliseconds = 0;

        cdiv = (CountDownInputView) findViewById(R.id.countdown_input);

        // TITLE
        TextView tv = (TextView) findViewById(R.id.textview_activity_title);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        tv.setTypeface(tf);
        tv.setTextColor(getResources().getColor(R.color.title_grey));

        // MIN
        TextView min_textview = (TextView) findViewById(R.id.textView_min);
        tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Static/Static.otf");
        min_textview.setTextColor(getResources().getColor(R.color.light_grey));
        min_textview.setTypeface(tf);

        // BUTTONS
        ImageButton btnNext = (ImageButton) findViewById(R.id.imageButton_next);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                milliseconds = cdiv.getMilliseconds();
                if (milliseconds > 0) {
                    Intent i = new Intent(AnimationSetTimeActivity.this, AnimationSetTypeActivity.class);
                    i.putExtra("duration", milliseconds);
                    AnimationSetTimeActivity.this.startActivity(i);
                }
            }
        });

        btnBack = (ImageButton) findViewById(R.id.imageButton_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }
}
