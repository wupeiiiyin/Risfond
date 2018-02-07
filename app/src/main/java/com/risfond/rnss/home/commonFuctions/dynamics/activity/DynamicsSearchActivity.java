package com.risfond.rnss.home.commonFuctions.dynamics.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.Dynamics;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.home.commonFuctions.dynamics.adapter.DynamicsAdapter;
import com.risfond.rnss.home.commonFuctions.dynamics.modelimpl.DynamicsImpl;
import com.risfond.rnss.home.commonFuctions.dynamics.modelinterface.IDynamics;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.widget.ClearEditText;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 动态搜索页面
 */
public class DynamicsSearchActivity extends BaseActivity implements ResponseCallBack {


    @BindView(R.id.et_resume_search)
    ClearEditText etResumeSearch;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_dynamic_search)
    RecyclerView rvDynamicSearch;
    @BindView(R.id.rv_resume_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;

    private Context context;
    private DynamicsAdapter adapter;
    private boolean isManager;
    private Map<String, String> request = new HashMap<>();
    private IDynamics iDynamics;
    private int pageindex = 1;
    private DynamicsResponse response;
    private List<Dynamics> customerSearches = new ArrayList<>();
    private List<Dynamics> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private List<String> histories;
    private List<String> historiesAESC;
    private ResumeSearchHistoryAdapter historyAdapter;
    private String tag = "dynamics";

    @Override
    public int getContentViewResId() {
        return R.layout.activity_dynamics_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = DynamicsSearchActivity.this;
        isManager = getIntent().getBooleanExtra("isManager", false);
        iDynamics = new DynamicsImpl();
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();

        adapter = new DynamicsAdapter(context, customerSearches, isManager, etResumeSearch.getText().toString().trim());
        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);


        rvDynamicSearch.setLayoutManager(new LinearLayoutManager(context));
        rvDynamicSearch.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        rvDynamicSearch.setAdapter(adapter);

        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        checkSearchEditText();
        etResumeSearch.setFocusable(false);
        etResumeSearch.setHint("动态（请输入关键字）");
        initHistoryData();

        onItemClick();
        reFresh();
    }

    private void customerRequest(String content) {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keywords", content);
        request.put("Staffid", "0");
        request.put("Categroy", "1");
        request.put("UserStaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("Page", String.valueOf(pageindex));
        request.put("PageSize", "15");
        iDynamics.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_GET_PAGE_INTERACTION_MANAGE, this);
    }

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        rvDynamicSearch.setVisibility(View.GONE);
        historiesAESC.clear();
        histories = SPUtil.loadHistoryArray(context, tag);
        for (int i = histories.size() - 1; i >= 0; i--) {
            historiesAESC.add(histories.get(i));
        }
        rvResumeSearchHistory.setAdapter(historyAdapter);
        historyAdapter.updateHistory(historiesAESC);
        hideHistory();
    }

    private void hideHistory() {
        if (historiesAESC.size() > 0) {
            rvResumeSearchHistory.setVisibility(View.VISIBLE);
            llEmptyHistory.setVisibility(View.GONE);
        } else {
            rvResumeSearchHistory.setVisibility(View.GONE);
            llEmptyHistory.setVisibility(View.VISIBLE);
        }
    }

    private void onItemClick() {

        //搜索里的列表点击
        historyAdapter.setOnItemClickListener(new ResumeSearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //如果点击的是清除历史记录
                if (historyAdapter.isFooterView(position)) {
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    histories.clear();
                    historiesAESC.clear();
                    historyAdapter.updateHistory(historiesAESC);
                    SPUtil.saveHistoryArray(context, tag, histories);
                    hideHistory();
                } else {
                    if (NetUtil.isConnected(context)) {
                        pageindex = 1;
                        customerSearches.clear();
                        ImeUtil.hideSoftKeyboard(etResumeSearch);
                        customerRequest(historiesAESC.get(position));
                        etResumeSearch.setText(historiesAESC.get(position));
                        etResumeSearch.clearFocus();
                        updateHistory(historiesAESC.get(position));
                    } else {
                        ToastUtil.showImgMessage(context, "请检查网络连接");
                    }

                }
            }
        });

        historyAdapter.setOnDeleteClickListener(new ResumeSearchHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                histories.remove(historiesAESC.get(position));
                SPUtil.saveHistoryArray(context, tag, histories);
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
            }
        });

        adapter.setOnItemClickListener(new DynamicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ContactsInfoActivity.startAction(context, String.valueOf(customerSearches.get(position).getStaffId()));
            }

            @Override
            public void onUpdateClick(int position) {
                ToastUtil.showShort(context, "更新");
            }

            @Override
            public void onStartClick(int position) {
                /*String state = customerSearches.get(position).getState();
                if (state.equals("1")) {
                    state = "2";
                } else {
                    state = "1";
                }
                customerSearches.get(position).setState(state);
                adapter.notifyItemChanged(position);*/
            }

            @Override
            public void onDeleteClick(int position) {
                customerSearches.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeChanged(position, customerSearches.size());
            }
        });
    }

    private void reFresh() {
        rvDynamicSearch.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            customerRequest(etResumeSearch.getText().toString().trim());
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_search_cancel})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            ImeUtil.hideSoftKeyboard(etResumeSearch);
            finish();
        }
    }

    private void checkSearchEditText() {
        etResumeSearch.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                etResumeSearch.setFocusableInTouchMode(true);
                etResumeSearch.setFocusable(true);
                etResumeSearch.requestFocus();
                initHistoryData();
                if (customerSearches.size() > 0) {
                    rvDynamicSearch.smoothScrollToPosition(0);
                }
                return false;
            }
        });

        etResumeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击“搜索”软键盘
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    pageindex = 1;
                    customerSearches.clear();
                    customerRequest(etResumeSearch.getText().toString().trim());
                    updateHistory(etResumeSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    private void updateHistory(String text) {
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).equals(text)) {
                histories.remove(i);
                break;
            }
        }
        if (histories.size() == 15) {
            histories.remove(0);
        }
        histories.add(text);
        SPUtil.saveHistoryArray(context, tag, histories);
    }

    private void initResumeData() {
        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }
        if (customerSearches.size() > 0) {
            if (rvDynamicSearch != null) {
                rvDynamicSearch.setVisibility(View.VISIBLE);
                adapter.updateData(customerSearches, etResumeSearch.getText().toString().trim());
            }
        } else {
            ToastUtil.showShort(context, "没有符合的结果");
            if (rvDynamicSearch != null) {
                rvDynamicSearch.setVisibility(View.GONE);
            }
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, DynamicsSearchActivity.class);
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
                if (obj instanceof DynamicsResponse) {
                    response = (DynamicsResponse) obj;
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
                    if (adapter != null) {
                        adapter.updateData(customerSearches);
                    }
                }
                initResumeData();
                if (isLoadMore) {
                    isLoadingMore = false;
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
