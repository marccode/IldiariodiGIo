/*
package com.polimi.deib.ildiariodigio;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DiarioGridActivity2 extends AppCompatActivity {

    GenericModelAdapter adapter;
    ListView listView;
    private static final int NUMBER_OF_COLS = 4;
    List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
    Map<String, String> sectionHeaderTitles = new HashMap<String, String>();
    ArrayList<Photo> photos;


    private class Photo {
        public int id;
        public String date;
        public String path;

        public Photo(int id, String path, String date) {
            this.id = id;
            this.date = date;
            this.path = path;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView);

        photos = new ArrayList<Photo>();
        DBAdapter db = new DBAdapter(getApplicationContext());
        db.open();
        Cursor c = db.getAllPhotos();
        if (c != null) {
            c.moveToFirst();
        }
        while (!c.isAfterLast()) {
            Log.e("TAG", Integer.toString(c.getInt(0)) + " ,PATH: " + c.getString(1) + " ,DATE: " + c.getString(2));
            Photo p = new Photo(c.getInt(0), c.getString(1), c.getString(2));
            photos.add(p);
            c.moveToNext();
        }

        initDummyItems();
        adapter = new GenericModelAdapter(this,R.layout.list_item, items, sectionHeaderTitles, NUMBER_OF_COLS, mItemClickListener);
        listView.setAdapter(adapter);
    }

    View.OnClickListener mItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (Integer)v.getTag(R.id.row);
            int col = (Integer)v.getTag(R.id.col);

            Map<String, List<Object>> map = adapter.getItem(position);
            String selectedItemType = adapter.getItemTypeAtPosition(position);
            List<Object> list = map.get(selectedItemType);
            GenericModel model = (GenericModel)list.get(col);
            Toast.makeText(getApplicationContext(), "" + model.getHeader(), Toast.LENGTH_SHORT).show();
        }
    };

    private void initDummyItems(){
        List<String> itemTypesList = new ArrayList<String>();
        itemTypesList.add(Constants.ORANGE);
        itemTypesList.add(Constants.PINEAPPLE);
        itemTypesList.add(Constants.BANANA);
        sectionHeaderTitles.put(Constants.ORANGE, "Oranges");
        sectionHeaderTitles.put(Constants.PINEAPPLE, "Pineapples");
        sectionHeaderTitles.put(Constants.BANANA, "Bananas");

        // PER CADA FOTO
        for (String itemType : itemTypesList){
            Map<String, List<Object>> map = new HashMap<String, List<Object>>();
            List<Object> list = new ArrayList<Object>();

            for (int i = 0 ; i < 10 ; i++){
                String itemName = itemType + " " + i;
                String countryOfOrigin = "Country " + i;
                int image;


                list.add(object);
            }

            map.put(itemType, list);
            items.add(map);
        }
    }

}
    public class GenericModelAdapter extends ArrayAdapter<Map<String, List<Object>>>{

        List<Map<String, List<Object>>> items = new ArrayList<Map<String, List<Object>>>();
        int numberOfCols;
        List<String> headerPositions = new ArrayList<String>();
        Map<String, String> itemTypePositionsMap = new LinkedHashMap<String, String>();
        Map<String, Integer> offsetForItemTypeMap = new LinkedHashMap<String, Integer>();
        LayoutInflater layoutInflater;
        View.OnClickListener mItemClickListener;
        Map<String, String> sectionHeaderTitles;

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
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(isHeaderPosition(position)){
                convertView = layoutInflater.inflate(R.layout.grid_header_view, null);

                TextView headerText = (TextView)convertView.findViewById(R.id.headerText);
                String section = getItemTypeAtPosition(position);
                headerText.setText(getHeaderForSection(section));
                return convertView;
            }else{
                LinearLayout row = (LinearLayout)layoutInflater.inflate(R.layout.row_item, null);
                Map<String, List<Object>> map = getItem(position);
                List<Object> list = map.get(getItemTypeAtPosition(position));

                for (int i = 0; i < numberOfCols; i++){
                    FrameLayout grid = (FrameLayout)layoutInflater.inflate(R.layout.grid_item, row, false);
                    ImageView imageView;
                    if (i < list.size()){
                        GenericModel model = (GenericModel)list.get(i);
                        if (grid != null){
                            imageView = (ImageView)grid.findViewWithTag("image");
                            imageView.setBackgroundResource(model.getImageResource());

                            TextView textView = (TextView)grid.findViewWithTag("subHeader");
                            textView.setText(model.getHeader());

                            grid.setTag(R.id.row, position);
                            grid.setTag(R.id.col, i);
                            grid.setOnClickListener(mItemClickListener);
                        }
                    }else{
                        if (grid != null){
                            grid.setVisibility(View.INVISIBLE);
                            grid.setOnClickListener(null);
                        }
                    }
                    row.addView(grid);
                }
                return row;
            }
        }

        @Override
        public int getCount() {
            int totalItems = 0;
            for (Map<String, List<Object>> map : items){
                Set<String> set = map.keySet();
                for(String key : set){
                    //calculate the number of rows each set homogeneous grid would occupy
                    List<Object> l = map.get(key);
                    int rows = l.size() % numberOfCols == 0 ? l.size() / numberOfCols : (l.size() / numberOfCols) + 1;

                    // insert the header position
                    if (rows > 0){
                        headerPositions.add(String.valueOf(totalItems));
                        offsetForItemTypeMap.put(key, totalItems);

                        itemTypePositionsMap.put(key, totalItems + "," + (totalItems + rows) );
                        totalItems += 1; // header view takes up one position
                    }
                    totalItems+= rows;
                }
            }
            return totalItems;
        }

        @Override
        public Map<String, List<Object>> getItem(int position) {
            if (!isHeaderPosition(position)){
                String itemType = getItemTypeAtPosition(position);
                List<Object> list = null;
                for (Map<String, List<Object>> map : items) {
                    if (map.containsKey(itemType)){
                        list = map.get(itemType);
                        break;
                    }
                }
                if (list != null){
                    int offset = position - getOffsetForItemType(itemType);
                    //remove header position
                    offset -= 1;
                    int low = offset * numberOfCols;
                    int high = low + numberOfCols  < list.size() ? (low + numberOfCols) : list.size();
                    List<Object> subList = list.subList(low, high);
                    Map<String, List<Object>> subListMap = new HashMap<String, List<Object>>();
                    subListMap.put(itemType, subList);
                    return subListMap;
                }
            }
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

*/