package com.risfond.rnss.home.commonFuctions.referencemanage.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.ReferenceItemInfo;
import com.risfond.rnss.entry.ReferenceManagePageResponse;
import com.risfond.rnss.entry.eventBusVo.ProcessEventBus;
import com.risfond.rnss.home.commonFuctions.referencemanage.adapter.ReferenceManagePageAdapter;
import com.risfond.rnss.home.commonFuctions.referencemanage.modelImpl.ReferenceManageImpl;
import com.risfond.rnss.home.commonFuctions.referencemanage.modelInterface.IReferenceManage;
import com.risfond.rnss.home.position.activity.RecommendInfoActivity;
import com.risfond.rnss.widget.RecycleViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 推荐管理-列表
 * Created by vicky on 2017/7/26.
 */
public class ReferenceMangePageFragment extends BaseFragment implements ResponseCallBack {

    @BindView(R.id.rv_reference_fragment_list)
    RecyclerView mRvReferenceFragmentList;
    @BindView(R.id.ll_empty_reference_fragment)
    LinearLayout mLlEmptyReferenceFragment;
    @BindView(R.id.tv_resume_total)
    TextView tvResumeTotal;

    private Context context;
    private ReferenceManagePageAdapter adapter;
    private Map<String, String> request = new HashMap<>();
    private IReferenceManage iPositionSearch;
    private int pageindex = 1;
    private ReferenceManagePageResponse response;
    private List<ReferenceItemInfo> positionSearches = new ArrayList<>();
    private List<ReferenceItemInfo> temp = new ArrayList<>();
    /* 标志位，标志已经初始化完成*/
    private boolean isPrepared;
    /* 是否已被加载过一次，第二次就不再去请求数据了*/
    private boolean mHasLoadedOnce;
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private String id = "0";//推荐管理不同界面对应的id
    private boolean isHasNum = true;//记录是否加载有数据

    @Override
    public void init(Bundle savedInstanceState) {
        context = getActivity();
        id = getArguments().getString("id");
        if (!isHasNum) {
            if (mLlEmptyReferenceFragment != null) {
                mLlEmptyReferenceFragment.setVisibility(View.VISIBLE);
            }
            if (mRvReferenceFragmentList != null) {
                mRvReferenceFragmentList.setVisibility(View.GONE);
            }
        }

        iPositionSearch = new ReferenceManageImpl();

        adapter = new ReferenceManagePageAdapter(context, positionSearches);

        mRvReferenceFragmentList.setLayoutManager(new LinearLayoutManager(context));
        mRvReferenceFragmentList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        mRvReferenceFragmentList.setAdapter(adapter);

        mRvReferenceFragmentList.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            positionRequest();
                        }
                    }
                }
            }
        });
        onItemClick();
        isPrepared = true;
        lazyLoad();

        if (tvResumeTotal != null && response != null) {
            tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
        }

        EventBusUtil.registerEventBus(this);
    }

    private void positionRequest() {
        if (!isLoadMore) {
            DialogUtil.getInstance().closeLoadingDialog();//防止上一个界面加载对话框到本次界面
            DialogUtil.getInstance().showLoadingDialog(context, "搜索中...");
        }
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("pageindex", String.valueOf(pageindex));
        request.put("status", String.valueOf(id));
        iPositionSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_RECOMMEND_MANAGE, this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_reference_manage;
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        positionRequest();

    }

    @Subscribe
    public void onEventBus(ProcessEventBus eventBus) {
        if (EventBusUtil.isRegisterEventBus(this)){
            if (id.equals(eventBus.getType())) {
                if (isPrepared && mHasLoadedOnce) {
                    isLoadMore = true;//不显示加载框
                    pageindex = 1;
                    positionRequest();
                }
            }
        }
    }

    private void onItemClick() {
        //简历列表点击
        adapter.setOnItemClickListener(new ReferenceManagePageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ReferenceItemInfo itemInfo = positionSearches.get(position);
                RecommendInfoActivity.startAction(context, String.valueOf(itemInfo.getRecomid()), itemInfo.getStatus());
            }
        });
    }

    @Override
    public void onSuccess(final Object obj) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mHasLoadedOnce = true;
                    if (!isLoadMore) {
                        DialogUtil.getInstance().closeLoadingDialog();
                    }
                    if (obj instanceof ReferenceManagePageResponse) {
                        response = (ReferenceManagePageResponse) obj;
                        if (tvResumeTotal != null && String.valueOf(response.getTotal()) != null) {
                            tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                        }
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
                        if (adapter != null) {
                            adapter.updateData(positionSearches);
                        }
                    }
                    if (isLoadMore) {
                        isLoadingMore = false;
                    }
                    if (positionSearches.size() > 0) {
                        isHasNum = true;
                        if (mLlEmptyReferenceFragment != null) {
                            mLlEmptyReferenceFragment.setVisibility(View.GONE);
                        }
                        if (mRvReferenceFragmentList != null) {
                            mRvReferenceFragmentList.setVisibility(View.VISIBLE);
                        }
                    } else {
                        isHasNum = false;
                        if (mLlEmptyReferenceFragment != null) {
                            mLlEmptyReferenceFragment.setVisibility(View.VISIBLE);
                        }
                        if (mRvReferenceFragmentList != null) {
                            mRvReferenceFragmentList.setVisibility(View.GONE);
                        }
                    }
                    isLoadMore = false;
                }
            });
        }
    }

    @Override
    public void onFailed(String str) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isLoadMore) {
                        DialogUtil.getInstance().closeLoadingDialog();
                    }
                }
            });
        }
    }

    @Override
    public void onError(String str) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (!isLoadMore) {
                        DialogUtil.getInstance().closeLoadingDialog();
                    }
                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

}
