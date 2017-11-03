package com.risfond.rnss.contacts.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.chat.activity.GroupListActivity;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.contacts.activity.CompanyListActivity;
import com.risfond.rnss.contacts.activity.MyCustomerActivity;
import com.risfond.rnss.group.AddGroupEventBus;
import com.risfond.rnss.search.activity.SearchActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 联系人主页面
 * Created by Abbott on 2017/5/9.
 */

public class ContactsFragment extends BaseFragment {
    private Context context;
    private String companyName;
    private String companyId;

    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_enterprise_groups)
    TextView tvEnterpriseGroups;
    @BindView(R.id.tv_my_customer)
    TextView tvMyCustomer;
    @BindView(R.id.tv_enterprise_address_list)
    TextView tvCompanyAddressList;
    @BindView(R.id.tv_headquarters_company)
    TextView tvHeadquartersCompany;

    private boolean isAddGroup;

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        companyName = SPUtil.loadCompanyName(context);
        companyId = String.valueOf(SPUtil.loadCompanyId(context));

        tvHeadquartersCompany.setText(companyName);

        isAddGroup = getArguments().getBoolean("isAddGroup");

        if (isAddGroup) {
            llSearch.setVisibility(View.GONE);
            tvEnterpriseGroups.setVisibility(View.GONE);
        }
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_contacts;
    }

    @OnClick({R.id.ll_search, R.id.tv_enterprise_address_list, R.id.tv_headquarters_company,
            R.id.tv_enterprise_groups, R.id.tv_my_customer})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_search://搜索
                SearchActivity.startAction(context);
                break;
            case R.id.tv_enterprise_groups://群
                GroupListActivity.startAction(context);
                break;
            case R.id.tv_my_customer://客户
                if (isAddGroup) {
                    EventBus.getDefault().post(new AddGroupEventBus(Constant.LIST_CUSTOMER_COMPANY, "客户", "", "", false));
                } else {
                    MyCustomerActivity.startAction(context);
                }
                break;
            case R.id.tv_enterprise_address_list://企业通讯录，获取企业列表
                if (isAddGroup) {
                    EventBus.getDefault().post(new AddGroupEventBus(Constant.LIST_COMPANY, "企业通讯录", "", "", false));
                } else {
                    CompanyListActivity.startAction(context, "联系人", "通讯录", "", false, Constant.LIST_COMPANY, "", "", "1001");
                }
                break;
            case R.id.tv_headquarters_company://所属公司，获取部门列表
                if (isAddGroup) {
                    EventBus.getDefault().post(new AddGroupEventBus(Constant.LIST_DEPART, companyName, companyId, "", true));
                } else {
                    CompanyListActivity.startAction(context, "联系人", companyName, companyName, true, Constant.LIST_DEPART, companyId, "", "1003");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void lazyLoad() {

    }

}
