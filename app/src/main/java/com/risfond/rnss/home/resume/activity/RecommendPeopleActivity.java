package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.statusBar.Eyes;
import com.risfond.rnss.entry.RecommendPeople;
import com.risfond.rnss.entry.RecommendPeopleResponse;
import com.risfond.rnss.home.resume.adapter.PeopleAdapter;
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

/**
 * 推荐人选，选择职位页面
 */
public class RecommendPeopleActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.rv_people)
    RecyclerView rvPeople;
    @BindView(R.id.tv_next)
    TextView tvNext;
    @BindView(R.id.ll_data)
    LinearLayout llData;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;

    private Context context;
    private PeopleAdapter adapter;
    private IRecommendPeople iRecommendPeople;
    private RecommendPeopleResponse response;
    private List<RecommendPeople> peoples = new ArrayList<>();
    private List<RecommendPeople> temp = new ArrayList<>();
    private String jobId = "";
    private Map<String, String> request = new HashMap<>();
    private int pageindex = 1;
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_recommend_people;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        Eyes.setStatusBarLightMode(this, Color.TRANSPARENT);

        context = RecommendPeopleActivity.this;
        iRecommendPeople = new RecommendPeopleImpl();

        tvTitle.setText(R.string.recommend_people);
        ivSearch.setVisibility(View.VISIBLE);

        adapter = new PeopleAdapter(context, peoples);
        rvPeople.setLayoutManager(new LinearLayoutManager(context));
        rvPeople.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_stoke)));
        rvPeople.setAdapter(adapter);

        onItemClick();
        onScroll();
        resumeRequest();
    }

    private void resumeRequest() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        }
        request.put("KeyWord", "");
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(pageindex));
        iRecommendPeople.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_GET_JOBS, this);
    }

    @OnClick({R.id.iv_search, R.id.tv_next})
    public void onClick(View v) {
        if (v.getId() == R.id.iv_search) {
            RecommendPeopleSearchActivity.startAction(context, getIntent().getStringExtra("resumeId"));
        } else if (v.getId() == R.id.tv_next) {
            if (TextUtils.isEmpty(jobId)) {
                ToastUtil.showShort(context, "请选择一个职位");
            } else {
                //                RecommendPeopleNextActivity.startAction(context, getIntent().getStringExtra("resumeId"), jobId);
                Intent intent = new Intent(context, RecommendPeopleNextActivity.class);
                intent.putExtra("resumeId", getIntent().getStringExtra("resumeId"));
                intent.putExtra("jobId", jobId);
                startActivityForResult(intent, 9999);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 9999) {
            finish();
        }
    }

    private void onScroll() {
        rvPeople.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            resumeRequest();
                        }
                    }
                }
            }
        });
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new PeopleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecommendPeople people = peoples.get(position);
                people.setChecked(!people.isChecked());
                updateData(position);
                adapter.update(peoples);
                if (people.isChecked()) {
                    jobId = people.getJobNum();
                } else {
                    jobId = "";
                }
            }
        });
    }

    private void updateData(int position) {
        for (int i = 0; i < peoples.size(); i++) {
            if (i != position) {
                peoples.get(i).setChecked(false);
            }
        }
    }

    public static void startAction(Context context, String resumeId) {
        Intent intent = new Intent(context, RecommendPeopleActivity.class);
        intent.putExtra("resumeId", resumeId);
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
                if (obj instanceof RecommendPeopleResponse) {
                    response = (RecommendPeopleResponse) obj;
                    if (response.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
                            peoples.removeAll(temp);
                            temp.clear();
                        }
                        peoples.addAll(response.getData());
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            peoples.removeAll(temp);
                            temp.clear();
                        }
                        temp = response.getData();
                        peoples.addAll(temp);
                    }
                    adapter.update(peoples);
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                if (peoples.size() > 0) {
                    if (llEmptySearch != null) {
                        llEmptySearch.setVisibility(View.GONE);
                    }
                    if (llData != null) {
                        llData.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (llEmptySearch != null) {
                        llEmptySearch.setVisibility(View.VISIBLE);
                    }
                    if (llData != null) {
                        llData.setVisibility(View.GONE);
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
