package com.risfond.rnss.home.position.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.risfond.rnss.R;
import com.risfond.rnss.entry.PositionSearch;
/**
 * @author  @zhangchuan622@gmail.com
 * @version 1.0
 * @create  2017/11/29
 * @desc
 */
public class PositionSearchV2Adapter extends BaseQuickAdapter<PositionSearch,BaseViewHolder> {
    public PositionSearchV2Adapter() {
        super(R.layout.item_position);
    }

    @Override
    protected void convert(BaseViewHolder holder, PositionSearch positionSearch) {
        holder.setText(R.id.tv_name, positionSearch.getTitle());
        holder.setText(R.id.tv_time, positionSearch.getLastCommunicationTime());
        holder.setText(R.id.tv_salary, positionSearch.getSalary());
        holder.setText(R.id.tv_location, positionSearch.getLocations());
        holder.setText(R.id.tv_period, String.valueOf(positionSearch.getRunDay()));
        holder.setText(R.id.tv_number, positionSearch.getCode());
        holder.setText(R.id.tv_company, positionSearch.getClientName());
        holder.setImageResource(R.id.iv_state, stateResource(Integer.parseInt(positionSearch.getStatus())));
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
