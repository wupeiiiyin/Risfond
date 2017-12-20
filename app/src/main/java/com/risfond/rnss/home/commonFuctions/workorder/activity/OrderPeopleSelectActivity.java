package com.risfond.rnss.home.commonFuctions.workorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.ImeUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.HttpUtil;
import com.risfond.rnss.entry.AddGroupSearchResponse;
import com.risfond.rnss.entry.UserList;
import com.risfond.rnss.group.adapter.AddGroupSearchAdapter;
import com.risfond.rnss.group.modleImpl.AddGroupSearchImpl;
import com.risfond.rnss.group.modleInterface.IAddGroupSearch;
import com.risfond.rnss.widget.ClearEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

public class OrderPeopleSelectActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.et_resume_search)
    ClearEditText etResumeSearch;
    @BindView(R.id.tv_search_cancel)
    TextView tvSearchCancel;
    @BindView(R.id.rv_people)
    RecyclerView rvPeople;

    private Context context;
    private List<UserList> userLists = new ArrayList<>();
    private Map<String, String> request = new HashMap<>();
    private AddGroupSearchAdapter addGroupSearchAdapter;
    private IAddGroupSearch iSearch;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_order_people_select;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = OrderPeopleSelectActivity.this;
        etResumeSearch.setHint("请输入员工姓名");

        addGroupSearchAdapter = new AddGroupSearchAdapter(context, userLists, true);
        iSearch = new AddGroupSearchImpl();

        rvPeople.setLayoutManager(new LinearLayoutManager(context));
        rvPeople.setAdapter(addGroupSearchAdapter);

        onItemClick();
    }

    private void requestSearch() {
        request.clear();
        String name = etResumeSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(name)) {
            request.put("name", name);
            iSearch.searchRequest(SPUtil.loadToken(context), request, URLConstant.URL_SEARCH_BY_STAFF, this);
        }
    }

    private void onItemClick() {
        addGroupSearchAdapter.setOnItemClickListener(new AddGroupSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImeUtil.hideSoftKeyboard(etResumeSearch);
                Intent intent = new Intent();
                intent.putExtra("CounselorId", userLists.get(position).getStaffid());
                intent.putExtra("CounselorName", userLists.get(position).getCnname());
                setResult(1200, intent);
                finish();
            }
        });

        rvPeople.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ImeUtil.hideSoftKeyboard(etResumeSearch);
                return false;
            }
        });
    }

    @OnClick({R.id.tv_search_cancel})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_cancel) {
            finish();
        }
    }

    @OnTextChanged({R.id.et_resume_search})
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        HttpUtil.getInstance().cancelRequest(URLConstant.URL_SEARCH_BY_STAFF);
        if (s.length() > 0) {
            requestSearch();
        }
    }

    @OnEditorAction({R.id.et_resume_search})
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            ImeUtil.hideSoftKeyboard(etResumeSearch);
            requestSearch();
        }
        return false;
    }

    private void updateUI(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (obj instanceof AddGroupSearchResponse) {
                    userLists.clear();
                    userLists.addAll(((AddGroupSearchResponse) obj).getData());
                    addGroupSearchAdapter.updateData(userLists);
                } else {
                    if (!obj.toString().equals("Canceled")&&!obj.toString().equals("Socket closed")) {
                        ToastUtil.showShort(context, obj.toString());
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
}
