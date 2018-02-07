package com.risfond.rnss.home.commonFuctions.myAttenDance.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.MyAttendance;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.DataUtil.AttendanceDataUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/5.
 */

public class MyAskLeaveAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MyAttendance> data = new ArrayList<>();
    private final int TITLE = 0;
    private final int CONTENT = 1;
    private final int TAIL = 2;

    private Map<String, Integer> mTextStateList = new ArrayMap<>();
    private final int STATE_COLLAPSED = 0;//文本折叠
    private final int STATE_EXPANDED = 1;//文本展开

    public MyAskLeaveAdapter(Context context, List<MyAttendance> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.my_askleave_header, parent, false);
                holder = new HeaderHolder(view);
                break;
            case CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_askleave_detail, parent, false);
                holder = new ContentHolder(view);
                break;
            case TAIL:
                view = LayoutInflater.from(context).inflate(R.layout.item_askleave_tail, parent, false);
                holder = new TailHolder(view);
                break;
        }
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (getItemViewType(position)) {
            case TITLE:
                final HeaderHolder headerHolder = (HeaderHolder) holder;
                headerHolder.ivFrontMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        headerHolder.tvCurremtMonth.setText(AttendanceDataUtils.getSomeMonthDay(((HeaderHolder) holder).tvCurremtMonth.getText().toString(), -1));
                    }
                });
                headerHolder.tvCurremtMonth.setText(((HeaderHolder) holder).tvCurremtMonth.getText());
                headerHolder.tvNextMonth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        headerHolder.tvCurremtMonth.setText(AttendanceDataUtils.getSomeMonthDay(((HeaderHolder) holder).tvCurremtMonth.getText().toString(), 1));
                    }
                });
                break;
            case CONTENT:
                final ContentHolder contentHolder = (ContentHolder) holder;
                final MyAttendance record = data.get(position);
                int state = 0;
                if (mTextStateList.get(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position) != null) {
                    state = mTextStateList.get(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position);
                }
                if (state == STATE_EXPANDED) {
                    contentHolder.llWentoutDetail.setVisibility(View.VISIBLE);
                    contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.item_clicked_gray));
                    mTextStateList.put(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position, STATE_EXPANDED);
                } else {
                    contentHolder.llWentoutDetail.setVisibility(View.GONE);
                    contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
                    mTextStateList.put(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position, STATE_COLLAPSED);
                }

                contentHolder.llWentoutList.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTextStateList.get(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position) != null && mTextStateList.get(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position) == STATE_EXPANDED) {
                            contentHolder.llWentoutDetail.setVisibility(View.GONE);
                            contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.color_white));
                            mTextStateList.put(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position, STATE_COLLAPSED);
                        } else {
                            contentHolder.llWentoutDetail.setVisibility(View.VISIBLE);
                            contentHolder.llWentoutList.setBackgroundColor(ContextCompat.getColor(context,R.color.item_clicked_gray));
                            mTextStateList.put(data.get(position).getDayFormat() + data.get(position).getTimeFormat() + position, STATE_EXPANDED);
                        }
                    }
                });

                contentHolder.tvAskleaveLeavetype.setText(record.getLeaveType());
                contentHolder.tvAskleaveDayformat.setText(record.getDayFormat());
                contentHolder.tvAskleaveTimeformat.setText(record.getTimeFormat());
                contentHolder.tvAskleaveLeavenum.setText(record.getLeaveNum());
                contentHolder.tvAskleaveLeavestatus.setText(record.getLeaveStatus());
                contentHolder.tvAskleaveReason.setText(setColorText("请假事由: ", record.getReason() + ""));

                break;
            case TAIL:
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private SpannableStringBuilder setColorText(String s, String s1) {
        String text = s + s1;
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context,R.color.text_dimgray)), 0, s.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置文字的颜色
        return style;
    }

    @Override
    public int getItemViewType(int position) {
//        if (position == 0) {
//            return TITLE;
//        } else {
        if(position == data.size()){
            return TAIL;
        }else {
            return CONTENT;
        }
//        }
    }

    @Override
    public int getItemCount() {
        if (data != null) {
//            return data.size() > 0 ? data.size() + 1 : data.size();
            return data.size() == 0 ? data.size() : data.size() + 1;
        } else {
            return 0;
        }
    }

    public void updateData(List<MyAttendance> data) {
        mTextStateList = new ArrayMap<>();
        this.data = data;
        notifyDataSetChanged();
    }

    public static class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_front_month)
        ImageView ivFrontMonth;
        @BindView(R.id.tv_curremt_month)
        TextView tvCurremtMonth;
        @BindView(R.id.tv_next_month)
        ImageView tvNextMonth;

        public HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ContentHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ll_wentout_list)
        LinearLayout llWentoutList;
        @BindView(R.id.ll_wentout_detail)
        LinearLayout llWentoutDetail;

        @BindView(R.id.tv_askleave_leavetype)
        TextView tvAskleaveLeavetype;
        @BindView(R.id.tv_askleave_dayformat)
        TextView tvAskleaveDayformat;
        @BindView(R.id.tv_askleave_timeformat)
        TextView tvAskleaveTimeformat;
        @BindView(R.id.tv_askleave_leavenum)
        TextView tvAskleaveLeavenum;
        @BindView(R.id.tv_askleave_leavestatus)
        TextView tvAskleaveLeavestatus;
        @BindView(R.id.tv_askleave_reason)
        TextView tvAskleaveReason;

        public ContentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TailHolder extends RecyclerView.ViewHolder {

        public TailHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
