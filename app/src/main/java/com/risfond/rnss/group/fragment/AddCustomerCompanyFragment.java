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
import com.risfond.rnss.contacts.adapter.MyCustomerCompanyAdapter;
import com.risfond.rnss.contacts.modleImpl.CompanyImpl;
import com.risfond.rnss.contacts.modleImpl.MyCustomerImpl;
import com.risfond.rnss.contacts.modleInterface.ICompany;
import com.risfond.rnss.contacts.modleInterface.IMyCustomer;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.DepartList;
import com.risfond.rnss.entry.MyCustomerCompany;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.group.AddGroupEventBus;
import com.risfond.rnss.group.GroupListUpdateEventBus;
import com.risfond.rnss.home.call.widget.IndexBar;
import com.risfond.rnss.home.call.widget.SuspensionDecoration;
import com.risfond.rnss.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/6/19.
 */

public class AddCustomerCompanyFragment extends BaseFragment implements ResponseCallBack {
    private Context context;
    private IMyCustomer iMyCourse;
    private Map<String, String> request = new HashMap<>();
    private ArrayList<MyCustomerCompany> data = new ArrayList<>();
    private MyCustomerCompanyAdapter adapter;

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
        iMyCourse = new MyCustomerImpl();
        adapter = new MyCustomerCompanyAdapter(context, data);
        rvGroupContacts.setLayoutManager(mManager);
        rvGroupContacts.setAdapter(adapter);

        requestService();
        onItemClick();

    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new MyCustomerCompanyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new AddGroupEventBus(Constant.LIST_CUSTOMER_HR, data.get(position).getHrCompanyName(), data.get(position).getList()));
            }
        });
    }

    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        iMyCourse.request(SPUtil.loadToken(context), request, URLConstant.URL_GET_USER_HR, this);
    }

    private void updateUI(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof ArrayList) {
                    data = (ArrayList<MyCustomerCompany>) obj;
                    adapter.updateData(data);
                    initIndexBarData(data);
                } else if (obj instanceof String) {
                    ToastUtil.showShort(context, obj.toString());
                } else {
                    ToastUtil.showShort(context, "服务器异常");
                }
            }
        });
    }

    private void initIndexBarData(ArrayList<MyCustomerCompany> data) {

        rvGroupContacts.addItemDecoration(mDecoration = new SuspensionDecoration(context, data));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        rvGroupContacts.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(data)//设置数据
                .invalidate();
        mDecoration.setmDatas(data);
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
    protected void lazyLoad() {

    }
}
