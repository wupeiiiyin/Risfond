package com.risfond.rnss.home.commonFuctions.successCase.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseActivity;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DensityUtils;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.NumberUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.entry.BaseWhole;
import com.risfond.rnss.entry.IndustrieInfo;
import com.risfond.rnss.entry.ResumeWhole;
import com.risfond.rnss.entry.SuccessCasResponse;
import com.risfond.rnss.entry.SuccessCaseWhole;
import com.risfond.rnss.home.commonFuctions.successCase.adapter.SuccessCaseV2Adapter;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.BaseSuccessCaseWholeFragment;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.SuccessCaseMoreFragment;
import com.risfond.rnss.home.commonFuctions.successCase.fragment.SuccessCaseOrderFragment;
import com.risfond.rnss.home.commonFuctions.successCase.modelImpl.SuccessCaseImpl;
import com.risfond.rnss.home.commonFuctions.successCase.modelInterface.ISuccessCase;
import com.risfond.rnss.home.resume.fragment.IndustrieFragment;
import com.risfond.rnss.home.resume.fragment.PositionFragment;
import com.risfond.rnss.home.resume.fragment.SuccessIndustrieFragment;
import com.risfond.rnss.home.resume.modleInterface.SelectCallBack;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.cb_order)
    CheckBox mOrder;
    @BindView(R.id.cb_jobtitle)
    CheckBox mJobtitle;
    @BindView(R.id.cb_worklocation)
    CheckBox mWorklocation;
    @BindView(R.id.cb_more)
    CheckBox mMore;

    @BindView(R.id.id_title_right)
    LinearLayout mTitleRightLinearLayout;

    private Context context;
    private SuccessCaseV2Adapter mAdapter;
    private Map<String, String> request = new HashMap<>();
    private ISuccessCase iResumeSearch;
    private SuccessCasResponse response;
    private boolean isLoadMore;

    /**
     * 查询条件
     */
    private SuccessCaseWhole mSuccessCaseWhole = new SuccessCaseWhole();

    /**
     * 待选择
     */
    private List<BaseFragment> mFragments;
    /**
     * 当前显示的Fragment
     */
    private BaseFragment mCurrentFragment;

    private ResumeWhole mResumeWhole = new ResumeWhole();

    @Override
    public int getContentViewResId() {
        return R.layout.activity_success_case;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initParams();
        initWhole();
        initTitle();
        initAdapter();
        initFragment();
        resumeRequest();
        //
    }

    private void initParams() {
        String id = getIntent().getStringExtra("id");
        ArrayList<String> industrys = new ArrayList<>();
        ArrayList<String> industrysTip = new ArrayList<>();
        if (!TextUtils.isEmpty(id)) {
            industrys.add(id);
            mResumeWhole.setIndustrys(industrys);
            List<IndustrieInfo> industrieInfos = SuccessIndustrieFragment.getIndustrieInfos();
            for (IndustrieInfo industrieInfo : industrieInfos) {
                if (Integer.parseInt(industrieInfo.getCode()) == Integer.parseInt(id)) {
                    industrysTip.add(industrieInfo.getContent());
                    mResumeWhole.setIndustrysTip(industrysTip);
                    break;
                }
            }
            setActionTitle();
        }

    }

    /**
     * 初始化选择条件Fragment
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(SuccessCaseOrderFragment.getInstance(mSuccessCaseWhole, this));
        SuccessIndustrieFragment industrieFragment = new SuccessIndustrieFragment(mResumeWhole, this);
        Bundle bundle = new Bundle();
        bundle.putString(IndustrieFragment.INDUSTRIE_TYPE, IndustrieFragment.INDUSTRIE);
        industrieFragment.setArguments(bundle);
        mFragments.add(industrieFragment);
        mFragments.add(new PositionFragment(mSuccessCaseWhole.getWorkLocation(),mSuccessCaseWhole.getWorkLocations(), new SelectCallBack() {
            @Override
            public void onPositionConfirm(List<String> positions, List<String> names) {
                changeFragmentVisibleStatus(false);
                mSuccessCaseWhole.setWorkLocation((ArrayList<String>) positions);
                mSuccessCaseWhole.setWorkLocations((ArrayList<String>) names);
                //重置
                resetPageIndex();
                //刷新数据
                resumeRequest();

                setActionTitle();
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
        mFragments.add(SuccessCaseMoreFragment.getInstance(mSuccessCaseWhole, this));
    }

    private void initWhole() {
        mSuccessCaseWhole = new SuccessCaseWhole();
    }

    private void initTitle() {
        context = SuccessCaseActivity.this;
        tvResumeSearch.setVisibility(View.GONE);
        mtvTitleImg.setText("成功案例");
        mTitleRightLinearLayout.setVisibility(View.VISIBLE);
        ImageView searchView = new ImageView(this);
        searchView.setImageResource(R.mipmap.biconsearch);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuccessCaseResultActivity.StartAction(context);
            }
        });
        mTitleRightLinearLayout.addView(searchView);

        ImageView iconView = new ImageView(this);
        iconView.setImageResource(R.mipmap.rs_successcase_icon);
        iconView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuccessCaseActivity.this.finish();
            }
        });
        mTitleRightLinearLayout.addView(iconView);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) iconView.getLayoutParams();
        layoutParams.leftMargin = DensityUtils.dip2px(this, 17);
        iconView.requestFocus();
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
        request.clear();
        request.put("KeyWords", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        request.put("PageIndex", String.valueOf(mSuccessCaseWhole.getPageIndex()));
        request.put("PageSize", String.valueOf(mSuccessCaseWhole.getPageSize()));
        //request.put("Type", mSuccessCaseWhole.getType());
        request.put("StartTime", mSuccessCaseWhole.getStartTime());
        request.put("EndTime", mSuccessCaseWhole.getEndTime());
        joinParams("WorkLocation", mSuccessCaseWhole.getWorkLocation());
        joinParams("WorkIndusty", (ArrayList<String>) mResumeWhole.getIndustrys());
        if (mSuccessCaseWhole.getStartYearlySalary() > 0) {
            request.put("StartYearlySalary", String.valueOf(mSuccessCaseWhole.getStartYearlySalary()));
        }
        if (mSuccessCaseWhole.getEndYearlySalary() > 0) {
            request.put("EndYearlySalary", String.valueOf(mSuccessCaseWhole.getEndYearlySalary()));
        }
        request.put("OrderType", String.valueOf(mSuccessCaseWhole.getOrderType()));
        iResumeSearch.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_SUCCESS_CASE3, this);
    }

    private void joinParams(String key, ArrayList<String> params) {
        if (params == null)
            return;
        for (int i = 0; i < params.size(); i++) {
            String nkey = key + "[" + i + "]";
            request.put(nkey, params.get(i));
        }
    }


    @OnClick({R.id.cb_order, R.id.cb_worklocation, R.id.cb_more, R.id.cb_jobtitle})
    public void onCheckedChanged(View view) {
        CompoundButton buttonView = (CompoundButton) view;
        int index = Integer.parseInt(buttonView.getTag().toString());
        boolean checked = buttonView.isChecked();
        clearAll(mCbContent);
        if (checked) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            mCurrentFragment = mFragments.get(index);
            fragmentTransaction.replace(R.id.id_successcase_framelayout, mCurrentFragment);
            fragmentTransaction.commit();
            changeFragmentVisibleStatus(true);
            buttonView.setChecked(checked);
        } else {
            //false  收起window
            changeFragmentVisibleStatus(false);
        }
    }

    private void changeFragmentVisibleStatus(boolean isShow) {
        if (!isShow) {
            clearAll(mCbContent);
        }
        mFrameLayout.setVisibility(isShow ? View.VISIBLE : View.GONE);
        //销毁Fragment
        if (mCurrentFragment != null && !isShow) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.remove(mCurrentFragment);
            fragmentTransaction.commit();
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
                    if (tvResumeTotal != null) {
                        tvResumeTotal.setText(NumberUtil.formatString(new BigDecimal(response.getTotal())));
                    }
                    mAdapter.addData(response.getData());
                    if (mAdapter.getData().size() >= response.getTotal()) {
                        mAdapter.setEnableLoadMore(false);
                    } else {
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
            mSuccessCaseWhole.setPageIndex(mSuccessCaseWhole.getPageIndex() + 1);
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

    private void clearAll(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            if (viewGroup.getChildAt(i) instanceof ViewGroup) {
                clearAll((ViewGroup) viewGroup.getChildAt(i));
            }
            if (viewGroup.getChildAt(i) instanceof CheckBox) {
                ((CheckBox) viewGroup.getChildAt(i)).setChecked(false);
            }
        }
    }

    @Override
    public void onCancel() {
        changeFragmentVisibleStatus(false);
    }

    @Override
    public void onConfirm(BaseWhole successCaseWhole) {
        if (successCaseWhole instanceof ResumeWhole) {
            this.mResumeWhole = (ResumeWhole) successCaseWhole;
        } else {
            this.mSuccessCaseWhole = (SuccessCaseWhole) successCaseWhole;
        }
        changeFragmentVisibleStatus(false);
        //重置
        resetPageIndex();
        //刷新数据
        resumeRequest();
        setActionTitle();
    }

    private void setActionTitle() {
        if (mSuccessCaseWhole.getOrderType() == 0) {
            mOrder.setText("排序");
        }else{
            mOrder.setText(mSuccessCaseWhole.getOrderType() == 1 ? "薪资" : "时间");
        }
        if (mResumeWhole.getIndustrys().size()<=0) {
            mJobtitle.setText("行业");
        }else{
            mJobtitle.setText(joinSelect(mResumeWhole.getIndustrysTip()));
        }

        if (mSuccessCaseWhole.getWorkLocation().size() <= 0) {
            mWorklocation.setText("地点");
        }else{
            mWorklocation.setText(joinSelect(mSuccessCaseWhole.getWorkLocations()));
        }

    }
    private String joinSelect(List<String> data) {
        StringBuffer sb = new StringBuffer();
        if (data == null) {
            return sb.toString();
        }
        for (String s : data) {
            sb.append(s + "+");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();

    }



}
