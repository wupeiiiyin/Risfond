package com.risfond.rnss.home.customer.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.CustomerSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/1
 * @desc    客户 适配器
 */
public class CustomerSearchV2Adapter extends BaseQuickAdapter<CustomerSearch,BaseViewHolder> {


    public CustomerSearchV2Adapter() {
        super(R.layout.item_customer);
    }

    @Override
    protected void convert(BaseViewHolder holder, CustomerSearch item) {
        holder.setText(R.id.tv_position, item.getName());
        setLevelResource(item.getLevel(), (ImageView) holder.getView(R.id.iv_level));
        holder.setText(R.id.tv_time, item.getLastCommunicationTime());
        holder.setText(R.id.tv_source, item.getClientSource());
        holder.setText(R.id.tv_status, item.getClientStatus());
        holder.setText(R.id.tv_important, item.getClientImportStatus());
        holder.setText(R.id.tv_customer_number, item.getCode());
        holder.setText(R.id.tv_record_number, String.valueOf(item.getMemoCount()));
        holder.setText(R.id.tv_job_number, String.valueOf(item.getJobCount()));
    }


    private void setLevelResource(int level, ImageView view) {
        switch (level) {
            case 0:
                view.setVisibility(View.INVISIBLE);
                break;
            case 1:
                view.setImageResource(R.mipmap.level1);
                view.setVisibility(View.VISIBLE);
                break;
            case 2:
                view.setImageResource(R.mipmap.level2);
                view.setVisibility(View.VISIBLE);
                break;
            case 3:
                view.setImageResource(R.mipmap.level3);
                view.setVisibility(View.VISIBLE);
                break;
            case 4:
                view.setImageResource(R.mipmap.level4);
                view.setVisibility(View.VISIBLE);
                break;
            case 5:
                view.setImageResource(R.mipmap.level5);
                view.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
