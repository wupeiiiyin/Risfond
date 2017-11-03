package com.risfond.rnss.group.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.contacts.activity.CompanyData;
import com.risfond.rnss.contacts.adapter.AddressListAdapter;
import com.risfond.rnss.contacts.modleImpl.CompanyImpl;
import com.risfond.rnss.contacts.modleInterface.ICompany;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.DepartList;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.group.AddGroupEventBus;
import com.risfond.rnss.group.GroupListUpdateEventBus;
import com.risfond.rnss.home.call.widget.IndexBar;
import com.risfond.rnss.home.call.widget.SuspensionDecoration;
import com.risfond.rnss.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/6/19.
 */

public class AddGroupFragment extends BaseFragment implements ResponseCallBack {
    private Context context;
    private String type;
    private String companyId;
    private String departId;
    private ICompany iCompany;
    private AddressListAdapter adapter;
    private CompanyData companyData = new CompanyData();
    private Map<String, String> request = new HashMap<>();

    @BindView(R.id.rv_group_contacts)
    RecyclerView rvGroupContacts;
    @BindView(R.id.ll_all)
    LinearLayout llAll;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;

    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    public int getLayoutResId() {
        return R.layout.fragmen_add_group;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        mManager = new LinearLayoutManager(context);
        iCompany = new CompanyImpl();

        type = getArguments().getString("type");
        companyId = getArguments().getString("companyId");
        departId = getArguments().getString("departId");

        adapter = new AddressListAdapter(context, true, companyData);
        rvGroupContacts.setLayoutManager(mManager);
        rvGroupContacts.setAdapter(adapter);

        requestService();
        onItemClick();

        if (type.equals(Constant.LIST_USER)) {
            EventBusUtil.registerEventBus(this);
        }

    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new AddressListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (type.equals(Constant.LIST_COMPANY)) {//获取部门列表
                    CompanyList company = companyData.getCompanyLists().get(position);
                    EventBus.getDefault().post(new AddGroupEventBus(Constant.LIST_DEPART, company.getName(), String.valueOf(company.getId()), "", false));
                } else if (type.equals(Constant.LIST_DEPART)) {//获取人员列表
                    DepartList depart = companyData.getDepartLists().get(position);
                    EventBus.getDefault().post(new AddGroupEventBus(Constant.LIST_USER, depart.getName(), String.valueOf(depart.getCompanyid()),
                            String.valueOf(depart.getId()), false));
                } else {//添加到群组
                    UserList userInfo = companyData.getUserLists().get(position);
                    userInfo.setSelected(!userInfo.isSelected());
                    adapter.notifyItemChanged(position);
                    EventBus.getDefault().post(new AddGroupEventBus("addTo", userInfo.getEasemobaccount(), userInfo.getCnname(), userInfo.getHeadphoto()));

                }
            }
        });
    }

    @Subscribe
    public void onEventBus(GroupListUpdateEventBus eventBus) {
        adapter.updateData(companyData);
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

    }

    private void initIndexBarData1(List<CompanyList> barDatas) {

        rvGroupContacts.addItemDecoration(mDecoration = new SuspensionDecoration(context, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        rvGroupContacts.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    private void initIndexBarData2(List<DepartList> barDatas) {

        rvGroupContacts.addItemDecoration(mDecoration = new SuspensionDecoration(context, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        rvGroupContacts.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    private void initIndexBarData3(List<UserList> barDatas) {

        rvGroupContacts.addItemDecoration(mDecoration = new SuspensionDecoration(context, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        rvGroupContacts.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    private void updateUI(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj != null && obj instanceof List<?>) {
                    initListData(obj);
                    adapter.updateData(companyData);
                    showSelectAllView();
                } else {
                    ToastUtil.showShort(context, obj.toString());
                }
            }
        });

    }

    private void showSelectAllView() {
        //显示“全选”，暂时不开发
        /*if (type.equals(Constant.LIST_USER)) {
            llAll.setVisibility(View.VISIBLE);
        } else {
            llAll.setVisibility(View.GONE);
        }*/
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
    public void onDestroy() {
        super.onDestroy();
        if (type.equals(Constant.LIST_DEPART)) {
            EventBusUtil.unRegisterEventBus(this);
        }
    }

    @Override
    protected void lazyLoad() {

    }
}
