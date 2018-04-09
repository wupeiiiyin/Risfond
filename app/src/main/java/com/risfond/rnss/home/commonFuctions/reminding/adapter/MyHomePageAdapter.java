package com.risfond.rnss.home.commonFuctions.reminding.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.home.commonFuctions.reminding.activity.TransactiondatabaseSQL;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/12/26.
 */

public class MyHomePageAdapter extends BaseAdapter {

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return ids.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        if (null == view) {
            view = LayoutInflater.from(context).inflate(R.layout.item_my_list_againdetail, null);
            new ViewHolder(view);
        }
        ViewHolder holder = (ViewHolder) view.getTag();


        if (i == 0) {
            holder.tv_item_hand.setVisibility(View.INVISIBLE);
            holder.tv_item_foot.setVisibility(View.VISIBLE);

        }
//        if (i == list.size() - 1) {
//            holder.tv_item_foot.setVisibility(View.INVISIBLE);
//        }
        holder.tv_item_content.setText(list.get(i) + "");//内容
        String s = list2.get(i);
        String substring = s.substring(10);
        holder.tv_item_time.setText(substring + "");//时间

        return view;
    }

    private List<String> list2 = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    private Context context;
    private List<Integer> ids;
    private TransactiondatabaseSQL ttdbsqlite;


    public MyHomePageAdapter(List<String> list, Context context, List<String> list2, List<Integer> ids, TransactiondatabaseSQL ttdbsqlite) {
        this.list = list;
        this.context = context;
        this.list2 = list2;
        this.ids = ids;
        this.ttdbsqlite = ttdbsqlite;
    }


    public void addAll(List<String> list, List<String> list2) {
        this.list = list;
        this.list2 = list2;
        notifyDataSetChanged();
    }
    class ViewHolder {
        private TextView tv_item_content, tv_item_time, tv_item_hand, tv_item_foot;

        public ViewHolder(View view) {
            tv_item_content = (TextView) view.findViewById(R.id.tv_item_content);
            tv_item_time = (TextView) view.findViewById(R.id.tv_item_time);
            tv_item_hand = (TextView) view.findViewById(R.id.tv_item_hand);
            tv_item_foot = (TextView) view.findViewById(R.id.tv_item_foot);
            view.setTag(this);
        }
    }
}
