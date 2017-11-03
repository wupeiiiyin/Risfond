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
import com.risfond.rnss.contacts.adapter.MyCustomerCompanyAdapter;
import com.risfond.rnss.contacts.adapter.MyCustomerHRAdapter;
import com.risfond.rnss.contacts.modleImpl.MyCustomerImpl;
import com.risfond.rnss.contacts.modleInterface.IMyCustomer;
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
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/6/19.
 */

public class AddCustomerHRFragment extends BaseFragment {
    private Context context;
    private ArrayList<UserList> data = new ArrayList<>();
    private MyCustomerHRAdapter adapter;

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

        data = getArguments().getParcelableArrayList("list");

        mManager = new LinearLayoutManager(context);
        adapter = new MyCustomerHRAdapter(context, data, true);
        rvGroupContacts.setLayoutManager(mManager);
        rvGroupContacts.setAdapter(adapter);
        initIndexBarData(data);

        onItemClick();
        EventBusUtil.registerEventBus(this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new MyCustomerHRAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserList userInfo = data.get(position);
                userInfo.setSelected(!userInfo.isSelected());
                adapter.notifyItemChanged(position);
                EventBus.getDefault().post(new AddGroupEventBus("addTo", userInfo.getEasemobaccount(), userInfo.getCnname(), userInfo.getHeadphoto()));
            }
        });
    }

    @Subscribe
    public void onEventBus(GroupListUpdateEventBus eventBus) {
        adapter.updateData(data);
    }

    private void initIndexBarData(ArrayList<UserList> data) {

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
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegisterEventBus(this);
    }

    @Override
    protected void lazyLoad() {

    }
}
