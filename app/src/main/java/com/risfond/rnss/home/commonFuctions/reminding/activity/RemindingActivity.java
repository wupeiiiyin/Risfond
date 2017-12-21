package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CaledarAdapter;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarBean;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarDateView;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarUtil;
import com.risfond.rnss.home.commonFuctions.reminding.calendar.CalendarView;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.risfond.rnss.home.commonFuctions.reminding.activity.Utils.px;

public class RemindingActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_reminding_addaffairs)
    TextView tvRemindingAddaffairs;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reminding;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("事务提醒");
        mCalendarDateView.setAdapter(new CaledarAdapter() {
            @Override
            public View getView(View convertView, ViewGroup parentView, CalendarBean bean) {
                TextView view;
                if (convertView == null) {
                    convertView = LayoutInflater.from(parentView.getContext()).inflate(R.layout.item_calendar, null);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(px(60), px(48));
                    convertView.setLayoutParams(params);
                }

                view = (TextView) convertView.findViewById(R.id.text);

                view.setText("" + bean.day);
                if (bean.mothFlag != 0) {
                    view.setTextColor(0xff999999);
                } else {
                    view.setTextColor(0xff333333);
                }

                return convertView;
            }
        });

        mCalendarDateView.setOnItemClickListener(new CalendarView.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int postion, CalendarBean bean) {
                mTitle.setText(bean.year + "/" + getDisPlayNumber(bean.moth) + "/" + getDisPlayNumber(bean.day));
            }
        });

        int[] data = CalendarUtil.getYMD(new Date());
        mTitle.setText(data[0] + "/" + data[1] + "/" + data[2]);


        mList.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(RemindingActivity.this).inflate(android.R.layout.simple_list_item_1, null);
                }

                TextView textView = (TextView) convertView;
                textView.setText("item" + position);

                return convertView;
            }
        });
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, RemindingActivity.class);
        context.startActivity(intent);
    }

    @BindView(R.id.calendarDateView)
    CalendarDateView mCalendarDateView;
    @BindView(R.id.list)
    ListView mList;
    @BindView(R.id.title)
    TextView mTitle;

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_reminding_addaffairs)
    public void onViewClicked() {
        startActivity(AddTheTransactionActivity.class,true);
    }
}

