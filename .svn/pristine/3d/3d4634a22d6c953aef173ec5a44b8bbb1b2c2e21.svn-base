package com.risfond.rnss.home.position.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.eventBusVo.ProcessEventBus;
import com.risfond.rnss.home.position.modelImpl.ProcessNextImpl;
import com.risfond.rnss.home.position.modelInterface.IProcessNext;
import com.risfond.rnss.widget.CustomerRadioGroup;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 流程跟进页面
 */
public class ProcessNextActivity extends BaseActivity implements CustomerRadioGroup.OnCheckedChangeListener, ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_message)
    EditText etMessage;
    @BindView(R.id.cb_rencai)
    CheckBox cbRencai;
    @BindView(R.id.cb_jixiao)
    CheckBox cbJixiao;
    @BindView(R.id.cb_duanxin)
    CheckBox cbDuanxin;
    @BindView(R.id.ll_duanxin)
    LinearLayout llDuanxin;
    @BindView(R.id.tv_tishi)
    TextView tvTishi;
    @BindView(R.id.ll_tishi)
    LinearLayout llTishi;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.rg_process_1)
    CustomerRadioGroup rgProcess1;
    @BindView(R.id.rg_process_2)
    CustomerRadioGroup rgProcess2;
    @BindView(R.id.rg_process_3)
    CustomerRadioGroup rgProcess3;
    @BindView(R.id.rg_process_4)
    CustomerRadioGroup rgProcess4;
    @BindView(R.id.rg_process_5)
    CustomerRadioGroup rgProcess5;
    @BindView(R.id.rg_process_6)
    CustomerRadioGroup rgProcess6;
    @BindView(R.id.rg_process_7)
    CustomerRadioGroup rgProcess7;
    @BindView(R.id.rg_process_8)
    CustomerRadioGroup rgProcess8;
    @BindView(R.id.rg_process_9)
    CustomerRadioGroup rgProcess9;
    @BindView(R.id.rg_process_10)
    CustomerRadioGroup rgProcess10;
    @BindView(R.id.tv_commit)
    TextView tvCommit;
    @BindView(R.id.v_line1)
    View vLine1;
    @BindView(R.id.v_line2)
    View vLine2;

    private Context context;
    private TimePickerView pvTime;
    private String time;
    private SimpleDateFormat sdf;
    private Calendar startDate, endDate, calendar;
    private String type;
    private String Content;//短语内容
    private String IsResumeMemo;//是否添加到人才评语 true：是 false：否
    private String JobCandidateId;//ID
    private String IsApplyAssess;//是否申请绩效考核 true：是 false：否
    private String StepDate;//步骤时间
    private String StepStatus;//11种状态  添加新评语直接传0
    private String IsSend;//是否发送短信
    private Map<String, String> request = new HashMap<>();
    private IProcessNext iProcessNext;
    private Map<String, String> stepValues = new HashMap<>();

    @Override
    public int getContentViewResId() {
        return R.layout.activity_procress_next;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ProcessNextActivity.this;
        iProcessNext = new ProcessNextImpl();
        tvTitle.setText("流程跟进");

        type = getIntent().getStringExtra("type");
        JobCandidateId = getIntent().getStringExtra("JobCandidateId");

        IsResumeMemo = "1";
        IsApplyAssess = "1";
        IsSend = "0";
        IsResumeMemo = "1";
        StepStatus = "0";

        duanXinState();
        tiShiState();
        time();
        showProcessView();
        addCheckedChange();
        createStepValues();
    }

    @OnClick({R.id.tv_time, R.id.tv_commit})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_time) {
            setTime();
        } else if (v.getId() == R.id.tv_commit) {
            Content = etMessage.getText().toString().trim();
            StepDate = tvTime.getText().toString().trim();
            if (TextUtils.isEmpty(Content)) {
                ToastUtil.showShort(context, "请输入推荐评语");
            } else {
                positionRequest(Content);
            }
        }
    }

    private void positionRequest(String content) {
        DialogUtil.getInstance().showLoadingDialog(context, "提交中...");
        request.put("Content", content);
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("IsResumeMemo", IsResumeMemo);
        request.put("JobCandidateId", JobCandidateId);
        request.put("IsApplyAssess", IsApplyAssess);
        request.put("StepDate", StepDate);
        request.put("StepStatus", StepStatus);
        request.put("IsSend", IsSend);
        iProcessNext.processNextRequest(SPUtil.loadToken(context), request, URLConstant.URL_ADD_JOB_ADDLIUCHENG, this);
    }

    private void showProcessView() {
        process1View();
        process2View();
        process3View();
        process4View();
        process5View();
        process6View();
        process7View();
        process8View();
        process9View();
        process10View();
    }

    private void createStepValues() {
        stepValues.put("添加新评语", "0");
        stepValues.put("客户面试", "6");
        stepValues.put("加入项目", "1");
        stepValues.put("推给顾问", "4");
        stepValues.put("推给客户", "2");
        stepValues.put("预约面试", "10");
        stepValues.put("确认OFFER", "8");
        stepValues.put("成功入职", "9");
        stepValues.put("否决人选", "3");
        stepValues.put("人选放弃", "5");
        stepValues.put("客户否决", "7");
        stepValues.put("人选离职", "12");
    }

    private void addCheckedChange() {
        rgProcess1.setOnCheckedChangeListener(this);
        rgProcess2.setOnCheckedChangeListener(this);
        rgProcess3.setOnCheckedChangeListener(this);
        rgProcess4.setOnCheckedChangeListener(this);
        rgProcess5.setOnCheckedChangeListener(this);
        rgProcess6.setOnCheckedChangeListener(this);
        rgProcess7.setOnCheckedChangeListener(this);
        rgProcess8.setOnCheckedChangeListener(this);
        rgProcess9.setOnCheckedChangeListener(this);
        rgProcess10.setOnCheckedChangeListener(this);
    }

    @OnCheckedChanged({R.id.cb_rencai, R.id.cb_jixiao, R.id.cb_duanxin})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_rencai:
                if (isChecked) {
                    IsResumeMemo = "1";
                } else {
                    IsResumeMemo = "0";
                }
                break;
            case R.id.cb_jixiao:
                if (isChecked) {
                    IsApplyAssess = "1";
                } else {
                    IsApplyAssess = "0";
                }
                break;
            case R.id.cb_duanxin:
                if (isChecked) {
                    IsSend = "1";
                } else {
                    IsSend = "0";
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(CustomerRadioGroup group, @IdRes int checkedId) {
        String msg = ((RadioButton) findViewById(group.getCheckedRadioButtonId())).getText().toString();
        StepStatus = stepValues.get(msg);
    }

    /**
     *
     */
    private void time() {
        sdf = new SimpleDateFormat("yyyy-MM-dd ahh:mm", Locale.CHINA);
        calendar = Calendar.getInstance();
        startDate = Calendar.getInstance();
        startDate.set(calendar.get(Calendar.YEAR) - 5, 0, 1);
        endDate = Calendar.getInstance();
        endDate.set(calendar.get(Calendar.YEAR) + 5, 11, 31);

        tvTime.setText(sdf.format(new Date(System.currentTimeMillis())));
    }

    /**
     * 控制“小提示”显示状态
     */
    private void tiShiState() {
        //预约面试，客户面试，确认offer  显示小提示
        if (type.equals("10") || type.equals("6") || type.equals("8")) {
            llTishi.setVisibility(View.VISIBLE);
            vLine1.setVisibility(View.VISIBLE);
            vLine2.setVisibility(View.VISIBLE);
            if (type.equals("10")) {
                tvTishi.setText(getResources().getString(R.string.process_tip1));
            } else if (type.equals("6")) {
                tvTishi.setText(getResources().getString(R.string.process_tip1) + "\n" + getResources().getString(R.string.process_tip2));
            } else if (type.equals("8")) {
                tvTishi.setText(getResources().getString(R.string.process_tip3));
            }
        } else {
            llTishi.setVisibility(View.GONE);
            vLine1.setVisibility(View.GONE);
            vLine2.setVisibility(View.GONE);
        }
    }

    /**
     * 控制“短信通知人选”显示状态
     */
    private void duanXinState() {
        //预约面试，客户面试，确认offer  显示小提示
        if (type.equals("10") || type.equals("6")) {
            llDuanxin.setVisibility(View.VISIBLE);
        } else {
            llDuanxin.setVisibility(View.GONE);
        }
    }

    /**
     * 客户面试
     */
    private void process1View() {
        if (type.equals("6")) {
            rgProcess1.setVisibility(View.VISIBLE);
        } else {
            rgProcess1.setVisibility(View.GONE);
        }
    }

    /**
     * 加入项目
     */
    private void process2View() {
        if (type.equals("1")) {
            rgProcess2.setVisibility(View.VISIBLE);
        } else {
            rgProcess2.setVisibility(View.GONE);
        }
    }

    /**
     * 推给顾问
     */
    private void process3View() {
        if (type.equals("4")) {
            rgProcess3.setVisibility(View.VISIBLE);
        } else {
            rgProcess3.setVisibility(View.GONE);
        }
    }

    /**
     * 推给客户
     */
    private void process4View() {
        if (type.equals("2")) {
            rgProcess4.setVisibility(View.VISIBLE);
        } else {
            rgProcess4.setVisibility(View.GONE);
        }
    }

    /**
     * 预约面试
     */
    private void process5View() {
        if (type.equals("10")) {
            rgProcess5.setVisibility(View.VISIBLE);
        } else {
            rgProcess5.setVisibility(View.GONE);
        }
    }

    /**
     * 确认offer
     */
    private void process6View() {
        if (type.equals("8")) {
            rgProcess6.setVisibility(View.VISIBLE);
        } else {
            rgProcess6.setVisibility(View.GONE);
        }
    }

    /**
     * 成功入职
     */
    private void process7View() {
        if (type.equals("9")) {
            rgProcess7.setVisibility(View.VISIBLE);
        } else {
            rgProcess7.setVisibility(View.GONE);
        }
    }

    /**
     * 否决人选，人选放弃
     */
    private void process8View() {
        if (type.equals("3") || type.equals("5")) {
            rgProcess8.setVisibility(View.VISIBLE);
        } else {
            rgProcess8.setVisibility(View.GONE);
        }
    }

    /**
     * 客户否决
     */
    private void process9View() {
        if (type.equals("7")) {
            rgProcess9.setVisibility(View.VISIBLE);
        } else {
            rgProcess9.setVisibility(View.GONE);
        }
    }

    /**
     * 人选离职
     */
    private void process10View() {
        if (type.equals("12")) {
            rgProcess10.setVisibility(View.VISIBLE);
        } else {
            rgProcess10.setVisibility(View.GONE);
        }
    }

    /**
     * 设置推荐时间
     */
    private void setTime() {
        showDateDialog(strTime2calender(tvTime.getText().toString().trim()), tvTime);
    }

    /**
     * 将string时间转为calender
     *f
     * @param strTime
     * @return
     */
    private Calendar strTime2calender(String strTime) {
        Date date = null;
        try {
            date = sdf.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 显示时间滚动时间选择器
     */
    private void showDateDialog(Calendar calendar, final TextView textView) {
        boolean[] type = {true, true, true, true, true, false};
        //时间选择器
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                time = sdf.format(date);
                textView.setText(time);
            }
        })
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .isCenterLabel(true)
                .setType(type)
                .isCyclic(false)
                .build();
        pvTime.setDate(calendar);//默认显示时间
        pvTime.show();
    }

    /*public static void StartAction(Context context, String type, String JobCandidateId) {
        Intent intent = new Intent(context, ProcessNextActivity.class);
        intent.putExtra("type", type);
        intent.putExtra("JobCandidateId", JobCandidateId);
        context.startActivity(intent);
    }*/

    @Override
    public void onSuccess(Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(context, "操作成功");
                EventBus.getDefault().post(new ProcessEventBus("liuCheng"));
                if (!StepStatus.equals("0") && !StepStatus.equals(type)) {
                    EventBus.getDefault().post(new ProcessEventBus(type));
                    EventBus.getDefault().post(new ProcessEventBus(StepStatus));
                    Intent data = new Intent();
                    data.putExtra("type", StepStatus);
                    setResult(998, data);
                }
                DialogUtil.getInstance().closeLoadingDialog();

                finish();
            }
        });
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(context, str.toString());
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }

    @Override
    public void onError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtil.showShort(context, str.toString());
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }
}
