package com.risfond.rnss.home.resume.adapter;


import android.support.annotation.LayoutRes;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.IndustrieInfo;

/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/5
 * @desc    行业适配器
 */
public class IndustrieAdapter  extends BaseQuickAdapter<IndustrieInfo,BaseViewHolder>{
    public IndustrieAdapter() {
        super(R.layout.item_industrie_layout);
    }

    @Override
    protected void convert(BaseViewHolder holder, IndustrieInfo item) {
        holder.setText(R.id.id_industrie_item_content, item.getContent());
        holder.setChecked(R.id.id_industrie_item_cb, item.isCheck());
    }
}
