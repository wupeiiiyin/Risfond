package com.risfond.rnss.home.commonFuctions.myAttenDance.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.statusBar.Eyes;
import com.risfond.rnss.entry.MyAttendance;
import com.risfond.rnss.entry.MyAttendanceResponse;
import com.risfond.rnss.entry.MyLeaveResponse;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.DataUtil.AttendanceDataUtils;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.SystemBarTintManager;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.leaveUtil.DataPickerDialog;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.leaveUtil.DatePickerDialog;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.leaveUtil.DateUtil;
import com.risfond.rnss.home.commonFuctions.myAttenDance.Util.leaveUtil.TimePickerDialog;
import com.risfond.rnss.home.commonFuctions.myAttenDance.adapter.LeaveMessageListAdapter;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelImpl.MyAttendanceImpl;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelImpl.MyLeaveImpl;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelInterface.ILeave;
import com.risfond.rnss.home.commonFuctions.myAttenDance.modelInterface.IMyAttendance;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 请假
 * Created by vicky on 2017/8/11.
 */

public class LeaveRequstActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.rl_leave_type)
    RelativeLayout mrlLeaveType;
    @BindView(R.id.rl_leave_time)
    RelativeLayout mrlLeaveTime;
    @BindView(R.id.rl_leave_long)
    RelativeLayout rlLeaveLong;
    @BindView(R.id.tv_leave_type)
    TextView mtvLeaveType;
    @BindView(R.id.tv_leave_date)
    TextView mtvLeaveDate;
    @BindView(R.id.tv_leave_long)
    TextView mtvLeaveLong;
    @BindView(R.id.et_leave_reason)
    EditText metLeaveReason;
    @BindView(R.id.rl_leave_reason)
    RelativeLayout mrlLeaveReason;
    @BindView(R.id.gv_leave_message)
    RecyclerView mgvLeaveMessage;
    @BindView(R.id.ll_back)
    LinearLayout mll_back;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_title)
    TextView mtvTitle;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.tv_quest)
    TextView mtvQuest;

    private Context context;

    private Dialog dateDialog, timeDialog, chooseDialog;
    private List<String> list = new ArrayList<>();
    private LeaveMessageListAdapter mProjectListAdapter;
    private List<String> mMessageId = new ArrayList<>();
    private Map<String, String> request = new HashMap<>();

    private IMyAttendance iCustomerSearch;
    private MyAttendanceResponse response;
    private List<MyAttendance> pictures;

    private ILeave mILeave;
    private MyLeaveResponse responseLeave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setStatusBarColor(R.color.transparent);
    }


    /**
     * 设置状态栏颜色
     * 也就是所谓沉浸式状态栏
     */
    public void setStatusBarColor(int color) {
        /**
         * Android4.4以上  但是抽屉有点冲突，目前就重写一个方法暂时解决4.4的问题
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }

    @OnClick({R.id.rl_leave_type, R.id.rl_leave_time, R.id.rl_leave_long, R.id.rl_leave_reason, R.id.ll_back, R.id.btn_ok, R.id.ll_empty_search})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_leave_type:
                hideSoftKeyboard(mrlLeaveType);
                zidingyi();
                break;
            case R.id.rl_leave_time:
                hideSoftKeyboard(mrlLeaveTime);
                date();
                break;
            case R.id.rl_leave_long:
                hideSoftKeyboard(rlLeaveLong);
                time();
                break;

            case R.id.rl_leave_reason:
                metLeaveReason.requestFocus();
                showSoftKeyboard(metLeaveReason);
                break;

            case R.id.ll_back:
                hideSoftKeyboard(mll_back);
                finish();
                break;

            case R.id.btn_ok:
                leaveRequest();
                hideSoftKeyboard(mBtnOk);
                break;

            case R.id.ll_empty_search:
                customerRequest();
                break;
            default:
                break;
        }
    }

    private void showSoftKeyboard(EditText view) {
        if (view != null && context != null) {
            ((InputMethodManager) context.getSystemService(
                    Context.INPUT_METHOD_SERVICE)).showSoftInput(view,
                    InputMethodManager.SHOW_FORCED);
        }
    }

    public void hideSoftKeyboard(View view) {
        if (view == null)
            return;
        ((InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                view.getWindowToken(), 0);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_leave_requst;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        statusBar();
        context = LeaveRequstActivity.this;
        mtvTitle.setText("请假");
        pictures = new ArrayList<MyAttendance>();
        iCustomerSearch = new MyAttendanceImpl();
        mILeave = new MyLeaveImpl();

        String[] catedata = context.getResources().getStringArray(R.array.list);
        for (int i = 0; i < catedata.length; i++) {
            list.add(catedata[i]);
        }


        initEdie();

        GridLayoutManager linearLayoutManager = new GridLayoutManager(context, 4);
        mgvLeaveMessage.setLayoutManager(linearLayoutManager);
        mProjectListAdapter = new LeaveMessageListAdapter(context, pictures);
        mProjectListAdapter.setOnItemClickListener(new LeaveMessageListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<String> data) {
                mMessageId = data;
            }
        });
        mgvLeaveMessage.setAdapter(mProjectListAdapter);

        customerRequest();
    }

    private void statusBar() {
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            titleMargin();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
//            titleMargin();
        }
        Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);

    }

//    private void titleMargin() {
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        params.setMargins(0, getStatusBarHeight(), 0, 0);
//        mrlWhiteBack.setLayoutParams(params);
//    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, sbar = 70;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            sbar = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return sbar;
    }

    private void customerRequest() {
        DialogUtil.getInstance().closeLoadingDialog();
        DialogUtil.getInstance().showLoadingDialog(context, "加载中...");

        request = new HashMap<>();
        request.put("Id", String.valueOf(SPUtil.loadId(context)));
        iCustomerSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_GETAPPROVALSAFF, this);
    }

    private void leaveRequest() {
        request = new HashMap<>();

        request.put("LoginStaffid", String.valueOf(SPUtil.loadId(context)));
        request.put("Staffid", String.valueOf(SPUtil.loadId(context)));

        if (list.indexOf(mtvLeaveType.getText().toString().trim()) != -1) {
            request.put("Category", (list.indexOf(mtvLeaveType.getText().toString().trim()) + 1) + "");
        } else {
            ToastUtil.showShortCent(context, "请选择请假类型");
            return;
        }
        request.put("StartTime", mtvLeaveDate.getText().toString().trim());

        if (mtvLeaveLong.getText().toString().contains("天")) {
            request.put("Unit", 2 + "");
        } else if (mtvLeaveLong.getText().toString().contains("小时")) {
            request.put("Unit", 1 + "");
        } else {
            ToastUtil.showShortCent(context, "请选择请假时间和时长");
            return;
        }

        request.put("Num", Pattern.compile("[^0-9]").matcher(mtvLeaveLong.getText().toString().trim()).replaceAll("").trim().toString());

        if (!TextUtils.isEmpty(metLeaveReason.getText().toString().trim())) {
            request.put("Description", metLeaveReason.getText().toString().trim());
        } else {
            ToastUtil.showShortCent(context, "请假事由不能为空");
            return;
        }

        if (pictures != null && pictures.size() != 0 && mMessageId.size() != 0) {
            String mstr = "";
            for (int i = 0; i < mMessageId.size(); i++) {
                if (i == 0) {
                    mstr = mMessageId.get(i);
                } else {
                    mstr += "," + mMessageId.get(i);
                }
            }
            request.put("MsgStaffids", mstr);
        } else {
            ToastUtil.showShortCent(context, "请选择消息通知人");
            return;
        }

        DialogUtil.getInstance().closeLoadingDialog();
        DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        mILeave.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_QUSTLEAVE, this);
    }

    private void initEdie() {
        metLeaveReason.addTextChangedListener(contentTextWatcher);
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, LeaveRequstActivity.class);
        context.startActivity(intent);
    }


    /**
     * chooseDialog
     */
    private void showChooseDialog(List<String> mlist) {
        DataPickerDialog.Builder builder = new DataPickerDialog.Builder(context);
        chooseDialog = builder.setData(mlist).setSelection(0).setTitle("取消")
                .setOnDataSelectedListener(new DataPickerDialog.OnDataSelectedListener() {
                    @Override
                    public void onDataSelected(String itemValue, int position) {
                        if (mtvLeaveType != null) {
                            mtvLeaveType.setText(itemValue + "");
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                }).create();

        chooseDialog.show();
    }

    private void showDateDialog(final List<Integer> date) {
        DatePickerDialog.Builder builder = new DatePickerDialog.Builder(context);
        builder.setOnDateSelectedListener(new DatePickerDialog.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int[] dates) {
                mtvLeaveDate.setText(dates[0] + "-" + (dates[1] > 9 ? dates[1] : ("0" + dates[1])) + "-"
                        + (dates[2] > 9 ? dates[2] : ("0" + dates[2])) + " " + (dates[3] > 9 ? dates[3] : ("0" + dates[3]))
                        + ":" + (dates[4] > 9 ? dates[4] : ("0" + dates[4])));

            }

            @Override
            public void onCancel() {

            }
        })

                .setSelectYear(date.get(0) - 1)
                .setSelectMonth(date.get(1) - 1)
                .setSelectDay(date.get(2) - 1)
                .setSelectHour(date.get(3))
                .setSelectMin(date.get(4));

//        builder.setMaxYear(DateUtil.getYear());
//        builder.setMaxMonth(DateUtil.getDateForString(DateUtil.getToday()).get(1));
//        builder.setMaxDay(DateUtil.getDateForString(DateUtil.getToday()).get(2));
        dateDialog = builder.create();
        dateDialog.show();
    }

    private void showTimePick() {

        if (timeDialog == null) {

            TimePickerDialog.Builder builder = new TimePickerDialog.Builder(context);
            timeDialog = builder.setOnTimeSelectedListener(new TimePickerDialog.OnTimeSelectedListener() {
                @Override
                public void onTimeSelected(String[] times) {

                    mtvLeaveLong.setText(times[0] + " " + times[1]);

                }
            }).create();
        }

        timeDialog.show();

    }

    public void time() {

        showTimePick();
    }

    public void date() {
        showDateDialog(DateUtil.getDateForString(AttendanceDataUtils.getCurrDate("yyy-MM-dd-HH-mm")));
    }

    public void zidingyi() {

        showChooseDialog(list);
    }


    //   content 50字以内
    private TextWatcher contentTextWatcher = new TextWatcher() {
        private CharSequence temp;
        private int editStart;
        private int editEnd;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            temp = charSequence;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 50) {
                ToastUtil.showShortCent(context, "不能超过50字符");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            editStart = metLeaveReason.getSelectionStart();
            editEnd = metLeaveReason.getSelectionEnd();
            if (temp.length() > 50) {
                editable.delete(editEnd - (temp.length() - 50), editEnd);
                int tempSelection = editStart;
                metLeaveReason.setText(editable);
                metLeaveReason.setSelection(tempSelection);
            }
        }
    };

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof MyAttendanceResponse) {
                    response = (MyAttendanceResponse) obj;

                    pictures = response.getData();
                    if (mProjectListAdapter != null) {
                        if (llEmptySearch != null) {
                            llEmptySearch.setVisibility(View.GONE);
                        }
                        mProjectListAdapter.updateData(pictures);
                    }
                } else if (obj instanceof MyLeaveResponse) {
                    responseLeave = (MyLeaveResponse) obj;
                    if (responseLeave != null && responseLeave.isStatus()) {
                        MyAttendanceActivity.isUpdate = true;
                        ToastUtil.showShort(context, responseLeave.getMessage() + "");
                        finish();
                    } else {
                        if (responseLeave != null && responseLeave.getMessage() != null) {
                            ToastUtil.showShort(context, responseLeave.getMessage() + "");
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (str != null) {
                    if(mtvQuest!=null){
                        mtvQuest.setText("请求失败，点击刷新");
                    }
                    ToastUtil.showShort(context, str);
                }
            }
        });
    }

    @Override
    public void onError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (str != null) {
                    if(mtvQuest!=null){
                        mtvQuest.setText("请求失败，点击刷新");
                    }
                    ToastUtil.showShort(context, str);
                }
            }
        });
    }
}
