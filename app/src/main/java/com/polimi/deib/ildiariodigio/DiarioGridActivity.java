package com.polimi.deib.ildiariodigio;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiarioGridActivity extends AppCompatActivity {

    ImageButton back;

    GenericModelAdapter adapter;
    ListView listView;
    private static final int NUMBER_OF_COLS = 3;
    List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
    Map<String, String> sectionHeaderTitles = new HashMap<String, String>();

    ArrayList<Photo> photos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario_grid);

        ImageView profile_pic = (ImageView) findViewById(R.id.imageview_profile);
        DBAdapter db2 = new DBAdapter(getApplicationContext());
        db2.open();
        String path = db2.getProfilePhotoChildren();
        if (!path.equals("null")) {
            File parent_photo = new File(path);
            if(parent_photo.exists() && !parent_photo.isDirectory()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(parent_photo.getAbsolutePath());
                profile_pic.setImageBitmap(getCroppedBitmap(myBitmap));
            }
        }
        db2.close();


        photos = new ArrayList<Photo>();
        DBAdapter db = new DBAdapter(getApplicationContext());
        db.open();
        Cursor c = db.getAllPhotos();
        if (c != null) {
            c.moveToFirst();
        }
        while (!c.isAfterLast()) {
            Log.e("TAG", Integer.toString(c.getInt(0)) + " ,PATH: " + c.getString(1) + " ,DATE: " + c.getString(2));
            Photo p = new Photo(c.getInt(0), c.getString(1), c.getString(2), c.getString(3));
            photos.add(p);
            c.moveToNext();
        }


        back = (ImageButton)findViewById(R.id.imageButton_back);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiarioGridActivity.this, DiarioMenuActivity.class);
                DiarioGridActivity.this.startActivity(intent);
            }
        });

        adapter = new GenericModelAdapter(this,R.layout.list_item, items, sectionHeaderTitles, NUMBER_OF_COLS, mItemClickListener);
        if (adapter == null) {
            Log.e("TAG", "adapter is null");
        }
        listView = (ListView)findViewById(R.id.listView);
        if (listView == null) {
            Log.e("TAG", "listview is null");
        }
        listView.setAdapter(adapter);
    }

    View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //int position = (Integer)v.getTag(R.id.row);
            //int col = (Integer)v.getTag(R.id.col);

            //Map<String, List<Object>> map = adapter.getItem(position);
            //String selectedItemType = adapter.getItemTypeAtPosition(position);
            //List<Object> list = map.get(selectedItemType);
            //GenericModel model = (GenericModel)list.get(col);
            //Toast.makeText(getApplicationContext(), "" + model.getHeader(), Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "clikclicklick", Toast.LENGTH_SHORT).show();
        }
    };

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    private class Photo {
        public int id;
        public String name;
        public String date;
        public String path;

        public Photo(int id, String path, String date, String name) {
            this.id = id;
            this.name = name;
            this.date = date;
            this.path = path;
        }
    }

    public class GenericModelAdapter extends ArrayAdapter<Map<String, List<Object>>> {

        List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
        int numberOfCols;
        List<String> headerPositions = new ArrayList<String>();
        Map<String, String> itemTypePositionsMap = new LinkedHashMap<String, String>();
        Map<String, Integer> offsetForItemTypeMap = new LinkedHashMap<String, Integer>();
        LayoutInflater layoutInflater;
        View.OnClickListener mItemClickListener;
        Map<String, String> sectionHeaderTitles;
        ArrayList<Integer> header_positions;
        int pos;

        // <JO>
        Date last_date;

        ArrayList<ArrayList<Photo>> days;
        //boolean header;
        int aux;
        // </JO>

        public GenericModelAdapter(Context context, int textViewResourceId, List<Map<String, List<Object>>> items, int numberOfCols, View.OnClickListener mItemClickListener){
            this(context, textViewResourceId, items, null, numberOfCols, mItemClickListener);
        }

        public GenericModelAdapter(Context context, int textViewResourceId, List<Map<String, List<Object>>> items, Map<String, String> sectionHeaderTitles, int numberOfCols, View.OnClickListener mItemClickListener){
            super(context, textViewResourceId, items);
            this.items = items;
            this.numberOfCols = numberOfCols;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.mItemClickListener = mItemClickListener;
            this.sectionHeaderTitles = sectionHeaderTitles;
            header_positions = new ArrayList<>();

            // <JO>
            //header = true;
            if (photos.size() > 0) {
                try {
                    last_date = new SimpleDateFormat("yyyy-MM-dd").parse(photos.get(0).date);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
                days = new ArrayList<ArrayList<Photo>>();
                days.add(new ArrayList<Photo>());
                header_positions.add(0);
                pos = 1;
                int column_count = 0;
                int j = 0;
                int size = photos.size();
                for (int i = 0; i < size; ++i) {
                    Date d = new Date();
                    try {
                        d = new SimpleDateFormat("yyyy-MM-dd").parse(photos.get(i).date);
                    } catch (java.text.ParseException e) {
                        e.printStackTrace();
                    }
                    //if (d.after(last_date)) {
                    if (!d.toString().equals(last_date.toString())) {
                        Log.e("AAA", " COMPARATION: " + d.toString() + " != " + last_date.toString());
                        days.add(new ArrayList<Photo>());
                        last_date = d;
                        ++j;
                        ++pos;
                        header_positions.add(pos);
                        ++pos;
                        column_count = 0;
                    }
                    if (column_count == numberOfCols) {
                        // NEW ROW
                        column_count = 0;
                        ++pos;
                    } else {
                        ++column_count;
                    }
                    Photo p = photos.get(i);
                    days.get(j).add(p);
                }

                Log.e("AAA", "Number of days: " + Integer.toString(days.size()));
                for (int i = 0; i < days.size(); ++i) {
                    Log.e("AAA", "Number of photos in day " + Integer.toString(i) + ": " + Integer.toString(days.get(i).size()));
                }
            }
            else {
                pos = -1;
            }
        }

        private boolean isHeader(int position) {
            return header_positions.contains(position);
        }

        private int getAux(int num) {
            int size = header_positions.size();
            for (int i = 0; i < size; ++i) {
                if (num < header_positions.get(i)){
                    return i-1;
                }
            }
            return size -1;
        }

        private int getDiff(int position) {
            int size = header_positions.size();
            for (int i = 0; i < size; ++i) {
                if (position < header_positions.get(i)){
                    return (position - header_positions.get(i-1));
                }
            }
            return (position - header_positions.get(size-1));
        }

        private int getI(int num, int position) {
            return num + (3 * (getDiff(position) - 1));
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.e("AAA", "getView");
            aux = position / 2;
            // <JO>
            if (isHeader(position)) {
                convertView = layoutInflater.inflate(R.layout.grid_header_view, null);
                TextView headerText = (TextView)convertView.findViewById(R.id.headerText);
                Log.e("AAA", "--- IF ---");
                Log.e("AAA", "POSITION: " + Integer.toString(position));
                String date_string = getDateString(days.get(getAux(position)).get(0).date);
                headerText.setText(date_string);
                //header = false;
                return convertView;
            }
            else {
                LinearLayout row = (LinearLayout)layoutInflater.inflate(R.layout.row_item, null);

                //Map<String, List<Object>> map = getItem(position);
                //List<Object> list = map.get(getItemTypeAtPosition(position));

                Log.e("AAA", "--- ELSE ---");
                Log.e("AAA", "POSITION: " + Integer.toString(position));
                Log.e("AAA", "AUX: " + Integer.toString(getAux(position)));
                Log.e("AAA", "DAYS SIZE: " + Integer.toString(days.size()));
                int size_day = days.get(getAux(position)).size();
                //for (int i = 0; i < size_day; i++){
                for (int i = 0; i < numberOfCols; i++){
                    Log.e("AAA", "FOR ITERATION: " + Integer.toString(i));
                    FrameLayout grid = (FrameLayout) layoutInflater.inflate(R.layout.grid_item, row, false);
                    ImageView imageView;
                    Log.e("BBB", "ANTES IF getI: " + Integer.toString(getI(i, position)));
                    Log.e("BBB", "ANTES IF POSITION: " + Integer.toString(position));
                    if (getI(i, position) < days.get(getAux(position)).size()) {
                        //if (i < list.size()){
                        //    if (grid != null){
                        imageView = (ImageView) grid.findViewWithTag("image");
                        //Log.e("BBB", "getAux: " + Integer.toString(getAux(position)) + "; Position: " + Integer.toString(position) + "; i: " + Integer.toString(i));
                        Log.e("BBB", "getI: " + Integer.toString(getI(i, position)));
                        File imgFile = new File(days.get(getAux(position)).get(getI(i, position)).path);

                        if (imgFile.exists()) {

                            Picasso.with(getApplicationContext()).load(imgFile).into(imageView);

                            //Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                            //imageView.setImageBitmap(myBitmap);

                        }

                        TextView textView = (TextView) grid.findViewWithTag("subHeader");
                        final int id = days.get(getAux(position)).get(getI(i, position)).id;
                        String photo_name = days.get(getAux(position)).get(getI(i, position)).name;
                        textView.setText(photo_name);

                        //grid.setTag(R.id.row, position);
                        //grid.setTag(R.id.col, i);
                        grid.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Toast.makeText(getApplicationContext(), Integer.toString(id), Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(DiarioGridActivity.this, FotoDiarioActivity.class);
                                i.putExtra("id", id);
                                DiarioGridActivity.this.startActivity(i);
                            }
                        });

                    }else {
                        if (grid != null) {
                            grid.setVisibility(View.INVISIBLE);
                            grid.setOnClickListener(null);
                        }
                        i = numberOfCols;
                    }
                    row.addView(grid);
                }
                //header = true;
                //++aux;
                return row;
            }
        }

        private String getDateString(String date) {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7));
            String day_string = date.substring(8, 10);

            String month_string;
            switch (month) {
                case 1:
                    month_string = "gennaio";
                    break;

                case 2:
                    month_string = "febbraio";
                    break;

                case 3:
                    month_string = "marzo";
                    break;

                case 4:
                    month_string = "aprile";
                    break;

                case 5:
                    month_string = "maggio";
                    break;

                case 6:
                    month_string = "giugno";
                    break;

                case 7:
                    month_string = "luglio";
                    break;

                case 8:
                    month_string = "agosto";
                    break;

                case 9:
                    month_string = "settembre";
                    break;

                case 10:
                    month_string = "ottobre";
                    break;

                case 11:
                    month_string = "novembre";
                    break;

                case 12:
                    month_string = "dicembre";
                    break;

                default:
                    month_string = "unknown";
            }

            String year_string = "";
            Date now = new Date();
            if (now.getYear() != year) {
                year_string = " " + Integer.toString(year);
            }

            Date d = new Date();
            try {
                d = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                Log.e("fecha",d.toString());
            } catch(java.text.ParseException e) {
                e.printStackTrace();
            }
            String weekday_string = "Oggi";
            now.setHours(0);
            now.setMinutes(0);
            now.setSeconds(0);

            Log.e("fecha_actual", now.toString());

            if (!now.toString().equals(d.toString())) {
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int weekday = c.get(Calendar.DAY_OF_WEEK);

                switch (weekday) {
                    case 1:
                        weekday_string = "domenica";
                        break;

                    case 2:
                        weekday_string = "lunedì";
                        break;

                    case 3:
                        weekday_string = "martedì";
                        break;

                    case 4:
                        weekday_string = "mercoledì";
                        break;

                    case 5:
                        weekday_string = "giovedì";
                        break;

                    case 6:
                        weekday_string = "venerdì";
                        break;

                    case 7:
                        weekday_string = "sabato";
                        break;
                }
            }
            return weekday_string + ", " + day_string + " " + month_string + year_string;
        }

        @Override
        public int getCount() {
            return pos + 1;
        }

        @Override
        public Map<String, List<Object>> getItem(int position) {
            return null;
        }

        public String getItemTypeAtPosition(int position){
            String itemType = "Unknown";
            Set<String> set = itemTypePositionsMap.keySet();

            for(String key : set){
                String[] bounds = itemTypePositionsMap.get(key).split(",");
                int lowerBound = Integer.valueOf(bounds[0]);
                int upperBoundary = Integer.valueOf(bounds[1]);
                if (position >= lowerBound && position <= upperBoundary){
                    itemType = key;
                    break;
                }
            }
            return itemType;
        }

        public int getOffsetForItemType(String itemType){
            return offsetForItemTypeMap.get(itemType);
        }

        public boolean isHeaderPosition(int position){
            return headerPositions.contains(String.valueOf(position));
        }

        private String getHeaderForSection(String section){
            if (sectionHeaderTitles != null){
                return sectionHeaderTitles.get(section);
            }else{
                return section;
            }
        }

    }

}