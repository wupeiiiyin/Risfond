package com.risfond.rnss.home.commonFuctions.successCase.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.SuccessCase;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/12/4
 * @desc 成功案例适配器 V2
 */
public class SuccessCaseV2Adapter extends BaseQuickAdapter<SuccessCase, BaseViewHolder> {

    public SuccessCaseV2Adapter() {
        super(R.layout.item_success_case_list);
    }

    @Override
    protected void convert(BaseViewHolder holder, SuccessCase item) {
        holder.setText(R.id.id_successcase_item_jobname, item.getJobName());
        holder.setText(R.id.id_successcase_item_salary, String.valueOf(item.getPositionYearlySalary()));
        holder.setText(R.id.id_successcase_item_enterprise, item.getEnterpriseName());
        holder.setText(R.id.id_successcase_item_time, item.getCreatedTime());
        holder.setText(R.id.id_successcase_item_jobtitle, item.getJobTitle());
        holder.setText(R.id.id_successcase_item_location, item.getWorkingPlace());
    }
}
