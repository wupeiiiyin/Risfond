package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.DateUtils;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.WheelMain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.risfond.rnss.home.commonFuctions.reminding.wheelview.DateUtils.yyyyMMddHHmm;

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
        //        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
//        duan = soundPool.load(this, R.raw.duan, 1);
//        yulu = soundPool.load(this, R.raw.yulu, 1);
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
                finish();
                ToastUtil.showShort(getApplication(),"不提醒");
                break;
            case R.id.tv_time_tq5:
                //startActivity(RemindingTimeActivity.class, false);
                //获取当前系统的时间
                Calendar calendar= Calendar.getInstance();
                int hour=calendar.get(Calendar.HOUR_OF_DAY);
                int minutef=calendar.get(Calendar.MINUTE);
                //当前时间
                Log.e("cq","小时:"+hour+"分:"+minutef);


                Intent intent = getIntent();
                String time = intent.getStringExtra("time");
                String date = intent.getStringExtra("date");
                String yMdhm = time+date;

                //5分钟毫秒值
                int m5=300000;
                long five_minutes = millionSeconds - m5;
                long current = System.currentTimeMillis();
                long millionSeconds = 0;

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
                    millionSeconds = sdf.parse(yMdhm.trim()).getTime();
//                    Intent intent1 = new Intent();
//                    intent1.putExtra("key",yyyyMMddHHmm);
//                    sendBroadcast(intent1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                tvTimeTq0.setText(DateUtils.formateStringH(date+"."+time, yyyyMMddHHmm));
                Log.i("cqq","定时的毫秒:"+millionSeconds+"\t提前五分的毫秒:"+five_minutes+"\t当前的毫秒"+current);

                break;
            case R.id.tv_time_tq15:

                break;
            case R.id.tv_time_tq30:

                break;
            case R.id.tv_time_tq60:

                break;
            case R.id.tv_time_tq240:

                break;
        }
    }
}
