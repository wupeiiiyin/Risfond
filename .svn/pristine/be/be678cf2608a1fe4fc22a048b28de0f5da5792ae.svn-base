package com.risfond.rnss.home.position.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.PositionSearch;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;


/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/12/4
 * @desc    我的职位适配器V2
 */
public class PositionSearchV2Adapter extends BaseQuickAdapter<PositionSearch,BaseViewHolder> {


    public PositionSearchV2Adapter() {
        super(R.layout.item_position);
    }



    @Override
    protected void convert(BaseViewHolder holder, PositionSearch item) {
        holder.setText(R.id.tv_name, item.getTitle());
        holder.setText(R.id.tv_time, item.getLastCommunicationTime());
        holder.setText(R.id.tv_salary, item.getSalary());
        holder.setText(R.id.tv_location, item.getLocations());
        holder.setText(R.id.tv_period, String.valueOf(item.getRunDay()));
        holder.setText(R.id.tv_number, item.getCode());
        holder.setText(R.id.tv_company, item.getClientName());
        holder.setImageResource(R.id.iv_state, stateResource(Integer.parseInt(item.getStatus())));

    }

    private int stateResource(int state) {
        int id = 0;
        switch (state) {
            case 1:
                id = R.mipmap.job_state1;
                break;
            case 2:
                id = R.mipmap.job_state2;
                break;
            case 3:
                id = R.mipmap.job_state3;
                break;
            case 4:
                id = R.mipmap.job_state4;
                break;
            default:
                id = R.mipmap.job_state2;
                break;
        }
        return id;
    }
}
