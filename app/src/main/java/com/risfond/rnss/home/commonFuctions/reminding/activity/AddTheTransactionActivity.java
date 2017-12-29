package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.app.ActionBar;
import android.app.DatePickerDialog;
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
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.DateUtils;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.JudgeDate;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.ScreenInfo;
import com.risfond.rnss.home.commonFuctions.reminding.wheelview.WheelMain;

import org.greenrobot.eventbus.EventBus;

import java.util.Calendar;
import java.util.Date;

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
            tvTq5.setText("不提醒");//ok了
        }//把这个封装个方法
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        echodata();//ok了

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
                    date = year + "-" + Integer.parseInt(monthOfYear + 1 + "") + "-" + dayOfMonth;

                    //时间选择器
                    TimePickerDialog dialog = new TimePickerDialog(AddTheTransactionActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            time = hourOfDay + ":" + minute;
                            tvTimeDisplay.setText(date +" "+ time);
                            Log.e("CQQQQ",tvTimeDisplay.getText().toString());

                            String mTvTimeDisplay = tvTimeDisplay.getText().toString();
                            EventBus.getDefault().post(new MessageEvent(mTvTimeDisplay));


//                            String mTvTimeDisplay = tvTimeDisplay.getText().toString();
//                            Intent intent = new Intent(AddTheTransactionActivity.this,RemindingActivity.class);
//                            intent.putExtra("mTvTimeDisplay",mTvTimeDisplay);
//                            startActivity(intent);
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
                startActivity(RemindingTimeActivity.class, false);
                break;


            //添加
            case R.id.tv_addthetransaction_commit:
                String trim = editAddthetransactionContent.getText().toString();
                String date = tvTimeDisplay.getText().toString();

                String datetime = tvTimeDisplay.getText().toString();
                Intent intenttime = new Intent();
                intenttime.putExtra("datetime",datetime);

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
                    startActivity(RemindingActivity.class, true);
                    break;
                }
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
}