package com.risfond.rnss.contacts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.AppManager;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.contacts.adapter.AddressListAdapter;
import com.risfond.rnss.contacts.modleImpl.CompanyImpl;
import com.risfond.rnss.contacts.modleInterface.ICompany;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.DepartList;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.home.call.widget.IndexBar;
import com.risfond.rnss.home.call.widget.SuspensionDecoration;
import com.risfond.rnss.search.activity.SearchActivity;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 通讯录列表页面
 */
public class CompanyListActivity extends BaseActivity implements ResponseCallBack {
    private final static String TAG = CompanyListActivity.class.getSimpleName();
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;
    private Context context;
    private String title;
    private String first;
    private String title2;
    private String type;
    private String companyId;
    private String departId;
    private String requestCode;
    private ICompany iCompany;
    private AddressListAdapter adapter;
    private CompanyData companyData = new CompanyData();
    private Map<String, String> request = new HashMap<>();
    private boolean hide;

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_company_list)
    TextView tvCompanyList;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    @BindView(R.id.tv_depart)
    TextView tvDepart;
    @BindView(R.id.lv_address_list)
    RecyclerView lvAddressList;

    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_company_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = CompanyListActivity.this;
        mManager = new LinearLayoutManager(this);

        first = getIntent().getStringExtra("first");
        type = getIntent().getStringExtra("type");
        title = getIntent().getStringExtra("title");
        title2 = getIntent().getStringExtra("title2");
        companyId = getIntent().getStringExtra("companyId");
        departId = getIntent().getStringExtra("departId");
        requestCode = getIntent().getStringExtra("requestCode");
        hide = getIntent().getBooleanExtra("hide", false);
        if (requestCode == null) {
            requestCode = "";
        }

        iCompany = new CompanyImpl();

        tvTitle.setText(title);
        tvContacts.setText(first);

        if (requestCode.equals("1001")) {
            tvCompany.setText(title);
        }
        if (requestCode.equals("1002")) {
            tvCompany.setText(title);
        }
        if (requestCode.equals("1003")) {
            tvCompany.setText(title2);
            tvDepart.setText(title);
        }
        AppManager.activities.add(this);

        lvAddressList.setLayoutManager(mManager);

        requestService();
        updateIndicate();
    }

    /**
     * 开始登录请求
     */
    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        if (type.equals(Constant.LIST_COMPANY)) {//请求公司列表
            iCompany.companyRequest(type, SPUtil.loadToken(context), null, URLConstant.URL_COMPANY_LIST, this);
        } else if (type.equals(Constant.LIST_DEPART)) {//请求部门列表
            request.put("id", companyId);
            iCompany.companyRequest(type, SPUtil.loadToken(context), request, URLConstant.URL_DEPART_LIST, this);
        } else {//请求员工列表
            request.put("companyid", companyId);
            request.put("departmentid", departId);
            iCompany.companyRequest(type, SPUtil.loadToken(context), request, URLConstant.URL_USER_LIST, this);
        }
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new AddressListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (type.equals(Constant.LIST_COMPANY)) {//获取部门列表
                    CompanyList company = companyData.getCompanyLists().get(position);
                    startAction(context, "联系人", company.getName(), company.getName(), hide, Constant.LIST_DEPART,
                            String.valueOf(company.getId()), "", "1002");
                } else if (type.equals(Constant.LIST_DEPART)) {//获取人员列表
                    DepartList depart = companyData.getDepartLists().get(position);
                    startAction(context, "联系人", depart.getName(), title, hide, Constant.LIST_USER,
                            String.valueOf(depart.getCompanyid()), String.valueOf(depart.getId()), "1003");
                } else {
                    UserList userInfo = companyData.getUserLists().get(position);
                    Chat2Activity.startAction(context, userInfo.getEasemobaccount(), SPUtil.loadName(context),
                            SPUtil.loadHeadPhoto(context), userInfo.getCnname(), userInfo.getHeadphoto(), EaseConstant.CHATTYPE_SINGLE);
                }
            }
        });
    }

    private void removeActivity(int count, int end) {
        for (int i = count - 1; i > end; i--) {
            AppManager.activities.get(i).finish();
            AppManager.activities.remove(i);
        }
    }

    @OnClick({R.id.ll_back, R.id.ll_search, R.id.tv_contacts, R.id.tv_company_list, R.id.tv_company, R.id.tv_depart})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                if (AppManager.activities.size() > 0) {
                    AppManager.activities.get(AppManager.activities.size() - 1).finish();
                    AppManager.activities.remove(AppManager.activities.size() - 1);
                }
                break;
            case R.id.ll_search:
                SearchActivity.startAction(context);
                break;
            case R.id.tv_contacts:
                removeActivity(AppManager.activities.size(), -1);
                break;
            case R.id.tv_company_list:
                removeActivity(AppManager.activities.size(), 0);
                break;
            case R.id.tv_company:
                if (hide) {
                    removeActivity(AppManager.activities.size(), 0);
                } else {
                    removeActivity(AppManager.activities.size(), 1);
                }
                break;
            case R.id.tv_depart:
                removeActivity(AppManager.activities.size(), 2);
                break;
        }
    }

    /**
     * 设置顶部title颜色和点击
     */
    private void updateIndicate() {
        if (type.equals("0")) {
            tvCompanyList.setClickable(false);
            if (requestCode.equals("1002")) {
                tvCompanyList.setVisibility(View.GONE);
            }
            tvCompany.setVisibility(View.GONE);
            tvDepart.setVisibility(View.GONE);
        } else if (type.equals("1")) {
            tvCompanyList.setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
            tvCompany.setClickable(false);
            if (requestCode.equals("1003")) {
                tvCompanyList.setVisibility(View.GONE);
            }
            tvDepart.setVisibility(View.GONE);
        } else {
            if (hide && requestCode.equals("1003")) {
                tvCompanyList.setVisibility(View.GONE);
            }
            tvCompanyList.setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
            tvCompany.setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
            tvDepart.setClickable(false);

        }
    }

    private void initListData(Object obj) {
        if (type.equals("0")) {
            companyData.setCompanyLists((List<CompanyList>) obj);
            initIndexBarData1(companyData.getCompanyLists());
        } else if (type.equals("1")) {
            companyData.setDepartLists((List<DepartList>) obj);
            initIndexBarData2(companyData.getDepartLists());
        } else {
            companyData.setUserLists((List<UserList>) obj);
            initIndexBarData3(companyData.getUserLists());
        }
        adapter = new AddressListAdapter(context, companyData);
        lvAddressList.setAdapter(adapter);
        onItemClick();
    }

    private void initIndexBarData1(List<CompanyList> barDatas) {

        lvAddressList.addItemDecoration(mDecoration = new SuspensionDecoration(this, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        lvAddressList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    private void initIndexBarData2(List<DepartList> barDatas) {

        lvAddressList.addItemDecoration(mDecoration = new SuspensionDecoration(this, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        lvAddressList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    private void initIndexBarData3(List<UserList> barDatas) {

        lvAddressList.addItemDecoration(mDecoration = new SuspensionDecoration(this, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        lvAddressList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj != null && obj instanceof List<?>) {
                    initListData(obj);

                } else {
                    ToastUtil.showShort(context, obj.toString());
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


    /**
     * 启动员工列表页面
     *
     * @param context
     */
    public static void startAction(Context context, String first, String title, String title2, boolean hide, String type, String companyId, String departId, String requestCode) {
        Intent intent = new Intent(context, CompanyListActivity.class);
        intent.putExtra("first", first);
        intent.putExtra("type", type);
        intent.putExtra("title", title);
        intent.putExtra("title2", title2);
        intent.putExtra("companyId", companyId);
        intent.putExtra("departId", departId);
        intent.putExtra("requestCode", requestCode);
        intent.putExtra("hide", hide);
        context.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (AppManager.activities.size() > 0) {
            AppManager.activities.get(AppManager.activities.size() - 1).finish();
            AppManager.activities.remove(AppManager.activities.size() - 1);
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_COMPANY_LIST);
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_DEPART_LIST);
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_USER_LIST);
    }

}
