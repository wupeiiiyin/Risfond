package com.risfond.rnss.home.resume.window;


import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.risfond.rnss.R;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.manager.BasePopupWindow;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.net.UtilHelper;
import com.risfond.rnss.entry.PositionSearch;
import com.risfond.rnss.entry.PositionSearchResponse;
import com.risfond.rnss.entry.ResumeSearchSelectResponse;
import com.risfond.rnss.home.position.adapter.PositionSearchV2Adapter;
import com.risfond.rnss.home.position.modelImpl.PositionSearchImpl;
import com.risfond.rnss.home.position.modelInterface.IPositionSearch;
import com.risfond.rnss.home.resume.activity.ResumeQuickSearchActivity;
import com.risfond.rnss.home.resume.activity.ResumeSearchActivity;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author @zhangchuan622@gmail.com
 * @version 1.0
 * @create 2017/11/29
 * @desc 快捷搜索>>按已有的职位搜索
 */
public class ResumeQuickSearchPopupWindow extends BasePopupWindow implements ResponseCallBack, View.OnClickListener {
    /**
     * 请求数据
     */
    private Map<String, String> request;
    /**
     * 分页-页数
     */
    private int pageindex = 1;

    private RecyclerView rvResumePop;
    private LinearLayout llEmptySearchPoP;
    private PositionSearchV2Adapter mAdapter;
    private List<PositionSearch> positionSearches;

    private IPositionSearch iPositionSearch;

    public ResumeQuickSearchPopupWindow(Context context, Activity activity, int layoutId) {
        super(context, activity, layoutId);
        getPopupWindow().setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        getPopupWindow().setBackgroundDrawable(dw);
    }

    public static ResumeQuickSearchPopupWindow getInstance(Context context, Activity activity, int layoutId) {
        return new ResumeQuickSearchPopupWindow(context, activity, layoutId);
    }

    @Override
    public void initLayoutView(View customView) {
        init();
        rvResumePop = (RecyclerView) customView.findViewById(R.id.rv_resume_pop_list);
        llEmptySearchPoP = (LinearLayout) customView.findViewById(R.id.ll_empty_search_pop);//暂无职位


        rvResumePop.setLayoutManager(new LinearLayoutManager(getContext()));
        //        rvResumePop.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        //控制分割线的宽度 参数1：上下文，参数2：方向，参数3：分割线高度，参数4：颜色
        rvResumePop.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL, 2, ContextCompat.getColor(getContext(), R.color.color_home_stoke_small)));
        rvResumePop.setAdapter(mAdapter);

        customView.findViewById(R.id.tv_search_quick).setOnClickListener(this);
        positionRequest();
    }

    private void init() {
        iPositionSearch = new PositionSearchImpl();
        request = new HashMap<>();
        positionSearches  = new ArrayList<>();
        mAdapter = new PositionSearchV2Adapter();//弹框的适配器
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ResumeSearchSelectResponse.DataBean dataBean = new ResumeSearchSelectResponse.DataBean();
                PositionSearch positionSearch = mAdapter.getData().get(position);
               /*
                try {
                    dataBean.setKeyword(positionSearch.getTitle());
                    if (positionSearch.getSalary()!=null) {
                        String[] split = positionSearch.getSalary().split("-");
                        dataBean.setSalaryfrom(Integer.parseInt(split[0]));
                        dataBean.setSalaryto(Integer.parseInt(split[1].substring(0, split[1].length() - 1)));
                    }
                    if (positionSearch.getLocations() != null) {
                        ArrayList<String> worklocation = new ArrayList<>();
                        worklocation.add(positionSearch.getLocations());
                        dataBean.setWorklocations(worklocation);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    UtilHelper.outLog(TAG, e.getMessage());
                }*/
                ((ResumeQuickSearchActivity) getActivity()).shutdownActivityByJobSearch(positionSearch);
            }
        });
    }

    /**
     * 弹出框列表数据加载
     */
    private void positionRequest() {
        request.clear();
        request.put("keyword", "");
        request.put("staffid", String.valueOf(SPUtil.loadId(getContext())));
        request.put("pageindex", String.valueOf("1"));
        iPositionSearch.positionSearchRequest(SPUtil.loadToken(getContext()), request, URLConstant.URL_JOB_SEARCH, this);
    }

    @Override
    public void onSuccess(Object obj) {
        PositionSearchResponse response = (PositionSearchResponse) obj;
        positionSearches = response.getData();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.addData(positionSearches);
                rvResumePop.setVisibility(mAdapter.getData().size() > 0 ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void onFailed(String str) {
        UtilHelper.outLog("ResumeQuickSearchPopupWindow", str);
    }

    @Override
    public void onError(String str) {
        UtilHelper.outLog("ResumeQuickSearchPopupWindow", str);
    }

    @Override
    public void onClick(View v) {
        getPopupWindow().dismiss();
    }
}
