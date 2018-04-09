package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.Thing;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ToastUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.addapp.pickers.common.LineConfig;
import cn.addapp.pickers.picker.DateTimePicker;

public class AddTheTransactionActivity extends BaseActivity {
    @BindView(R.id.tv_time_display)
    TextView tvTimeDisplay;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.edit_addthetransaction_content)
    EditText editAddthetransactionContent;
    @BindView(R.id.ll_addthetransaction_time)
    LinearLayout llAddthetransactionTime;
    @BindView(R.id.ll_addthetransaction_reminding)
    LinearLayout llAddthetransactionReminding;
    @BindView(R.id.tv_addthetransaction_commit)
    TextView tvAddthetransactionCommit;
    @BindView(R.id.tv_tq5)
    TextView tvTq5;

    private static String CALANDER_URL = "content://com.android.calendar/calendars";
    private static String CALANDER_EVENT_URL = "content://com.android.calendar/events";
    private static String CALANDER_REMIDER_URL = "content://com.android.calendar/reminders";

    private static String CALENDARS_NAME = "test";
    private static String CALENDARS_ACCOUNT_NAME = "test@gmail.com";
    private static String CALENDARS_ACCOUNT_TYPE = "com.android.exchange";
    private static String CALENDARS_DISPLAY_NAME = "测试账户";

    private TransactiondatabaseSQL ttdbsqlite;
    private Cursor c;
    private TextView tv_center;
    private MediaPlayer mediaPlayer;
    private String time, date;
    private SharedPreferences remind;
    private int mHour = 0;
    private int mMinute = 0;
    private int mDay = 0;
    private String date1;
    private String datetime;
    private static String trim;
    private String times;
    private String branch;
    private static String advance;
    private static Integer integer;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    public int getContentViewResId() {
        return R.layout.activity_add_the_transaction;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tv_center = (TextView) findViewById(R.id.tv_center);
        ttdbsqlite = new TransactiondatabaseSQL(this.getApplication());
        tvTitle.setText("添加事务");
        ButterKnife.bind(this);
        c = ttdbsqlite.checktransaction();
        remind = getSharedPreferences("remind", MODE_PRIVATE);
        echodata();
    }

    public void echodata() {
        String time_tp = remind.getString("time_tp", "");
        if (time_tp.length() > 0) {
            tvTq5.setText(time_tp);
        } else {
            tvTq5.setText("不提醒");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        echodata();

    }

    public static void main(String[] args) {


    }

    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @OnClick({R.id.ll_addthetransaction_reminding, R.id.ll_addthetransaction_time, R.id.tv_addthetransaction_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //日期
            case R.id.ll_addthetransaction_reminding:
                DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
                picker.setActionButtonTop(false);
                picker.setDateRangeStart(2017, 1, 1);
                picker.setDateRangeEnd(2025, 11, 11);
                Calendar c = Calendar.getInstance();//
                int YEAR = c.get(Calendar.YEAR);// 获取当前年份
                int MONTH = c.get(Calendar.MONTH) + 1;// 获取当前月份
                int DAY_OF_MONTH = c.get(Calendar.DAY_OF_MONTH);// 获取当日期
                int DAY_OF_WEEK = c.get(Calendar.DAY_OF_WEEK);// 获取当前日期的星期
                int HOUR_OF_DAY = c.get(Calendar.HOUR_OF_DAY);//时
                int MINUTE = c.get(Calendar.MINUTE);//分
                picker.setSelectedItem(YEAR, MONTH, DAY_OF_MONTH, HOUR_OF_DAY, MINUTE);
                picker.setTimeRangeStart(9, 0);
                picker.setTimeRangeEnd(20, 30);
                picker.setCanLinkage(false);
                picker.setTitleText("请选择");
//        picker.setStepMinute(5);
                picker.setWeightEnable(true);
                picker.setWheelModeEnable(true);
                LineConfig config = new LineConfig();
                config.setColor(Color.BLUE);//线颜色
                config.setAlpha(120);//线透明度
                config.setVisible(true);//线不显示 默认显示
                picker.setLineConfig(config);
                picker.setLabel(null, null, null, null, null);
                picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
                    @Override
                    public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                        tvTimeDisplay.setText(year + "-" + month + "-" + day + " " + hour + ":" + minute);
                        //    Toast.makeText(AddTheTransactionActivity.this, year + "-" + month + "-" + day + " " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
                    }
                });
                picker.show();
                break;
            case R.id.ll_addthetransaction_time:
                String mTvTimeDisplay = tvTimeDisplay.getText().toString();
                Intent intent = new Intent(this, RemindingTimeActivity.class);
                intent.putExtra("mTvTimeDisplay", mTvTimeDisplay);
                Log.e("TAGDisplay", "mTvTimeDisplay: " + mTvTimeDisplay);
                startActivityForResult(intent, 1010);
                break;

            //添加
            case R.id.tv_addthetransaction_commit:
                //编辑框内容
                trim = editAddthetransactionContent.getText().toString();
