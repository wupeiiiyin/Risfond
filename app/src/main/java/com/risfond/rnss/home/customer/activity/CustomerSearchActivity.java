package com.risfond.rnss.home.customer.activity;

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
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.CustomerSearch;
import com.risfond.rnss.entry.CustomerSearchResponse;
import com.risfond.rnss.home.customer.adapter.CustomerSearchAdapter;
import com.risfond.rnss.home.customer.modelImpl.CustomerSearchImpl;
import com.risfond.rnss.home.customer.modelInterface.ICustomerSearch;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 客户首页
 */
public class CustomerSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_resume_search)
    TextView tvResumeSearch;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.activity_customer)
    LinearLayout activityCustomer;

    private Context context;
    private CustomerSearchAdapter adapter;
    private Map<String, String> request = new HashMap<>();
    private ICustomerSearch iCustomerSearch;
    private int pageindex = 1;
    private CustomerSearchResponse response;
    private List<CustomerSearch> customerSearches = new ArrayList<>();
    private List<CustomerSearch> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_customer;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = CustomerSearchActivity.this;
        iCustomerSearch = new CustomerSearchImpl();
        //        activityResumeSearch.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);

        adapter = new CustomerSearchAdapter(context, customerSearches);

        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        rvResumeList.setAdapter(adapter);

        rvResumeList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int last = manager.findLastCompletelyVisibleItemPosition();
                int totalCount = manager.getItemCount();
                // last >= totalCount - x表示剩余x个item是自动加载，可自己设置
                // dy>0表示向下滑动
                if (isCanLoadMore) {
                    if (last >= totalCount - 5 && dy > 0) {
                        if (!isLoadingMore) {
                            isLoadMore = true;
                            isLoadingMore = true;
                            customerRequest();
                        }
                    }
                }
            }
        });
        onItemClick();
        customerRequest();

    }

    private void customerRequest() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        iCustomerSearch.customerSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_CUSTOMER_SEARCH, this);
    }

    private void onItemClick() {
        //简历列表点击
        adapter.setOnItemClickListener(new CustomerSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                CustomDetailActivity2.startAction(context, String.valueOf(customerSearches.get(position).getClientId()), URLConstant.URL_CUSTOMER_DETAIL);
            }
        });
    }

    @OnClick({R.id.tv_resume_search})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_resume_search) {
            CustomerSearchResultActivity.StartAction(context);
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, CustomerSearchActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof CustomerSearchResponse) {
                    response = (CustomerSearchResponse) obj;
                    if(tvResumeTotal != null){
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    if (response.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
                            customerSearches.removeAll(temp);
                            temp.clear();
                        }
                        customerSearches.addAll(response.getData());
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            customerSearches.removeAll(temp);
                            temp.clear();
                        }
                        temp = response.getData();
                        customerSearches.addAll(temp);
                    }
                    adapter.updateData(customerSearches);
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                if (customerSearches.size() > 0) {
                    if(llEmptySearch != null){
                        llEmptySearch.setVisibility(View.GONE);
                    }
                    if(rvResumeList != null){
                        rvResumeList.setVisibility(View.VISIBLE);
                    }
                } else {
                    if(llEmptySearch != null){
                        llEmptySearch.setVisibility(View.VISIBLE);
                    }
                    if(rvResumeList != null){
                        rvResumeList.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    @Override
    public void onFailed(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
            }
        });
    }

    @Override
    public void onError(String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
            }
        });
    }
}
