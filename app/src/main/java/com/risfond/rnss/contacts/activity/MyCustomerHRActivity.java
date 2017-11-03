package com.risfond.rnss.contacts.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.AppManager;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.contacts.adapter.MyCustomerCompanyAdapter;
import com.risfond.rnss.contacts.adapter.MyCustomerHRAdapter;
import com.risfond.rnss.contacts.modleImpl.MyCustomerImpl;
import com.risfond.rnss.contacts.modleInterface.IMyCustomer;
import com.risfond.rnss.entry.MyCustomerCompany;
import com.risfond.rnss.entry.MyCustomerHR;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.home.call.widget.IndexBar;
import com.risfond.rnss.home.call.widget.SuspensionDecoration;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class MyCustomerHRActivity extends BaseActivity {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_contacts)
    TextView tvContacts;
    @BindView(R.id.tv_kehu)
    TextView tvKehu;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rv_my_customer)
    RecyclerView rvMyCustomer;
    @BindView(R.id.indexBar)
    IndexBar indexBar;
    @BindView(R.id.tvSideBarHint)
    TextView tvSideBarHint;

    private Context context;
    private String name;
    private ArrayList<UserList> data = new ArrayList<>();
    private MyCustomerHRAdapter adapter;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_customer;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = MyCustomerHRActivity.this;
        tvTitle.setText("客户");

        name = getIntent().getStringExtra("name");
        data = getIntent().getParcelableArrayListExtra("list");
        tvKehu.setTextColor(ContextCompat.getColor(context, R.color.color_title_in));
        tvName.setText(name);

        adapter = new MyCustomerHRAdapter(context, data);
        mManager = new LinearLayoutManager(context);
        rvMyCustomer.setLayoutManager(mManager);
        rvMyCustomer.setAdapter(adapter);

        initIndexBarData(data);
        onItemClick();
    }

    @OnClick({R.id.tv_contacts, R.id.tv_kehu})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_contacts:
                setResult(8888);
                finish();
                break;
            case R.id.tv_kehu:
                finish();
                break;
        }
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new MyCustomerHRAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserList userList = data.get(position);
                Chat2Activity.startAction(context, userList.getEasemobaccount(), SPUtil.loadName(context), SPUtil.loadHeadPhoto(context),
                        userList.getCnname(), userList.getHeadphoto(), EaseConstant.CHATTYPE_SINGLE);
            }
        });
    }

    private void initIndexBarData(List<UserList> barDatas) {

        rvMyCustomer.addItemDecoration(mDecoration = new SuspensionDecoration(context, barDatas));
        //indexbar初始化
        indexBar.setmPressedShowTextView(tvSideBarHint)//设置HintTextView
                .setNeedRealIndex(false)//设置需要真实的索引
                .setmLayoutManager(mManager);//设置RecyclerView的LayoutManager
        rvMyCustomer.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        //模拟线上加载数据
        indexBar.setmSourceDatas(barDatas)//设置数据
                .invalidate();
        mDecoration.setmDatas(barDatas);
    }

    public static void startAction(Context context, String name, ArrayList<UserList> list) {
        Intent intent = new Intent(context, MyCustomerHRActivity.class);
        intent.putExtra("name", name);
        intent.putParcelableArrayListExtra("list", list);
        context.startActivity(intent);
    }

}
