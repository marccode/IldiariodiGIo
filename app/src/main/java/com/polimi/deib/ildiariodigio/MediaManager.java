package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by marc on 01/03/16.
 */
public class MediaManager {

    // SDCard Path
    final String MEDIA_PATH = new String("/sdcard/");
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    private ArrayList<HashMap<String, String>> videosList = new ArrayList<HashMap<String, String>>();

    private Context mContext;

    // Constructor
    public MediaManager(Context context) {
        mContext = context;
    }

    public ArrayList<HashMap<String, String>> getAllSongs(){

        /*
        File home = new File(MEDIA_PATH);

        if (home.listFiles(new FileExtensionFilterSong()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilterSong())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("songPath", file.getPath());

                // Adding each song to SongList
                songsList.add(song);
            }
        }
        // return songs list array
        */
        Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Audio.Media.TITLE);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            Log.e("TAG", path);
            HashMap<String, String> song = new HashMap<String, String>();
            song.put("songTitle", title);
            song.put("songPath", path);
            songsList.add(song);
        }
        return songsList;
    }

    public ArrayList<HashMap<String, String>> getAllVideos(){
        /*
        File home = new File(MEDIA_PATH);

        if (home.listFiles(new FileExtensionFilterVideo()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilterVideo())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("videoTitle", file.getName().substring(0, file.getName().lastIndexOf('.')));
                song.put("videoPath", file.getPath());

                // Adding each song to SongList
                videosList.add(song);
            }
        }
        // return songs list array
        */Cursor cursor = mContext.getContentResolver().query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                MediaStore.Video.Media.TITLE);

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            Log.e("TAG", path);
            HashMap<String, String> song = new HashMap<String, String>();
            song.put("videoTitle", title);
            song.put("videoPath", path);
            videosList.add(song);
        }
        return videosList;
    }

    class FileExtensionFilterSong implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

    class FileExtensionFilterVideo implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp4") || name.endsWith(".MP4") || name.endsWith(".webm") || name.endsWith(".WEBM"));
        }
    }
}