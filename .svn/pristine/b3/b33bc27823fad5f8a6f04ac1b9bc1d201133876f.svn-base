package com.risfond.rnss.home.commonFuctions.reminding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.risfond.rnss.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/12/22.
 */

public class list_positionSearchesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList list_positionSearches;

    public list_positionSearchesAdapter(Context context, ArrayList list_positionSearches) {
        this.context = context;
        this.list_positionSearches = list_positionSearches;
    }

    @Override
    public int getCount() {
        if (list_positionSearches!=null){
            return list_positionSearches.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list_positionSearches.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyHolder myHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_remindinformation,null);
            myHolder = new MyHolder();
            convertView.setTag(myHolder);
        }else {
            myHolder = (MyHolder) convertView.getTag();
        }
        return convertView;
    }

    private class MyHolder{

    }
}






















