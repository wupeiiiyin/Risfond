package com.risfond.rnss.chat.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.utils.GlideUtil;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.chat.modleImpl.GroupListImpl;
import com.risfond.rnss.chat.modleInterface.IGroupList;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.GroupList;
import com.risfond.rnss.search.activity.SearchActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class GroupListActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_group_county)
    ImageView ivGroupCounty;
    @BindView(R.id.tv_group_county)
    TextView tvGroupCounty;
    @BindView(R.id.iv_group_company)
    ImageView ivGroupCompany;
    @BindView(R.id.tv_group_company)
    TextView tvGroupCompany;
    @BindView(R.id.ll_group_county)
    LinearLayout llGroupCounty;
    @BindView(R.id.ll_group_company)
    LinearLayout llGroupCompany;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;

    private Context context;
    private IGroupList iGroupList;
    private Map<String, String> request = new HashMap<>();
    private GroupList groupList;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_group;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = GroupListActivity.this;
        tvTitle.setText("群组");

        iGroupList = new GroupListImpl();
        request.put("id", String.valueOf(SPUtil.loadId(context)));
        iGroupList.groupListRequest(request, SPUtil.loadToken(context), URLConstant.URL_GROUP_LIST, this);
        DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
    }

    @OnClick({R.id.ll_search, R.id.ll_group_county, R.id.ll_group_company})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_search:
                SearchActivity.startAction(context);
                break;
            case R.id.ll_group_county:
                if (groupList != null) {
                    if (!"".equals(groupList.getAllId())){
                        Chat2Activity.startAction(context, groupList.getAllId(), SPUtil.loadName(context),
                                SPUtil.loadHeadPhoto(context), "全国聊天", Constant.IMG_COUNTRY, EaseConstant.CHATTYPE_GROUP);
                    }else {
                        ToastUtil.showShort(context,"群组获取失败");
                    }

                }

                break;
            case R.id.ll_group_company:
                if (groupList != null) {
                    if (!"".equals(groupList.getOtherId())){
                        Chat2Activity.startAction(context, groupList.getOtherId(), SPUtil.loadName(context),
                                SPUtil.loadHeadPhoto(context), SPUtil.loadCompanyName(context) + "群", Constant.IMG_COUNTRY, EaseConstant.CHATTYPE_GROUP);
                    }else {
                        ToastUtil.showShort(context,"群组获取失败");
                    }

                }
                break;
            default:
                break;
        }
    }

    private void updateUI(final Object object) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (object instanceof GroupList) {
                    groupList = (GroupList) object;
                    tvGroupCounty.setText("全国聊天");
                    tvGroupCompany.setText(SPUtil.loadCompanyName(context) + "群");
                    GlideUtil.downLoadHeadImage(context, Constant.IMG_COUNTRY, ivGroupCounty,
                            new RoundedCornersTransformation(context, 0, 0));
                    GlideUtil.downLoadHeadImage(context, Constant.IMG_COMPANY, ivGroupCompany,
                            new RoundedCornersTransformation(context, 0, 0));

                } else {
                    ToastUtil.showShort(context, object.toString());
                }
            }
        });
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

    public static void startAction(Context context) {
        Intent intent = new Intent(context, GroupListActivity.class);
        context.startActivity(intent);
    }
}
