package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AudioGridActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_grid);

        GridView grid = (GridView) findViewById(R.id.grid_audios);
        grid.setAdapter(new GridAdapter(this));

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                //do some stuff here on click
            }
        });
    }

    public class GridAdapter extends BaseAdapter {

        private Context mContext;
        private LayoutInflater inflater = null;

        // TINC QUE BUSCAR A LA BASE DE DADES LA DURACIÓ I NOMS DE LES CANÇONS I POSAR-HO EN ELS ARRAYS:
        ArrayList<String> times = new ArrayList<String>();
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<String> paths = new ArrayList<String>();


        //private String[] times = ["3:42", "4:11", "3:35", "3:12", "4:53", "4:09"];
        //private String[] names = ["Vull", "Corbelles", "Estiu", "Hivern", "imperfeccions", "Falling"];

        public GridAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //-------
            times.add("3:42");
            times.add("4:11");
            times.add("3:35");
            times.add("3:12");
            times.add("4:53");
            times.add("4:09");

            names.add("Vull");
            names.add("Corbelles");
            names.add("Estiu");
            names.add("Hivern");
            names.add("Imperfeccions");
            names.add("Falling");

            paths.add("/storage/sdcard/roma.mp3");
            paths.add(Environment.getExternalStorageDirectory().getPath() + "/roma.mp3");
            paths.add(Environment.getExternalStorageDirectory().getPath() + "/roma.mp3");
            paths.add(Environment.getExternalStorageDirectory().getPath() + "/roma.mp3");
            paths.add(Environment.getExternalStorageDirectory().getPath() + "/roma.mp3");
            paths.add(Environment.getExternalStorageDirectory().getPath() + "/roma.mp3");
            paths.add(Environment.getExternalStorageDirectory().getPath() + "/roma.mp3");
            //-------
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            final View rowView;

            if (position < names.size()) {
                rowView = inflater.inflate(R.layout.audio_button_layout, null);
                TextView time_textView = (TextView) rowView.findViewById(R.id.audio_button_time_textView);
                TextView name_textView = (TextView) rowView.findViewById(R.id.audio_button_name_textView);

                Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/Static/Static.otf");
                time_textView.setTypeface(tf);
                name_textView.setTypeface(tf);

                time_textView.setTextColor(getResources().getColor(R.color.white));
                name_textView.setTextColor(getResources().getColor(R.color.black));

                time_textView.setText(times.get(position));
                name_textView.setText(names.get(position));

                RelativeLayout rl = (RelativeLayout) rowView.findViewById(R.id.relativelayout_audio_button);
                rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button));

                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(AudioGridActivity.this, ChronometerAudioActivity.class);
                        i.putExtra("title", names.get(position));
                        i.putExtra("duration", times.get(position));
                        i.putExtra("path", paths.get(position));
                        AudioGridActivity.this.startActivity(i);
                    }
                });
            }
            else {

                rowView = inflater.inflate(R.layout.add_item_layout, null);
                RelativeLayout rl = (RelativeLayout) rowView.findViewById(R.id.relativelayout_audio_button);
                rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button));
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(), "ADD SONG", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            rowView.setLayoutParams(new GridView.LayoutParams(GridView.AUTO_FIT, 160));
            return rowView;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        public final int getCount() {
            return names.size() + 1;
        }

        public final long getItemId(int position) {
            return position;
        }
    }
}