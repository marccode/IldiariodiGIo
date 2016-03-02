package com.polimi.deib.ildiariodigio;

import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.Calendar;

public class AnimationSetUpActivity extends AppCompatActivity {

    private EditText minutes_TextView;
    private EditText seconds_TextView;
    private Button button_time;
    private TimePickerDialog timePickerDialog;
    private int minutes;
    private int seconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set_up);


        // ----------------

        final TimePickerDialog.OnTimeSetListener onTimeSetListener
                = new TimePickerDialog.OnTimeSetListener(){
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                minutes = hourOfDay;
                seconds = minute;
            }};

        button_time = (Button) findViewById(R.id.button_time);
        button_time.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //oast.makeText(this, "Button Clicked", Toast.LENGTH_LONG).show();
                timePickerDialog = new TimePickerDialog(
                        AnimationSetUpActivity.this,
                        onTimeSetListener,
                        0,
                        0, false);
                timePickerDialog.setTitle("Quanto tempo?");

                timePickerDialog.show();
            }
        });

    }

    // ---------


/*
        minutes_TextView = (EditText) findViewById(R.id.editText_minutes);
        seconds_TextView = (EditText) findViewById(R.id.editText_seconds);

        minutes_TextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //if (s.toString().isEmpty()) return;
                //if (Integer.parseInt(s.toString()) < 10)
                //    minutes_TextView.setText("0" + s);
                //minutes_TextView.setText(s);
                //your code
            }
        });
    }
    */
}
