package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.home.commonFuctions.reminding.broadcastreceiver.AlarmReceiver;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.WheelMain;

import java.util.Calendar;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RemindingTimeActivity extends BaseActivity {
    private AlarmManager alarmManager;

    @BindView(R.id.tv_time_tq0)
    TextView tvTimeTq0;
    @BindView(R.id.tv_time_tq5)
    TextView tvTimeTq5;
    @BindView(R.id.tv_time_tq15)
    TextView tvTimeTq15;
    @BindView(R.id.tv_time_tq30)
    TextView tvTimeTq30;
    @BindView(R.id.tv_time_tq60)
    TextView tvTimeTq60;
    @BindView(R.id.tv_time_tq240)
    TextView tvTimeTq240;
    private long millionSeconds;
    //响铃
    private SoundPool soundPool;
    private int duan, yulu;


    private WheelMain wheelMainDate;
    private String beginTime;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reminding_time;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("提醒时间");

        Intent intent = getIntent();
        String year_month_day = intent.getStringExtra("year_month_day");
        tvTimeTq0.setText(year_month_day);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.tv_time_tq0,R.id.tv_time_tq5, R.id.tv_time_tq15, R.id.tv_time_tq30, R.id.tv_time_tq60, R.id.tv_time_tq240})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_tq0:
                ToastUtil.showShort(getApplication(),"不提醒");
                break;
            case R.id.tv_time_tq5:
                Intent intent = getIntent();
                String time = intent.getStringExtra("time");
                    if (time == null){
                        Toast.makeText(this, "请选择日期", Toast.LENGTH_SHORT).show();
                    }else{
                        String[] split = time.split(":");
                        int mHour = Integer.parseInt(split[0]);
                        int mMinute = Integer.parseInt(split[1]);

                        long l = millionSeconds - System.currentTimeMillis();
                        int day = (int) (l / 1000 / 60 / 60 / 24);

                        startRemind(mHour,mMinute,day);

                        Intent intent1 = new Intent();
                        String tq5 = "提前5分钟";
                        intent1.putExtra("tq5",tq5);
                        intent1.putExtra("mHour",mHour);
                        intent1.putExtra("mMinute",mMinute);
                        intent1.putExtra("day",day);
                    }
                ToastUtil.showShort(getApplication(),"提前5分钟");

                break;
            case R.id.tv_time_tq15:
                ToastUtil.showShort(getApplication(),"提前15分钟");
                break;
            case R.id.tv_time_tq30:
                ToastUtil.showShort(getApplication(),"提前30分钟");
                break;
            case R.id.tv_time_tq60:
                ToastUtil.showShort(getApplication(),"提前60分钟");
                break;
            case R.id.tv_time_tq240:
                ToastUtil.showShort(getApplication(),"提前1天");
                break;
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
}
