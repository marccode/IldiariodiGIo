package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AnimationSetTypeActivity extends AppCompatActivity {

    private ImageView circle_imageView;
    private ImageView pizza_imageView;
    private ImageView waves_imageView;
    private int selected;
    private String[] type = {"circle","pizza", "waves"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_set_type);

        selected = -1;

        Intent i = getIntent(); // gets the previously created intent
        final int duration = i.getIntExtra("duration", 0);

        // TITLE
        TextView tv = (TextView) findViewById(R.id.textview_activity_title);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        tv.setTypeface(tf);
        tv.setTextColor(getResources().getColor(R.color.title_grey));

        // BUTTONS
        ImageButton btnNext = (ImageButton) findViewById(R.id.imageButton_next);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (selected >= 0) {
                    Intent i = new Intent(AnimationSetTypeActivity.this, ChronometerAnimationActivity.class);
                    i.putExtra("duration", duration);
                    i.putExtra("animation_type", type[selected]);
                    AnimationSetTypeActivity.this.startActivity(i);
                }
            }
        });

        ImageButton btnBack = (ImageButton) findViewById(R.id.imageButton_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(AnimationSetTypeActivity.this, AnimationSetTimeActivity.class);
                AnimationSetTypeActivity.this.startActivity(i);
                //finish();
            }
        });

        // GRID
        //GridView grid = (GridView) findViewById(R.id.grid_audios);
        //grid.setAdapter(new GridAdapter(this));

        // TABLE
        circle_imageView = (ImageView) findViewById(R.id.item_circle);
        pizza_imageView = (ImageView) findViewById(R.id.item_pizza);
        waves_imageView = (ImageView) findViewById(R.id.item_waves);

        circle_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selected != 0) {
                    selected = 0;
                    circle_imageView.setImageResource(R.drawable.circle_col);
                    pizza_imageView.setImageResource(R.drawable.pizza_bn);
                    waves_imageView.setImageResource(R.drawable.waves_bn);
                }
                else {
                    selected = -1;
                    circle_imageView.setImageResource(R.drawable.circle_bn);
                }

                //circle_imageView.setBackgroundColor(Color.parseColor("#EDCF9D"));
                //pizza_imageView.setBackgroundColor(Color.TRANSPARENT);
                //waves_imageView.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        pizza_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selected != 1) {
                    selected = 1;
                    pizza_imageView.setImageResource(R.drawable.pizza_col);
                    circle_imageView.setImageResource(R.drawable.circle_bn);
                    waves_imageView.setImageResource(R.drawable.waves_bn);
                }
                else {
                    selected = -1;
                    pizza_imageView.setImageResource(R.drawable.pizza_bn);
                }

                //pizza_imageView.setBackgroundColor(Color.parseColor("#EDCF9D"));
                //circle_imageView.setBackgroundColor(Color.TRANSPARENT);
                //waves_imageView.setBackgroundColor(Color.TRANSPARENT);
            }
        });
        waves_imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (selected != 2) {
                    selected = 2;
                    waves_imageView.setImageResource(R.drawable.waves_col);
                    pizza_imageView.setImageResource(R.drawable.pizza_bn);
                    circle_imageView.setImageResource(R.drawable.circle_bn);
                }
                else {
                    selected = -1;
                    waves_imageView.setImageResource(R.drawable.waves_bn);
                }
                //waves_imageView.setBackgroundColor(Color.parseColor("#EDCF9D"));
                //pizza_imageView.setBackgroundColor(Color.TRANSPARENT);
                //circle_imageView.setBackgroundColor(Color.TRANSPARENT);
            }
        });


    }

    public class GridAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater inflater = null;

        ArrayList<Integer> images_selected = new ArrayList<Integer>();
        ArrayList<Integer> images_not_selected = new ArrayList<Integer>();


        public GridAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            images_not_selected.add(R.drawable.circle_bn);
            images_not_selected.add(R.drawable.pizza_bn);
            images_not_selected.add(R.drawable.waves_bn);

            images_selected.add(R.drawable.circle_col);
            images_selected.add(R.drawable.pizza_col);
            images_selected.add(R.drawable.waves_col);
        }

        public View getView(final int position, View convertView, final ViewGroup parent) {

            ImageView rowView = new ImageView(mContext);

            //final View rowView;
            //rowView = inflater.inflate(R.layout.animations_grid_item, null);
            //TextView time_textView = (TextView) rowView.findViewById(R.id.audio_button_time_textView);
            //TextView name_textView = (TextView) rowView.findViewById(R.id.audio_button_name_textView);


            //RelativeLayout rl = (RelativeLayout) rowView.findViewById(R.id.relativelayout_audio_button);
            //rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button));

            if (position == selected) {
                rowView.setBackground(ContextCompat.getDrawable(mContext, (int) images_selected.get(position)));
                //rowView.setImageResource();
                //rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button_selected));
            }
            else {
                rowView.setBackground(ContextCompat.getDrawable(mContext, (int)images_not_selected.get(position)));
                //rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button_not_selected));
            }

            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (position == selected) {
                        selected = -1;
                    }
                    else {
                        selected = position;
                    }
                    notifyDataSetChanged();
                }
            });
            rowView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 160));
            return rowView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        public final int getCount() {
            return images_selected.size();
        }

        public final long getItemId(int position) {
            return position;
        }

    }
}
