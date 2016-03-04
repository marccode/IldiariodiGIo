package com.polimi.deib.ildiariodigio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class VideoGridActivity extends AppCompatActivity {

    private MediaManager video_manager;
    private ArrayList<HashMap<String, String>> all_videos = new ArrayList<HashMap<String, String>>();

    private String title_selected;
    private String duration_selected;
    private String path_selected;
    private  int selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_grid);

        selected = -1;

        TextView tv = (TextView) findViewById(R.id.textview_activity_title);
        Typeface tf = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Roboto/Roboto-Bold.ttf");
        tv.setTypeface(tf);
        tv.setTextColor(getResources().getColor(R.color.title_grey));

        ImageButton btnNext = (ImageButton) findViewById(R.id.imageButton_next);
        btnNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (selected >= 0) {
                    Intent i = new Intent(VideoGridActivity.this, ChronometerVideoActivity.class);
                    i.putExtra("title", title_selected);
                    i.putExtra("duration", duration_selected);
                    i.putExtra("path", path_selected);
                    VideoGridActivity.this.startActivity(i);
                }
            }
        });

        ImageButton btnBack = (ImageButton) findViewById(R.id.imageButton_back);
        btnBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                finish();
            }
        });

        video_manager = new MediaManager();
        all_videos = video_manager.getAllVideos();
        if (all_videos == null) {
            Log.e("TAG", "all_songs is NULL");
        }
        else {
            Log.e("TAG", "all_songs is NOT NULL");
        }

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


        public GridAdapter(Context c) {
            mContext = c;
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            DBAdapter db = new DBAdapter(mContext);
            db.open();
            Cursor cursor = db.getAllVideos();
            if (cursor != null) {
                cursor.moveToFirst();
            }
            while (!cursor.isAfterLast()) {
                names.add(cursor.getString(0));
                paths.add(cursor.getString(1));
                times.add(millisecondsToString(cursor.getInt(2)));
                cursor.moveToNext();
            }
            cursor.close();
            db.close();
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
                //LayerDrawable layer = (LayerDrawable) rl.getBackground();
                //LayerDrawable layer = (LayerDrawable) getResources().getDrawable(R.drawable.audio_button);
                //Drawable bm = layer.getDrawable(1);
                //Bitmap bMap = ThumbnailUtils.createVideoThumbnail(paths.get(position), MediaStore.Video.Thumbnails.MINI_KIND);
                //Drawable d = new BitmapDrawable(getResources(), bMap);
                //rl.setBackground(d);

                //layer.setDrawableByLayerId(0, d);
                rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button_not_selected));
                //rl.setBackground(layer);

                if (position == selected) {
                    rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button_selected));
                }
                else {
                    rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button_not_selected));
                }

                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == selected) {
                            selected = -1;
                        }
                        else {
                            title_selected = names.get(position);
                            duration_selected = times.get(position);
                            path_selected = paths.get(position);
                            selected = position;
                        }
                        notifyDataSetChanged();
                    }
                });

                rowView.setOnLongClickListener(new View.OnLongClickListener() {

                    public boolean onLongClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(VideoGridActivity.this);
                        alert.setTitle("Eliminare video");
                        alert.setMessage("Vuoi eliminare il video \"" + names.get(position) + "\"?");
                        alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBAdapter db = new DBAdapter(mContext);
                                db.open();
                                db.deleteVideo(names.get(position));
                                db.close();
                                names.remove(position);
                                times.remove(position);
                                paths.remove(position);
                                dialog.dismiss();
                                selected = -1;
                                notifyDataSetChanged();

                            }
                        });
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        alert.show();
                        return true;
                    }
                });
            }
            else {

                rowView = inflater.inflate(R.layout.add_item_layout, null);
                RelativeLayout rl = (RelativeLayout) rowView.findViewById(R.id.relativelayout_add_audio_button);
                rl.setBackground(mContext.getResources().getDrawable(R.drawable.audio_button));
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(VideoGridActivity.this);
                        builderSingle.setTitle("Seleziona un video");

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                                VideoGridActivity.this,
                                android.R.layout.simple_selectable_list_item);

                        for (int i = 0; i < all_videos.size(); ++i) {
                            arrayAdapter.add(all_videos.get(i).get("videoTitle"));
                        }

                        builderSingle.setNegativeButton(
                                "cancel",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builderSingle.setAdapter(
                                arrayAdapter,
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        String title = all_videos.get(which).get("videoTitle");
                                        String path = all_videos.get(which).get("videoPath");

                                        // Find Duration:
                                        Uri uri = Uri.parse(path);
                                        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                                        mmr.setDataSource(getApplicationContext(), uri);
                                        int duration = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

                                        // Add to Database
                                        DBAdapter db = new DBAdapter(mContext);
                                        db.open();
                                        db.addAudio(title, path, duration);
                                        db.close();
                                        names.add(title);
                                        times.add(millisecondsToString(duration));
                                        paths.add(path);
                                        notifyDataSetChanged();
                                    }
                                });
                        builderSingle.show();
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