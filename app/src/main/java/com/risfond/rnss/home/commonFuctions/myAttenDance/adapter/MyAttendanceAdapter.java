package com.risfond.rnss.home.commonFuctions.myAttenDance.adapter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.entry.MyAttendance;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.DataUtil.AttendanceDataUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Abbott on 2017/7/5.
 */

public class MyAttendanceAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MyAttendance> data = new ArrayList<>();
    private final int TITLE = 0;
    private final int CONTENT = 1;
    private final int TAIL = 2;

    public MyAttendanceAdapter(Context context, List<MyAttendance> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.my_attendance_header, parent, false);
                holder = new HeaderHolder(view);
                break;
            case CONTENT:
                view = LayoutInflater.from(context).inflate(R.layout.item_attendance_detail, parent, false);
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
                contentHolder.tvAttendanceWeekFormat.setText(record.getWeekFormat());
                contentHolder.tvAttendanceDateformat.setText(record.getDateFormat());
                if (record.getShangBanTime().compareTo("09:00") > 0) {
                    contentHolder.tvAttendanceShangbantime.setTextColor(ContextCompat.getColor(context,R.color.text_attend_read));
                } else {
                    contentHolder.tvAttendanceShangbantime.setTextColor(ContextCompat.getColor(context,R.color.text_attend_dimgray));
                }
                contentHolder.tvAttendanceShangbantime.setText(record.getShangBanTime());

                if (record.getXiaBanTime().contains(":") && record.getXiaBanTime().compareTo("18:00") < 0) {
                    contentHolder.tvAttendanceXiabantime.setTextColor(ContextCompat.getColor(context,R.color.text_attend_read));
                } else {
                    contentHolder.tvAttendanceXiabantime.setTextColor(ContextCompat.getColor(context,R.color.text_attend_dimgray));
                }
                contentHolder.tvAttendanceXiabantime.setText(record.getXiaBanTime());

                break;
            case TAIL:
                break;
        }
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
            return data.size() == 0 ? data.size() : data.size() + 1;
        } else {
            return 0;
        }
    }

    public void updateData(List<MyAttendance> data) {
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
        @BindView(R.id.tv_attendance_weekformat)
        TextView tvAttendanceWeekFormat;
        @BindView(R.id.tv_attendance_dateformat)
        TextView tvAttendanceDateformat;
        @BindView(R.id.tv_attendance_shangbantime)
        TextView tvAttendanceShangbantime;
        @BindView(R.id.tv_attendance_xiabantime)
        TextView tvAttendanceXiabantime;

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
