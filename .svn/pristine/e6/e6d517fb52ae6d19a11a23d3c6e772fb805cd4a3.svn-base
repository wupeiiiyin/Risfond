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

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.AppManager;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.contacts.adapter.MyCustomerCompanyAdapter;
import com.risfond.rnss.contacts.modleImpl.MyCustomerImpl;
import com.risfond.rnss.contacts.modleInterface.IMyCustomer;
import com.risfond.rnss.entry.MyCustomerCompany;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.home.call.widget.IndexBar;
import com.risfond.rnss.home.call.widget.SuspensionDecoration;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MyCustomerActivity extends BaseActivity implements ResponseCallBack {

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
    private IMyCustomer iMyCourse;
    private Map<String, String> request = new HashMap<>();
    private ArrayList<MyCustomerCompany> data = new ArrayList<>();
    private MyCustomerCompanyAdapter adapter;
    private LinearLayoutManager mManager;
    private SuspensionDecoration mDecoration;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_customer;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = MyCustomerActivity.this;
        tvTitle.setText("客户");
        iMyCourse = new MyCustomerImpl();
        adapter = new MyCustomerCompanyAdapter(context, data);
        mManager = new LinearLayoutManager(context);
        rvMyCustomer.setLayoutManager(mManager);
        rvMyCustomer.setAdapter(adapter);

        requestService();
        onItemClick();
        tvName.setVisibility(View.GONE);
        tvContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void requestService() {
        DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        iMyCourse.request(SPUtil.loadToken(context), request, URLConstant.URL_GET_USER_HR, this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new MyCustomerCompanyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(context, MyCustomerHRActivity.class);
                intent.putExtra("name", data.get(position).getHrCompanyName());
                intent.putParcelableArrayListExtra("list", data.get(position).getList());
                startActivityForResult(intent, 8888);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 8888) {
            finish();
        }
    }

    private void initIndexBarData(List<MyCustomerCompany> barDatas) {

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

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
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
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_GET_USER_HR);
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, MyCustomerActivity.class);
        context.startActivity(intent);
    }

}
