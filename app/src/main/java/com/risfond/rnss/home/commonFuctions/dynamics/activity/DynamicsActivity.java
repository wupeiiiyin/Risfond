package com.risfond.rnss.home.commonFuctions.dynamics.activity;

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
import com.risfond.rnss.common.utils.SPUtil;
import com.risfond.rnss.common.utils.TimeUtil;
import com.risfond.rnss.common.utils.ToastUtil;
import com.risfond.rnss.contacts.activity.ContactsInfoActivity;
import com.risfond.rnss.entry.Dynamics;
import com.risfond.rnss.entry.DynamicsResponse;
import com.risfond.rnss.home.commonFuctions.dynamics.adapter.DynamicsAdapter;
import com.risfond.rnss.home.commonFuctions.dynamics.modelimpl.DynamicsImpl;
import com.risfond.rnss.home.commonFuctions.dynamics.modelimpl.UpdateImpl;
import com.risfond.rnss.home.commonFuctions.dynamics.modelinterface.IDynamics;
import com.risfond.rnss.home.commonFuctions.dynamics.modelinterface.IUpdate;
import com.risfond.rnss.widget.RecycleViewDivider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 动态页面
 */
public class DynamicsActivity extends BaseActivity implements ResponseCallBack {

    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.ll_title_edit)
    LinearLayout llTitleEdit;
    @BindView(R.id.rv_dynamic)
    RecyclerView rvDynamic;

    private Context context;
    private DynamicsAdapter adapter;
    private boolean isManager;

    private Map<String, String> request = new HashMap<>();
    private IDynamics iDynamics;
    private int pageindex = 1;
    private DynamicsResponse response;
    private List<Dynamics> customerSearches = new ArrayList<>();
    private List<Dynamics> temp = new ArrayList<>();
    private boolean isLoadMore;
    private boolean isCanLoadMore = true;
    private boolean isLoadingMore = false;
    private String operateType = "1";//1:获取动态，2:更新动态，3:删除动态
    private IUpdate iUpdate;
    private int operatePosition;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_dynamics;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        context = DynamicsActivity.this;
        iDynamics = new DynamicsImpl();
        iUpdate = new UpdateImpl();

        isManager = getIntent().getBooleanExtra("isManager", false);

        if (isManager) {
            tvTitle.setText("我的动态");
            llTitleSearch.setVisibility(View.GONE);
            llTitleEdit.setVisibility(View.GONE);
        } else {
            tvTitle.setText("动态");
        }

        adapter = new DynamicsAdapter(context, customerSearches, isManager);

        rvDynamic.setLayoutManager(new LinearLayoutManager(context));
        rvDynamic.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 20, ContextCompat.getColor(context, R.color.color_home_back)));
        rvDynamic.setAdapter(adapter);

        onItemClick();
        reFresh();
        dynamicsRequest();

    }


    private void dynamicsRequest() {
        request.clear();
        operateType = "1";
        if (!isLoadMore) {
            DialogUtil.getInstance().showLoadingDialog(context, "请求中...");
        }
        request.put("Keywords", "");
        if (isManager) {
            request.put("Staffid", String.valueOf(SPUtil.loadId(context)));
        } else {
            request.put("Staffid", "0");
        }
        request.put("Categroy", "1");
        request.put("UserStaffId", String.valueOf(SPUtil.loadId(context)));
        request.put("Page", String.valueOf(pageindex));
        request.put("PageSize", "15");
        iDynamics.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_GET_PAGE_INTERACTION_MANAGE, this);
    }

    private void operateRequest() {
        request.clear();
        if (operateType.equals("2")) {
            DialogUtil.getInstance().showLoadingDialog(context, "更新中...");
            request.put("type", "2");
        } else if (operateType.equals("3")) {
            DialogUtil.getInstance().showLoadingDialog(context, "删除中...");
            request.put("type", "1");
        }
        request.put("id", customerSearches.get(operatePosition).getId());
        request.put("staffId", String.valueOf(SPUtil.loadId(context)));
        iUpdate.positionSearchRequest(SPUtil.loadToken(context), request, URLConstant.URL_INTERACTION, this);
    }

    private void onItemClick() {
        adapter.setOnItemClickListener(new DynamicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ContactsInfoActivity.startAction(context, String.valueOf(customerSearches.get(position).getStaffId()));
            }

            @Override
            public void onUpdateClick(final int position) {
                DialogUtil.getInstance().showConfigDialog(context, "确认更新吗？", "确定", "取消", new DialogUtil.PressCallBack() {
                    @Override
                    public void onPressButton(int buttonIndex) {
                        if (buttonIndex == DialogUtil.BUTTON_OK) {
                            operateType = "2";
                            operatePosition = position;
                            operateRequest();
                        }
                    }
                });
            }

            @Override
            public void onStartClick(int position) {
                /*String state = customerSearches.get(position).getState();
                if (state.equals("1")) {
                    state = "2";
                } else {
                    state = "1";
                }
                customerSearches.get(position).setState(state);
                adapter.notifyItemChanged(position);*/
            }

            @Override
            public void onDeleteClick(final int position) {
                DialogUtil.getInstance().showConfigDialog(context, "确认删除吗？", "确定", "取消", new DialogUtil.PressCallBack() {
                    @Override
                    public void onPressButton(int buttonIndex) {
                        if (buttonIndex == DialogUtil.BUTTON_OK) {
                            operateType = "3";
                            operatePosition = position;
                            operateRequest();
                        }
                    }
                });
            }
        });
    }

    private void reFresh() {
        rvDynamic.addOnScrollListener(new RecyclerView.OnScrollListener() {

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
                            dynamicsRequest();
                        }
                    }
                }
            }
        });
    }

    @OnClick({R.id.ll_title_search, R.id.ll_title_edit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_title_search:
                DynamicsSearchActivity.StartAction(this);
                break;
            case R.id.ll_title_edit:
                Intent intent = new Intent(context, PublishingDynamicsActivity.class);
                startActivityForResult(intent, 999);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            customerSearches.clear();
            temp.clear();
            pageindex = 1;
            dynamicsRequest();
        }

    }

    public static void StartAction(Context context, boolean isManager) {
        Intent intent = new Intent(context, DynamicsActivity.class);
        intent.putExtra("isManager", isManager);
        context.startActivity(intent);
    }

    @Override
    public void onSuccess(final Object obj) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (operateType.equals("1")) {
                    if (!isLoadMore) {
                        DialogUtil.getInstance().closeLoadingDialog();
                    }
                    if (obj instanceof DynamicsResponse) {
                        response = (DynamicsResponse) obj;
                        if (response.getData().size() == 15) {
                            pageindex++;
                            isCanLoadMore = true;
                            if (temp.size() > 0) {
                                customerSearches.removeAll(temp);
                                temp.clear();
                            }
                            customerSearches.addAll(response.getData());
                        } else {
                            isCanLoadMore = false;
                            if (temp.size() > 0) {
                                customerSearches.removeAll(temp);
                                temp.clear();
                            }
                            temp = response.getData();
                            customerSearches.addAll(temp);
                        }
                        if (adapter != null) {
                            adapter.updateData(customerSearches);
                        }
                    }
                    if (isLoadMore) {
                        isLoadingMore = false;
                    }
                } else {
                    DialogUtil.getInstance().closeLoadingDialog();
                    if (obj instanceof String) {
                        String data = obj.toString();
                        if (data.equals("success")) {
                            if (operateType.equals("2")) {
                                ToastUtil.showShort(context, "更新成功");
                                customerSearches.clear();
                                temp.clear();
                                pageindex = 1;
                                isLoadMore = true;
                                dynamicsRequest();
                            } else if (operateType.equals("3")) {
                                customerSearches.remove(operatePosition);
                                adapter.notifyItemRemoved(operatePosition);
                                adapter.notifyItemRangeChanged(operatePosition, customerSearches.size());
                            }
                        } else {
                            ToastUtil.showShort(context, data);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onFailed(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (!isLoadMore) {
                    DialogUtil.getInstance().closeLoadingDialog();
                }
                ToastUtil.showShort(context, str);
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
                ToastUtil.showShort(context, str);
            }
        });
    }
}
