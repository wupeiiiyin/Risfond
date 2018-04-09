package com.risfond.rnss.mine.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.ui.ImageViewActivity;
import com.hyphenate.easeui.utils.GlideUtil;
import com.hyphenate.util.EMLog;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.AppManager;
import com.risfond.rnss.common.utils.CallUtil;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.PropertiesUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.entry.UserInfo;
import com.risfond.rnss.home.Bizreader.fragment.Card_Frag_Activity;
import com.risfond.rnss.home.Bizreader_Activity.CardcaseActivity;
import com.risfond.rnss.home.Bizreader_Activity.Main2Activity;
import com.risfond.rnss.home.commonFuctions.dynamics.activity.DynamicsActivity;
import com.risfond.rnss.login.activity.LoginActivity;
import com.risfond.rnss.message.activity.MainActivity;
import com.risfond.rnss.mine.activity.AboutActivity;
import com.risfond.rnss.mine.activity.EvaluateViewActivity;
import com.risfond.rnss.mine.modleImpl.UserInfoImpl;
import com.risfond.rnss.mine.modleInterface.IUserInfo;
import com.risfond.rnss.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static android.os.Looper.getMainLooper;

/**
 * 我的主页面
 * Created by Abbott on 2017/5/9.
 */

public class MineFragment extends BaseFragment implements ResponseCallBack {

    @BindView(R.id.iv_person_photo)
    CircleImageView ivPersonPhoto;
    @BindView(R.id.tv_my_name_zh)
    TextView tvMyNameZh;
    @BindView(R.id.tv_my_name_cn)
    TextView tvMyNameCn;
    @BindView(R.id.tv_job)
    TextView tvJob;
    @BindView(R.id.tv_job_level)
    TextView tvJobLevel;
    @BindView(R.id.tv_job_number)
    TextView tvJobNumber;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.tv_team_leader)
    TextView tvTeamLeader;
    @BindView(R.id.tv_landline)
    TextView tvLandline;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_my_dynamics)
    TextView tvMyDynamics;
    @BindView(R.id.tv_about)
    TextView tvAbout;
    @BindView(R.id.tv_evaluate)
    TextView tvEvaluate;
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.sw_close)
    SwitchCompat swClose;
    @BindView(R.id.tv_Card)
    TextView tvCard;
    Unbinder unbinder;

    private Context context;
    private IUserInfo iUserInfo;
    private Map<String, String> request = new HashMap<>();
    private UserInfo userInfo;
    private ArrayList<String> bigPhoto = new ArrayList<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        userInfo = getArguments().getParcelable("UserInfo");
        swClose.setChecked(SPUtil.loadCloseMsg(context));
        swClose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtil.saveCloseMsg(context, isChecked);
            }
        });
//        LinearLayout ll_home = (LinearLayout) new MainActivity().findViewById(R.id.ll_home_heade_scroll);
//        ll_home.setVisibility(View.GONE);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    private void initData() {
        if (userInfo == null) {
            iUserInfo = new UserInfoImpl();
            requestService();
        } else {
            initUserInfo(userInfo);
        }

    }

    private void requestService() {
        request.put("id", String.valueOf(SPUtil.loadId(context)));
        iUserInfo.userInfoRequest(request, SPUtil.loadToken(context), URLConstant.URL_USER_INFO, this);
    }

    private void showBigPhoto() {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putStringArrayListExtra("images", bigPhoto);
        intent.putExtra("clickedIndex", 0);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_person_photo, R.id.tv_landline, R.id.tv_phone,
            R.id.tv_my_dynamics, R.id.tv_about, R.id.tv_evaluate, R.id.tv_exit,R.id.tv_Card})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_photo://点击头像查看大图
                //showBigPhoto();
                break;
            case R.id.tv_landline://座机
                CallUtil.call(context, tvLandline.getText().toString().trim());
                break;
            case R.id.tv_phone://手机
                CommonUtil.call(context, tvPhone.getText().toString().trim());
                break;
            case R.id.tv_my_dynamics://动态
                DynamicsActivity.StartAction(context, true);
                break;
            case R.id.tv_about://关于
                AboutActivity.startAction(context);
                break;
            case R.id.tv_evaluate://满意度
                EvaluateViewActivity.startAction(context);
                break;
            case R.id.tv_exit:
                DialogUtil.getInstance().showConfigDialog(context, "确定退出吗？", "确定", "取消", new DialogUtil.PressCallBack() {
                    @Override
                    public void onPressButton(int buttonIndex) {
                        if (buttonIndex == DialogUtil.BUTTON_OK) {
                            if (NetUtil.isConnected(context)) {
                                DialogUtil.getInstance().showLoadingDialog(context, "退出中...");
                                exitLogin();
                            } else {
                                ToastUtil.showShort(context, "请检查网络连接");
                            }
                        }
                    }
                });
                break;
            case R.id.tv_Card:
                startActivity(new Intent(getContext(), Card_Frag_Activity.class));
//                startActivity(new Intent(getContext(), Main2Activity.class));
                break;
            default:
                break;
        }
    }

    private void exitResult(final boolean flg) {
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (flg) {
                    SPUtil.clearIMLoginData(context);
                    //                    SPUtil.saveCloseMsg(context, false);
                    LoginActivity.startAction(context);
                    AppManager.getInstance().finishActivity(MainActivity.class);
                } else {
                    ToastUtil.showShort(context, "请重新退出");
                }
            }
        });
    }

    private void exitLogin() {
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                exitResult(true);
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
                EMLog.e("退出：", code + message);
                exitResult(false);
            }
        });
    }

    private void initUserInfo(UserInfo userInfo) {
        bigPhoto.add(userInfo.getHeadphoto());
        GlideUtil.downLoadHeadImage(context, userInfo.getHeadphoto(), ivPersonPhoto, new CropCircleTransformation(context));
        tvMyNameZh.setText(userInfo.getCnname());
        tvMyNameCn.setText(userInfo.getEnname());
        tvJob.setText(userInfo.getPositionname());
        tvJobLevel.setText(userInfo.getLevel() + "");
        tvCompany.setText(userInfo.getCompanyname());
        tvDepart.setText(userInfo.getDepartname());
        tvTeamLeader.setText(userInfo.getTeams());
        tvLandline.setText(userInfo.getPhone());
        tvPhone.setText(userInfo.getMobilephone());
        tvEmail.setText(userInfo.getEmail());

    }

    private void updateUI(final Object obj) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                if (obj != null) {
                    if (obj instanceof UserInfo) {
                        userInfo = (UserInfo) obj;
                        initUserInfo(userInfo);
                    } else if (obj instanceof String) {
                        String msg = obj.toString();
                        if (msg.equals(PropertiesUtil.getMessageTextByCode("201"))) {
                            DialogUtil.getInstance().showToLoginDialog(context);
                        } else {
                            ToastUtil.showShort(context, msg);
                        }
                    }
                }
            }
        });

    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
