package com.risfond.rnss.home.position.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.Recommend;
import com.risfond.rnss.entry.eventBusVo.ProcessEventBus;
import com.risfond.rnss.home.position.activity.RecommendListActivity;
import com.risfond.rnss.home.position.adapter.RecommendManagementAdapter;
import com.risfond.rnss.home.position.modelImpl.RecommendManagementImpl;
import com.risfond.rnss.home.position.modelInterface.IRecommendManagement;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/7/13.
 * 推荐管理 页面
 */

public class RecommendManagementFragment extends BaseFragment implements ResponseCallBack {

    @BindView(R.id.rv_recommend_management)
    RecyclerView rvRecommendManagement;

    private Context context;
    private RecommendManagementAdapter adapter;
    private List<Recommend> recommends;
    private IRecommendManagement iRecommendManagement;
    private String jobid;
    private Map<String, String> request = new HashMap<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend_management;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        recommends = new ArrayList<>();
        iRecommendManagement = new RecommendManagementImpl();
        jobid = getArguments().getString("jobid");

        adapter = new RecommendManagementAdapter(context, recommends);

        rvRecommendManagement.setLayoutManager(new LinearLayoutManager(context));
        rvRecommendManagement.setAdapter(adapter);
        onItemClick();

        requestServer();

        EventBusUtil.registerEventBus(this);
    }

    private void requestServer() {
        request.put("jobid", jobid);
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        iRecommendManagement.recommendManagementRequest(SPUtil.loadToken(context), request, URLConstant.URL_JOB_RECOMMEND, this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new RecommendManagementAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RecommendListActivity.startAction(context, jobid, String.valueOf(recommends.get(position).getRecomtype()));
            }
        });
    }

    @Subscribe
    public void onEventBus(ProcessEventBus eventBus) {
        if (EventBusUtil.isRegisterEventBus(this)){
            if (eventBus.getType().equals("liuCheng")){
                requestServer();
            }
        }
    }

    @Override
    protected void lazyLoad() {
        if (recommends != null && recommends.size() == 0) {
            DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
            requestServer();
        }
    }

    @Override
    public void onSuccess(final Object obj) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
                if (obj instanceof ArrayList) {
                    recommends = (List<Recommend>) obj;
                    adapter.updateData(recommends);
                } else {
                    ToastUtil.showShort(context, obj.toString());
                }
            }
        });
    }

    @Override
    public void onFailed(String str) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }

    @Override
    public void onError(String str) {
        ((Activity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DialogUtil.getInstance().closeLoadingDialog();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegisterEventBus(this);
    }
}
