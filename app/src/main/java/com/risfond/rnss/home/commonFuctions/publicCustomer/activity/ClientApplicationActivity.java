package com.risfond.rnss.home.commonFuctions.publicCustomer.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
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
import com.risfond.rnss.entry.ClientApplicationResponse;
import com.risfond.rnss.home.commonFuctions.publicCustomer.adapter.ClientApplationAdapter;
import com.risfond.rnss.home.commonFuctions.publicCustomer.modelImpl.ClientApplationImpl;
import com.risfond.rnss.home.commonFuctions.publicCustomer.modelInterface.IClientApplication;
import com.risfond.rnss.widget.MyScrollview;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by vicky on 2017/8/11.
 */

public class ClientApplicationActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.et_leave_reason)
    EditText metLeaveReason;
    @BindView(R.id.rl_client_reason)
    RelativeLayout mrlClientReason;
    @BindView(R.id.rl_all_client_reason)
    RelativeLayout mrlAllLeaveReason;
    @BindView(R.id.ll_back)
    LinearLayout mll_back;
    @BindView(R.id.btn_ok)
    Button mBtnOk;
    @BindView(R.id.tv_title)
    TextView mtvTitle;
    @BindView(R.id.rv_client_applation_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.my_sl)
    MyScrollview mSl;

    private Context context;
    private Map<String, String> request = new HashMap<>();

    private IClientApplication mILeave;
    private ClientApplicationResponse responseLeave;

    private String itemId;
    private String mOptionStatus;
    private String mSeleteText = "";

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @OnClick({R.id.rl_client_reason, R.id.ll_back, R.id.btn_ok})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_client_reason:
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
            default:
                break;
        }
    }

    private void leaveRequest() {
        request = new HashMap<>();

        request.put("publicClientId", itemId + "");
        request.put("staffId", String.valueOf(SPUtil.loadId(context)));

        if (!TextUtils.isEmpty(mSeleteText.toString().trim())) {
            if (mSeleteText.toString().trim().length()<10) {
                ToastUtil.showShortCent(context, "申请理由不能少于10个字符");
                return;
            } else {
                request.put("reason", mSeleteText.toString().trim());
            }
        } else {
            ToastUtil.showShortCent(context, "申请理由不能为空");
            return;
        }


        DialogUtil.getInstance().closeLoadingDialog();
        DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        mILeave.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_CLIENT_APPLICATION, this);
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
        return R.layout.activity_return_follow;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        statusBar();
        context = ClientApplicationActivity.this;
        mtvTitle.setText("申请转入");
        mILeave = new ClientApplationImpl();
        itemId = getIntent().getStringExtra("itemId");
        mOptionStatus = getIntent().getStringExtra("OptionStatus");

        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3

        initList();
        initEdie();

//        mSl.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//              /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
//              现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
//                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
////                    new Handler().postDelayed(new Runnable(){
////
////                        public void run() {
////                            mrlAllLeaveReason.setVisibility(View.VISIBLE);
////                            mSl.fullScroll(View.FOCUS_DOWN);
////                            metLeaveReason.setFocusable(true);
////                        }
////
////                    }, 300);
//
//                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
//
//                }
//
//            }
//        });
    }

    private void initList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        final String[] stringArray = context.getResources().getStringArray(R.array.clent_applation_list);
        ClientApplationAdapter clentAdapter = new ClientApplationAdapter(context, stringArray);
        clentAdapter.setOnItemClickListener(new ClientApplationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (position == 10) {
                    mrlAllLeaveReason.setVisibility(View.VISIBLE);
                    mSeleteText = metLeaveReason.getText().toString().trim();
                    metLeaveReason.setFocusable(true);
//                    showSoftKeyboard(metLeaveReason);
//                    new Handler().postDelayed(new Runnable(){
//
//                        public void run() {
//                            mSl.fullScroll(View.FOCUS_DOWN);
//                        }
//
//                    }, 100);
                } else {
                    mSeleteText = stringArray[position].toString().trim();
                    mrlAllLeaveReason.setVisibility(View.INVISIBLE);
                    hideSoftKeyboard(view);
                }
            }
        });
        mRecyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.line_dimgray)));
        mRecyclerView.setAdapter(clentAdapter);
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

    private void initEdie() {
        metLeaveReason.addTextChangedListener(contentTextWatcher);
    }

    public static void StartAction(Context context, String itemId,String mOptionStatus) {
        Intent intent = new Intent(context, ClientApplicationActivity.class);
        intent.putExtra("itemId", itemId);
        intent.putExtra("OptionStatus", mOptionStatus);
        context.startActivity(intent);
    }

    //   content 4000字以内
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
            if (charSequence.length() >= 500) {
                ToastUtil.showShortCent(context, "不能超过500字符");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            editStart = metLeaveReason.getSelectionStart();
            editEnd = metLeaveReason.getSelectionEnd();
            if (temp.length() > 500) {
                editable.delete(editEnd - (temp.length() - 500), editEnd);
                int tempSelection = editStart;
                metLeaveReason.setText(editable);
                metLeaveReason.setSelection(tempSelection);
            }
            mSeleteText = metLeaveReason.getText().toString().trim();
        }
    };

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof ClientApplicationResponse) {
                    responseLeave = (ClientApplicationResponse) obj;
                    if (responseLeave != null && responseLeave.isStatus()) {
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
                    ToastUtil.showShort(context, str);
                }
            }
        });
    }
}
