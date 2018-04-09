package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailsTimeActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_detailstime_timedate)
    TextView tvDetailstimeTimedate;
    @BindView(R.id.tv_detailstime_content)
    TextView tvDetailstimeContent;

    @BindView(R.id.ll_detailstime_onclick)
    RelativeLayout llDetailstimeOnclick;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title_text)
    TextView tvTitleText;
    @BindView(R.id.delete_title)
    ImageView deleteTitle;
    @BindView(R.id.ll_back_tit)
    LinearLayout llBackTit;
    private static String CALANDER_URL = "content://com.android.calendar/calendars";
    private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDARS_NAME = "test";
    private static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
    private static String CALENDARS_DISPLAY_NAME = "测试账户";
    private TransactiondatabaseSQL ttdbsqlite;
    private List<Integer> ids = new ArrayList<>();
    private Cursor c;
    private int position;
    private List<String> list_positionSearches = new ArrayList();
    private List<String> list_positionSearches_time = new ArrayList();
    private String cursorString1;
    private int id;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_details_time;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void init(Bundle savedInstanceState) {
        llBack.setVisibility(View.GONE);
        llBackTit.setVisibility(View.VISIBLE);
        tvTitleText.setText("日程");
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);
        String tv_itemcontent = intent.getStringExtra("tv_itemcontent");
        String tv_itemtime = intent.getStringExtra("tv_itemtime");
        tvDetailstimeContent.setText(tv_itemcontent);
        tvDetailstimeTimedate.setText(tv_itemtime);
        ttdbsqlite = new TransactiondatabaseSQL(this.getApplication());
        c = ttdbsqlite.checktransaction();
        c.moveToFirst();
        while (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex("_id"));
            //内容
            cursorString1 = c.getString(c.getColumnIndex("name"));
            String time = c.getString(c.getColumnIndex("time"));//时间 年月日时分
            Log.e("TAG+adap", "onMenuItemClick: "+time );
            list_positionSearches_time.add(time);
            list_positionSearches.add(cursorString1);
            ids.add(id);
        }
    }
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ll_detailstime_onclick:
////                startActivity(RemindingTimeActivity.class, false);
//                break;
//        }
//    }

    @OnClick({R.id.delete_title, R.id.ll_back_tit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.delete_title:
                if (ttdbsqlite != null) {


                    ttdbsqlite.deletetransaction(ids.get(position));
                    // map.remove(position);
                    if (list_positionSearches != null && list_positionSearches.size() != 0) {
                        deleteCalendarEvent(getApplicationContext(), list_positionSearches.get(position));
                    }
                }
                finish();
                break;
            case R.id.ll_back_tit:
                    finish();
                break;
        }
    }

    public static void deleteCalendarEvent(Context context, String title) {
        Cursor eventCursor = context.getContentResolver().query(Uri.parse(CALANDER_EVENT_URL), null, null, null, null);
        try {
            if (eventCursor == null)//查询返回空值
                return;
            if (eventCursor.getCount() > 0) {
                //遍历所有事件，找到title跟需要查询的title一样的项
                for (eventCursor.moveToFirst(); !eventCursor.isAfterLast(); eventCursor.moveToNext()) {
                    String eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"));
                    if (!TextUtils.isEmpty(title) && title.equals(eventTitle)) {
                        int id = eventCursor.getInt(eventCursor.getColumnIndex(CalendarContract.Calendars._ID));//取得id
                        Uri deleteUri = ContentUris.withAppendedId(Uri.parse(CALANDER_EVENT_URL), id);
                        int rows = context.getContentResolver().delete(deleteUri, null, null);
                        if (rows == -1) {
                            //事件删除失败
                            return;
                        }
                    }
                }
            }
        } finally {
            if (eventCursor != null) {
                eventCursor.close();
            }
        }
    }
}
