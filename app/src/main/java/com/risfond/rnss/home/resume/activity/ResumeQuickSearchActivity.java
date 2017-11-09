package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.PositionSearch;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.home.position.modelImpl.PositionSearchImpl;
import com.risfond.rnss.home.position.modelInterface.IPositionSearch;
import com.risfond.rnss.home.resume.adapter.ResumeQuickSearchAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeSearchAdapter;
import com.risfond.rnss.widget.CustomDecoration;
import com.risfond.rnss.widget.DividerItemDecoration;
import com.risfond.rnss.widget.EmptyRecyclerView;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 快捷搜索界面
 */
public class ResumeQuickSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;//返回按钮
    @BindView(R.id.ll_empty_quicksearch)
    LinearLayout ll_empty_quicksearch;//暂无职位
    @BindView(R.id.tv_resume_quick_totals)
    TextView tv_resume_quick_totals;//快捷搜索职位数量
    @BindView(R.id.rv_quick_resume_list)
    RecyclerView recruitmentQuick;
    @BindView(R.id.ll_resume_quick)
    LinearLayout llResumeQuick;
    @BindView(R.id.tv_resume_quick_num)
    TextView tvResumeQuickNum;//已有职位数量

    private Context context;
    private List<ResumeSearch> searches = new ArrayList<>();
    private boolean isLoadMore;
    private PositionSearchResponse response;
    private Map<String, String> request = new HashMap<>();
    private List<PositionSearch> positionSearches = new ArrayList<>();
    private int pageindex = 1;
    private IPositionSearch iPositionSearch;
    private ResumeQuickSearchAdapter adapter;

    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayList<String> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getContentViewResId() {
        return R.layout.activity_resume_quick_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        EmptyRecyclerView emptyRecyclerView = new EmptyRecyclerView(context);
//        recruitmentQuick = (EmptyRecyclerView) findViewById(R.id.rv_quick_resume_list);
        context = ResumeQuickSearchActivity.this;
        iPositionSearch = new PositionSearchImpl();

        //模拟数据

        for (int i=0;i<2;i++){
            list.add("模拟经理/总监+"+i);
        }

        adapter = new ResumeQuickSearchAdapter(context, list);

        recruitmentQuick.setLayoutManager(new LinearLayoutManager(context));
        //控制分割线的宽度 参数1：上下文，参数2：方向，参数3：分割线高度，参数4：颜色
        recruitmentQuick.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.color_home_stoke_small)));
//        recruitmentQuick.addItemDecoration(new CustomDecoration(this, CustomDecoration.VERTICAL_LIST, R.drawable.list_item_divider));
//        recruitmentQuick.addItemDecoration(new DividerItemDecoration(R.color.color_home_stoke_small,1,LinearLayoutManager.HORIZONTAL,15f,15f));

        recruitmentQuick.setAdapter(adapter);
//        recruitmentQuick.setEmptyView(ll_empty_quicksearch);
        tv_resume_quick_totals.setText(list.size()+"2");
        tvResumeQuickNum.setText("2");
        recruitmentQuick.addOnScrollListener(new RecyclerView.OnScrollListener(){
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
                        }
                    }
                }
            }
        });
        onItemClick();//监听
        positionRequest();
//        if(list.size()<0){
//
//            ll_empty_quicksearch.setVisibility(View.VISIBLE);
//            recruitmentQuick.setVisibility(View.GONE);
//        }

    }

    private void onItemClick() {
        //简历列表点击
        adapter.setOnItemClickListener(new ResumeQuickSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                ResumeDetailActivity.startAction(context, String.valueOf(searches.get(position).getId()));
                findViewById(R.id.tv_quick);
            }
        });
    }

    //跳转界面的方法
    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ResumeQuickSearchActivity.class);
        context.startActivity(intent);
    }
    private void positionRequest() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        iPositionSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_JOB_SEARCH, this);
    }

    @Override
    public void onSuccess(final Object obj) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof PositionSearchResponse) {
                    response = (PositionSearchResponse) obj;
                    if (tv_resume_quick_total != null) {
                        tv_resume_quick_total.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                }
//                if (list.size() > 0) {
//                    ll_empty_quicksearch.setVisibility(View.GONE);
//                    recruitmentQuick.setVisibility(View.VISIBLE);
//                } else {
//                    ll_empty_quicksearch.setVisibility(View.VISIBLE);
//                    recruitmentQuick.setVisibility(View.GONE);
//                }
//                if (list.size() == 2) {
//                    pageindex++;
//                    isCanLoadMore = true;
//                    if (lists.size() > 0) {
//                        list.removeAll(lists);
//                        lists.clear();
//                    }
//                    list.addAll(lists);
//                }else {
//                    isCanLoadMore = false;
//                    if (lists.size() > 0) {
//                        searches.removeAll(lists);
//                        lists.clear();
//                    }
//                    lists = list;
//                    list.addAll(lists);
//                }
                adapter.updateData(list);
//                adapter.notifyDataSetChanged();
                if (list.size() > 0) {
                    if (ll_empty_quicksearch != null) {
                        ll_empty_quicksearch.setVisibility(View.GONE);
                    }
                    if (recruitmentQuick != null) {
                        recruitmentQuick.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (ll_empty_quicksearch != null) {
                        ll_empty_quicksearch.setVisibility(View.VISIBLE);
                    }
                    if (recruitmentQuick != null) {
                        recruitmentQuick.setVisibility(View.GONE);
                    }

                }
            }
        });

    }

    @Override
    public void onFailed(String str) {

    }

    @Override
    public void onError(String str) {

    }
}
