package com.polimi.deib.ildiariodigio;

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

    // Constructor
    public MediaManager() {}

    public ArrayList<HashMap<String, String>> getAllSongs(){
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
        return songsList;
    }

    public ArrayList<HashMap<String, String>> getAllVideos(){
        File home = new File(MEDIA_PATH);

        if (home.listFiles(new FileExtensionFilterVideo()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilterVideo())) {
                HashMap<String, String> song = new HashMap<String, String>();
                song.put("videoTitle", file.getName().substring(0, (file.getName().length() - 4)));
                song.put("videoPath", file.getPath());

                // Adding each song to SongList
                videosList.add(song);
            }
        }
        // return songs list array
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