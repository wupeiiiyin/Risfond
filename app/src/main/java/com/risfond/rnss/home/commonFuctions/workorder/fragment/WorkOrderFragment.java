package com.risfond.rnss.home.commonFuctions.workorder.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.risfond.rnss.R;
import com.risfond.rnss.base.BaseFragment;
import com.risfond.rnss.callback.ResponseCallBack;
import com.risfond.rnss.common.constant.URLConstant;
import com.risfond.rnss.common.utils.DialogUtil;
import com.risfond.rnss.common.utils.EventBusUtil;
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.common.utils.net.IHttpRequest;
import com.risfond.rnss.entry.WorkOrder;
import com.risfond.rnss.entry.WorkOrderList;
import com.risfond.rnss.entry.eventBusVo.WorkOrderEventBus;
import com.risfond.rnss.home.commonFuctions.workorder.activity.WorkOrderDetailActivity;
import com.risfond.rnss.home.commonFuctions.workorder.adapter.WorkOrderAdapter;
import com.risfond.rnss.home.commonFuctions.workorder.modelimpl.WorkOrderImpl;
import com.risfond.rnss.widget.RecycleViewDivider;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * 工单
 * Created by Abbott on 2017/10/19.
 */

public class WorkOrderFragment extends BaseFragment implements ResponseCallBack, PullLoadMoreRecyclerView.PullLoadMoreListener {

    @BindView(R.id.refresh_view_order)
    PullLoadMoreRecyclerView refreshViewOrder;

    private Context context;
    private WorkOrderAdapter adapter;
    private String Status;//1:未接收,2:已接收
    private Map<String, String> request = new HashMap<>();
    private List<WorkOrderList> workOrderLists = new ArrayList<>();
    private List<WorkOrderList> list = new ArrayList<>();
    private List<WorkOrderList> tempList = new ArrayList<>();
    private boolean isHasRequest, isPrepare;
    private IHttpRequest iWorkOrder;
    private int PageIndex = 1;
    private boolean isEventBusRefresh;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_work_order;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = getActivity();
        Status = getArguments().getString("Status");
        iWorkOrder = new WorkOrderImpl();

        adapter = new WorkOrderAdapter(context, list);
        refreshViewOrder.setLinearLayout();
        refreshViewOrder.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,
                20, ContextCompat.getColor(context, R.color.color_home_back)));
        refreshViewOrder.setColorSchemeResources(R.color.color_blue);
        refreshViewOrder.setOnPullLoadMoreListener(this);
        refreshViewOrder.setAdapter(adapter);

        isPrepare = true;
        onItemClick();
        lazyLoad();
        EventBusUtil.registerEventBus(this);
    }

    private void requestService() {
        if (!refreshViewOrder.isRefresh() && !refreshViewOrder.isLoadMore() && !isEventBusRefresh) {
            DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        }
        request.put("StaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("Status", Status);
        request.put("PageIndex", String.valueOf(PageIndex));
        iWorkOrder.requestService(SPUtil.loadToken(context), request, URLConstant.URL_WORK_ORDER_LIST, this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new WorkOrderAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                WorkOrderDetailActivity.StartAction(context, list.get(position).getId());
            }
        });

    }

    @Override
    protected void lazyLoad() {
        if (!isHasRequest && isPrepare && isVisible) {
            requestService();
        }
    }

    private void updateUI(final Object obj) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (refreshViewOrder.isRefresh()) {
                    workOrderLists.clear();
                    list.clear();
                    tempList.clear();
                    refreshViewOrder.setPullLoadMoreCompleted();
                } else if (refreshViewOrder.isLoadMore()) {
                    refreshViewOrder.setPullLoadMoreCompleted();
                } else if (isEventBusRefresh) {
                    isEventBusRefresh = false;
                } else {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                if (obj instanceof WorkOrder) {
                    isHasRequest = true;
                    list = (((WorkOrder) obj)).getWorkOrderList();
                    workOrderLists.removeAll(tempList);
                    if (list != null) {
                        if (list.size() == 15) {
                            PageIndex++;
                            workOrderLists.addAll(list);
                        } else {
                            tempList = list;
                            workOrderLists.addAll(tempList);
                        }
                    } else {
                        if (isEventBusRefresh) {
                            isEventBusRefresh = false;
                        } else {
                            if (Status.equals("1")) {
                                ToastUtil.showShort(context, "暂无未接收工单");
                            } else {
                                ToastUtil.showShort(context, "暂无已接收工单");
                            }
                        }

                    }
                    adapter.updateData(workOrderLists);
                } else {
                    ToastUtil.showShort(context, obj.toString());
                }
            }
        });
    }

    @Override
    public void onSuccess(Object obj) {
        updateUI(obj);
    }

    @Override
    public void onFailed(String str) {
        updateUI(str);
    }

    @Override
    public void onError(String str) {
        updateUI(str);
    }

    @Override
    public void onRefresh() {
        PageIndex = 1;
        workOrderLists.clear();
        tempList.clear();
        requestService();
    }

    @Override
    public void onLoadMore() {
        requestService();
    }

    @Subscribe
    public void onEventBus(WorkOrderEventBus eventBus) {
        if (eventBus.getType().equals("workOrder")) {
            isEventBusRefresh = true;
            requestService();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBusUtil.unRegisterEventBus(this);
    }
}
