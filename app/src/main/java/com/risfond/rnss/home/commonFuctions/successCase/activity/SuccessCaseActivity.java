package com.risfond.rnss.home.commonFuctions.successCase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.SuccessCasResponse;
import com.risfond.rnss.entry.SuccessCase;
import com.risfond.rnss.entry.SuccessCaseWhole;
import com.risfond.rnss.home.commonFuctions.successCase.adapter.SuccessCaseAdapter;
import com.risfond.rnss.home.commonFuctions.successCase.adapter.SuccessCaseV2Adapter;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.BaseSuccessCaseWholeFragment;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.SuccessCaseIndustryFragment;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.SuccessCaseOrderFragment;
import com.risfond.rnss.home.commonFuctions.successCase.modelImpl.SuccessCaseImpl;
import com.risfond.rnss.home.commonFuctions.successCase.modelInterface.ISuccessCase;
import com.risfond.rnss.home.resume.fragment.PositionFragment;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * 成功案例主页
 */
public class SuccessCaseActivity extends BaseActivity implements ResponseCallBack, BaseQuickAdapter.RequestLoadMoreListener, BaseSuccessCaseWholeFragment.OnSelectListener {

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
    @BindView(R.id.id_cb_conetnt)
    LinearLayout mCbContent;
    @BindView(R.id.id_successcase_framelayout)
    FrameLayout mFrameLayout;

    private Context context;
    private SuccessCaseV2Adapter mAdapter;
    private Map<String, String> request = new HashMap<>();
    private ISuccessCase iResumeSearch;
    private SuccessCasResponse response;
    private boolean isLoadMore;

    /**
     * 查询条件
     */
    private SuccessCaseWhole mSuccessCaseWhole;

    /**
     * 待选择
     */
    private List<BaseFragment> mFragments;

    /**
     * 地点
     */
    private ArrayList<String> selectedIds = new ArrayList<>();
    private ArrayList<String> selectedNames = new ArrayList<>();

    @Override
    public int getContentViewResId() {
        return R.layout.activity_success_case;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initWhole();
        initTitle();
        initAdapter();
        initFragment();
        resumeRequest();
    }

