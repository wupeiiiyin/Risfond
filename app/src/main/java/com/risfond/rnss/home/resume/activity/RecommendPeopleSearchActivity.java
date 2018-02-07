package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.entry.RecommendPeople;
import com.risfond.rnss.entry.RecommendPeopleResponse;
import com.risfond.rnss.home.resume.adapter.PeopleAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.home.resume.modleImpl.RecommendPeopleImpl;
import com.risfond.rnss.home.resume.modleInterface.IRecommendPeople;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecommendPeopleSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.et_resume_search)
    EditText etResumeSearch;
    @BindView(R.id.rv_resume_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.ll_resume)
    LinearLayout llResume;
    @BindView(R.id.tv_next)
    TextView tvNext;

    private Context context;
    private PeopleAdapter adapter;
    private ResumeSearchHistoryAdapter historyAdapter;
    private Map<String, String> request = new HashMap<>();
    private IRecommendPeople iPositionSearch;
    private int pageindex = 1;
    private RecommendPeopleResponse response;
    private List<RecommendPeople> positionSearches = new ArrayList<>();
    private List<RecommendPeople> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private List<String> histories;
    private List<String> historiesAESC;
    private String jobId = "";

    @Override
    public int getContentViewResId() {
        return R.layout.activity_recommend_next;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = RecommendPeopleSearchActivity.this;
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();
        iPositionSearch = new RecommendPeopleImpl();

        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        adapter = new PeopleAdapter(context, positionSearches);
        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);

        rvResumeList.setAdapter(adapter);

        checkSearchEditText();
        etResumeSearch.setFocusable(false);
        initHistoryData();
        itemClick();
        scroll();
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

    /**
     * 请求简历列表
     *
     * @param content
     */
    private void positionRequest(String content) {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("KeyWords", content);
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(pageindex));
        iPositionSearch.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_GET_JOBS, this);
    }

    @OnClick({R.id.tv_search_cancel, R.id.tv_next})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            ImeUtil.hideSoftKeyboard(etResumeSearch);
            finish();
        } else if (v.getId() == R.id.tv_next) {
            if (TextUtils.isEmpty(jobId)) {
                ToastUtil.showShort(context, "请选择一个职位");
            } else {
                RecommendPeopleNextActivity.startAction(context, getIntent().getStringExtra("resumeId"), jobId);
            }
        }
    }

    private void initResumeData() {
        llHistory.setVisibility(View.GONE);
        llResume.setVisibility(View.VISIBLE);
        adapter.update(positionSearches);
        hideResume();
    }

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        llResume.setVisibility(View.GONE);
        historiesAESC.clear();
        histories = SPUtil.loadRecommendHistoryArray(context);
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
                    SPUtil.saveRecommendHistoryArray(context, histories);
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
                SPUtil.saveRecommendHistoryArray(context, histories);
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
            }
        });

        adapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecommendPeople people = positionSearches.get(position);
                people.setChecked(!people.isChecked());
                updateData(position);
                adapter.update(positionSearches);
                if (people.isChecked()) {
                    jobId = people.getJobNum();
                } else {
                    jobId = "";
                }
            }
        });
    }

    private void updateData(int position) {
        for (int i = 0; i < positionSearches.size(); i++) {
            if (i != position) {
                positionSearches.get(i).setChecked(false);
            }
        }
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
        SPUtil.saveRecommendHistoryArray(context, histories);
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
            llData.setVisibility(View.VISIBLE);
            llEmptySearch.setVisibility(View.GONE);
        } else {
            llData.setVisibility(View.GONE);
            llEmptySearch.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof RecommendPeopleResponse) {
                    response = (RecommendPeopleResponse) obj;
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
                    adapter.update(positionSearches);
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


    public static void startAction(Context context, String resumeId) {
        Intent intent = new Intent(context, RecommendPeopleSearchActivity.class);
        intent.putExtra("resumeId", resumeId);
        context.startActivity(intent);
    }

}
