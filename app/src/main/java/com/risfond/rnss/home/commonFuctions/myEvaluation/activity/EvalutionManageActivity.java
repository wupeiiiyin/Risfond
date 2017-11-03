package com.risfond.rnss.home.commonFuctions.myEvaluation.activity;

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
import com.risfond.rnss.entry.InvoiceSearch;
import com.risfond.rnss.entry.InvoiceSearchResponse;
import com.risfond.rnss.home.commonFuctions.myEvaluation.adapter.EvalutionManageAdapter;
import com.risfond.rnss.home.commonFuctions.myEvaluation.modelImpl.EvaluationManageImpl;
import com.risfond.rnss.home.commonFuctions.myEvaluation.modelInterface.IEvaluationManage;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的业绩页面
 */
public class EvalutionManageActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_search)
    LinearLayout llSearch;
    @BindView(R.id.tv_invoice_total)
    TextView tvResumeTotal;
    @BindView(R.id.tv_invoice_maney_total)
    TextView tvMoneyTotal;
    @BindView(R.id.rv_invoice_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.activity_customer)
    LinearLayout activityCustomer;
    @BindView(R.id.tv_common_type)
    TextView mtvCommonType;

    private Context context;
    private EvalutionManageAdapter adapter;
    private Map<String, String> request = new HashMap<>();
    private IEvaluationManage iCustomerSearch;
    private int pageindex = 1;
    private InvoiceSearchResponse response;
    private List<InvoiceSearch> customerSearches = new ArrayList<>();
    private List<InvoiceSearch> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_invoice_manage;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = EvalutionManageActivity.this;
        tvTitle.setText("我的业绩");
        mtvCommonType.setText(" 笔业绩， 共 ");
        llSearch.setVisibility(View.VISIBLE);
        iCustomerSearch = new EvaluationManageImpl();
        //        activityResumeSearch.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);

        adapter = new EvalutionManageAdapter(context, customerSearches);

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
        iCustomerSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_INVOICE_MANAGE, this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new EvalutionManageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                InvoiceDetailActivity.startAction(context, String.valueOf(customerSearches.get(position).getId()),URLConstant.URL_INVOIVCE_MANAGE_DETAIL);
            }
        });
    }

    @OnClick({R.id.ll_title_search})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_title_search) {
            EvalutionManageSearchActivity.StartAction(context);
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, EvalutionManageActivity.class);
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
                if (obj instanceof InvoiceSearchResponse) {
                    response = (InvoiceSearchResponse) obj;
                    if(tvResumeTotal != null){
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    if(tvMoneyTotal != null){
                        tvMoneyTotal.setText(NumberUtil.formatString(response.getAmount()));
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
                    if(adapter != null){
                        adapter.updateData(customerSearches);
                    }
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
