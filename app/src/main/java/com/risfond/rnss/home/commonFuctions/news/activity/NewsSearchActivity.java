package com.risfond.rnss.home.commonFuctions.news.activity;

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
import com.risfond.rnss.common.utils.net.IHttpRequest;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.entry.News;
import com.risfond.rnss.entry.NewsResponse;
import com.risfond.rnss.home.commonFuctions.news.adapter.NewsAdapter;
import com.risfond.rnss.home.commonFuctions.news.modelimpl.NewsImpl;
import com.risfond.rnss.home.commonFuctions.workorder.modelimpl.WorkOrderImpl;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.widget.ClearEditText;
import com.risfond.rnss.widget.RecycleViewDivider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class NewsSearchActivity extends BaseActivity implements ResponseCallBack, PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.refresh_news)
    PullLoadMoreRecyclerView refreshNews;
    @BindView(R.id.et_resume_search)
    ClearEditText etResumeSearch;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_resume_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.ll_news)
    LinearLayout llNews;
    @BindView(R.id.tv_total_number)
    TextView tvTotalNumber;

    private Context context;
    private NewsAdapter adapter;
    private List<String> histories;
    private List<String> historiesAESC;
    private ResumeSearchHistoryAdapter historyAdapter;
    private String tag = "news";
    private List<News> newsList = new ArrayList<>();
    private List<News> data = new ArrayList<>();
    private List<News> tempData = new ArrayList<>();
    private IHttpRequest iNews;
    private int PageIndex = 1;
    private Map<String, String> request = new HashMap<>();

    @Override
    public int getContentViewResId() {
        return R.layout.activity_search_news;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = NewsSearchActivity.this;
        iNews = new NewsImpl();
        etResumeSearch.setHint("公司新闻（请输入关键字）");

        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();

        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);
        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));


        adapter = new NewsAdapter(context, newsList);
        refreshNews.setLinearLayout();
        refreshNews.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,
                2, ContextCompat.getColor(context, R.color.color_home_back)));
        refreshNews.setColorSchemeResources(R.color.color_blue);
        refreshNews.setOnPullLoadMoreListener(this);
        refreshNews.setAdapter(adapter);

        initHistoryData();
        checkSearchEditText();
        onItemClick();
    }

    /**
     * 请求新闻
     *
     * @param content
     */
    private void newsRequest(String content) {
        if (!refreshNews.isRefresh() && !refreshNews.isLoadMore()) {
            DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        }
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(PageIndex));
        request.put("PageSize", "15");
        request.put("Category", "0");
        request.put("Keyword", content);
        iNews.requestService(SPUtil.loadToken(context), request, URLConstant.URL_NEWS_LIST, this);
    }

    @OnClick({R.id.tv_search_cancel})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            ImeUtil.hideSoftKeyboard(etResumeSearch);
            finish();
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
                        PageIndex = 1;
                        newsList.clear();
                        data.clear();
                        tempData.clear();
                        ImeUtil.hideSoftKeyboard(etResumeSearch);
                        newsRequest(historiesAESC.get(position));
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

        adapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsDetailActivity.startAction(context, String.valueOf(newsList.get(position).getID()));
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
        if (data.size() > 0) {
            adapter.updateData(newsList);
            llNews.setVisibility(View.VISIBLE);
        } else {
            ToastUtil.showShort(context, "没有符合的结果");
            refreshNews.setVisibility(View.GONE);
        }
    }

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        llNews.setVisibility(View.GONE);
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

    private void checkSearchEditText() {
        etResumeSearch.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                etResumeSearch.setFocusableInTouchMode(true);
                etResumeSearch.setFocusable(true);
                etResumeSearch.requestFocus();
                initHistoryData();
                if (data.size() > 0) {
                    refreshNews.getRecyclerView().smoothScrollToPosition(0);
                }
                return false;
            }
        });

        etResumeSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击“搜索”软键盘
                    ImeUtil.hideSoftKeyboard(etResumeSearch);
                    PageIndex = 1;
                    newsList.clear();
                    data.clear();
                    tempData.clear();
                    newsRequest(etResumeSearch.getText().toString().trim());
                    updateHistory(etResumeSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, NewsSearchActivity.class);
        context.startActivity(intent);
    }

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (refreshNews.isRefresh()) {
                    refreshNews.setPullLoadMoreCompleted();
                } else if (refreshNews.isLoadMore()) {
                    refreshNews.setPullLoadMoreCompleted();
                } else {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof NewsResponse) {
                    data = ((NewsResponse) obj).getData();
                    tvTotalNumber.setText(String.valueOf(((NewsResponse) obj).getTotal()));
                    if (data.size() == 15) {
                        PageIndex++;
                        if (tempData.size() > 0) {
                            newsList.removeAll(tempData);
                            tempData.clear();
                        }
                        newsList.addAll(data);
                    } else {
                        if (tempData.size() > 0) {
                            newsList.removeAll(tempData);
                            tempData.clear();
                        }
                        tempData = data;
                        newsList.addAll(tempData);
                    }
                    initResumeData();
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

    @Override
    public void onRefresh() {
        PageIndex = 1;
        newsList.clear();
        data.clear();
        tempData.clear();
        newsRequest(etResumeSearch.getText().toString().trim());
    }

    @Override
    public void onLoadMore() {
        newsRequest(etResumeSearch.getText().toString().trim());
    }

}
