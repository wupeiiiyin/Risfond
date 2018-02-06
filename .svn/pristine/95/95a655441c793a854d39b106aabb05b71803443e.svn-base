package com.risfond.rnss.home.commonFuctions.reminding.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.risfond.rnss.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/26.
 */

public class HomePageAdapter extends BaseAdapter {
    public HomePageAdapter(List<String> list,Context context) {
        this.list = list;
        this.context = context;
    }
    public interface OnItemClicklitener{
        void OnItemClick(View view, int position);
    }

    private OnItemClicklitener onItemClicklitener;

    public void setOnItemClicklitener(OnItemClicklitener onItemClicklitener) {
        this.onItemClicklitener = onItemClicklitener;
    }

    private List<String> list = new ArrayList<>();
    private Context context;
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_list_againdetail,null);
        TextView tv_item_content = (TextView) view.findViewById(R.id.tv_item_content);
//        TextView tv_item_time = (TextView) view.findViewById(R.id.tv_item_time);

        tv_item_content.setText(list.get(i)+"");
        return view;
    }
}
