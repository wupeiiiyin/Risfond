package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.reminding.adapter.EMMessageListenerAdapter;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.WheelMain;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    private NotificationManager notificationManager;

    private WheelMain wheelMainDate;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reminding_time;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        tvTitle.setText("提醒时间");
        initSoudpool();
        initListener();

        Intent intent = getIntent();
        intent.getStringExtra("tvhousetime");
    }

    private void initSoudpool() {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        duan = soundPool.load(this, R.raw.duan, 1);
        yulu = soundPool.load(this, R.raw.yulu, 1);
    }

    private void initListener() {
        //监听新的消息的到来
        EMClient.getInstance().chatManager().addMessageListener(new EMMessageListenerAdapter() {
            @Override
            public void onMessageReceived(List<EMMessage> list) {
                super.onMessageReceived(list);

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
                    long millionSeconds = sdf.parse(wheelMainDate.getTime2()).getTime();//毫秒

                    int m5 = 300000;
                    long l = millionSeconds - m5;
                    long l1 = System.currentTimeMillis();
                    Log.i("cq", "定时的毫秒:" + millionSeconds + "\t提前五分的毫秒:" + l + "\t当前的毫秒:" + l1);

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                //通知发出声音(后台长音，前台短音)
                //发出声音5通知（如果应用时在后台运行则播放长声音，如果是在前台运行就播放短声音）
                if (isRuninBackground()) {
                    soundPool.play(yulu, 1, 1, 0, 0, 1);
                    //显示通知栏
                    showNotification(list.get(0));
                } else {
                    soundPool.play(duan, 1, 1, 0, 0, 1);
                }
                //发布事件
                EventBus.getDefault().post(list.get(0));
            }
        });
    }

    private boolean isRuninBackground() {
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = activityManager.getRunningTasks(100);
        //获取到的是最上层的任务栈
        ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(0);
        //是任务栈中最上面的Activity
        return !runningTaskInfo.topActivity.getPackageName().equals(getPackageName());

    }

    private void showNotification(EMMessage emMessage) {
        //通知
        if (notificationManager == null) {
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);


        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.tv_time_tq0,R.id.tv_time_tq5, R.id.tv_time_tq15, R.id.tv_time_tq30, R.id.tv_time_tq60, R.id.tv_time_tq240})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_time_tq0:
                break;
            case R.id.tv_time_tq5:
                //获取当前系统的时间
                Calendar calendar= Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minute=calendar.get(Calendar.MINUTE);
                //弹出时间对话框
                TimePickerDialog timePickerDialog=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        Calendar c=Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        c.set(Calendar.MINUTE,minute);
                        //时间一到，发送广播（闹钟响了）
                        Intent intent=new Intent();


//                        intent.setAction("com.pxd.alarmandnotice.RING");
                        //将来时态的跳转
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(RemindingTimeActivity.this,0x101,intent,0);
                        //设置闹钟
                        alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                        //设置周期闹钟(5秒)
                        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),5000, pendingIntent);
                    }
                },hour,minute,true);
                timePickerDialog.show();

                show();
                int m5=300000;
                long lm5 = millionSeconds - m5;
                long l1m5 = System.currentTimeMillis();
                Log.i("cq","定时的毫秒:"+millionSeconds+"\t提前五分的毫秒:"+lm5+"\t当前的毫秒"+l1m5);

                break;
            case R.id.tv_time_tq15:
//                show();
//                int m15=900000;
//                long lm15 = millionSeconds - m15;
//                long l1m15 = System.currentTimeMillis();
//                Log.i("cq","定时的毫秒:"+millionSeconds+"\t提前十五分的毫秒:"+lm15+"\t当前的毫秒"+l1m15);

                break;
            case R.id.tv_time_tq30:
                //show();
                break;
            case R.id.tv_time_tq60:
                //show();
                break;
            case R.id.tv_time_tq240:
                //show();
                break;
        }
    }

    private void show() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
            millionSeconds = sdf.parse(wheelMainDate.getTime2()).getTime();//毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
