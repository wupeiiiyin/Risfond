package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.reminding.broadcastreceiver.AlarmReceiver;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.DateUtils;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.JudgeDate;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.ScreenInfo;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.WheelMain;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddTheTransactionActivity extends BaseActivity {
    @BindView(R.id.tv_time_display)
    TextView tvTimeDisplay;
    private WheelMain wheelMainDate;
    private TransactiondatabaseSQL ttdbsqlite;
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

//    @BindView(R.id.ll_addtime)
//    LinearLayout lladdtime;

    private Cursor c;
    private TextView tv_center;
    private MediaPlayer mediaPlayer;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private String time, date;
    private GoogleApiClient client;
    private String beginTime;

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
    }

    @OnClick({R.id.ll_addthetransaction_reminding, R.id.ll_addthetransaction_time, R.id.tv_addthetransaction_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //日期
            case R.id.ll_addthetransaction_reminding:
                showBottoPopupWindow();
//                DatePickerDialog dialog1 = new DatePickerDialog(AddTheTransactionActivity.this, new DatePickerDialog.OnDateSetListener() {
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                        //当前选择的日期
//                        date = year + "-" + Integer.parseInt(monthOfYear + 1 + "") + "-" + dayOfMonth;
//
//                        TimePickerDialog dialog = new TimePickerDialog(AddTheTransactionActivity.this, new TimePickerDialog.OnTimeSetListener() {
//                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                                time = hourOfDay + ":" + minute;
//                                tvTimeDisplay.setText(date +" "+ time);
//                            }
//                        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
//                        //显示
//                        dialog.show();
//                    }
//                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
//                //通过Calendar获得当前年、月、日
//                //显示
//                dialog1.show();
                break;
            case R.id.ll_addthetransaction_time:
                Intent intent = new Intent();
                intent.putExtra("time", time);
                intent.putExtra("date", date);
                intent.setClass(AddTheTransactionActivity.this, RemindingTimeActivity.class);
                AddTheTransactionActivity.this.startActivity(intent);
                break;
            //添加
            case R.id.tv_addthetransaction_commit:
                String trim = editAddthetransactionContent.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put("name", trim);
                ttdbsqlite.Addtransaction(cv);
                String arr_list = editAddthetransactionContent.getText().toString();
                if (arr_list == null || arr_list.equals("")) {
                    Toast.makeText(getApplicationContext(), "添加的内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent1 = getIntent();
                    //String tq5 = intent1.getStringExtra("tq5");
                    int mHour = intent1.getExtras().getInt("mHour");
                    int mMinute = intent1.getExtras().getInt("mMinute");
                    int day = intent1.getExtras().getInt("day");
                    startRemind(mHour,mMinute,day);

                    startActivity(RemindingActivity.class, true);
                    break;
                }
        }
    }
    /**
     * 开启提醒
     */
    private void startRemind(int hour, int minute, int day) {
        //得到日历实例，主要是为了下面的获取时间
        final Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(System.currentTimeMillis());

        //获取当前毫秒值
        final long systemTime = System.currentTimeMillis();

        //是设置日历的时间，主要是让日历的年月日和当前同步
        mCalendar.setTimeInMillis(System.currentTimeMillis());
        // 这里时区需要设置一下，不然可能个别手机会有8个小时的时间差
        mCalendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        //设置在几点提醒  设置的为13点
        mCalendar.set(Calendar.HOUR_OF_DAY, hour);
        //设置在几分提醒  设置的为25分
        mCalendar.set(Calendar.MINUTE, minute);
        //下面这两个看字面意思也知道
        mCalendar.set(Calendar.SECOND, 0);
        mCalendar.set(Calendar.MILLISECOND, 0);
        //上面设置的就是13点25分的时间点
        //获取上面设置的13点25分的毫秒值
        final long selectTime = mCalendar.getTimeInMillis();

        // 如果当前时间大于设置的时间，那么就从第二天的设定时间开始
        mCalendar.add(Calendar.DAY_OF_MONTH, day);

        //AlarmReceiver.class为广播接受者
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, 0);
        //得到AlarmManager实例
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //**********注意！！下面的两个根据实际需求任选其一即可*********
        /**
         * 单次提醒
         * mCalendar.getTimeInMillis() 上面设置的13点25分的时间点毫秒值
         */
        am.set(AlarmManager.RTC_WAKEUP, selectTime, pi);
//        /**
//         * 重复提醒
//         * 第一个参数是警报类型；下面有介绍
//         * 第二个参数网上说法不一，很多都是说的是延迟多少毫秒执行这个闹钟，但是我用的刷了MIUI的三星手机的实际效果是与单次提醒的参数一样，即设置的13点25分的时间点毫秒值
//         * 第三个参数是重复周期，也就是下次提醒的间隔 毫秒值 我这里是一天后提醒
//         */
//        am.setRepeating(AlarmManager.RTC_WAKEUP, mCalendar.getTimeInMillis(), (1000 * 60 * 60 * 24), pi);
    }
    /**
     * 关闭提醒
     */
    private void stopRemind() {
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this, 0,
                intent, 0);
        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        //取消警报
        am.cancel(pi);
        Toast.makeText(this, "关闭了提醒", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("AddTheTransaction Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
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
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    public void showBottoPopupWindow() {
        WindowManager manager = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        Display defaultDisplay = manager.getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        View menuView = LayoutInflater.from(this).inflate(R.layout.show_popup_window,null);
        final PopupWindow mPopupWindow = new PopupWindow(menuView, (int)(width*0.8),
                ActionBar.LayoutParams.WRAP_CONTENT);
        ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelMainDate = new WheelMain(menuView, true);
        wheelMainDate.screenheight = screenInfoDate.getHeight();
        String time = DateUtils.currentMonth().toString();
        Calendar calendar = Calendar.getInstance();
        if (JudgeDate.isDate(time, "yyyy-MM-DD")) {
            try {
                calendar.setTime(new Date(time));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        wheelMainDate.initDateTimePicker(year, month, day, hours,minute);
        mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.showAtLocation(tv_center, Gravity.CENTER, 0, 0);
        mPopupWindow.setOnDismissListener(new poponDismissListener());
        backgroundAlpha(0.6f);

        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
        TextView tv_pop_title = (TextView) menuView.findViewById(R.id.tv_pop_title);
        tv_pop_title.setText("选择起始时间");
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
            }
        });
        //确定
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                beginTime = wheelMainDate.getTime().toString();
                tvTimeDisplay.setText(DateUtils.formateStringH(beginTime,DateUtils.yyyyMMddHHmm));

                //传递时间
                String selectedtime = tvTimeDisplay.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("selectedtime",selectedtime);
//                intent.setClass(TimeTransactionActivity.this, AddTheTransactionActivity.class);
//                TimeTransactionActivity.this.startActivity(intent);
                mPopupWindow.dismiss();
                backgroundAlpha(1f);
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
//                    long millionSeconds = sdf.parse(wheelMainDate.getTime2()).getTime();//毫秒
//
//                    int m5=300000;
//                    long l = millionSeconds - m5;
//                    long l1 = System.currentTimeMillis();
//                    Log.i("cq","定时的毫秒:"+millionSeconds+"\t提前五分的毫秒:"+l+"\t当前的毫秒"+l1);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }
    class poponDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            backgroundAlpha(1f);
        }
    }
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}