    /**
     * 初始化选择条件Fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(SuccessCaseOrderFragment.getInstance(mSuccessCaseWhole, this));
        mFragments.add(SuccessCaseIndustryFragment.getInstance(mSuccessCaseWhole, this));
        mFragments.add(new PositionFragment(selectedIds, selectedNames, new SelectCallBack() {
            @Override
            public void onPositionConfirm(List<String> positions, List<String> names) {
                changeFragmentVisibleStatus(false);
                selectedIds.clear();
                selectedNames.clear();
                selectedIds.addAll(positions);
                selectedNames.addAll(names);
                if (mSuccessCaseWhole.getWorkLocation()!=null) {
                    mSuccessCaseWhole.getWorkLocation().clear();
                }
                if (mSuccessCaseWhole.getWorkLocations() != null) {
                    mSuccessCaseWhole.getWorkLocations().clear();
                }
                mSuccessCaseWhole.setWorkLocation(selectedIds);
                mSuccessCaseWhole.setWorkLocations(selectedNames);
                //重置
                resetPageIndex();
                //刷新数据
                resumeRequest();
            }

            @Override
            public void onSelected(List<String> positions, List<String> names) {

            }

            @Override
            public void onExperienceConfirm(String from, String to) {

            }

            @Override
            public void onEducationConfirm(List<String> educations, List<String> educationName) {

            }

            @Override
            public void onMoreConfirm(List<String> recommends, String age_from, String age_to, List<String> sex, String salary_from, String salary_to, List<String> languages, String page) {

            }

            @Override
            public void onOutside() {

            }
        }));
    }

    private void initWhole() {
        mSuccessCaseWhole = new SuccessCaseWhole();
    }
    private void initTitle() {
        context = SuccessCaseActivity.this;
        tvResumeSearch.setVisibility(View.VISIBLE);
        mtvTitleImg.setText("成功案例");
    }

    private void initAdapter() {
        iResumeSearch = new SuccessCaseImpl();
        mAdapter = new SuccessCaseV2Adapter();
        mAdapter.setOnLoadMoreListener(this, rvResumeList);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                SuccessCaseDetailActivity.startAction(context, String.valueOf(mAdapter.getData().get(position).getArticleId()));

            }
        });
        rvResumeList.setLayoutManager(new LinearLayoutManager(context));
        rvResumeList.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(context, R.color.color_home_back)));
        rvResumeList.setAdapter(mAdapter);

    }

    private void resumeRequest() {
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
        }
        request.put("KeyWords", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(mSuccessCaseWhole.getPageIndex()));
        request.put("PageSize", String.valueOf(mSuccessCaseWhole.getPageSize()));
        request.put("Type", mSuccessCaseWhole.getType());
        request.put("StartTime", mSuccessCaseWhole.getStartTime());
        request.put("EndTime", mSuccessCaseWhole.getEndTime());
        joinParams("WorkLocation", mSuccessCaseWhole.getWorkIndusty());
        joinParams("WorkIndusty", mSuccessCaseWhole.getWorkLocation());
        request.put("StartYearlySalary", String.valueOf(mSuccessCaseWhole.getStartYearlySalary()));
        request.put("EndYearlySalary", String.valueOf(mSuccessCaseWhole.getEndYearlySalary()));
        request.put("OrderType", String.valueOf(mSuccessCaseWhole.getOrderType()));
        iResumeSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_SUCCESS_CASE2, this);
    }

    private void joinParams(String key, ArrayList<String> params) {
        if (params == null ) return;
        for (int i = 0; i < params.size(); i++) {
            String nkey = key + "[" + i + "]";
            request.put(nkey, params.get(i));
        }
    }


    @OnClick({R.id.ll_title_search})
    public void onClick(View v) {
        if (v.getId() == R.id.ll_title_search) {
            SuccessCaseResultActivity.StartAction(context);
        }
    }
    @OnCheckedChanged({R.id.cb_order, R.id.cb_worklocation, R.id.cb_more, R.id.cb_jobtitle})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int index = Integer.parseInt(buttonView.getTag().toString());
        /*if (!isChecked && index == getCurrentVisibleFragment()) {
            //隐藏当前Framgnet
            changeFragmentVisibleStatus(false);
        }*/
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.id_successcase_framelayout, mFragments.get(index));
        fragmentTransaction.commit();
        changeFragmentVisibleStatus(true);
    }

    private void changeFragmentVisibleStatus(boolean isShow) {
        mFrameLayout.setVisibility(isShow?View.VISIBLE:View.GONE);
        /*if (!isShow) {
            removeAllFragment();
        }*/

    }

    private void removeAllFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null) return;
        for (Fragment fragment : fragments) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(fragment);
            fragmentTransaction.commit();
        }
    }

    private int getCurrentVisibleFragment() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments == null) {
            return 0;
        }
        if (fragments.size() <= 0) {
            return 0;
        }
        return mFragments.indexOf(fragments.get(0));
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
                    if (tvResumeTotal != null) {
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    mAdapter.addData(response.getData());
                    if (mAdapter.getData().size() >= response.getTotal()) {
                        mAdapter.setEnableLoadMore(false);
                    }else{
                        mAdapter.loadMoreComplete();
                    }
                }

                if (mAdapter.getData().size() > 0) {
                    if (llEmptySearch != null) {
                        llEmptySearch.setVisibility(View.GONE);
                    }
                    if (rvResumeList != null) {
                        rvResumeList.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (llEmptySearch != null) {
                        llEmptySearch.setVisibility(View.VISIBLE);
                    }
                    if (rvResumeList != null) {
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

    @Override
    public void onLoadMoreRequested() {
        if (response == null) {
            mAdapter.loadMoreEnd(false);
            return;
        }

        if (mAdapter.getData().size() >= response.getTotal()) {
            mAdapter.loadMoreEnd(false);
        } else {
            isLoadMore = true;
            //加载更多
            mSuccessCaseWhole.setPageIndex(mSuccessCaseWhole.getPageIndex()+1);
            resumeRequest();
        }
    }

    /**
     * 重置页码
     */
    private void resetPageIndex() {
        mAdapter.setNewData(null);
        mSuccessCaseWhole.setPageIndex(1);
    }

    @Override
    public void onCancel() {
        changeFragmentVisibleStatus(false);
    }

    @Override
    public void onConfirm(SuccessCaseWhole successCaseWhole) {
        this.mSuccessCaseWhole = successCaseWhole;
        changeFragmentVisibleStatus(false);
        //重置
        resetPageIndex();
        //刷新数据
        resumeRequest();
    }
}
