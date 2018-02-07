package com.risfond.rnss.home.commonFuctions.myCourse.activity;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.entry.MyCourse;
import com.risfond.rnss.entry.MyCourseResponse;
import com.risfond.rnss.home.commonFuctions.myCourse.adapter.MyCourseAdapter;
import com.risfond.rnss.home.commonFuctions.myCourse.modelImpl.MyCourseImpl;
import com.risfond.rnss.home.commonFuctions.myCourse.modelInterface.IMyCourse;
import com.risfond.rnss.home.resume.adapter.ResumeSearchHistoryAdapter;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 课程培训搜索页面
 */
public class MyCourseSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.et_resume_search)
    EditText etResumeSearch;
    @BindView(R.id.rv_resume_search_history)
    RecyclerView rvResumeSearchHistory;
    @BindView(R.id.ll_empty_history)
    LinearLayout llEmptyHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.ll_resume)
    LinearLayout llResume;

    private Context context;
    private MyCourseAdapter adapter;
    private ResumeSearchHistoryAdapter historyAdapter;
    private Map<String, String> request = new HashMap<>();
    private IMyCourse iCustomerSearch;
    private int pageindex = 1;
    private MyCourseResponse response;
    private List<MyCourse> customerSearches = new ArrayList<>();
    private List<MyCourse> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private List<String> histories;
    private List<String> historiesAESC;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_my_course_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = MyCourseSearchActivity.this;
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();
        iCustomerSearch = new MyCourseImpl();

        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        rvResumeSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvResumeSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        adapter = new MyCourseAdapter(context, customerSearches);
        historyAdapter = new ResumeSearchHistoryAdapter(context, historiesAESC);

        rvResumeList.setAdapter(adapter);

        checkSearchEditText();
        etResumeSearch.setFocusable(false);
        etResumeSearch.setHint("课程培训（请输入关键字）");
        initHistoryData();
        itemClick();
        scroll();
    }

    /**
     * 请求简历列表
     *
     * @param content
     */
    private void customerRequest(String content) {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        }
        request.put("keyword", content);
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        iCustomerSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_COURSE_LIST, this);
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
                    customerSearches.clear();
                    customerRequest(etResumeSearch.getText().toString().trim());
                    saveHistory(etResumeSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    private void initResumeData() {
        if (llHistory != null) {
            llHistory.setVisibility(View.GONE);
        }
        if (llResume != null) {
            llResume.setVisibility(View.VISIBLE);
        }
        if (adapter != null) {
            adapter.updateData(customerSearches);
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
        histories = SPUtil.loadMyCourseHistoryArray(context);
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
                    SPUtil.saveMyCourseHistoryArray(context, histories);
                    hideHistory();
                } else {
                    if (NetUtil.isConnected(context)) {
                        pageindex = 1;
                        customerSearches.clear();
                        ImeUtil.hideSoftKeyboard(etResumeSearch);
                        customerRequest(historiesAESC.get(position));
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
                SPUtil.saveMyCourseHistoryArray(context, histories);
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
            }
        });

        //客户列表点击，查看客户详情
        adapter.setOnItemClickListener(new MyCourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String url = URLConstant.URL_COURSE_LTDETAIL + "?token=" + SPUtil.loadToken(context) + "&id=" + customerSearches.get(position).getId() + "&staffid=" + SPUtil.loadId(context);
                MyCourseDetailActivity.StartAction(context, url);
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
                            customerRequest(etResumeSearch.getText().toString().trim());
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
        SPUtil.saveMyCourseHistoryArray(context, histories);
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
        if (customerSearches.size() > 0) {
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
        Intent intent = new Intent(context, MyCourseSearchActivity.class);
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
                if (obj instanceof MyCourseResponse) {
                    response = (MyCourseResponse) obj;
                    if (tvResumeTotal != null) {
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
                    initResumeData();
                    if (adapter != null) {
                        adapter.updateData(customerSearches);
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
                } else {
                    isLoadingMore = false;
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
                } else {
                    isLoadingMore = false;
                }
            }
        });
    }
}
