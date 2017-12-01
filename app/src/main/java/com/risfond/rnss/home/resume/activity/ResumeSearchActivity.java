package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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
import com.risfond.rnss.common.utils.JsonUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.PositionSearch;
import com.risfond.rnss.entry.ResumeSearch;
import com.risfond.rnss.entry.ResumeSearchResponse;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;
import com.risfond.rnss.home.resume.adapter.ResumeSearchAdapter;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchImpl;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.key;
import static com.xiaomi.network.HostManager.join;

/**
 * 简历搜索首页
 */
public class ResumeSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_resume_search)
    TextView tvResumeSearch;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;//简历份数
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.activity_resume_search)
    LinearLayout activityResumeSearch;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.tv_search_quick)
    TextView tvQuickSearch;//快捷搜索

    private Context context;
    private ResumeSearchAdapter adapter;
    private Map<String, String> request = new HashMap<>();
    private IResumeSearch iResumeSearch;
    private int pageindex = 1;
    private ResumeSearchResponse response;
    private List<ResumeSearch> searches = new ArrayList<>();
    private List<ResumeSearch> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private ResumeSearchSelectResponse.DataBean mDataBean;
    private PositionSearch mPositionSearch;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_resume_search;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = ResumeSearchActivity.this;
        iResumeSearch = new ResumeSearchImpl();
        //        activityResumeSearch.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);

        adapter = new ResumeSearchAdapter(context);

        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        rvResumeList.setAdapter(adapter);

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
                            resumeRequest();
                        }
                    }
                }
            }
        });
        onItemClick();
        resumeRequest();

    }

    private void resumeRequest() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.clear();
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        if (mDataBean != null) {
            request.put("keyword", mDataBean.getKeyword());
            if (mDataBean.getYearfrom() > 0) {
                request.put("yearfrom", String.valueOf(mDataBean.getYearfrom()));
            }

            if (mDataBean.getYearto() > 0) {
                request.put("yearto", String.valueOf(mDataBean.getYearto()));
            }

            if (mDataBean.getAgefrom() > 0) {
                request.put("agefrom", String.valueOf(mDataBean.getAgefrom()));
            }

            if (mDataBean.getAgeto() > 0) {
                request.put("ageto", String.valueOf(mDataBean.getAgeto()));
            }

            if (mDataBean.getSalaryfrom() > 0) {
                request.put("salaryfrom", String.valueOf(mDataBean.getSalaryfrom()));
            }

            if (mDataBean.getSalaryto() > 0) {
                request.put("salaryto", String.valueOf(mDataBean.getSalaryto()));
            }

            join(mDataBean.getEdu(), "edu");
            join(mDataBean.getResumestatus(), "resumestatus");
            join(mDataBean.getGender(), "gender");
            join(mDataBean.getLang(), "lang");
            join(mDataBean.getWorklocation(), "worklocation");

            /**
             * 0 > 关键字搜索
             * 1 > 职位搜索
             * 2 > 公司搜索
             */
            request.put("keywordstype", "0");
            /**
             * 0 > 简历搜索
             * 1 > 职位搜索
             */
            request.put("selecttype", "0");
        }

        if (mPositionSearch != null) {
            request.put("keywordstype", "1");
            request.put("selecttype", "1");
            request.put("jobid", String.valueOf(mPositionSearch.getID()));
        }

        iResumeSearch.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_SEARCH, this);
    }

    private void join(List<String> infos, String key) {
        if (infos == null) {
            return;
        }
        for (int i = 0; i < infos.size(); i++) {
            request.put(key + "[" + i + "]", infos.get(i));
        }
    }

    private void onItemClick() {
        //简历列表点击
        adapter.setOnItemClickListener(new ResumeSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ResumeDetailActivity.startAction(context, String.valueOf(searches.get(position).getId()));
            }
        });
    }

    /**
     * 1、点击搜索框进入简历搜索界面
     * 2、点击快捷搜索 进入简历快速搜索
     *
     * @param v
     */
    @OnClick({R.id.tv_resume_search, R.id.tv_search_quick})
    public void onClick(View v) {
        if (v.getId() == R.id.tv_resume_search) {
            ResumeSearchResultActivity.StartAction(context);
        }
        /**
         * 跳转到快捷搜索界面
         */
        if (v.getId() == R.id.tv_search_quick) {
            Intent intent = new Intent(this, ResumeQuickSearchActivity.class);
            startActivityForResult(intent, 0x1010);
        }
    }

    /**
     * 接收来自其他页面的检索条件
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x1010 && resultCode == RESULT_OK) {
            mDataBean = data.getParcelableExtra("ResumeSearchSelectResponse.DataBean");
            mPositionSearch = data.getParcelableExtra("PositionSearch");
            ///////////////////////////////////////////////////////////////////////////
            // 重新刷新数据
            ///////////////////////////////////////////////////////////////////////////
            pageindex = 1;
            searches.clear();
            isLoadMore = false;
            resumeRequest();
        }
    }

    //提供一个方法供其他类调用
    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ResumeSearchActivity.class);
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
                if (mDataBean != null && !isLoadingMore) {
                    searches.clear();
                }
                if (obj instanceof ResumeSearchResponse) {//实体类
                    response = (ResumeSearchResponse) obj;
                    if (tvResumeTotal != null) {
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));//设置简历数量
                    }
                    if (response.getData().size() == 15) {//设置数量等于15
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {//当2集合大小>0
                            searches.removeAll(temp);
                            temp.clear();
                        }
                        searches.addAll(response.getData());
                    } else {
                        isCanLoadMore = false;
                        if (temp.size() > 0) {
                            searches.removeAll(temp);
                            temp.clear();
                        }
                        temp = response.getData();
                        searches.addAll(temp);
                    }

                    adapter.updateData(searches);
                }
                if (isLoadMore) {
                    isLoadingMore = false;
                }
                if (searches.size() > 0) {
                    if (llEmptySearch != null) {
                        llEmptySearch.setVisibility(View.GONE);//无数据时的布局隐藏
                    }
                    if (rvResumeList != null) {
                        rvResumeList.setVisibility(View.VISIBLE);//显示RecyclerView控件
                    }
                } else {
                    if (llEmptySearch != null) {
                        llEmptySearch.setVisibility(View.VISIBLE);//无数据时的布局显示
                    }
                    if (rvResumeList != null) {
                        rvResumeList.setVisibility(View.GONE);//隐藏RecyclerView控件
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


    /*private boolean softKeyboard = false;
    private int rootBottom = Integer.MIN_VALUE;

    private ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {
            Rect r = new Rect();
            activityResumeSearch.getGlobalVisibleRect(r);
            // 进入Activity时会布局，第一次调用onGlobalLayout，先记录开始软键盘没有弹出时底部的位置
            if (rootBottom == Integer.MIN_VALUE) {
                rootBottom = r.bottom;
                return;
            }
            // adjustResize，软键盘弹出后高度会变小
            if (r.bottom < rootBottom) {
                softKeyboard = true;
            } else {
                softKeyboard = false;
            }

        }
    };*/

    /*protected void onDestroy() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            activityResumeSearch.getViewTreeObserver().removeGlobalOnLayoutListener(mOnGlobalLayoutListener);
        } else {
            activityResumeSearch.getViewTreeObserver().removeOnGlobalLayoutListener(mOnGlobalLayoutListener);
        }
        super.onDestroy();

    }*/

}
