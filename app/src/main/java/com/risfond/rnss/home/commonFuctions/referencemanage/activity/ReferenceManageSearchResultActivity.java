package com.risfond.rnss.home.commonFuctions.referencemanage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.entry.ReferenceItemInfo;
import com.risfond.rnss.entry.ReferenceManagePageResponse;
import com.risfond.rnss.entry.eventBusVo.ProcessEventBus;
import com.risfond.rnss.home.commonFuctions.referencemanage.adapter.ReferenceManagePageAdapter;
import com.risfond.rnss.home.commonFuctions.referencemanage.modelImpl.ReferenceManageImpl;
import com.risfond.rnss.home.commonFuctions.referencemanage.modelInterface.IReferenceManage;
import com.risfond.rnss.home.position.activity.RecommendInfoActivity;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 推荐管理 搜索页面
 */
public class ReferenceManageSearchResultActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.et_resume_search)
    EditText etResumeSearch;
    @BindView(R.id.rv_reference_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_reference_total)
    TextView tvResumeTotal;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_reference_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.ll_resume)
    LinearLayout llResume;
    @BindView(R.id.activity_resume_search_result)
    LinearLayout activityResumeSearchResult;

    private Context context;
    private ReferenceManagePageAdapter adapter;
    private ResumeSearchHistoryAdapter historyAdapter;
    private Map<String, String> request = new HashMap<>();
    private IReferenceManage iPositionSearch;
    private int pageindex = 1;
    private ReferenceManagePageResponse response;
    private List<ReferenceItemInfo> positionSearches = new ArrayList<>();
    private List<ReferenceItemInfo> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private List<String> histories;
    private List<String> historiesAESC;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_reference_search_result;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ReferenceManageSearchResultActivity.this;
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();
        iPositionSearch = new ReferenceManageImpl();

        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));

        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        adapter = new ReferenceManagePageAdapter(context, positionSearches);
        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);

        rvResumeList.setAdapter(adapter);

        checkSearchEditText();
        etResumeSearch.setFocusable(false);
        etResumeSearch.setHint("推荐管理（请输入关键字）");
        initHistoryData();
        itemClick();
        scroll();

        EventBusUtil.registerEventBus(this);
    }

    /**
     * 请求简历列表
     *
     * @param content
     */
    private void positionRequest(String content) {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keyword", content + "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        request.put("status", String.valueOf(0));
        iPositionSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_RECOMMEND_MANAGE, this);
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
                return false;
            }
        });

        etResumeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击“搜索”软键盘
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    pageindex = 1;
                    positionSearches.clear();
                    positionRequest(etResumeSearch.getText().toString().trim());
                    saveHistory(etResumeSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    @Subscribe
    public void onEventBus(ProcessEventBus eventBus) {
        if (EventBusUtil.isRegisterEventBus(this)){
            if (eventBus.getType().equals("liuCheng")){
                isLoadMore = true;//不显示加载框
                pageindex = 1;
                positionRequest(etResumeSearch.getText().toString().trim());
            }
        }
    }

    private void initResumeData() {
        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }

        if (llResume != null) {
            llResume.setVisibility(View.VISIBLE);
        }
        if (adapter != null) {
            adapter.updateData(positionSearches);
        }
        hideResume();
    }

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        llResume.setVisibility(View.GONE);
        historiesAESC.clear();
        histories = SPUtil.loadReferenceHistoryArray(context, "public_reference_history_");
        for (int i = histories.size() - 1; i >= 0; i--) {
            historiesAESC.add(histories.get(i));
        }
        rvResumeSearchHistory.setAdapter(historyAdapter);
        historyAdapter.updateHistory(historiesAESC);
        hideHistory();
    }

    private void itemClick() {
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
                    SPUtil.saveReferenceHistoryArray(context, histories, "public_reference_history_");
                    hideHistory();
                } else {
                    if (NetUtil.isConnected(context)) {
                        pageindex = 1;
                        positionSearches.clear();
                        ImeUtil.hideSoftKeyboard(etResumeSearch);
                        positionRequest(historiesAESC.get(position));
                        etResumeSearch.setText(historiesAESC.get(position));
                        saveHistory(historiesAESC.get(position));
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
                SPUtil.saveReferenceHistoryArray(context, histories, "public_reference_history_");
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
            }
        });

        //客户列表点击，查看客户详情
        adapter.setOnItemClickListener(new ReferenceManagePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ReferenceItemInfo itemInfo = positionSearches.get(position);
                RecommendInfoActivity.startAction(context, String.valueOf(itemInfo.getRecomid()), itemInfo.getStatus());
            }
        });
    }

    private void scroll() {
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
                            positionRequest(etResumeSearch.getText().toString().trim());
                        }
                    }
                }

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
        SPUtil.saveReferenceHistoryArray(context, histories, "public_reference_history_");
    }

    /**
     * 搜素记录只保存15条
     */
    private void saveHistory(String text) {
        updateHistory(text);
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

    private void hideResume() {
        if (positionSearches.size() > 0) {
            if (rvResumeList != null) {
                rvResumeList.setVisibility(View.VISIBLE);
            }
            if (llEmptySearch != null) {
                llEmptySearch.setVisibility(View.GONE);
            }
        } else {
            if (rvResumeList != null) {
                rvResumeList.setVisibility(View.GONE);
            }
            if (llEmptySearch != null) {
                llEmptySearch.setVisibility(View.VISIBLE);
            }
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ReferenceManageSearchResultActivity.class);
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
                if (obj instanceof ReferenceManagePageResponse) {
                    response = (ReferenceManagePageResponse) obj;
                    if (tvResumeTotal != null) {
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    if (response.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
                            positionSearches.removeAll(temp);
                            temp.clear();
                        }
                        positionSearches.addAll(response.getData());
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            positionSearches.removeAll(temp);
                            temp.clear();
                        }
                        temp = response.getData();
                        positionSearches.addAll(temp);
                    }
                    initResumeData();
                    if (adapter != null) {
                        adapter.updateData(positionSearches);
                    }
                }
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
                    initResumeData();
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
                    initResumeData();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }
}
