package com.risfond.rnss.contacts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.ImageViewActivity;
import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.CallUtil;
import com.risfond.rnss.common.utils.CommonUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.entry.UserInfo;
import com.risfond.rnss.mine.modleImpl.UserInfoImpl;
import com.risfond.rnss.mine.modleInterface.IUserInfo;
import com.risfond.rnss.widget.CircleImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * 好友详情信息页面
 */
public class ContactsInfoActivity extends BaseActivity implements ResponseCallBack {

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
    @BindView(R.id.tv_job_state)
    TextView tvJobState;
    @BindView(R.id.tv_landline)
    TextView tvLandline;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_send_message)
    TextView tvSendMessage;
    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.textView2)
    TextView textView2;

    private String name;
    private Context context;
    private IUserInfo iUserInfo;
    private Map<String, String> request = new HashMap<>();
    private UserInfo userInfo;
    private ArrayList<String> bigPhoto = new ArrayList<>();

    @Override
    public int getContentViewResId() {
        return R.layout.activity_contacts_info;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ContactsInfoActivity.this;
        iUserInfo = new UserInfoImpl();
        name = getIntent().getStringExtra("name");
        tvTitle.setText("详细信息");
        requestService();
    }

    private void requestService() {
        request.put("id", name);
        iUserInfo.userInfoRequest(request, SPUtil.loadToken(context), URLConstant.URL_GET_BY_ACCOUNT, this);
    }

    private void showBigPhoto() {
        Intent intent = new Intent(context, ImageViewActivity.class);
        intent.putStringArrayListExtra("images", bigPhoto);
        intent.putExtra("clickedIndex", 0);
        context.startActivity(intent);
    }

    @OnClick({R.id.iv_person_photo, R.id.tv_landline, R.id.tv_phone, R.id.tv_send_message})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_person_photo://点击头像查看大图
                //showBigPhoto();
                break;
            case R.id.tv_landline:
                CallUtil.call(context, tvLandline.getText().toString().trim());
                break;
            case R.id.tv_phone:
                CommonUtil.call(context, tvPhone.getText().toString().trim());
                break;
            case R.id.tv_send_message:
                if (userInfo != null) {
                    Chat2Activity.startAction(context, name, SPUtil.loadName(context), SPUtil.loadHeadPhoto(context),
                            userInfo.getCnname(), userInfo.getHeadphoto(), EaseConstant.CHATTYPE_SINGLE);
                } else {
                    ToastUtil.showShort(context, "好友信息获取失败,请退出重新进入");
                }

                break;
            default:
                break;
        }
    }


    private void initUserInfo(UserInfo userInfo) {
        bigPhoto.add(userInfo.getHeadphoto());
        GlideUtil.downLoadHeadImage(context, userInfo.getHeadphoto(), ivPersonPhoto, new RoundedCornersTransformation(context, 0, 0));
        tvMyNameZh.setText(userInfo.getCnname());
        tvMyNameCn.setText(userInfo.getEnname());
        tvJob.setText(userInfo.getPositionname());
        tvJobLevel.setText(userInfo.getLevel() + "");
        tvCompany.setText(userInfo.getCompanyname());
        tvDepart.setText(userInfo.getDepartname());
        tvTeamLeader.setText(userInfo.getTeams());
        tvJobState.setText(userInfo.getWorkstatus());
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
                        Log.d("ContactsInfoActivity", obj.toString());
                    }
                }
            }
        });

    }

    /**
     * 启动联系人信息页面
     *
     * @param context
     */
    public static void startAction(Context context, String name) {
        Intent intent = new Intent(context, ContactsInfoActivity.class);
        intent.putExtra("name", name);
        context.startActivity(intent);
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
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_GET_BY_ACCOUNT);
    }

}
