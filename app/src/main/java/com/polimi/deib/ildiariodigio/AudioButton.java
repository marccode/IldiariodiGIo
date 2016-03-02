package com.polimi.deib.ildiariodigio;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by marc on 02/02/16.
 */
public class AudioButton extends RelativeLayout {

    public AudioButton(Context context, int id)
    {
        super(context);

        // if our context is not Activity we can't get View supplied by id
        if (!(context instanceof Activity))
            return;

        // find relative layout by id
        View v = ((Activity)context).findViewById(id);

        //LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View v = inflater.inflate(R.layout.audio_button_layout, null);

        // is it RelativeLayout ?
        if (!(v instanceof RelativeLayout))
            return;

        //cast it to relative layout
        RelativeLayout layout = (RelativeLayout)v;

        // copy layout parameters
        ViewGroup.LayoutParams params = layout.getLayoutParams();
        this.setLayoutParams(params);

        // here I am using temporary instance of Button class
        // to get standard button background and to get button text color

        //Button bt = new Button(context);
        //this.setBackgroundDrawable(bt.getBackground());

        // copy all child from relative layout to this button
        while (layout.getChildCount() > 0)
        {
            View vchild = layout.getChildAt(0);
            layout.removeViewAt(0);
            this.addView(vchild);

            // if child is textView set its color to standard buttong text colors
            // using temporary instance of Button class
            //if (vchild instanceof TextView)
            //{
            //    ((TextView)vchild).setTextColor(bt.getTextColors());
            //}

            // just to be sure that child views can't be clicked and focused
            vchild.setClickable(false);
            vchild.setFocusable(false);
            vchild.setFocusableInTouchMode(false);
        }

        // remove all view from layout (maybe it's not necessary)
        layout.removeAllViews();

        // set that this button is clickable, focusable, ...
        this.setClickable(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(false);

        // replace relative layout in parent with this one modified to looks like button
        ViewGroup vp = (ViewGroup)layout.getParent();
        int index = vp.indexOfChild(layout);
        vp.removeView(layout);
        vp.addView(this, index);

        setFont("fonts/Static/Static.otf");
        setBackground(this.getResources().getDrawable(R.drawable.audio_button));
        TextView timer = (TextView) findViewById(R.id.audio_button_time_textView);
        TextView name = (TextView) findViewById(R.id.audio_button_name_textView);
        timer.setTextColor(getResources().getColor(R.color.white));
        name.setTextColor(getResources().getColor(R.color.black));

        this.setId(id);
    }

    // method for setting texts for the text views
    public void setTime(String time_song) {
        TextView timer = (TextView) findViewById(R.id.audio_button_time_textView);
        //timer.setText(time_song);
        timer.setText("bbb");
    }

    public void setName(String name_song) {
        TextView name = (TextView) findViewById(R.id.audio_button_name_textView);
        //name.setText(name_song);
        name.setText("aaa");
    }

    // method for setting drawable for the images
    public void setImageDrawable(int id, Drawable drawable)
    {

        View v = findViewById(id);
        if (null != v && v instanceof ImageView)
        {
            ((ImageView)v).setImageDrawable(drawable);
        }

    }

    // method for setting images by resource id
    public void setImageResource(int id, int image_resource_id)
    {

        View v = findViewById(id);
        if (null != v && v instanceof ImageView)
        {
            ((ImageView)v).setImageResource(image_resource_id);
        }

    }

    public void setFont(String font) {
        Typeface tf = Typeface.createFromAsset(this.getContext().getAssets(), font);
        TextView timer = (TextView) findViewById(R.id.audio_button_time_textView);
        TextView name = (TextView) findViewById(R.id.audio_button_name_textView);
        timer.setTypeface(tf);
        name.setTypeface(tf);

    }

}