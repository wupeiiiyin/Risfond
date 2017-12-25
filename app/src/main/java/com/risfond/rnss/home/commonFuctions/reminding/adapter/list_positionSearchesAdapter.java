package com.risfond.rnss.home.commonFuctions.reminding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.home.commonFuctions.reminding.activity.AgainRemindingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Abbott on 2017/12/22.
 */

public class list_positionSearchesAdapter extends BaseAdapter {
    private LayoutInflater mInflater;//得到一个LayoutInfalter对象用来导入布局
    private List list;

    /**构造函数*/
    public list_positionSearchesAdapter(Context context,List list) {
        this.mInflater = LayoutInflater.from(context);
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();//返回数组的长度
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_list_againdetail,null);
            holder = new ViewHolder();
            /**得到各个控件的对象*/
            holder.tv_item_content = (TextView) convertView.findViewById(R.id.tv_item_content);
            convertView.setTag(holder);//绑定ViewHolder对象
        }
        else{
            holder = (ViewHolder)convertView.getTag();//取出ViewHolder对象
        }

        holder.tv_item_content.setText((Integer) list.get(position));

        return convertView;
    }

    /**存放控件*/
    class ViewHolder{
        public TextView tv_item_content;
    }

}





















