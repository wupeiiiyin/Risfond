package com.risfond.rnss.home.commonFuctions.myAttenDance.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.home.commonFuctions.myAttenDance.fragment.MyAskLeaveFragment;
import com.risfond.rnss.home.commonFuctions.myAttenDance.fragment.MyAttendanceFragment;
import com.risfond.rnss.home.commonFuctions.myAttenDance.fragment.MyWentOutFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的考勤页面
 */
public class MyAttendanceActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_tv_img)
    LinearLayout mllTvImg;
    @BindView(R.id.tv_title_img)
    TextView mtvTitleImg;
    @BindView(R.id.fragment_container)
    FrameLayout mFragmentContainer;
    @BindView(R.id.Rl_float_view)
    RelativeLayout mRlFloatView;
    @BindView(R.id.iv_button_leave)
    ImageView iv_button_leave;

    private Context context;
    public static boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_attendance;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = MyAttendanceActivity.this;
        tvTitle.setVisibility(View.GONE);
        mllTvImg.setVisibility(View.VISIBLE);
        mtvTitleImg.setText("我的考勤");

        initFragment();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(isUpdate){
            isUpdate = false;
            initFragment();
        }
    }

    private void initFragment() {
        try {
            FragmentTransaction trans = getSupportFragmentManager().beginTransaction();

            if (mtvTitleImg != null && mtvTitleImg.getText().equals("我的外出")) {
                MyWentOutFragment f = new MyWentOutFragment(context);
                trans.replace(R.id.fragment_container, f);
                trans.commitAllowingStateLoss();
            } else if (mtvTitleImg != null && mtvTitleImg.getText().equals("我的请假")) {
                MyAskLeaveFragment f = new MyAskLeaveFragment(context);
                trans.replace(R.id.fragment_container, f);
                trans.commitAllowingStateLoss();
            } else {
                MyAttendanceFragment f = new MyAttendanceFragment(context);
                trans.replace(R.id.fragment_container, f);
                trans.commitAllowingStateLoss();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.ll_tv_img, R.id.iv_button_leave})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_tv_img) {
            if (popupwindow != null && popupwindow.isShowing()) {
                popupwindow.dismiss();
                return;
            } else {
                initmPopupWindowView();
                popupwindow.showAsDropDown(v, 0, 5);
            }
        }else if(v.getId() == R.id.iv_button_leave){
            LeaveRequstActivity.StartAction(context);
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, MyAttendanceActivity.class);
        context.startActivity(intent);
    }

    private PopupWindow popupwindow;

    public void initmPopupWindowView() {

        // // 获取自定义布局文件pop.xml的视图
        View customView = LayoutInflater.from(MyAttendanceActivity.this).inflate(R.layout.popview_item, null);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
//        popupwindow.setAnimationStyle(R.style.AnimationFade);
        // 自定义view添加触摸事件
//        popupwindow.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        popupwindow.setOutsideTouchable(true); // 设置是否允许在外点击使其消失，到底有用没？

//        popupwindow.getContentView().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
//        int popupWidth = popupwindow.getContentView().getMeasuredWidth();
//        int popupHeight = popupwindow.getContentView().getMeasuredHeight();
//        // 设置好参数之后再show
//        int[] location = new int[2];
//        customView.getLocationOnScreen(location);
//        popupwindow.showAtLocation(customView,  Gravity.CENTER_HORIZONTAL, (location[0]+customView.getWidth()/2)-popupWidth/2 , location[1]-popupHeight);


        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                }

                return false;
            }
        });

        /** 在这里可以实现自定义视图的功能 */
        final TextView btton2 = (TextView) customView.findViewById(R.id.button2);
        final TextView btton3 = (TextView) customView.findViewById(R.id.button3);
        if (mtvTitleImg != null && mtvTitleImg.getText().equals("我的外出")) {
            btton2.setText("我的考勤");
            btton3.setText("我的请假");
        } else if (mtvTitleImg != null && mtvTitleImg.getText().equals("我的请假")) {
            btton2.setText("我的考勤");
            btton3.setText("我的外出");
        } else {
            btton2.setText("我的外出");
            btton3.setText("我的请假");
        }
        btton2.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          if (btton2.getText() != null && mtvTitleImg != null) {
                                              mtvTitleImg.setText(btton2.getText());
                                              initFragment();
                                              if (popupwindow != null && popupwindow.isShowing()) {
                                                  popupwindow.dismiss();
                                                  popupwindow = null;
                                              }
                                          }
                                      }
                                  }

        );
        btton3.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View view) {
                                          if (btton3.getText() != null && mtvTitleImg != null) {
                                              mtvTitleImg.setText(btton3.getText());
                                              initFragment();
                                              if (popupwindow != null && popupwindow.isShowing()) {
                                                  popupwindow.dismiss();
                                                  popupwindow = null;
                                              }
                                          }
                                      }
                                  }

        );

    }
}
