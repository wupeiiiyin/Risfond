package com.risfond.rnss.home.position.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.entry.RecommendProcess;
import com.risfond.rnss.entry.UnReadMessageCountEventBus;
import com.risfond.rnss.entry.eventBusVo.ProcessEventBus;
import com.risfond.rnss.home.position.adapter.RecommendProcessAdapter;
import com.risfond.rnss.home.position.modelImpl.RecommendProcessImpl;
import com.risfond.rnss.home.position.modelInterface.IRecommendProcess;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by Abbott on 2017/7/13.
 * 流程管理页面
 */

public class RecommendProcessFragment extends BaseFragment implements ResponseCallBack {

    @BindView(R.id.rv_recommend_process)
    RecyclerView rvRecommendProcess;

    private Context context;
    private RecommendProcessAdapter adapter;
    private List<RecommendProcess> data;
    private IRecommendProcess iRecommendProcess;
    private String Recomid;
    private Map<String, String> request = new HashMap<>();

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_recommend_process;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getContext();
        data = new ArrayList<>();
        iRecommendProcess = new RecommendProcessImpl();
        Recomid = getArguments().getString("Recomid");

        rvRecommendProcess.setLayoutManager(new LinearLayoutManager(context));

        requestServer();

        EventBus.getDefault().register(this);//订阅
    }

    private void requestServer() {
        request.put("Recomid", Recomid);
        request.put("staffid", String.valueOf(SPUtil.loadId(context)));
        iRecommendProcess.recommendProcessRequest(SPUtil.loadToken(context), request, URLConstant.URL_JOB_RECOMMEND_STEP, this);
    }

    @Override
    protected void lazyLoad() {
        if (data != null && data.size() == 0) {
            DialogUtil.getInstance().showLoadingDialog(context, "加载中...");
            requestServer();
        }
    }

    @Subscribe
    public void onEventBus(ProcessEventBus eventBus) {
        if ("liuCheng".equals(eventBus.getType())) {
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
                    data = (List<RecommendProcess>) obj;
                    adapter = new RecommendProcessAdapter(context, data);
                    rvRecommendProcess.setAdapter(adapter);
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
        EventBus.getDefault().unregister(this);//解除订阅
    }
}
