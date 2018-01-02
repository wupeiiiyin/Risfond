package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.TimePicker;
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

    private WheelMain wheelMainDate;
    private TransactiondatabaseSQL ttdbsqlite;
    private Cursor c;
    private TextView tv_center;
    private MediaPlayer mediaPlayer;
    private String time, date;
    private GoogleApiClient client;
    private String beginTime;
    private String datatime;
    private int year,month,day,hours,minute;
    private SharedPreferences remind;
    private int mHour=0;
    private int mMinute=0;
    private int mDay=0;

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
    public void echodata(){
        String time_tp = remind.getString("time_tp", "");
        if (time_tp.length()>0){
            tvTq5.setText(time_tp);
        }else{
            tvTq5.setText("不提醒");
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        echodata();

    }
    private String getDisPlayNumber(int num) {
        return num < 10 ? "0" + num : "" + num;
    }

    @OnClick({R.id.ll_addthetransaction_reminding, R.id.ll_addthetransaction_time, R.id.tv_addthetransaction_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //日期
            case R.id.ll_addthetransaction_reminding:
                //雷达图,统计图
                //startActivity(ContionActivity.class, false);
                //showBottoPopupWindow();
                //系统自带
                DatePickerDialog dialog1 = new DatePickerDialog(AddTheTransactionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        //日期选择器
                        date = year + "-" + getDisPlayNumber(monthOfYear + 1 ) + "-" +getDisPlayNumber(dayOfMonth) ;

                        //时间选择器
                        TimePickerDialog dialog = new TimePickerDialog(AddTheTransactionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                time = getDisPlayNumber(hourOfDay) + ":" + getDisPlayNumber(minute);
                                tvTimeDisplay.setText(date +" "+ time);
                                Log.e("CQQQQ",tvTimeDisplay.getText().toString());

//                            String mTvTimeDisplay = tvTimeDisplay.getText().toString();
//                            EventBus.getDefault().post(new MessageEvent(mTvTimeDisplay));



                            }
                        }, Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), true);
                        dialog.show();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                //通过Calendar获得当前年、月、日
                dialog1.show();
                break;



            case R.id.ll_addthetransaction_time:
                //tvTq5.setText();

                String mTvTimeDisplay = tvTimeDisplay.getText().toString();
                Intent intent = new Intent(this,RemindingTimeActivity.class);
                intent.putExtra("mTvTimeDisplay",mTvTimeDisplay);
                startActivityForResult(intent,1010);
                break;


            //添加
            case R.id.tv_addthetransaction_commit:
                String trim = editAddthetransactionContent.getText().toString();
                String date = tvTimeDisplay.getText().toString();
                String datetime = tvTimeDisplay.getText().toString();
                Intent intenttime = new Intent();
                intenttime.putExtra("datetime",datetime);
                if (mHour == 0 && mMinute == 0 && mDay == 0) {
                    Log.e("ccccc","if的 没有执行保存时间");
                }else {
                    Log.e("ccccc","zhixing保存的时间");
                    startRemind(mHour, mMinute, mDay);
                }
                if (date == null&&date.equals("")&&date.equals("请选择时间")){
                    Toast.makeText(getApplicationContext(), "日期未选择", Toast.LENGTH_SHORT).show();
                }
                if (trim == null && trim.equals("")) {
                    Toast.makeText(getApplicationContext(), "添加的内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    ContentValues cv = new ContentValues();
                    cv.put("name", trim);
                    cv.put("time", date);
                    ttdbsqlite.Addtransaction(cv);
                    startActivity(RemindingActivity.class, false);
                    break;
                }



        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1010&&resultCode==2020){
            mHour = data.getIntExtra("mHour", 0);
            mMinute = data.getIntExtra("mMinute", 0);
            mDay = data.getIntExtra("mDay", 0);
            Log.e("ccccc","调回来的"+mHour+"小时"+mMinute+"分"+mDay+"天");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        final ScreenInfo screenInfoDate = new ScreenInfo(this);
        wheelMainDate = new WheelMain(menuView, true);
        backgroundAlpha(3f);
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
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        wheelMainDate.initDateTimePicker(year, month, day, hours,minute);
        //mPopupWindow.setAnimationStyle(R.style.AnimationPreview);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        mPopupWindow.showAtLocation(tv_center, Gravity.CENTER, 0, 0);
        mPopupWindow.setOnDismissListener(new poponDismissListener());

        TextView tv_cancle = (TextView) menuView.findViewById(R.id.tv_cancle);
        TextView tv_ensure = (TextView) menuView.findViewById(R.id.tv_ensure);
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mPopupWindow.dismiss();
            }
        });
        //确定
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String mTime = year+"-"+month+"-"+day; //年-月-日
                beginTime = wheelMainDate.getTime().toString();
                //Log.e("CCCCC",beginTime);  //正确的值
                datatime=DateUtils.formateStringH(beginTime,DateUtils.yyyyMMddHHmm);
                //年月日//tvTimeDisplay.setText(mTime);
                //年月日时分
                tvTimeDisplay.setText(datatime);
                mPopupWindow.dismiss();
                //传递时间
                Intent intent = new Intent();
                intent.putExtra("beginTime",beginTime);
//                startActivity(intent);
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



    /**
     * 开启提醒
     */
    private void startRemind(int hour, int minute, int day) {
        Log.e("ccccc","开启提醒的时间是"+hour+" "+minute+" "+day);
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
}