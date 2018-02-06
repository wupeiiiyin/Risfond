package com.risfond.rnss.home.resume.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.UtilHelper;
import com.risfond.rnss.entry.AppDeleteQuery;
import com.risfond.rnss.entry.AppSelectQuery;
import com.risfond.rnss.entry.PositionSearch;
import com.risfond.rnss.entry.ResumeSearchDeleteResponse;
import com.risfond.rnss.entry.ResumeSearchHight;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;
import com.risfond.rnss.home.resume.adapter.ResumeQuickSearchAdapter;
import com.risfond.rnss.home.resume.adapter.ResumeQuickSearchV2Adapter;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchDeleteImpl;
import com.risfond.rnss.home.resume.modleImpl.ResumeSearchSelectImpl;
import com.risfond.rnss.home.resume.modleInterface.IResumeSearch;
import com.risfond.rnss.home.resume.window.ResumeQuickSearchPopupWindow;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快捷搜索界面
 */
public class ResumeQuickSearchActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout ll_back;//返回按钮
    @BindView(R.id.ll_empty_quicksearch)
    LinearLayout ll_empty_quicksearch;//暂无职位
    @BindView(R.id.tv_resume_quick_position)
    TextView tv_ResumeQuickPosition;//快捷搜索职位数量
    @BindView(R.id.rv_quick_resume_list)
    RecyclerView recruitmentQuick;
    @BindView(R.id.ll_resume_quick)
    LinearLayout llResumeQuick;//按已有位置搜索控件
    @BindView(R.id.tv_resume_quick_num)
    TextView tvResumeQuickNum;//已有职位数量
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private Context context;

    private boolean isLoadMore;
    private Map<String, String> request = new HashMap<>();
    private int pageindex = 1;

    private ResumeQuickSearchV2Adapter mAdapter;

    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;


    //自定义的弹出框类
    private IResumeSearch iResumeSearchDelece;
    private IResumeSearch iResumeSearchSelect;


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
        context = this;

        iResumeSearchDelece = new ResumeSearchDeleteImpl();//删除
        iResumeSearchSelect = new ResumeSearchSelectImpl();//查询

        mAdapter = new ResumeQuickSearchV2Adapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.image_quick_search_deletes) {
                    delete(position);
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ResumeSearchSelectResponse.DataBean dataBean = mAdapter.getData().get(position);
                shutdownActivity(dataBean);
            }
        });
        recruitmentQuick.setLayoutManager(new LinearLayoutManager(context));
        //控制分割线的宽度 参数1：上下文，参数2：方向，参数3：分割线高度，参数4：颜色
        recruitmentQuick.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(context, R.color.color_home_stoke_small)));

        recruitmentQuick.setAdapter(mAdapter);


        resumeRequest();

        /*recruitmentQuick.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });*/

        //onItemClick();//监听
    }

    /**
     * 关闭快捷搜索页面，并提供检索信息
     *
     * @param dataBean
     */
    public void shutdownActivity(ResumeSearchSelectResponse.DataBean dataBean) {
        UtilHelper.outLog("ResumeQuickSearchActivity", dataBean.toString());
        Intent data = new Intent();
        data.putExtra("ResumeSearchSelectResponse.DataBean", dataBean);
        setResult(RESULT_OK, data);
        ResumeQuickSearchActivity.this.finish();
    }

    public void shutdownActivityByJobSearch(PositionSearch search) {
        Intent data = new Intent();
        data.putExtra("PositionSearch", search);
        setResult(RESULT_OK, data);
        ResumeQuickSearchActivity.this.finish();
    }

    private void resumeRequest() {//快速搜索请求
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        //request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));//当前页数
        request.put("pageSize", String.valueOf(100));//每页条数
        iResumeSearchSelect.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_SELECTRESUMEQUERY, this);
    }


    /**
     * 删除
     *
     * @param position
     */
    private void delete(final int position) {
        DialogUtil.getInstance().showConfigDialog(context, "是否删除？", "是", "否", new DialogUtil.PressCallBack() {
            @Override
            public void onPressButton(int buttonIndex) {
                if (buttonIndex == DialogUtil.BUTTON_OK) {
                    ResumeSearchSelectResponse.DataBean dataBean = mAdapter.getData().get(position);
                    mAdapter.remove(position);
                    mAdapter.notifyItemChanged(position);
                    tv_ResumeQuickPosition.setText(mAdapter.getData().size() + "");

                    if (mAdapter.getData().size() == 0) {
                        ll_empty_quicksearch.setVisibility(View.VISIBLE);
                        recruitmentQuick.setVisibility(View.GONE);
                    }
                    request.put("Id", String.valueOf(dataBean.getResumequeryid()));
                    request.put("staffid", String.valueOf(SPUtil.loadId(context)));
                    iResumeSearchDelece.resumeRequest(SPUtil.loadToken(context), request, URLConstant.URL_RESUME_DELETERESUMEQUERY, new ResponseCallBack() {
                        @Override
                        public void onSuccess(Object obj) {
                            final ResumeSearchDeleteResponse response = (ResumeSearchDeleteResponse) obj;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShort(context, response.getMessage());
                                }
                            });
                        }

                        @Override
                        public void onFailed(final String str) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShort(context, str);
                                }
                            });
                        }

                        @Override
                        public void onError(final String str) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtil.showShort(context, str);
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    //关闭加载框的
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                ResumeSearchSelectResponse response = (ResumeSearchSelectResponse) obj;
                mAdapter.addData(response.getData());
                loadServiceInoToComponent(response);
            }
        });

    }

    /**
     * 加载服务器返回的数据到页面组件中
     *
     * @param response
     */
    private void loadServiceInoToComponent(ResumeSearchSelectResponse response) {
        if (response == null) {
            tvResumeQuickNum.setText("0");
            changeUiVisible(false);
        } else {
            tvResumeQuickNum.setText(String.valueOf(response.getJobCount()));
            tv_ResumeQuickPosition.setText(String.valueOf(mAdapter.getData().size()));
            if (mAdapter.getData().size() > 0) {
                changeUiVisible(true);
            }
        }
    }

    @Override
    public void onFailed(final String str) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (str.contains("没有数据")) {
                    tvResumeQuickNum.setText("0");
                    changeUiVisible(false);
                }

            }
        });
    }


    @Override
    public void onError(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                tvResumeQuickNum.setText("0");
                changeUiVisible(false);
            }
        });
    }

    public void changeUiVisible(boolean flag) {
        recruitmentQuick.setVisibility(flag ? View.VISIBLE : View.GONE);
    }

    //跳转界面的方法
    public static void StartAction(Context context) {
        Intent intent = new Intent(context, ResumeQuickSearchActivity.class);
        context.startActivity(intent);
    }

    /**
     * 快捷搜索 >> 按已有的职位搜索
     *
     * @param view
     */
    @OnClick(R.id.ll_resume_quick)
    public void onShowPopupWindowClickEvent(View view) {
        ResumeQuickSearchPopupWindow
                .getInstance(this, this, R.layout.popview_item_search)
                .show(tvTitle, Gravity.BOTTOM);
    }
}
