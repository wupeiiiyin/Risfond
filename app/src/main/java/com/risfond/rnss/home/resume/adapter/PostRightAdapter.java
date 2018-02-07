package com.risfond.rnss.home.resume.adapter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.PositionInfo;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/5
 * @desc 职位选择 右侧适配器
 */
public class PostRightAdapter extends BaseQuickAdapter<PositionInfo.Data, BaseViewHolder> {
    public PostRightAdapter() {
        super(R.layout.item_city);
    }

    @Override
    protected void convert(BaseViewHolder holder, PositionInfo.Data item) {
        holder.setText(R.id.tv_city, item.getContent());
        holder.setChecked(R.id.cb_city, item.isCheck());
    }
}
