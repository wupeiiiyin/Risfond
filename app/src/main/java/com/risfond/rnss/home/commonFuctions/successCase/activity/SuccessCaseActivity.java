package com.risfond.rnss.home.commonFuctions.successCase.activity;

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
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.SuccessCasResponse;
import com.risfond.rnss.entry.SuccessCase;
import com.risfond.rnss.home.commonFuctions.successCase.adapter.SuccessCaseAdapter;
import com.risfond.rnss.home.commonFuctions.successCase.modelImpl.SuccessCaseImpl;
import com.risfond.rnss.home.commonFuctions.successCase.modelInterface.ISuccessCase;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 成功案例主页
 */
public class SuccessCaseActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.ll_title_search)
    LinearLayout tvResumeSearch;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;
    @BindView(R.id.rv_resume_list)
    RecyclerView rvResumeList;
    @BindView(R.id.activity_resume_search)
    LinearLayout activityResumeSearch;
    @BindView(R.id.ll_empty_search)
    LinearLayout llEmptySearch;
    @BindView(R.id.tv_title)
    TextView mtvTitleImg;

    private Context context;
    private SuccessCaseAdapter adapter;
    private Map<String, String> request = new HashMap<>();
    private ISuccessCase iResumeSearch;
    private int pageindex = 1;
    private SuccessCasResponse response;
    private List<SuccessCase> searches = new ArrayList<>();
    private List<SuccessCase> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_success_case;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = SuccessCaseActivity.this;
        tvResumeSearch.setVisibility(View.VISIBLE);
        mtvTitleImg.setText("成功案例");
        iResumeSearch = new SuccessCaseImpl();
        //        activityResumeSearch.getViewTreeObserver().addOnGlobalLayoutListener(mOnGlobalLayoutListener);

        adapter = new SuccessCaseAdapter(context, searches);

        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
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
            DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        }
        request.put("KeyWords", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(pageindex));
        request.put("PageSize",String.valueOf(15));
        iResumeSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_SUCCESS_CASE, this);
    }

    private void onItemClick() {
        //列表点击
        adapter.setOnItemClickListener(new SuccessCaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SuccessCaseDetailActivity.startAction(context, String.valueOf(searches.get(position).getArticleId()));
            }
        });
    }

    @OnClick({R.id.ll_title_search})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_title_search) {
            SuccessCaseResultActivity.StartAction(context);
        }
    }

    public static void StartAction(Context context) {
        Intent intent = new Intent(context, SuccessCaseActivity.class);
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
                if (obj instanceof SuccessCasResponse) {
                    response = (SuccessCasResponse) obj;
                    if(tvResumeTotal != null){
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    if (response.getData().size() == 15) {
                        pageindex++;
                        isCanLoadMore = true;
                        if (temp.size() > 0) {
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
                    if(llEmptySearch != null){
                        llEmptySearch.setVisibility(View.GONE);
                    }
                    if(rvResumeList != null){
                        rvResumeList.setVisibility(View.VISIBLE);
                    }
                } else {
                    if(llEmptySearch != null){
                        llEmptySearch.setVisibility(View.VISIBLE);
                    }
                    if(rvResumeList != null){
                        rvResumeList.setVisibility(View.GONE);
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
