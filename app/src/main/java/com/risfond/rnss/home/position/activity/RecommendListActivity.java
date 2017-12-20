package com.risfond.rnss.home.position.activity;

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
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.RecommendList;
import com.risfond.rnss.entry.RecommendListResponse;
import com.risfond.rnss.entry.eventBusVo.ProcessEventBus;
import com.risfond.rnss.home.position.adapter.RecommendListAdapter;
import com.risfond.rnss.home.position.modelImpl.RecommendListImpl;
import com.risfond.rnss.home.position.modelInterface.IRecommendList;
import com.risfond.rnss.home.resume.activity.ResumeDetailActivity;
import com.risfond.rnss.widget.RecycleViewDivider;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 职位详情-推荐管理-内容列表
 */
public class RecommendListActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rv_recommend_list)
    RecyclerView rvRecommendList;

    private Context context;
    private String jobid;
    private String status;
    private List<RecommendList> recommendLists = new ArrayList<>();
    private List<RecommendList> temp = new ArrayList<>();
    private IRecommendList iRecommendList;
    private Map<String, String> request = new HashMap<>();
    private RecommendListAdapter adapter;
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private int pageindex = 1;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_recommend_list;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = RecommendListActivity.this;
        jobid = getIntent().getStringExtra("jobid");
        status = getIntent().getStringExtra("status");
        tvTitle.setText(title(Integer.parseInt(status)));
        iRecommendList = new RecommendListImpl();
        adapter = new RecommendListAdapter(context, recommendLists);

        rvRecommendList.setLayoutManager(new LinearLayoutManager(context));
        rvRecommendList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        rvRecommendList.setAdapter(adapter);

        onItemClick();
        requestServer();

        EventBusUtil.registerEventBus(this);
    }


    private void requestServer() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        }
        request.put("jobid", jobid);
        request.put("status", status);
        request.put("pageindex", String.valueOf(pageindex));
        iRecommendList.recommendListRequest(SPUtil.loadToken(context), request, URLConstant.URL_JOB_RECOMMEND_LIST, this);
    }


    private void onItemClick() {

        adapter.setOnItemClickListener(new RecommendListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecommendList recommendList = recommendLists.get(position);
                RecommendInfoActivity.startAction(context, String.valueOf(recommendList.getRecomid()), String.valueOf(recommendList.getStatus()));
            }
        });

        rvRecommendList.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            requestServer();
                        }
                    }
                }
            }
        });
    }

    @Subscribe
    public void onEventBus(ProcessEventBus eventBus) {
        if (EventBusUtil.isRegisterEventBus(this)) {
            if (eventBus.getType().equals("liuCheng")) {
                isLoadMore = true;
                pageindex = 1;
                requestServer();
            }
        }
    }

    public static void startAction(Context context, String jobid, String status) {
        Intent intent = new Intent(context, RecommendListActivity.class);
        intent.putExtra("jobid", jobid);
        intent.putExtra("status", status);
        context.startActivity(intent);
    }

    private String title(int status) {
        String result = "";
        switch (status) {
            case 0:
                result = "全部";
                break;
            case 1:
                result = "加项目";
                break;
            case 4:
                result = "给顾问";
                break;
            case 2:
                result = "给客户";
                break;
            case 10:
                result = "约面试";
                break;
            case 6:
                result = "已面试";
                break;
            case 11:
                result = "发OFFER";
                break;
            case 8:
                result = "收OFFER";
                break;
            case 9:
                result = "已入职";
                break;
            case 12:
                result = "人选离职";
                break;
            default:
                break;
        }
        return result;
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof ArrayList) {
                    if (((List<RecommendList>) obj).size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
                            recommendLists.removeAll(temp);
                            temp.clear();
                        }
                        recommendLists.addAll((List<RecommendList>) obj);
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            recommendLists.removeAll(temp);
                            temp.clear();
                        }
                        temp = (List<RecommendList>) obj;
                        recommendLists.addAll(temp);
                    }
                    adapter.updateData(recommendLists);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegisterEventBus(this);
    }
}
