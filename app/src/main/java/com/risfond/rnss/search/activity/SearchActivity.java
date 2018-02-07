package com.risfond.rnss.search.activity;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.chat.activity.Chat2Activity;
import com.risfond.rnss.common.constant.Constant;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.common.utils.net.NetUtil;
import com.risfond.rnss.contacts.activity.CompanyListActivity;
import com.risfond.rnss.entry.CompanyList;
import com.risfond.rnss.entry.Search;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.search.adapter.ContactsSearchHistoryAdapter;
import com.risfond.rnss.search.adapter.SearchAdapter;
import com.risfond.rnss.search.modelImpl.SearchImpl;
import com.risfond.rnss.search.modleInterface.ISearch;
import com.risfond.rnss.widget.ClearEditText;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

public class SearchActivity extends BaseActivity implements ResponseCallBack {
    private Context context;
    private ISearch iSearch;
    private Map<String, String> request = new HashMap<>();
    private List<Object> data = new ArrayList<>();
    private List<UserList> userLists = new ArrayList<>();
    private List<CompanyList> companyLists = new ArrayList<>();
    private List<Object> searches = new ArrayList<>();
    private SearchAdapter adapter;
    private ContactsSearchHistoryAdapter historyAdapter;
    private List<String> histories;
    private List<String> historiesAESC;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;


    @BindView(R.id.rv_constacts_search_history)
    RecyclerView rvConstactsSearchHistory;
    @BindView(R.id.ll_history)
    LinearLayout llHistory;
    @BindView(R.id.et_resume_search)
    ClearEditText etSearch;
    @BindView(R.id.ll_top_search)
    LinearLayout llTopSearch;
    @BindView(R.id.rv_search)
    RecyclerView rvSearch;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = SearchActivity.this;
        etSearch.setHint("搜索...");
        histories = new ArrayList<>();
        historiesAESC = new ArrayList<>();
        iSearch = new SearchImpl();

        rvConstactsSearchHistory.setLayoutManager(new LinearLayoutManager(context));
        rvConstactsSearchHistory.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));

        adapter = new SearchAdapter(context, data);
        historyAdapter = new ContactsSearchHistoryAdapter(context, historiesAESC);

        rvSearch.setLayoutManager(new LinearLayoutManager(context));
        rvSearch.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.colo_bg_login)));
        rvSearch.setAdapter(adapter);

        onItemClick();
        checkSearchEditText();
        etSearch.setFocusable(false);
        initHistoryData();
    }

    @OnClick({R.id.tv_search_cancel})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            ImeUtil.hideSoftKeyboard(etSearch);
            finish();
        }
    }

    @OnEditorAction({R.id.et_resume_search})
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            requestService(etSearch.getText().toString());
        }
        return false;
    }

    private void requestService(String contact) {
        request.put("name", contact);
        iSearch.searchRequest(SPUtil.loadToken(context), request, URLConstant.URL_SEARCH, this);
    }

    private void onItemClick() {
        //搜索里的列表点击
        historyAdapter.setOnItemClickListener(new ContactsSearchHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //如果点击的是清除历史记录
                if (historyAdapter.isFooterView(position)) {
                    ImeUtil.hideSoftKeyboard(etSearch);
                    histories.clear();
                    historiesAESC.clear();
                    historyAdapter.updateHistory(historiesAESC);
                    SPUtil.saveConstactsHistoryArray(context, histories);
                    hideHistory();
                } else {
                    if (NetUtil.isConnected(context)) {
                        searches.clear();
                        ImeUtil.hideSoftKeyboard(etSearch);
                        requestService(historiesAESC.get(position));
                        etSearch.setText(historiesAESC.get(position));
                        saveHistory(historiesAESC.get(position));
                    } else {
                        ToastUtil.showImgMessage(context, "请检查网络连接");
                    }

                }
            }
        });

        historyAdapter.setOnDeleteClickListener(new ContactsSearchHistoryAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(View view, int position) {
                histories.remove(historiesAESC.get(position));
                SPUtil.saveConstactsHistoryArray(context, histories);
                historiesAESC.remove(position);
                historyAdapter.updateHistory(historiesAESC);
                if (historiesAESC.size() == 0) {
                    llTopSearch.setVisibility(View.VISIBLE);
                    llHistory.setVisibility(View.GONE);
                } else {
                    llTopSearch.setVisibility(View.GONE);
                    llHistory.setVisibility(View.VISIBLE);
                }
            }
        });

        adapter.setOnItemClickListener(new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (data.get(position) instanceof UserList) {
                    UserList userList = (UserList) data.get(position);
                    Chat2Activity.startAction(context, userList.getEasemobaccount(), SPUtil.loadName(context), SPUtil.loadHeadPhoto(context),
                            userList.getCnname(), userList.getHeadphoto(), EaseConstant.CHATTYPE_SINGLE);
                } else if (data.get(position) instanceof CompanyList) {
                    CompanyList company = (CompanyList) data.get(position);
                    CompanyListActivity.startAction(context, "搜索", company.getName(), company.getName(), true, Constant.LIST_DEPART, String.valueOf(company.getId()), "", "1003");
                }
            }
        });
    }

    @Override
    public void back(View v) {
        super.back(v);
        ImeUtil.hideSoftKeyboard(etSearch);
        finish();
    }

    public static void startAction(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj != null && obj instanceof Search) {
                    companyLists = ((Search) obj).getCompanylist();
                    userLists = ((Search) obj).getStafflist();
                    data.clear();
                    if (userLists != null && userLists.size() > 0) {
                        data.add("联系人");
                        data.addAll(userLists);
                    }
                    if (companyLists != null && companyLists.size() > 0) {
                        data.add("公司");
                        data.addAll(companyLists);
                    }
                    adapter.updateData(data);
                    if (data.size() > 0) {
                        llTopSearch.setVisibility(View.GONE);
                        llHistory.setVisibility(View.GONE);
                        rvSearch.setVisibility(View.VISIBLE);
                    } else {
                        ToastUtil.showShort(context, "无搜索结果");
                    }
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

    /**
     * 获取并显示搜索历史
     */
    private void initHistoryData() {
        llHistory.setVisibility(View.VISIBLE);
        rvSearch.setVisibility(View.GONE);
        historiesAESC.clear();
        histories = SPUtil.loadContactsHistoryArray(context);
        for (int i = histories.size() - 1; i >= 0; i--) {
            historiesAESC.add(histories.get(i));
        }
        rvConstactsSearchHistory.setAdapter(historyAdapter);
        historyAdapter.updateHistory(historiesAESC);
        hideHistory();
    }

    private void checkSearchEditText() {
        etSearch.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                etSearch.setFocusableInTouchMode(true);
                etSearch.setFocusable(true);
                etSearch.requestFocus();
                initHistoryData();
                return false;
            }
        });

        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {//点击“搜索”软键盘
                    ImeUtil.hideSoftKeyboard(etSearch);
                    searches.clear();
                    requestService(etSearch.getText().toString().trim());
                    saveHistory(etSearch.getText().toString().trim());
                }
                return false;
            }
        });

    }

    private void hideHistory() {
        if (historiesAESC.size() > 0) {
            llTopSearch.setVisibility(View.GONE);
            rvConstactsSearchHistory.setVisibility(View.VISIBLE);
        } else {
            llTopSearch.setVisibility(View.VISIBLE);
            rvConstactsSearchHistory.setVisibility(View.GONE);
        }
    }

    /**
     * 搜素记录只保存15条
     */
    private void saveHistory(String text) {
        updateHistory(text);
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
        SPUtil.saveConstactsHistoryArray(context, histories);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_SEARCH);
    }

}
