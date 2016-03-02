package com.polimi.deib.ildiariodigio;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TestActivity extends ListActivity {

    private static final int LIST_ITEM_TYPE_1 = 0;
    private static final int LIST_ITEM_TYPE_2 = 1;
    private static final int LIST_ITEM_TYPE_COUNT = 2;

    private static final int LIST_ITEM_COUNT = 10;
    // The first five list items will be list item type 1
    // and the last five will be list item type 2
    private static final int LIST_ITEM_TYPE_1_COUNT = 5;

    private MyCustomAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new MyCustomAdapter();
        for (int i = 0; i < LIST_ITEM_COUNT; i++) {
            if (i < LIST_ITEM_TYPE_1_COUNT)
                mAdapter.addItem("item type 1");
            else
                mAdapter.addItem("item type 2");
        }
        setListAdapter(mAdapter);
    }

    private class MyCustomAdapter extends BaseAdapter {

        private ArrayList<String> mData = new ArrayList<String>();
        private LayoutInflater mInflater;

        public MyCustomAdapter() {
            mInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public void addItem(final String item) {
            mData.add(item);
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            if(position < LIST_ITEM_TYPE_1_COUNT)
                return LIST_ITEM_TYPE_1;
            else
                return LIST_ITEM_TYPE_2;
        }

        @Override
        public int getViewTypeCount() {
            return LIST_ITEM_TYPE_COUNT;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            int type = getItemViewType(position);
            if (convertView == null) {
                holder = new ViewHolder();
                //convertView = mInflater.inflate(R.layout.list_item_type1, null);
                //holder.song_name = (TextView)convertView.findViewById(R.id.list_item_type1_text_view);
                convertView.setTag(holder);
            }
            else {
                holder = (ViewHolder)convertView.getTag();
            }
            holder.song_name.setText(mData.get(position));
            return convertView;
        }

    }

    public static class ViewHolder {
        public TextView song_name;
        //public TextView song_duration;
    }

}