//                开始时间
                date1 = tvTimeDisplay.getText().toString();
                //提前时间
                datetime = tvTimeDisplay.getText().toString();
                Log.e("TAG", "onViewClicked+data1++++++++++: "+date1 );
                if (trim.equals(null)||trim.equals("")) {
                    ToastUtil.showShort(this, "请添加事务");
                } else if (date1.equals(null)||date1.equals("")||date1.length()==0||date1.equals("请选择时间  ")||date1.equals(0)||date1==null) {
                    Log.e("TAG", "onViewClicked+data1: "+date1 );
                    ToastUtil.showShort(this, "请选择提醒时间");
                }else {
                Intent intenttime = new Intent();
                intenttime.putExtra("datetime", datetime);
                ContentValues cv = new ContentValues();
                cv.put("name", trim);
                cv.put("time", date1);
                ttdbsqlite.Addtransaction(cv);
                startActivity(RemindingActivity.class, false);
                Log.e("TAG", "onViewClicked: " + date1);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
                String str = date1.replace("-", "");
                String replace = str.replace(":", "");
                String a = replace + "00";
                Log.e("TAG", "onViewClicked: " + str);
                Log.e("TAG", "onViewClicked: " + a);
                long millionSeconds = 0;//毫秒
                try {
                    millionSeconds = sdf.parse(a).getTime();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println(millionSeconds);
                Log.e("TAG", "onViewClicked: " + millionSeconds);
                //添加到日历
                addCalendarEvent(getApplicationContext(), trim, trim, millionSeconds);
                finish();
                }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1010 && resultCode == 2020) {
            mHour = data.getIntExtra("mHour", 0);
            mMinute = data.getIntExtra("mMinute", 0);
            mDay = data.getIntExtra("mDay", 0);
            advance = data.getStringExtra("advance");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddTheTransaction Page")
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private static int checkCalendarAccount(Context context) {
        Cursor userCursor = context.getContentResolver().query(Uri.parse(CALANDER_URL), null, null, null, null);
        try {
            if (userCursor == null)//查询返回空值
                return -1;
            int count = userCursor.getCount();
            if (count > 0) {//存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst();
                return userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID));
            } else {
                return -1;
            }
        } finally {
            if (userCursor != null) {
                userCursor.close();
            }
        }
    }

    private static long addCalendarAccount(Context context) {
        TimeZone timeZone = TimeZone.getDefault();
        ContentValues value = new ContentValues();
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME);

        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE);
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME);
        value.put(CalendarContract.Calendars.VISIBLE, 1);
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE);
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL, CalendarContract.Calendars.CAL_ACCESS_OWNER);
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.getID());
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME);
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0);

        Uri calendarUri = Uri.parse(CALANDER_URL);
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
                .build();

        Uri result = context.getContentResolver().insert(calendarUri, value);
        long id = result == null ? -1 : ContentUris.parseId(result);
        return id;
    }

    //检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再查询
    private static int checkAndAddCalendarAccount(Context context) {
        int oldId = checkCalendarAccount(context);
        if (oldId >= 0) {
            return oldId;
        } else {
            long addId = addCalendarAccount(context);
            if (addId >= 0) {
                return checkCalendarAccount(context);
            } else {
                return -1;
            }
        }
    }

    public static void addCalendarEvent(Context context, String title, String description, long beginTime) {
        // 获取日历账户的id
        int calId = checkAndAddCalendarAccount(context);
        if (calId < 0) {
            // 获取账户id失败直接返回，添加日历事件失败
            return;
        }

        ContentValues event = new ContentValues();
        event.put("title", title);
        event.put("description", description);
        // 插入账户的id
        event.put("calendar_id", calId);
        Calendar mCalendar = Calendar.getInstance();

        mCalendar.setTimeInMillis(beginTime);//设置开始时间
        long start = mCalendar.getTime().getTime();
        mCalendar.setTimeInMillis(start + 1);//设置终止时间
        long end = mCalendar.getTime().getTime();

        event.put(CalendarContract.Events.DTSTART, start);
        event.put(CalendarContract.Events.DTEND, end);
        event.put(CalendarContract.Events.HAS_ALARM, 1);//设置有闹钟提醒
        event.put(CalendarContract.Events.EVENT_TIMEZONE, "Asia/Shanghai");  //这个是时区，必须有，
        //添加事件
        Uri newEvent = context.getContentResolver().insert(Uri.parse(CALANDER_EVENT_URL), event);
        if (newEvent == null) {
            // 添加日历事件失败直接返回
            return;
        }
        //事件提醒的设定
        ContentValues values = new ContentValues();
        values.put(CalendarContract.Reminders.EVENT_ID, ContentUris.parseId(newEvent));
        // 提前10分钟有提醒
        if (advance != null) {
            Log.e("TAG+advance", "initAddcontent: " + advance);
            String substring = advance.substring(2, 4);
            Log.e("TAG+advance", "initAddcontent: " + substring);
            if (substring.equals("开始")) {
                integer = 0;
            } else if (substring.equals("5分")) {
                integer = 5;
            } else if (substring.equals("15")) {
                integer = 15;
            } else if (substring.equals("30")) {
                integer = 30;
            } else if (substring.equals("60")) {
                integer = 60;
            } else if (substring.equals("1天")) {
                integer = 1440;
            }
        } else {
            integer = 0;
        }
        // 提前10分钟有提醒
        values.put(CalendarContract.Reminders.MINUTES, integer);
        values.put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT);
        Uri uri = context.getContentResolver().insert(Uri.parse(CALANDER_REMIDER_URL), values);
        if (uri == null) {
            // 添加闹钟提醒失败直接返回
            return;
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
