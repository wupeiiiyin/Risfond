package com.risfond.rnss.home.resume.adapter;

import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.PositionInfo;

import static android.R.attr.data;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/5
 * @desc 职位选择 左侧适配器
 */
public class PostLeftAdapter extends BaseQuickAdapter<PositionInfo, BaseViewHolder> {
    public PostLeftAdapter() {
        super(R.layout.item_provinces);
    }

    @Override
    protected void convert(BaseViewHolder holder, PositionInfo item) {
        holder.setText(R.id.province, item.getTitle());
        holder.setBackgroundColor(R.id.id_item,item.isCheck() ? ContextCompat.getColor(mContext, R.color.color_home_back) : Color.WHITE);
        boolean flag = false;
        for (PositionInfo.Data data : item.getDatas()) {
            if (data.isCheck()) {
                flag = true;
            }
        }
        holder.setVisible(R.id.v_tip, flag);
    }
}
